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

import androidx.recyclerview.widget.ItemTouchHelper;
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

        // Add ItemTouchHelper
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Get the position of the item to be deleted
                int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(TodoActivity.this)
                        .setTitle("Conferma eliminazione")
                        .setMessage("Vuoi eliminare questo elemento?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            ItemTodoActivity item = data.get(position);

                            TodoDbHelper dbHelper = new TodoDbHelper(TodoActivity.this);
                            dbHelper.deleteTodoItem(item.getId());

                            data.remove(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .setNegativeButton(android.R.string.no, (dialog, which) -> adapter.notifyItemChanged(position)) // restore item
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ImageButton addButton = findViewById(R.id.img_add);
        addButton.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.my_edit_text);
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                ItemTodoActivity newItem = new ItemTodoActivity(false, text);

                TodoDbHelper dbHelper = new TodoDbHelper(TodoActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                if (newItem.isChecked()) {
                    values.put("completed", 1);
                } else {
                    values.put("completed", 0);
                }

                values.put("description", newItem.getText());

                long newRowId = db.insert("todo", null, values);

                newItem.setId((int) newRowId);

                data.add(newItem);
                adapter.notifyDataSetChanged();
            }
            editText.setText("");
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
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed")) == 1;
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            ItemTodoActivity item = new ItemTodoActivity(id, completed, description);
            items.add(item);
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