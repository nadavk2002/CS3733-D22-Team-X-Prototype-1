package edu.wpi.cs3733.D22.teamX;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import java.awt.*;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class VoiceSearch {
  public static void main(String args[]) throws Exception {
    streamingMicRecognize();
  }

  /** Performs microphone streaming speech recognition with a duration of 1 minute. */
  public static String streamingMicRecognize() throws Exception {
    final String[] transcript = {""};

    // code is based on sample from Google Cloud
    // https://cloud.google.com/speech-to-text/docs/streaming-recognize

    String jsonPath = "/edu/wpi/cs3733/D22/teamX/team-x-347714-448a74675b18.json";
    GoogleCredentials credentials =
        GoogleCredentials.fromStream(App.class.getResourceAsStream(jsonPath))
            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

    ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
    try {
      // Instantiates a client
      SpeechSettings speechSettings =
          SpeechSettings.newBuilder()
              .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
              .build();
      SpeechClient speechClient = SpeechClient.create(speechSettings);

      responseObserver =
          new ResponseObserver<StreamingRecognizeResponse>() {
            ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

            public void onStart(StreamController controller) {}

            public void onResponse(StreamingRecognizeResponse response) {
              responses.add(response);
            }

            public void onComplete() {
              StringBuilder stringBuilder = new StringBuilder();
              for (StreamingRecognizeResponse response : responses) {
                StreamingRecognitionResult result = response.getResultsList().get(0);
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                // System.out.printf("Transcript : %s\n", alternative.getTranscript());
                stringBuilder.append(alternative.getTranscript());
              }
              transcript[0] = new String(stringBuilder);
            }

            public void onError(Throwable t) {
              System.out.println(t);
            }
          };

      ClientStream<StreamingRecognizeRequest> clientStream =
          speechClient.streamingRecognizeCallable().splitCall(responseObserver);

      RecognitionConfig recognitionConfig =
          RecognitionConfig.newBuilder()
              .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
              .setLanguageCode("en-US")
              .setSampleRateHertz(16000)
              .build();

      StreamingRecognitionConfig streamingRecognitionConfig =
          StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

      StreamingRecognizeRequest request =
          StreamingRecognizeRequest.newBuilder()
              .setStreamingConfig(streamingRecognitionConfig)
              .build(); // The first request in a streaming call has to be a config

      clientStream.send(request);
      // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
      // bigEndian: false
      AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
      DataLine.Info targetInfo =
          new DataLine.Info(
              TargetDataLine.class,
              audioFormat); // Set the system information to read from the microphone audio stream

      if (!AudioSystem.isLineSupported(targetInfo)) {
        System.out.println("Microphone not supported");
        System.exit(0);
      }
      // Target data line captures the audio stream the microphone produces.
      TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
      targetDataLine.open(audioFormat);
      targetDataLine.start();
      System.out.println("Start speaking!");
      long startTime = System.currentTimeMillis();
      // Audio Input Stream
      AudioInputStream audio = new AudioInputStream(targetDataLine);
      // searchBox.setPromptText("Listening...");
      while (true) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime);
        byte[] data = new byte[6400];
        audio.read(data);
        if (estimatedTime > 5000) { // 5 seconds max
          System.out.println("Stop speaking.");
          targetDataLine.stop();
          targetDataLine.close();
          break;
        }
        request =
            StreamingRecognizeRequest.newBuilder()
                .setAudioContent(ByteString.copyFrom(data))
                .build();
        clientStream.send(request);
      }
      speechClient.shutdown();
    } catch (Exception e) {
      System.out.println(e);
    }
    // searchBox.setPromptText("Search here.");
    responseObserver.onComplete();
    System.out.println("Transcript: " + transcript[0]);
    return transcript[0];
  }
}
