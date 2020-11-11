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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.edit_note_title);
        content = findViewById(R.id.edit_note_content);
        //intent pour recevoir des infos
        Intent receivedData = getIntent();
        //on recupere les trois champs passé
        String receivedTitle = receivedData.getStringExtra("title");
        String receivedContent = receivedData.getStringExtra("content");
        String receivedId = receivedData.getStringExtra("id");
        //on initialise nos edittext avec
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

    public void validateNote(View view){
        if (id == null){
            String vtitle = title.getText().toString();
            String vcontent = content.getText().toString();
            Intent result = new Intent();
            result.putExtra("title",vtitle);
            result.putExtra("content",vcontent);
            setResult(0,result);
            finish();
        } else {
            String vtitle = title.getText().toString();
            String vcontent = content.getText().toString();
            Intent result = new Intent();
            result.putExtra("title",vtitle);
            result.putExtra("content",vcontent);
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
