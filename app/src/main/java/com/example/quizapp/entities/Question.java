package com.example.quizapp.entities;

import java.util.List;

public class Question {
    private String question;
    private List<String> choices;
    private String correctAnswer;
    private String imageUrl;

    // Constructeur vide (obligatoire pour Firestore)
    public Question() {}

    public Question(String question, List<String> choices, String correctAnswer, String imageUrl) {
        this.question = question;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.imageUrl = imageUrl;
    }

    // Getters et Setters
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getChoices() { return choices; }
    public void setChoices(List<String> choices) { this.choices = choices; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

