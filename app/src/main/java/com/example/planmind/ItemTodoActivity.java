package com.example.planmind;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ItemTodoActivity {
    private boolean checked;
    private String text;
    private int imageResource;

    public ItemTodoActivity(boolean checked, String text, int imageResource) {
        this.checked = checked;
        this.text = text;
        this.imageResource = imageResource;
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

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}