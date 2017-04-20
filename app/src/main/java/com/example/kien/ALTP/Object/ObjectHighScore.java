package com.example.kien.ALTP.Object;

/**
 * Created by KIEN on 3/12/2016.
 */
public class ObjectHighScore {
    private int score;
    private String name;

    public ObjectHighScore() {
    }

    public ObjectHighScore(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

}
