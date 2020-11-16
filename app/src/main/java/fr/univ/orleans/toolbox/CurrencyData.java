package fr.univ.orleans.toolbox;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class CurrencyData {

    private LocalDateTime date;
    private String source;
    private JSONObject targets;

    public CurrencyData(LocalDateTime date, String source, JSONObject targets) {
        this.date = date;
        this.source = source;
        this.targets = targets;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public JSONObject getTargets() {
        return targets;
    }

    public void setTargets(JSONObject targets) {
        this.targets = targets;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "date=" + date +
                ", source='" + source + '\'' +
                ", targets=" + targets +
                '}';
    }
}
