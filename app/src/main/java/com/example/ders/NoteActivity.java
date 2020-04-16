package com.example.ders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NoteActivity extends AppCompatActivity {

    EditText editText;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        editText = findViewById(R.id.editText);
        file = new File(NoteActivity.this.getFilesDir(), "note");
        read();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                write();
                finish();
                return true;
            case R.id.action_trash:
                if (file.delete()) {
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void write() {
        String note = editText.getText().toString();

        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(note.getBytes());
            stream.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            Log.d("Note Activity", "FileNotFound" + e.toString());
        }
        catch (IOException e) {
            Log.d("Note Activity", "IO" + e.toString());
        }
    }

    private void read() {
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        try {
            FileInputStream stream = new FileInputStream(file);
            stream.read(bytes);
            stream.close();
        }
        catch (FileNotFoundException e) {
            Log.d("Note Activity", "FileNotFound" + e.toString());
        }
        catch (IOException e) {
            Log.d("Note Activity", "IO" + e.toString());
        }

        String contents = new String(bytes);
        editText.setText(contents);
    }
}
