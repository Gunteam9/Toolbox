package fr.univ.orleans.toolbox;

import java.util.UUID;

public class Note {
    private String title,content;
    private String id;

    public Note(String title, String content){
        this.title = title;
        this.content = content;
        id = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }
}
