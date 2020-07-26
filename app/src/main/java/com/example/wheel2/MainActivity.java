package com.example.wheel2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final Random random = new Random();
    private static final long START_TIME_IN_MILLIS = 300000;
    private ImageView playBtn, wheel, btnShare;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    int count = 0;
    TextView moneyCount, clickabilityTimer;
    private static final float FACTOR = 18f;
    int degree = 0, degree_old = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = findViewById(R.id.play_btn);
        wheel = findViewById(R.id.wheel);
        moneyCount = findViewById(R.id.money_count);
        clickabilityTimer = findViewById(R.id.timer);
        btnShare = findViewById(R.id.btn_share);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                clickabilityTimer.setVisibility(View.VISIBLE);
                spinWheel();
                view.setClickable(false);

                new CountDownTimer(300000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeLeftInMillis = millisUntilFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        clickabilityTimer.setVisibility(View.INVISIBLE);
                        view.setClickable(true);
                    }
                } .start();

            }
        });



        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(MainActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share URL")
                        .setText("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                        .startChooser();
            }
        });


    }

    private void spinWheel() {

        degree_old = degree % 360;
        degree = random.nextInt(3600) + 720;
        RotateAnimation rotateAnimation = new RotateAnimation(degree_old, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                count += currentScore(360 - (degree%360));
                moneyCount.setText(count+"к");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);


    }


    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        clickabilityTimer.setText(timeLeftFormat);
    }

    private Integer currentScore (int degrees) {
        int score = 0;

        if (degrees >= (FACTOR) && degrees < (FACTOR * 3)) {
            score=40;
        }
        if (degrees >= (FACTOR * 3) && degrees < (FACTOR * 5)) {
            score=60;
        }
        if (degrees >= (FACTOR * 5) && degrees < (FACTOR * 7)) {
            score=10;
        }
        if (degrees >= (FACTOR * 7) && degrees < (FACTOR * 9)) {
            score=90;
        }
        if (degrees >= (FACTOR * 9) && degrees < (FACTOR * 11)) {
            score=90;
        }
        if (degrees >= (FACTOR* 11) && degrees < (FACTOR * 13)) {
            score=1000;
        }
        if (degrees >= (FACTOR * 13) && degrees < (FACTOR * 15)) {
            score*=2;
        }
        if (degrees >= (FACTOR * 15) && degrees < (FACTOR * 17)) {
            score=20;
        }
        if (degrees >= (FACTOR * 17) && degrees < (FACTOR * 19)) {
            score=70;
        }
        if ((degrees >= (FACTOR * 19) && degrees < 360) || (degrees >= 0 && degrees < (FACTOR *1))) {
            score=80;
        }


        return score;
    }
}
