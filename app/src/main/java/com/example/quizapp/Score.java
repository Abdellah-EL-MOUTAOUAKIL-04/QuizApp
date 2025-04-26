package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp.entities.ScoreEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    Button bLogout, TryAgain;
    TextView tvScore;
    ProgressBar pb;
    int score;
    ListView listView;
    ArrayList<String> scoreList;
    ArrayAdapter<String> adapter;
    FirebaseFirestore db;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Initialisation
        bLogout = findViewById(R.id.bLogout);
        TryAgain = findViewById(R.id.bTry);
        tvScore = findViewById(R.id.tvScore);
        pb = findViewById(R.id.ProgressBarId);
        listView = findViewById(R.id.lvTopScore);

        // Firestore + Auth
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Adapter pour ListView
        scoreList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_score, scoreList);
        listView.setAdapter(adapter);

        // Récupérer score
        Intent i1 = getIntent();
        score = i1.getIntExtra("score", 0);
        int scorePercent = score * 100 / 5;
        tvScore.setText(scorePercent + "%");
        pb.setProgress(scorePercent);

        // Sauvegarder le score dans Firestore
        saveScoreToFirestore(scorePercent);

        // Charger les meilleurs scores
        loadTopScores();

        // Bouton Logout
        bLogout.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Merci pour votre participation", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Score.this, MainActivity.class));
            overridePendingTransition(R.anim.exit, R.anim.entry);
            finish();
        });

        // Bouton Try Again
        TryAgain.setOnClickListener(view -> {
            startActivity(new Intent(Score.this, Quiz.class));
            finish();
        });
    }

    private void saveScoreToFirestore(int scorePercent) {
        if (currentUser != null && currentUser.getEmail() != null) {
            String email = currentUser.getEmail();
            String username = email.split("@")[0];

            ScoreEntry scoreEntry = new ScoreEntry(username, scorePercent);

            db.collection("scores")
                    .add(scoreEntry)
                    .addOnSuccessListener(documentReference -> {
                        // Score ajouté avec succès
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur d'enregistrement du score", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void loadTopScores() {
        db.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    scoreList.clear();
                    for (var document : queryDocumentSnapshots) {
                        String username = document.getString("username");
                        Long score = document.getLong("score");
                        if (username != null && score != null) {
                            scoreList.add(username + " : " + score + "%");
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de chargement des scores", Toast.LENGTH_SHORT).show();
                });
    }
}
