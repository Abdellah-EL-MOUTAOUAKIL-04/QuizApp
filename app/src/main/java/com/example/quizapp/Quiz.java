package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.entities.Question;
import com.example.quizapp.utils.RadioGroupUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {

    TextView tvQuestion, tvCurrentQstInd;
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    Button btnNext;
    ProgressBar progressBar;
    ImageView imageViewQst;

    CountDownTimer countDownTimer;
    int score = 0;
    int currentQuestionIndex = 0;

    int timeLeft = 10000; // 10s
    int interval = 100;

    List<Question> questionsList = new ArrayList<>(); // Nouvelle liste

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
        imageViewQst = findViewById(R.id.imageViewQst);

        // Setup radio group custom behavior
        RadioGroupUtils.setupCustomRadioGroup(radioGroup, this);

        // Load questions from Firestore
        loadQuestionsFromFirestore();

        btnNext.setOnClickListener(v -> {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            countDownTimer.cancel();

            RadioButton selected = findViewById(radioGroup.getCheckedRadioButtonId());
            String answer = selected.getText().toString();
            String correctAnswer = questionsList.get(currentQuestionIndex).getCorrectAnswer();

            if (answer.equals(correctAnswer)) {
                RadioGroupUtils.markAnswerCorrect(selected, this);
                score++;
            } else {
                RadioGroupUtils.markAnswerIncorrect(selected, this);
            }

            new android.os.Handler().postDelayed(this::goToNext, 500);
        });
    }

    private void loadQuestionsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (var document : task.getResult()) {
                                Question question = document.toObject(Question.class);
                                questionsList.add(question);
                            }
                            if (!questionsList.isEmpty()) {
                                loadQuestion();
                                startTimer();
                            } else {
                                Toast.makeText(Quiz.this, "No questions found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Quiz.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    private void loadQuestion() {
        if (currentQuestionIndex < questionsList.size()) {
            Question currentQuestion = questionsList.get(currentQuestionIndex);

            tvQuestion.setText(currentQuestion.getQuestion());
            tvCurrentQstInd.setText(String.format("Question %d", currentQuestionIndex + 1));

            List<String> choices = currentQuestion.getChoices();
            if (choices.size() >= 3) {
                radio1.setText(choices.get(0));
                radio2.setText(choices.get(1));
                radio3.setText(choices.get(2));
            }

            radioGroup.clearCheck();

            String imageName = "q" + (currentQuestionIndex + 1);
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());

            if (imageResId != 0) {
                imageViewQst.setImageResource(imageResId);
            }
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
                goToNext();
            }
        }.start();
    }

    private void goToNext() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questionsList.size()) {
            loadQuestion();
            startTimer();
        } else {
            Intent intent = new Intent(Quiz.this, Score.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }
}
