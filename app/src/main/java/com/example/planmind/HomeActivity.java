package com.example.planmind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);

        TextView dateTextView = (TextView) findViewById(R.id.current_date);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        dateTextView.setText(currentDate);

        ImageButton hamburger = findViewById(R.id.hamburger_i);

        hamburger.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(HomeActivity.this, HamburgerActivity.class);
            startActivity(intent);
        });

        RelativeLayout agendaLayout = findViewById(R.id.agenda);
        agendaLayout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AgendaActivity.class);
            startActivity(intent);
        });

        RelativeLayout toDoLayout = findViewById(R.id.to_do);
        toDoLayout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, TodoActivity.class);
            startActivity(intent);
        });
    }
}
