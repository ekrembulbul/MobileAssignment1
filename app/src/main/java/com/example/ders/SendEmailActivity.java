package com.example.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;

public class SendEmailActivity extends AppCompatActivity {

    public static final int PICKFILE_RESULT_CODE = 1;

    EditText email;
    EditText cc;
    EditText bcc;
    EditText subject;
    EditText content;
    ImageButton attachment;
    TextView attachmentFileName;

    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Button send = findViewById(R.id.send_button);
        email = findViewById(R.id.emails_input);
        cc = findViewById(R.id.cc_input);
        bcc = findViewById(R.id.bcc_input);
        subject = findViewById(R.id.subject_input);
        content = findViewById(R.id.content_input);
        attachment = findViewById(R.id.attachIcon);
        attachmentFileName = findViewById(R.id.attachmentFileName);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] emailsS = email.getText().toString().split("\\r?\\n");
                String[] ccS = cc.getText().toString().split("\\r?\\n");
                String[] bccS = bcc.getText().toString().split("\\r?\\n");
                String subjectS = subject.getText().toString();
                String contentS = content.getText().toString();

                composeEmail(emailsS, ccS, bccS, subjectS, contentS, fileUri);
            }
        });

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICKFILE_RESULT_CODE) {
                fileUri = data.getData();
                File file = new File(fileUri.getPath());
                attachmentFileName.setText(file.getName());
            }
        }
    }

    public void composeEmail(String[] addresses, String[] cc, String[] bcc, String subject, String content, Uri attachment) {
        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse("mailto:"));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.putExtra(Intent.EXTRA_BCC, bcc);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        intent.setSelector(emailSelectorIntent);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
