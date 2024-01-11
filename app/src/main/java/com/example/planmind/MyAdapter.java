package com.example.planmind;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends MyAdapterBase<ItemActivity, MyAdapter.ViewHolder> {
    MyAdapter(Context context, List<ItemActivity> data) {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.agenda_item_template_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemActivity item = mData.get(position);
        holder.myEditText.setText(item.getText());
        holder.myTimeText.setText(item.getTime());

        holder.myEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                holder.myTimeText.setText("12:00");
            }
        });

        holder.myCheckbox.setOnCheckedChangeListener(null);
        holder.myCheckbox.setChecked(false);
        holder.myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.myEditText.setTextColor(Color.parseColor("#A9A9AC"));
                holder.myTimeText.setTextColor(Color.parseColor("#A9A9AC"));
            } else {
                holder.myEditText.setTextColor(Color.WHITE);
                holder.myTimeText.setTextColor(Color.WHITE);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText myEditText;
        CheckBox myCheckbox;
        TextView myTimeText;

        ViewHolder(View itemView) {
            super(itemView);
            myEditText = itemView.findViewById(R.id.my_edit_text);
            myCheckbox = itemView.findViewById(R.id.my_checkbox);
            myTimeText = itemView.findViewById(R.id.my_time_text);
        }
    }
}