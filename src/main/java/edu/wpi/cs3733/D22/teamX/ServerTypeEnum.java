package edu.wpi.cs3733.D22.teamX;

public enum ServerTypeEnum
{
    INSTANCE;

    // example of how attributes are added to the Enum
    int value;
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
