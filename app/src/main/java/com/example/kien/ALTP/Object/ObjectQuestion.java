package com.example.kien.ALTP.Object;

import com.example.kien.ALTP.MyFunction;

/**
 * Created by KIEN on 3/15/2016.
 */
public class ObjectQuestion {
    private String question, t, f1, f2, f3;
    private int[] arrBtn;
    private int id, level;

    public ObjectQuestion(int id, String question, String t, String f1, String f2, String f3, int level) {
        this.id = id;
        this.level = level;
        this.question = question;
        this.t = t;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        arrBtn = new int[4];
        arrBtn[0] = MyFunction.myRandom(4, 1);
        do {
            arrBtn[1] = MyFunction.myRandom(4, 1);
        } while (arrBtn[1] == arrBtn[0]);
        do {
            arrBtn[2] = MyFunction.myRandom(4, 1);
        } while (arrBtn[2] == arrBtn[0] || arrBtn[2] == arrBtn[1]);
        do {
            arrBtn[3] = MyFunction.myRandom(4, 1);
        } while (arrBtn[3] == arrBtn[0] || arrBtn[3] == arrBtn[1] || arrBtn[3] == arrBtn[2]);
    }

    public ObjectQuestion(String question, String t, String f1, String f2, String f3, int level) {
        this.level = level;
        this.question = question;
        this.t = t;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getT() {
        return t;
    }

    public String getF1() {
        return f1;
    }

    public String getF2() {
        return f2;
    }

    public String getF3() {
        return f3;
    }

    public int[] getArrBtn() {
        return arrBtn;
    }

    public int getLevel(){
        return level;
    }
}
