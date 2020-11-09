package fr.univ.orleans.toolbox;

public class Note {
    private String title,content;

    public Note(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
