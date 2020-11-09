package fr.univ.orleans.toolbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Notepad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
    }

    public void addNote(View view){
        startActivityForResult(new Intent(this,EditNoteActivity.class), 0 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Back to home menu
     */
    // le back de flashlight est utilisé à la place
    public void back(View view) {
        finish();
    }

}