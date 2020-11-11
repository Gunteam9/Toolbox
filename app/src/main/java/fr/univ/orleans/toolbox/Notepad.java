package fr.univ.orleans.toolbox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Notepad extends AppCompatActivity {

    NoteList k = new NoteList();
    ListView list;
    private NoteAdaptater adapter;
    Note selectedNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        list = (ListView)findViewById(R.id.notelist);
        k.addInList("abc","def");

        this.adapter = new NoteAdaptater(this,R.layout.adapter_view_note,k.getListNote());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNote = k.get(i);
            }
        });
        this.adapter.notifyDataSetChanged();
    }


    public void addNote(View view){
        startActivityForResult(new Intent(this,EditNoteActivity.class), 0 );
    }

    public void removeNote(View view){
        if(selectedNote != null){
            k.removeInList(selectedNote.getTitle());
            selectedNote = null;
            this.adapter.notifyDataSetChanged();
        }
    }

    public void modifyNote(View view){
        if(selectedNote != null){
            Intent intent = new Intent(Notepad.this,EditNoteActivity.class);
            intent.putExtra("title",selectedNote.getTitle());
            intent.putExtra("content",selectedNote.getContent());
            intent.putExtra("id",selectedNote.getId());
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            String title = null;
            String content = null;
            try {
                title = data.getStringExtra("title");
                content = data.getStringExtra("content");
            } catch (NullPointerException ignored){}
            if (title == null || content == null){
                Log.e("Notepad","missing field");
                return;
            }
            k.addInList(title, content);
            this.adapter.notifyDataSetChanged();
        }
        if (requestCode == 1){
            String title = null;
            String content = null;
            String id = null;
            try {
                title = data.getStringExtra("title");
                content = data.getStringExtra("content");
                id = data.getStringExtra("id");
            } catch (NullPointerException ignored){}
            if (title == null || content == null || id == null){
                Log.e("Notepad","missing field");
                return;
            }
            k.modifyInList(id,title, content);
            this.adapter.notifyDataSetChanged();
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
