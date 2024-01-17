package com.example.planmind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class HamburgerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);

        RelativeLayout homeOption = findViewById(R.id.home_option);
        homeOption.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(HamburgerActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        RelativeLayout todoOption = findViewById(R.id.todo_option);
        todoOption.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(HamburgerActivity.this, TodoActivity.class);
            startActivity(intent);
        });

        RelativeLayout agendaOption = findViewById(R.id.agenda_option);
        agendaOption.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(HamburgerActivity.this, AgendaActivity.class);
            startActivity(intent);
        });

        ImageButton close_button = findViewById(R.id.hamburger_close);
        close_button.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            finish();
        });



    }
}
