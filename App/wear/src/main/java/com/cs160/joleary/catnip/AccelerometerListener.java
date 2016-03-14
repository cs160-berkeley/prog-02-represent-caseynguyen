package com.cs160.joleary.catnip;

/*
Credit:
elimiz
http://stackoverflow.com/questions/20839199/create-andriod-indefinitely-acceleromter-service-that-trigger-specific-events
 */

public interface AccelerometerListener {

    public void onAccelerationChanged(float x, float y, float z);

    public void onShake(float force);


}