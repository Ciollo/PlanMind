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

        setupHomeOption();
        setupTodoOption();
        setupAgendaOption();
        setupCloseButton();
    }

    private void setupHomeOption() {
        RelativeLayout homeOption = findViewById(R.id.home_option);
        homeOption.setOnClickListener(v -> {
            startAnimation((RelativeLayout) v);
            startNewActivity(HomeActivity.class);
        });
    }

    private void setupTodoOption() {
        RelativeLayout todoOption = findViewById(R.id.todo_option);
        todoOption.setOnClickListener(v -> {
            startAnimation((RelativeLayout) v);
            startNewActivity(TodoActivity.class);
        });
    }

    private void setupAgendaOption() {
        RelativeLayout agendaOption = findViewById(R.id.agenda_option);
        agendaOption.setOnClickListener(v -> {
            startAnimation((RelativeLayout) v);
            startNewActivity(AgendaActivity.class);
        });
    }

    private void setupCloseButton() {
        ImageButton close_button = findViewById(R.id.hamburger_close);
        close_button.setOnClickListener(v -> {
            startAnimation((RelativeLayout) v);
            finish();
        });
    }

    private void startAnimation(RelativeLayout view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
        view.startAnimation(animation);
    }

    private void startNewActivity(Class<?> activityClass) {
        Intent intent = new Intent(HamburgerActivity.this, activityClass);
        startActivity(intent);
    }
}