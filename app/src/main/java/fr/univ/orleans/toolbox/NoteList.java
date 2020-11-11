package fr.univ.orleans.toolbox;

import java.util.ArrayList;

public class NoteList {
    ArrayList<Note> listNote;

    public NoteList(){
        listNote = new ArrayList<>();

    }

    /**
     * Method that add a note in the note list
     */
    public void addInList(String title, String content){
        listNote.add(new Note(title,content));
    }

    /**
     * Method that remove a note in the note list (with the title)
     */
    public void removeInList(String title){
        for (int i=0;i<listNote.size();++i) {
            Note n=listNote.get(i);
            if (title.equals(n.getTitle())) {
                listNote.remove(n);
            }
        }
    }

    /**
     * Method that modify the selected note
     */
    public void modifyInList(String id, String title, String content){
        for (int i=0;i<listNote.size();++i) {
            Note n=listNote.get(i);
            if (id.equals(n.getId())) {
                listNote.get(i).setTitle(title);
                listNote.get(i).setContent(content);
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
