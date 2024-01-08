package com.example.planmind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
public class HamburgerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger);
        overridePendingTransition(R.anim.fade_in, R.anim.hold);

        ImageButton home = findViewById(R.id.hamburger_i);
        home.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hamburger_press_animation);
            v.startAnimation(animation);

            Intent intent = new Intent(HamburgerActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

}
