package com.example.tron;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView colorTextView;
    private String[] colors = {"Blue", "Red", "Green", "Yellow", "Purple"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        colorTextView = findViewById(R.id.colorTextView);
        Button leftArrowButton = findViewById(R.id.leftArrowButton);
        Button rightArrowButton = findViewById(R.id.rightArrowButton);
        Button returnToMainMenu = findViewById(R.id.returnToMainMenu);

        colorTextView.setText(colors[currentIndex]);

        returnToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex - 1) % colors.length;
                colorTextView.setText(colors[currentIndex]);
            }
        });

        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1) % colors.length;
                colorTextView.setText(colors[currentIndex]);
            }
        });
    }
}
