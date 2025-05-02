package com.heringer;

public interface IComplex {
    public double getImaginary();
    public double getMagnitude();
    public double getAngleDegrees();
    public double getAngleRadians();
    public double getReal();
    public void recToPolar();
    public void polarToRec();
    public void showRec();
    public void showPolar();
}
