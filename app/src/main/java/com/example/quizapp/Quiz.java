package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.utils.RadioGroupUtils;

public class Quiz extends AppCompatActivity {

    TextView tvQuestion, tvCurrentQstInd;
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    Button btnNext;
    ProgressBar progressBar;

    CountDownTimer countDownTimer;
    int score = 0;
    int currentQuestionIndex = 0;

    int timeLeft = 10000; // 10s
    int interval = 100;

    // Questions
    String[] questions = {
            "What is the capital of France?",
            "Which language is used in Android?",
            "2 + 2 = ?",
            "What color is the sky?",
            "What is the opposite of 'cold'?"
    };

    // Choices
    String[][] choices = {
            {"Paris", "London", "Berlin"},
            {"Java", "Python", "C++"},
            {"3", "4", "5"},
            {"Blue", "Green", "Red"},
            {"Hot", "Wet", "Soft"}
    };

    // Correct answers
    String[] correctAnswers = {
            "Paris", "Java", "4", "Blue", "Hot"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        tvQuestion = findViewById(R.id.TVQst);
        tvCurrentQstInd = findViewById(R.id.tvCurrentQstInd);
        radioGroup = findViewById(R.id.rg);
        radio1 = findViewById(R.id.radioBtn1);
        radio2 = findViewById(R.id.radioBtn2);
        radio3 = findViewById(R.id.radioBtn3);
        btnNext = findViewById(R.id.bNext);
        progressBar = findViewById(R.id.progressBar);

        // Load first question
        loadQuestion();
        startTimer();

        // Setup radio group custom behavior
        RadioGroupUtils.setupCustomRadioGroup(radioGroup,this);

        // Handle "Next" button click
        btnNext.setOnClickListener(v -> {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cancel countdown timer for next question
            countDownTimer.cancel();

            // Check selected answer
            RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
            String answer = selected.getText().toString();

            // Increment score if correct answer
            if (answer.equals(correctAnswers[currentQuestionIndex])) {
                RadioGroupUtils.markAnswerCorrect(selected, this);
                score++;
            }else{
                RadioGroupUtils.markAnswerIncorrect(selected, this);
            }

            new android.os.Handler().postDelayed(() -> {
                // Passer à la question suivante
                goToNext();
            }, 500);
        });
    }

    @SuppressLint("DefaultLocale")
    private void loadQuestion() {
        if (currentQuestionIndex < questions.length) {
            // Update question text
            tvQuestion.setText(questions[currentQuestionIndex]);
            // Update choices for the current question
            radio1.setText(choices[currentQuestionIndex][0]);
            radio2.setText(choices[currentQuestionIndex][1]);
            radio3.setText(choices[currentQuestionIndex][2]);
            radioGroup.clearCheck();

            // Update current question index display
            tvCurrentQstInd.setText(String.format("Question %d", currentQuestionIndex + 1));
        }
    }

    private void startTimer() {
        progressBar.setProgress(0);
        countDownTimer = new CountDownTimer(timeLeft, interval) {
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((timeLeft - millisUntilFinished) * 100 / timeLeft);
                progressBar.setProgress(progress);
            }

            public void onFinish() {
                goToNext(); // auto next if time is up
            }
        }.start();
    }

    private void goToNext() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.length) {
            // Load next question and restart timer
            loadQuestion();
            startTimer();
        } else {
            // Go to score activity
            Intent intent = new Intent(Quiz.this, Score.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }
}
