package com.example.ders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    BroadcastReceiver _br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button userList = findViewById(R.id.user_list);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        Button sendEmail = findViewById(R.id.send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SendEmailActivity.class);
                startActivity(intent);
            }
        });

        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        Button note = findViewById(R.id.note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        Button sensor = findViewById(R.id.sensor);
        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MenuActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS}, 100);
        } else {
            Toast.makeText(this, "Listening!", Toast.LENGTH_SHORT).show();
            listen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    boolean allGranted = true;
                    for (int i = 0; i < grantResults.length; ++i) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            allGranted = false;
                        }
                    }
                    if (allGranted) {
                        Toast.makeText(this, "Listening!", Toast.LENGTH_SHORT).show();
                        listen();
                    }
                    else {
                        Toast.makeText(this, "Not listening!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Not listening!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void listen() {
        _br = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        this.registerReceiver(_br, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(_br);
    }
}
