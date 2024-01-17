package com.example.planmind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends Activity {
    private MyAdapterTodo todoAdapter;
    private List<ItemTodoActivity> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);
        super.onResume();

        todoItems = new ArrayList<>();
        setupRecyclerView();
        setupAddButton();
        setupHamburgerButton();
        setupGoHomeButton();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new MyAdapterTodo(this, todoItems);
        recyclerView.setAdapter(todoAdapter);
        setupItemTouchHelper(recyclerView);
    }

    private void setupItemTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                showDeleteConfirmationDialog(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(TodoActivity.this)
                .setTitle("Delete item")
                .setMessage("Do you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteItem(position))
                .setNegativeButton(android.R.string.no, (dialog, which) -> todoAdapter.notifyItemChanged(position))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteItem(int position) {
        ItemTodoActivity item = todoItems.get(position);
        TodoDbHelper todoDbHelper = new TodoDbHelper(TodoActivity.this);
        todoDbHelper.deleteTodoItem(item.getId());
        todoItems.remove(position);
        todoAdapter.notifyItemRemoved(position);
        todoDbHelper.close();
    }

    private void setupAddButton() {
        ImageButton addButton = findViewById(R.id.img_add);
        addButton.setOnClickListener(v -> addItem());
    }

    private void addItem() {
        EditText editText = findViewById(R.id.my_edit_text);
        String text = editText.getText().toString().trim();
        if (!text.isEmpty()) {
            ItemTodoActivity newItem = new ItemTodoActivity(false, text);
            insertItemIntoDb(newItem);
            todoItems.add(newItem);
            todoAdapter.notifyItemInserted(todoItems.size() - 1);
            editText.setText("");
        }
    }

    private void insertItemIntoDb(ItemTodoActivity item) {
        TodoDbHelper todoDbHelper = new TodoDbHelper(TodoActivity.this);
        SQLiteDatabase database = todoDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("completed", item.isChecked() ? 1 : 0);
        values.put("description", item.getText());

        long newItemId = database.insert("todo", null, values);
        item.setId((int) newItemId);

        database.close();
    }

    private void setupHamburgerButton() {
        ImageButton hamburger = findViewById(R.id.hamburger_i);
        hamburger.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(TodoActivity.this, HamburgerActivity.class);
            startActivity(intent);
        });
    }

    private void setupGoHomeButton() {
        Button goHomeButton = findViewById(R.id.btn_go_to_home);
        goHomeButton.setOnClickListener(v -> {
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

        todoItems.clear();
        todoItems.addAll(loadTodoFromDb());
        todoAdapter.notifyDataSetChanged();
    }
}