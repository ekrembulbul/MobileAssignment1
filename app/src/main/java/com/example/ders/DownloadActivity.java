package com.example.ders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DownloadActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        progressBar = findViewById(R.id.download_progressbar);
        progressBar.setProgress(0);

        button = findViewById(R.id.download_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
    }

    public class BackgroundTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Random random = new Random();
            int sum = 0;
            while (sum < 100) {
                try {
                    TimeUnit.SECOND.sleep(1);
                }
                catch (InterruptedException e) {
                }
                int randInt = random.nextInt(10);
                sum += randInt;
                publishProgress(sum);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            setProgressPercent(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            button.setEnabled(true);
            result();
        }
    }

    private void setProgressPercent(Integer value) {
        if (value < 100) {
            progressBar.setProgress(value, true);
        }
        else {
            progressBar.setProgress(100, true);
        }
    }

    private void result() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new AlertDialog.Builder(DownloadActivity.this)
                .setTitle("Info")
                .setMessage("Download complated")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
