package com.example.quizapp.entities;


public class ScoreEntry {
    private String username;
    private int score;

    public ScoreEntry() {
        // Nécessaire pour Firestore
    }

    public ScoreEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
