package edu.wpi.cs3733.D22.teamX.controllers;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamX.App;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javax.sound.sampled.*;

public class AppController implements Initializable {
  @FXML BorderPane landingPage;
  @FXML TextField searchBox;
  @FXML
  private JFXButton ReqLang,
      ReqLaundry,
      ReqMedicineDelivery,
      ReqInTransport,
      mealReq,
      LabRequest,
      equipmentRequest,
      graphicalMapEditor,
      EquipReqTable,
      ReqJanitor,
      GiftDelivery,
      ReqSharps,
      ReqMaintenance;
  @FXML private ToggleButton nameToggle;
  @FXML
  private Label mainTitle,
      nameTextOne,
      nameTextTwo,
      nameTextThree,
      nameTextFour,
      nameTextFive,
      nameTextSix,
      nameTextSeven,
      nameTextEight,
      nameTextNine,
      nameTextTen,
      nameTextEleven;
  ObservableList<Button> buttonList = FXCollections.observableArrayList();

  @FXML private Button voice;

  @FXML
  void nameToggleButton(ActionEvent event) {
    if (!nameTextOne.isVisible()) {
      nameTextOne.setVisible(true);
      nameTextTwo.setVisible(true);
      nameTextThree.setVisible(true);
      nameTextFour.setVisible(true);
      nameTextFive.setVisible(true);
      nameTextSix.setVisible(true);
      nameTextSeven.setVisible(true);
      nameTextEight.setVisible(true);
      nameTextNine.setVisible(true);
      nameTextTen.setVisible(true);
      nameTextEleven.setVisible(true);
    } else {
      nameTextOne.setVisible(false);
      nameTextTwo.setVisible(false);
      nameTextThree.setVisible(false);
      nameTextFour.setVisible(false);
      nameTextFive.setVisible(false);
      nameTextSix.setVisible(false);
      nameTextSeven.setVisible(false);
      nameTextEight.setVisible(false);
      nameTextNine.setVisible(false);
      nameTextTen.setVisible(false);
      nameTextEleven.setVisible(false);
    }
  }

  void searchButtons() {
    FilteredList<Button> filteredButtons = new FilteredList<>(buttonList, b -> true);
    searchBox
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredButtons.setPredicate(
                  button -> {
                    if (newValue == null || newValue.isEmpty()) {
                      button.setVisible(true);
                      return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (button.getText().toLowerCase().contains(lowerCaseFilter)) {
                      button.setVisible(true);
                      searchBox.setOnKeyPressed(
                          event -> {
                            if (event.getCode() == KeyCode.ENTER) {
                              button.fire();
                            }
                          });
                      return true;
                    } else {
                      button.setVisible(false);

                      return false;
                    }
                  });
            });
  }

  @FXML
  void ReqLangButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqLang.fxml")));
    // Parent root =
    // FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/views/ReqLang.fxml"));
    // App.getPrimaryStage().getScene().setRoot(root);
  }

  @FXML
  void ReqLaundryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqLaundry.fxml")));
  }

  @FXML
  void ReqMedicineDeliveryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/Medicine_Delivery.fxml")));
  }

  @FXML
  void ReqJanitorButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/JanitorialRequest.fxml")));
  }

  @FXML
  void ReqInTransportButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ReqInTransport.fxml")));
  }

  @FXML
  void mealReqButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/mealRequest.fxml")));
  }

  @FXML
  void LabRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/LabRequest.fxml")));
  }

  @FXML
  void equipmentRequestButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/equipmentDelivery.fxml")));
  }

  //  @FXML
  //  void graphicalMapEditorButton() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GraphicalMapEditor.fxml")));
  //  }
  //
  //  @FXML
  //  void EquipReqTableButton() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //
  // getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/ServiceRequestTable.fxml")));
  //  }

  @FXML
  void GiftDeliveryButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/GiftDelivery.fxml")));
  }

  //  @FXML
  //  void ExitApplication() throws IOException {
  //    App.switchScene(
  //        FXMLLoader.load(
  //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/CSVFileSaver.fxml")));
  //    CSVFileSaverController.loaded = true;
  //    // Platform.exit();
  //  }

  public void ReqSharpsButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/SharpsDisposalRequest.fxml")));
  }

  public void ReqMaintenanceButton() throws IOException {
    App.switchScene(
        FXMLLoader.load(
            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/MaintenanceRequest.fxml")));
  }

  @FXML
  private void goToSecretPage() throws IOException {
    App.switchScene(
        FXMLLoader.load(getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/SecretPage.fxml")));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    buttonList.addAll(
        LabRequest,
        ReqInTransport,
        ReqLang,
        // EquipReqTable,
        // graphicalMapEditor,
        GiftDelivery,
        ReqLaundry,
        ReqMedicineDelivery,
        mealReq,
        ReqJanitor,
        equipmentRequest,
        ReqSharps,
        ReqMaintenance);
    //    searchBox.setPromptText("Search here.");
    //        firstRow.getChildren().addAll(equipmentRequest, graphicalMapEditor, EquipReqTable);
    //        secondRow.getChildren().addAll(ReqMedicineDelivery, LabRequest, ReqInTransport,
    // ReqLang);
    //        thirdRow.getChildren().addAll(ReqLaundry, ReqJanitor, GiftDelivery, mealReq);
    //        mainBox.getChildren().addAll(mainTitle, firstRow, secondRow, thirdRow);
    //        mainBox.setMargin(mainTitle, new Insets(40, 0, 40, 0));
    searchButtons();
    nameToggle.setOnAction(this::nameToggleButton);
    nameTextOne.setVisible(false);
    nameTextTwo.setVisible(false);
    nameTextThree.setVisible(false);
    nameTextFour.setVisible(false);
    nameTextFive.setVisible(false);
    nameTextSix.setVisible(false);
    nameTextSeven.setVisible(false);
    nameTextEight.setVisible(false);
    nameTextNine.setVisible(false);
    nameTextTen.setVisible(false);
    nameTextEleven.setVisible(false);
    voice.setOnMouseClicked(this::voiceSearchButtons);
  }

  @FXML
  private void voiceSearchButtons(MouseEvent mouseEvent) {
    searchBox.setPromptText("Listening...");
    try {
      String input = streamingMicRecognize();
      switch (input.toLowerCase()) {
        case "request equipment delivery":
          equipmentRequestButton();
          break;
        case "request janitorial services":
          ReqJanitorButton();
          break;
        case "request patient meal":
          mealReqButton();
          break;
        case "request medicine delivery":
          ReqMedicineDeliveryButton();
          break;
        case "request laundry service":
          ReqLaundryButton();
          break;
        case "gift delivery":
          GiftDeliveryButton();
          break;
        case "request language interpreter":
          ReqLangButton();
          break;
        case "request internal patient transportation":
          ReqInTransportButton();
          break;
        case "request lab service":
          LabRequestButton();
          break;
        case "request sharps disposal":
          ReqSharpsButton();
          break;
        case "request maintenance":
          ReqMaintenanceButton();
          break;
        case "secret page":
          goToSecretPage();
          break;
        default:
          if (input.equals("")) {
            searchBox.setPromptText("Error, please try again!");
          } else {
            searchBox.setText(input);
            searchBox.setPromptText("Search...");
          }
          //              toVoiceSearchPopup();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Performs microphone streaming speech recognition with a duration of 1 minute. */
  public String streamingMicRecognize() throws Exception {
    final String[] transcript = {""};

    // code is based on sample from Google Cloud
    // https://cloud.google.com/speech-to-text/docs/streaming-recognize

    String jsonPath = "team-x-347714-448a74675b18.json";
    GoogleCredentials credentials =
        GoogleCredentials.fromStream(new FileInputStream(jsonPath))
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
              searchBox.setPromptText("Search here.");
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
      while (true) {
        long estimatedTime = System.currentTimeMillis() - startTime;
        // System.out.println(estimatedTime);
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
    responseObserver.onComplete();
    System.out.println("Transcript: " + transcript[0]);
    return transcript[0];
  }

  //  public void toVoiceSearchPopup() throws IOException {
  //    FXMLLoader fxmlLoader =
  //        new FXMLLoader(
  //            getClass().getResource("/edu/wpi/cs3733/D22/teamX/views/VoiceSearchPopup.fxml"));
  //
  //    Parent root1 = (Parent) fxmlLoader.load();
  //    Stage stage = new Stage();
  //    stage.initModality(Modality.APPLICATION_MODAL);
  //    stage.initStyle(StageStyle.DECORATED);
  //    stage.setTitle("ERROR");
  //    stage.setScene(new Scene(root1));
  //    stage.show();
  //  }
}
