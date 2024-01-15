package com.example.planmind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends Activity {
    private MyAdapterTodo adapter;
    private List<ItemTodoActivity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);
        super.onResume();

        data = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapterTodo(this, data);
        recyclerView.setAdapter(adapter);

        ImageButton addButton = findViewById(R.id.img_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.my_edit_text);
                String text = editText.getText().toString().trim();
                if (!text.isEmpty()) {
                    // Crea un nuovo elemento Todo
                    ItemTodoActivity newItem = new ItemTodoActivity(false, text, R.drawable.red_circle);

                    // Aggiungi l'elemento alla lista e aggiorna il RecyclerView
                    data.add(newItem);
                    adapter.notifyDataSetChanged();

                    // Salva l'elemento nel database
                    TodoDbHelper dbHelper = new TodoDbHelper(TodoActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("completed", newItem.isChecked() ? 1 : 0);
                    values.put("description", newItem.getText());
                    values.put("priority", newItem.getImageResource());

                    long newRowId = db.insert("todo", null, values);
                }
                editText.setText("");
            }
        });

        ImageButton hamburger = findViewById(R.id.hamburger_i);

        hamburger.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(TodoActivity.this, HamburgerActivity.class);
            startActivity(intent);
        });

        RelativeLayout todoLayout = findViewById(R.id.go_to_home);
        Button todoButton = findViewById(R.id.btn_go_to_home);

        todoButton.setOnClickListener(v -> {
            Intent intent = new Intent(TodoActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
    private List<ItemTodoActivity> loadTodoFromDb() {
        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "completed",
                "description",
                "priority"
        };

        Cursor cursor = db.query(
                "todo",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<ItemTodoActivity> items = new ArrayList<>();
        while(cursor.moveToNext()) {
            boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed")) == 1;
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
            items.add(new ItemTodoActivity(completed, description, priority));
        }
        cursor.close();

        return items;
    }
    @Override
    public void onResume() {
        super.onResume();

        data.clear();
        data.addAll(loadTodoFromDb());
        adapter.notifyDataSetChanged();
    }
}