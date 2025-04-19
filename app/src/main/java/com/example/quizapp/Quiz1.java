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

public class Quiz1 extends AppCompatActivity {

    Button bNext;
    RadioGroup rg;
    RadioButton rb;
    int score;
    String correctResp="radioButton3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz1);

        bNext=findViewById(R.id.bNext);
        rg=findViewById(R.id.rg);

        //adding icon for the checked radio button
        RadioGroup rg = findViewById(R.id.rg);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    View child = radioGroup.getChildAt(i);
                    if (child instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) child;

                        if (radioButton.getId() == checkedId) {
                            // Checked: change border & icon
                            radioButton.setBackgroundResource(R.drawable.rounded_button_checked);
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_fixed, 0);
                        } else {
                            // Unchecked: revert
                            radioButton.setBackgroundResource(R.drawable.rounded_radio_button);
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }
                    }
                }
            }
        });


        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"Veuillez choisir une reponse svp",Toast.LENGTH_SHORT).show();
                }else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    if(rb.getText().toString().equals(correctResp)){
                        score+=1;
                    }
                    Intent i1=new Intent(Quiz1.this, Quiz2.class);
                    i1.putExtra("score",score);
                    startActivity(i1);

                    overridePendingTransition(R.anim.exit,R.anim.entry);
                    finish();
                }
            }
        });
    }
}