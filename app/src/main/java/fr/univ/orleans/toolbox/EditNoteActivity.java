package fr.univ.orleans.toolbox;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    EditText title,content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.edit_note_title);
        content = findViewById(R.id.edit_note_content);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void validateNote(View view){
        String vtitle = title.getText().toString();
        String vcontent = content.getText().toString();
    }

    public void deleteNote(View view){

    }
}
