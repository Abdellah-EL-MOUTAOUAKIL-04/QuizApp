package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Score extends AppCompatActivity {

    Button bLogout,TryAgain;
    TextView tvScore;
    ProgressBar pb;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        bLogout=findViewById(R.id.bLogout);
        TryAgain=findViewById(R.id.bTry);
        tvScore=findViewById(R.id.tvScore);
        pb=findViewById(R.id.ProgressBarId);

        Intent i1=getIntent();
        score= i1.getIntExtra("score",0);
        tvScore.setText(score*100/5+"%");
        pb.setProgress(score*100/5);
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Merci pour votre participation",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Score.this,MainActivity.class));
                overridePendingTransition(R.anim.exit,R.anim.entry);
                finish();
            }
        });

        TryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Score.this, Quiz.class));
            }
        });
    }
}