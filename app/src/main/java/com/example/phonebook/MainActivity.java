package com.example.phonebook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button startButton, stopButton, speedUpButton, slowDownButton;
    private TextView scoreTextView;

    private Handler handler = new Handler();
    private Random random = new Random();

    private int score = 0;
    private int speed = 1000; // Default speed in milliseconds
    private boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = findViewById(R.id.imageView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        speedUpButton = findViewById(R.id.speedUpButton);
        slowDownButton = findViewById(R.id.slowDownButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (isRunning) {
                                                 score += (1000 / speed); // Points depend on speed
                                                 scoreTextView.setText("Score: " + score);
                                             }
                                         }
                                     });

                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startGame();
                    }
                });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopGame();
            }
        });

        speedUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speed > 200) {
                    speed -= 200; // Increase speed
                }
            }
        });

        slowDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speed += 200; // Decrease speed
            }
        });
    }

    private void startGame() {
        if (!isRunning) {
            isRunning = true;
            handler.post(moveRunnable);
        }
    }

    private void stopGame() {
        isRunning = false;
        handler.removeCallbacks(moveRunnable);
        showScoreDialog();
    }

    private void showScoreDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage("Your Score: " + score);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        score = 0;
                        scoreTextView.setText("Score: 0");
                    }
                });
                builder.setCancelable(false);
        builder.show();
    }

    private Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                int x = random.nextInt(getWindow().getDecorView().getWidth() - imageView.getWidth());
                int y = random.nextInt(getWindow().getDecorView().getHeight() - imageView.getHeight());
                imageView.setX(x);
                imageView.setY(y);
                handler.postDelayed(this, speed);
            }
        }
    };
}