package fr.univ.orleans.toolbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    EditText title,content;
    private String id;

    public static final String EXTRA_TITLE = "Title";
    public static final String EXTRA_CONTENT = "Content";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.edit_note_title);
        content = findViewById(R.id.edit_note_content);
        //intent pour recevoir des infos
        Intent receivedData = getIntent();
        //on recupere les trois champs passés
        String receivedTitle = receivedData.getStringExtra("title");
        String receivedContent = receivedData.getStringExtra("content");
        String receivedId = receivedData.getStringExtra("id");
        //on initialise nos edittext avec et on stock l'id
        title.setText(receivedTitle);
        content.setText(receivedContent);
        id = receivedId;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Method that validate the actual note (that allow to create a new note or modify an existing note)
     */
    public void validateNote(View view){
        if (id == null){
            String vtitle = title.getText().toString();
            String vcontent = content.getText().toString();
            Intent result = new Intent();
            result.putExtra(EXTRA_TITLE,vtitle);
            result.putExtra(EXTRA_CONTENT,vcontent);
            setResult(0,result);
            finish();
        } else {
            String vtitle = title.getText().toString();
            String vcontent = content.getText().toString();
            Intent result = new Intent();
            result.putExtra(EXTRA_TITLE,vtitle);
            result.putExtra(EXTRA_CONTENT,vcontent);
            result.putExtra("id",id);
            setResult(1,result);
            finish();
        }
    }

    /**
     * Back to home menu
     */
    // le back de flashlight est utilisé à la place
    public void back(View view) {
        finish();
    }
}
