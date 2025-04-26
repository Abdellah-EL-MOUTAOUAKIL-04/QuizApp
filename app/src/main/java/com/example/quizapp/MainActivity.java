package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.entities.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button bLogin;
    TextView tvRegister;
    FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        bLogin = findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.textViewRegister);

        myAuth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(email, password);*/
                startActivity(new Intent(MainActivity.this, Quiz.class));
            }
        });
    }

    public void loginUser(String email, String password) {
        myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                            // Appeler pour insérer les questions
                            //insertQuestions();

                            // Aller au Quiz
                            startActivity(new Intent(MainActivity.this, Quiz.class));
                            overridePendingTransition(R.anim.exit, R.anim.entry);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void insertQuestions() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
                "Quel est le résultat affiché ?",
                Arrays.asList("2.5", "2.0", "Erreur de compilation"),
                "2.0",
                ""
        ));

        questions.add(new Question(
                "Que se passe-t-il à la compilation ?",
                Arrays.asList(
                        "Le programme compile et s'exécute normalement",
                        "Erreur de compilation",
                        "Le programme compile mais l'héritage est ignoré"
                ),
                "Erreur de compilation",
                ""
        ));

        questions.add(new Question(
                "Quel est le résultat affiché ?",
                Arrays.asList("A B C", "C B A", "Erreur de compilation"),
                "A B C",
                ""
        ));

        questions.add(new Question(
                "Que se passe-t-il si on sérialise puis désérialise un objet User ?",
                Arrays.asList(
                        "Les deux champs seront restaurés",
                        "Seul name sera restauré",
                        "Aucun champ ne sera restauré"
                ),
                "Seul name sera restauré",
                ""
        ));

        questions.add(new Question(
                "Quel est le résultat affiché ?",
                Arrays.asList("true", "false", "abc"),
                "false",
                ""
        ));

        // Envoyer chaque question dans Firestore
        for (Question question : questions) {
            db.collection("questions")
                    .add(question)
                    .addOnSuccessListener(documentReference -> {
                        // Question insérée
                    })
                    .addOnFailureListener(e -> {
                        // Erreur d'insertion
                    });
        }
    }
}
