package com.example.planmind;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.app.TimePickerDialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgendaActivity extends Activity {
    private MyAdapter adapter;
    private List<ItemActivity> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);

        data = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);

        ImageButton addButton = findViewById(R.id.img_add);
        addButton.setOnClickListener(v -> {
            EditText editText = findViewById(R.id.my_edit_text);
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        (view, hourOfDay, minute) -> {
                            String time = String.format("%02d:%02d", hourOfDay, minute);
                            data.add(new ItemActivity(text, time));
                            Collections.sort(data, (item1, item2) -> item1.getTime().compareTo(item2.getTime()));
                            adapter.notifyDataSetChanged();
                        }, 12, 0, true);
                timePickerDialog.show();
            }
            editText.setText("");
        });
    }
}