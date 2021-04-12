package ro.pub.cs.systems.eim.practicaltest01.service;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private double sum;
    private double minus;

    public ProcessingThread(Context context, String firstNumber, String secondNumber) {
        this.context = context;

        sum = Integer.parseInt(firstNumber) + Integer.parseInt(secondNumber);
        minus =  Integer.parseInt(firstNumber) - Integer.parseInt(secondNumber);
    }

    @Override
    public void run() {
        Log.d("service", "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.plus");
        intent.putExtra("service_extra",
                new Date(System.currentTimeMillis()) + " " + sum);
        context.sendBroadcast(intent);

        sleep();

        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.minus");
        intent.putExtra("service_extra",
                new Date(System.currentTimeMillis()) + " " + minus);
        context.sendBroadcast(intent);
        Log.d("service", "Thread has stopped!");
    }


    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}