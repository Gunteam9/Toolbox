package fr.univ.orleans.toolbox;

import org.json.JSONObject;

import java.time.LocalDate;

public class CoronavirusData {

    private LocalDate date;
    private JSONObject data;

    public CoronavirusData(LocalDate date, JSONObject data) {
        this.date = date;
        this.data = data;
    }

    public LocalDate getDate() {
        return date;
    }

    public JSONObject getData() {
        return data;
    }
}
