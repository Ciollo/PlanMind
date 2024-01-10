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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ItemActivity> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    // data is passed into the constructor
    MyAdapter(Context context, List<ItemActivity> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
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
                // imposta l'orario nel TextView quando l'utente finisce di scrivere
                holder.myTimeText.setText("12:00"); // sostituisci con l'orario desiderato
            }
        });

        holder.myCheckbox.setOnCheckedChangeListener(null);
        // set the checkbox state without triggering the listener
        // you can store the checkbox state in a separate list in your data model
        holder.myCheckbox.setChecked(false);
        holder.myCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                holder.myEditText.setTextColor(Color.parseColor("#A9A9AC"));
                holder.myTimeText.setTextColor(Color.parseColor("#A9A9AC"));
            } else {
                holder.myEditText.setTextColor(Color.WHITE); // replace with original color
                holder.myTimeText.setTextColor(Color.WHITE); // replace with original color
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
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