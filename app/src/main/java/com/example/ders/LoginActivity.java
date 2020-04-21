package com.example.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    Button singin;
    int loginErrorCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        final UsersData usersData = UsersData.getInstance();

        name = findViewById(R.id.name_input);
        password = findViewById(R.id.password_input);
        singin = findViewById(R.id.button);
        loginErrorCount = 0;

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameS = name.getText().toString();
                String passwordS = password.getText().toString();
                boolean isCorrect = false;
                for (User u: usersData.users) {
                    if (nameS.compareTo(u.name) == 0 && passwordS.compareTo(u.password) == 0) {
                        isCorrect = true;
                    }
                }
                if (isCorrect) {
                    String prefsName = nameS + "Prefs";
                    SharedPreferences sharedPreferences = getSharedPreferences(prefsName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userName", nameS);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.putExtra("prefsName", prefsName);
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect name or password!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    password.setText("");
                    if (++loginErrorCount == 3) {
                        Toast.makeText(LoginActivity.this, "Application closed!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }
}
