package fr.univ.orleans.toolbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NoteList {
    ArrayList<Note> listNote;
    DbOpenHelper db;
    Context context;

    public NoteList(Context context){
        listNote = new ArrayList<>();
        this.context = context.getApplicationContext();
        db = DbOpenHelper.instance;
        SQLiteDatabase finDB = db.getReadableDatabase();
        Cursor cursor = finDB.query(DbOpenHelper.TABLE_NOTE, new String[]{DbOpenHelper.COLUMN_TITLE, DbOpenHelper.COLUMN_CONTENT}, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                listNote.add(new Note(cursor.getString(0),cursor.getString(1)));
            }
        }
        finally {
            cursor.close();
        }
        finDB.close();
    }

    /**
     * Method that add a note in the note list
     */
    public void addInList(String title, String content){
        listNote.add(new Note(title,content));
        SQLiteDatabase actualdb = db.getWritableDatabase();
        ContentValues note =new ContentValues();
        note.put(DbOpenHelper.COLUMN_TITLE,title);
        note.put(DbOpenHelper.COLUMN_CONTENT,content);
        actualdb.insert(DbOpenHelper.TABLE_NOTE,null,note);
        db.close();
    }

    /**
     * Method that remove a note in the note list (with the title)
     */
    public void removeInList(String title){
        for (int i=0;i<listNote.size();++i) {
            Note n=listNote.get(i);
            if (title.equals(n.getTitle())) {
                listNote.remove(n);
                SQLiteDatabase actualdb = db.getWritableDatabase();
                actualdb.delete(DbOpenHelper.TABLE_NOTE,DbOpenHelper.COLUMN_TITLE+" = ?", new String[]{title});
                db.close();
            }
        }
    }

    /**
     * Method that modify the selected note
     */
    public void modifyInList(String id, String title, String content){
        for (int i=0;i<listNote.size();++i) {
            Note n=listNote.get(i);
            if (id.equals(n.getTitle())) {
                listNote.get(i).setTitle(title);
                listNote.get(i).setContent(content);
                //La suite fait planté l'appli lors d'une modif
                SQLiteDatabase actualdb = db.getWritableDatabase();
                ContentValues note =new ContentValues();
                note.put(DbOpenHelper.COLUMN_TITLE,title);
                note.put(DbOpenHelper.COLUMN_CONTENT,content);
                actualdb.update(DbOpenHelper.TABLE_NOTE, note,DbOpenHelper.COLUMN_TITLE+" = ?",new String[]{id});
                db.close();
            }
        }
    }

    public ArrayList<Note> getListNote() {
        return listNote;
    }

    public Note get(int i){
        if(i < 0 || i >= listNote.size()){
            return null;
        }
        return listNote.get(i);
    }
}
