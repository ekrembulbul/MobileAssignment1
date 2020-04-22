package com.example.ders;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    public static final String TAG = "MyReceiver";

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            int state = TelephonyManager.CALL_STATE_IDLE;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context, state, intent);
        }
        else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.d(TAG, "SMS received.\nTime: " + new Date().toString());

            StringBuilder sb = new StringBuilder();
            sb.append("SMS received.\n");
            sb.append("Time: " + new Date().toString() + "\n");
            sb.append("Message:\n");
            SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            for (SmsMessage message : smsMessages) {
                sb.append(message.getMessageBody() + "\n");
            }
            sb.append("\n");
            String log = sb.toString();

            write(context, log);
        }
    }

    public void onCallStateChanged(Context context, int state, Intent intent) {
        if (lastState == state) {
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                ringing(context);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                offhook(context);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    missCall(context);
                }
                else {
                    callEnded(context);
                }
                break;
        }
        lastState = state;
    }

    private void ringing(Context context) {
        Toast.makeText(context, "Ringing.\nTime: " + new Date().toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Ringing. Time: " + new Date().toString());

        StringBuilder sb = new StringBuilder();
        sb.append("Ringing.\n");
        sb.append("Time: " + new Date().toString() + "\n\n");
        String log = sb.toString();

        write(context, log);
    }

    private void offhook(Context context) {
        Toast.makeText(context, "Offhook.\nTime: " + new Date().toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Offhook. Time: " + new Date().toString());

        StringBuilder sb = new StringBuilder();
        sb.append("Offhook.\n");
        sb.append("Time: " + new Date().toString() + "\n\n");
        String log = sb.toString();

        write(context, log);
    }

    private void callEnded(Context context) {
        Toast.makeText(context, "Call ended.\nTime: " + new Date().toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Call ended. Time: " + new Date().toString());

        StringBuilder sb = new StringBuilder();
        sb.append("Call ended.\n");
        sb.append("Time: " + new Date().toString() + "\n\n");
        String log = sb.toString();

        write(context, log);
    }

    private void missCall(Context context) {
        Toast.makeText(context, "Miss call.\nTime: " + new Date().toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Miss call. Time: " + new Date().toString());

        StringBuilder sb = new StringBuilder();
        sb.append("Miss call.\n");
        sb.append("Time: " + new Date().toString() + "\n\n");
        String log = sb.toString();

        write(context, log);
    }

    private void write(Context context, String log) {
        try {
            File file = new File(context.getApplicationContext().getFilesDir(), "note");
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write(log.getBytes());
            stream.close();
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFound" + e.toString());
        }
        catch (IOException e) {
            Log.d(TAG, "IO" + e.toString());
        }
    }
}