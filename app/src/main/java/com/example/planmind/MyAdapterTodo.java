package com.example.planmind;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterTodo extends MyAdapterBase<ItemTodoActivity, MyAdapterTodo.ViewHolder> {
    private final int[] imageResources = {R.drawable.red_circle, R.drawable.green_circle};

    MyAdapterTodo(Context context, List<ItemTodoActivity> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todo_item_template_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemTodoActivity item = mData.get(position);
        holder.myEditText.setText(item.getText());

        holder.myImage.setImageResource(imageResources[item.isChecked() ? 1 : 0]);

        if (item.isChecked()) {
            holder.myEditText.setTextColor(Color.parseColor("#A9A9AC"));
            holder.myEditText.setPaintFlags(holder.myEditText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.myEditText.setTextColor(Color.WHITE);
            holder.myEditText.setPaintFlags(holder.myEditText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.myCheckbox.setOnCheckedChangeListener(null);
        holder.myCheckbox.setChecked(item.isChecked());
        holder.myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.myEditText.setTextColor(Color.parseColor("#A9A9AC"));
                holder.myEditText.setPaintFlags(holder.myEditText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.myImage.setImageResource(imageResources[1]);
            } else {
                holder.myEditText.setTextColor(Color.WHITE);
                holder.myEditText.setPaintFlags(holder.myEditText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.myImage.setImageResource(imageResources[0]);
            }
            item.setChecked(isChecked);
            TodoDbHelper dbHelper = new TodoDbHelper(holder.itemView.getContext());
            dbHelper.updateTodoItem(item.getId(), item.isChecked());
            dbHelper.close();
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText myEditText;
        CheckBox myCheckbox;
        ImageView myImage;

        ViewHolder(View itemView) {
            super(itemView);
            myEditText = itemView.findViewById(R.id.todo_edit_text);
            myCheckbox = itemView.findViewById(R.id.todo_checkbox);
            myImage = itemView.findViewById(R.id.todo_image);
        }
    }
}