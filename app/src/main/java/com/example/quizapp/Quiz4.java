package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz4 extends AppCompatActivity {

    Button bNext;
    RadioGroup rg;
    RadioButton rb;
    int score;
    String correctResp="radioButton3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz4);

        bNext=findViewById(R.id.bNext);
        rg=findViewById(R.id.rg);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"Veuillez choisir une reponse svp",Toast.LENGTH_SHORT).show();
                }else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    score=getIntent().getIntExtra("score",0);
                    if(rb.getText().toString().equals(correctResp)){
                        score+=1;
                    }
                    Intent i1=new Intent(Quiz4.this, Quiz5.class);
                    i1.putExtra("score",score);
                    startActivity(i1);
                }
            }
        });
    }
}