package com.example.planmind;

public class ItemTodoActivity {
    private Integer id;
    private boolean checked;
    private String text;
    private String completitionCirclePath;

    public ItemTodoActivity(boolean checked, String text) {
        this.id = null;
        this.checked = checked;
        this.text = text;
        if (checked) {
            completitionCirclePath = "green_circle";
        } else {
            completitionCirclePath = "red_circle";
        }
    }

    public ItemTodoActivity(int id, boolean checked, String text) {
        this.id = id;
        this.checked = checked;
        this.text = text;
        if (checked) {
            completitionCirclePath = "green_circle";
        } else {
            completitionCirclePath = "red_circle";
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public String getCompletitionCirclePath() {
        return completitionCirclePath;
    }
}