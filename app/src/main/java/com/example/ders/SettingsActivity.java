package com.example.ders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView userName;
    RadioGroup radioGroup;
    RadioButton radioMale;
    RadioButton radioFemale;
    EditText age;
    EditText height;
    EditText weight;
    Spinner languageSpinner;
    Spinner themeSpinner;

    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        userName = findViewById(R.id.userNameDisplay);
        radioGroup = findViewById(R.id.radioGroup);
        radioFemale = findViewById(R.id.genderFemale);
        radioMale = findViewById(R.id.genderMale);
        age = findViewById(R.id.ageET);
        height = findViewById(R.id.heightET);
        weight = findViewById(R.id.weightET);
        languageSpinner = findViewById(R.id.languageSpinner);
        themeSpinner = findViewById(R.id.themeSpinner);

        intent = getIntent();
        String prefsName = intent.getStringExtra("prefsName");
        sharedPreferences = getSharedPreferences(prefsName, Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(this, R.array.theme_array, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(themeAdapter);

        String userNameS = sharedPreferences.getString("userName", "");
        userName.setText(userNameS);

        int genderId = sharedPreferences.getInt("gender", -1);
        if (genderId == 0) {
            radioMale.toggle();
        }
        else if (genderId == 1) {
            radioFemale.toggle();
        }

        String ageS = sharedPreferences.getString("age", "");
        if (ageS != "") age.setText(ageS);

        String heightS = sharedPreferences.getString("height", "");
        if (heightS != "") height.setText(heightS);

        String weightS = sharedPreferences.getString("weight", "");
        if (weightS != "") weight.setText(weightS);

        int languageId = sharedPreferences.getInt("language", 0);
        languageSpinner.setSelection(languageId);

        int themeId = sharedPreferences.getInt("theme", 0);
        themeSpinner.setSelection(themeId);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkedId == R.id.genderFemale) {
                    editor.putInt("gender", 1);
                    editor.commit();
                }
                else if (checkedId == R.id.genderMale) {
                    editor.putInt("gender", 0);
                    editor.commit();
                }
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("language", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("theme", position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (item.getItemId()) {
            case R.id.action_save:
                editor.putString("age", age.getText().toString());
                editor.putString("height", height.getText().toString());
                editor.putString("weight", weight.getText().toString());
                editor.commit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
