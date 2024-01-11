package com.example.planmind;

public class ItemActivity {
    private String text;
    private String time;

    public ItemActivity(String text, String time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
