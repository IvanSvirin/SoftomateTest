package com.example.ivansv.softomatetest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by ivansv on 30.03.2016.
 */
@SuppressLint("ParcelCreator")
public class DataResultReceiver extends ResultReceiver {
    public static final String RECEIVER = "receiver";
    public static final int RESULT = 1;

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle data);
    }

    private Receiver receiver;

    public DataResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}