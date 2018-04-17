package com.example.kevin.firstapp;

public class NorthMonitor {

    private boolean north = false;

    public synchronized void waitForNorth() {
        while(north != true) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void setNorth(boolean isNorth){
        north = isNorth;
        notifyAll();
    }

}
