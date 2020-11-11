package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFlashLight(View view) {
        startActivity(new Intent(this, Flashlight.class));
    }

    public void openCalculator(View view) {
        startActivity(new Intent(this, Calculator.class));
    }

    public void openNotepad(View view) {
        startActivity(new Intent(this, Notepad.class));
    }
}