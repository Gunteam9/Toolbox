package fr.univ.orleans.toolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public void openFlashLight(View view) {
        startActivity(new Intent(this, Flashlight.class));
    }

    public void openCalculatrice(View view) {
        startActivity(new Intent(this, CalculatriceActivity.class));
    }

    public void openNotepad(View view) {
        startActivity(new Intent(this, Notepad.class));
    }

    public void openCurrency(View view) { startActivity(new Intent(this, Currency.class));}

    public void openClock(View view) { startActivity(new Intent(this,ClockActivity.class));}
}