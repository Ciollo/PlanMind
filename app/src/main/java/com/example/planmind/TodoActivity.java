package com.example.planmind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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

        data = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapterTodo(this, data);
        recyclerView.setAdapter(adapter);

        ImageButton addButton = findViewById(R.id.img_add);
        addButton.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.my_edit_text);
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                data.add(new ItemTodoActivity(false, text, R.drawable.red_circle));
                adapter.notifyDataSetChanged();  // Aggiungi questa riga
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
}