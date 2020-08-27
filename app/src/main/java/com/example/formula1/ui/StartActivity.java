package com.example.formula1.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.formula1.R;

import java.util.Objects;

import static com.example.formula1.R.drawable.raceflag;

public class StartActivity extends AppCompatActivity {

    private Button startButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(getDrawable(raceflag));
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#e56617'></font>"));

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
