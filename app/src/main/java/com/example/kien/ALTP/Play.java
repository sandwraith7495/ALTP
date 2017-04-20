package com.example.kien.ALTP;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kien.ALTP.Object.ObjectHighScore;
import com.example.kien.ALTP.Object.ObjectMusic;
import com.example.kien.ALTP.Object.ObjectQuestion;

import java.util.ArrayList;

/**
 * Created by KIEN on 3/9/2016.
 */
public class Play extends AppCompatActivity {
    private ArrayList<ObjectQuestion> arr = new ArrayList<ObjectQuestion>();
    private int questionNo = 0;
    private Button btn1, btn2, btn3, btn4, btnWatcher, btnChange;
    private TextView tvScore, tvQuestion, tvTime;
    private final static int RESULT_PLAY_CODE = 0;
    private CountDownTimer timer;
    private ObjectMusic music;
    private ObjectHighScore highScore = new ObjectHighScore();
    Database db;
    MyFunction my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        music = new ObjectMusic(Play.this);
        tvTime = (TextView) findViewById(R.id.time);
        tvScore = (TextView) findViewById(R.id.score);
        tvQuestion = (TextView) findViewById(R.id.question);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnWatcher = (Button) findViewById(R.id.watcher);
        btnChange = (Button) findViewById(R.id.change);
        db = new Database(Play.this);

        buttonControl();
        loadQuestion();
        createQuestion(null);

        my = new MyFunction(Play.this) {
            @Override
            public void functionOK(int code) {
                switch (code) {
                    case 1:
                        checkAnswer(btn1);
                        break;
                    case 2:
                        checkAnswer(btn2);
                        break;
                    case 3:
                        checkAnswer(btn3);
                        break;
                    case 4:
                        checkAnswer(btn4);
                        break;
                    case 5:
                        changeQuestion();
                        btnChange.setVisibility(View.GONE);
                        break;
                    case 6:
                        watcherHelp();
                        btnWatcher.setVisibility(View.GONE);
                        break;
                    case 7:
                        highScore.setName(my.getEditText().getText().toString());
                        returnResult(highScore);
                        break;
                    case 8:
                        checkAnswer(null);
                        break;
                }
            }

            @Override
            public void functionCancel(int code) {
                switch (code) {
                    case 1:
                        btn1.setBackgroundResource(R.drawable.txtbox);
                        break;
                    case 2:
                        btn2.setBackgroundResource(R.drawable.txtbox);
                        break;
                    case 3:
                        btn3.setBackgroundResource(R.drawable.txtbox);
                        break;
                    case 4:
                        btn4.setBackgroundResource(R.drawable.txtbox);
                        break;

                }
            }
        };
    }

    public void loadQuestion() {
//        Database db = new DB_Question(Play.this);
        arr = db.getQuestion(1, 5, null);
        ArrayList<ObjectQuestion> arr1 = db.getQuestion(2, 5, null);
        for (int i = 0; i < 5; i++)
            arr.add(arr1.get(i));
        arr1 = db.getQuestion(3, 5, null);
        for (int i = 0; i < 5; i++)
            arr.add(arr1.get(i));
    }

    public void buttonControl() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setBackgroundResource(R.drawable.b1);
                my.showDialog(MyFunction.ANSWER_A, MyFunction.YES, MyFunction.NO, 1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundResource(R.drawable.b1);
                my.showDialog(MyFunction.ANSWER_B, MyFunction.YES, MyFunction.NO, 2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3.setBackgroundResource(R.drawable.b1);
                my.showDialog(MyFunction.ANSWER_C, MyFunction.YES, MyFunction.NO, 3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4.setBackgroundResource(R.drawable.b1);
                my.showDialog(MyFunction.ANSWER_D, MyFunction.YES, MyFunction.NO, 4);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my.showDialog( MyFunction.SWITCH, MyFunction.YES, MyFunction.NO, 5);
            }
        });
        btnWatcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my.showDialog( MyFunction.WATCHER, MyFunction.YES, MyFunction.NO, 6);

            }
        });
    }

    public String musicControl(String song) {
        if (music != null && song != null)
            return music.play(song);
        return song;
    }

    public void createQuestion(ObjectQuestion o) {

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btnChange.setEnabled(true);
        btnWatcher.setEnabled(true);

        tvScore.setText("" + highScore.getScore());
        if (questionNo >= 15) {
            my.showDialog( MyFunction.WIN, MyFunction.OK, null, 7);
        } else {
            if (o == null) {
                o = arr.get(questionNo);
                questionNo++;
                timer = new CountDownTimer(30000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        tvTime.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        checkAnswer(null);
                    }
                }.start();
            } else {
                questionNo++;
                arr.set(questionNo - 1, o);
            }
            tvQuestion.setText("Number " + questionNo + ": " + o.getQuestion());
            if (o.getArrBtn()[0] == 1) {
                btn1.setText(o.getT());
            } else if (o.getArrBtn()[0] == 2) {
                btn2.setText(o.getT());
            } else if (o.getArrBtn()[0] == 3) {
                btn3.setText(o.getT());
            } else {
                btn4.setText(o.getT());
            }
            if (o.getArrBtn()[1] == 1) {
                btn1.setText(o.getF1());
            } else if (o.getArrBtn()[1] == 2) {
                btn2.setText(o.getF1());
            } else if (o.getArrBtn()[1] == 3) {
                btn3.setText(o.getF1());
            } else {
                btn4.setText(o.getF1());
            }

            if (o.getArrBtn()[2] == 1) {
                btn1.setText(o.getF2());
            } else if (o.getArrBtn()[2] == 2) {
                btn2.setText(o.getF2());
            } else if (o.getArrBtn()[2] == 3) {
                btn3.setText(o.getF2());
            } else {
                btn4.setText(o.getF2());
            }
            if (o.getArrBtn()[3] == 1) {
                btn1.setText(o.getF3());
            } else if (o.getArrBtn()[3] == 2) {
                btn2.setText(o.getF3());
            } else if (o.getArrBtn()[3] == 3) {
                btn3.setText(o.getF3());
            } else {
                btn4.setText(o.getF3());
            }
        }
        music.setCurrSong(musicControl(ObjectMusic.THINNKING));
    }

    public void checkAnswer(final Button btn) {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btnChange.setEnabled(false);
        btnWatcher.setEnabled(false);
        timer.cancel();
        final Handler handler = new Handler();

        String mess;
        if (!db.checkBeforeAdd(highScore))
            mess = MyFunction.GAME_OVER_NO_HIGHSCORE;
        else
            mess = MyFunction.GAME_OVER_HIGHSCORE;

        if (btn != null) {
            musicControl(ObjectMusic.CHECKING);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    musicControl(ObjectMusic.STOP);
                    final Button btnT;

                    if (arr.get(questionNo - 1).getArrBtn()[0] == 1) {
                        btnT = (Button) findViewById(R.id.btn1);
                    } else if (arr.get(questionNo - 1).getArrBtn()[0] == 2) {
                        btnT = (Button) findViewById(R.id.btn2);
                    } else if (arr.get(questionNo - 1).getArrBtn()[0] == 3) {
                        btnT = (Button) findViewById(R.id.btn3);
                    } else {
                        btnT = (Button) findViewById(R.id.btn4);
                    }
                    btnT.setBackgroundResource(R.drawable.b2);
                    if (btn.getText().toString().equals(arr.get(questionNo - 1).getT())) {
                        musicControl(ObjectMusic.RIGHT);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setBackgroundResource(R.drawable.txtbox);
                                btnT.setBackgroundResource(R.drawable.txtbox);
                                if (0 < questionNo && questionNo <= 3)
                                    highScore.setScore(highScore.getScore() + 1000);
                                else if (questionNo == 4)
                                    highScore.setScore(highScore.getScore() + 2000);
                                else if (questionNo == 5)
                                    highScore.setScore(highScore.getScore() + 5000);
                                else if (questionNo == 6 || questionNo == 7)
                                    highScore.setScore(highScore.getScore() + 10000);
                                else if (questionNo == 9 || questionNo == 8)
                                    highScore.setScore(highScore.getScore() + 30000);
                                else if (questionNo == 10)
                                    highScore.setScore(highScore.getScore() + 60000);
                                else if (questionNo == 11 || questionNo == 12)
                                    highScore.setScore(highScore.getScore() + 100000);
                                else if (questionNo == 13)
                                    highScore.setScore(highScore.getScore() + 150000);
                                else if (questionNo == 14)
                                    highScore.setScore(highScore.getScore() + 300000);
                                else if (questionNo == 15)
                                    highScore.setScore(highScore.getScore() + 400000);
                                createQuestion(null);
                            }
                        }, 4000);
                    } else {
                        musicControl(ObjectMusic.WRONG);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String mess;
                                if (!db.checkBeforeAdd(highScore))
                                    mess = MyFunction.GAME_OVER_NO_HIGHSCORE;
                                else
                                    mess = MyFunction.GAME_OVER_HIGHSCORE;
                                my.showDialog( mess, MyFunction.OK, null, 7);
                            }
                        }, 3000);
                    }
                }
            }, my.myRandom(6000, 4000));
        } else {
            musicControl(ObjectMusic.TIME_OVER);

            my.showDialog(mess, MyFunction.OK, null, 7);
        }
    }

    public void returnResult(ObjectHighScore o) {
        music.play(ObjectMusic.STOP);
        if (questionNo == 1)
            highScore.setScore(1);
        Intent i = getIntent();
        if (!o.getName().equals("") && o.getName() != null) {
            db.addHighScore(highScore);
        }
        setResult(RESULT_PLAY_CODE, i);
        finish();
    }

    public void changeQuestion() {
//        DB_Question db = new DB_Question(Play.this);
        int[] ls = new int[5];
        ArrayList<ObjectQuestion> change = new ArrayList<ObjectQuestion>();
        if (questionNo < 6) {
            for (int i = 0; i < 5; i++) {
                ls[i] = arr.get(i).getId();
            }
            change = db.getQuestion(1, 1, ls);
        } else if (questionNo < 11) {
            for (int i = 5; i < 10; i++) {
                ls[i - 5] = arr.get(i).getId();
            }
            change = db.getQuestion(2, 1, ls);
        } else if (questionNo < 16) {
            for (int i = 10; i < 15; i++) {
                ls[i - 10] = arr.get(i).getId();
            }
            change = db.getQuestion(3, 1, ls);
        }
        questionNo--;
        createQuestion(change.get(0));
    }

    public void watcherHelp() {
        int tPercent = 0;
        do {
            if (questionNo < 5) {
                tPercent = MyFunction.myRandom(90, 60);
            } else if (questionNo < 10) {
                tPercent = MyFunction.myRandom(60, 50);
            } else if (questionNo < 15) {
                tPercent = MyFunction.myRandom(50, 25);
            }
        } while (tPercent < 0 || tPercent > 100);
        int f1Percent = 0, f2Percent = 0, f3Percent = 0;
        f1Percent = MyFunction.myRandom((100 - tPercent), 0);
        f2Percent = MyFunction.myRandom((100 - tPercent - f1Percent), 0);
        f3Percent = 100 - tPercent - f1Percent - f2Percent;

        String mess = "A: ";
        if (arr.get(questionNo - 1).getArrBtn()[0] == 1)
            mess += tPercent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[1] == 1)
            mess += f1Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[2] == 1)
            mess += f2Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[3] == 1)
            mess += f3Percent + "%\n";

        mess += "B: ";
        if (arr.get(questionNo - 1).getArrBtn()[0] == 2)
            mess += tPercent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[1] == 2)
            mess += f1Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[2] == 2)
            mess += f2Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[3] == 2)
            mess += f3Percent + "%\n";

        mess += "C: ";
        if (arr.get(questionNo - 1).getArrBtn()[0] == 3)
            mess += tPercent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[1] == 3)
            mess += f1Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[2] == 3)
            mess += f2Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[3] == 3)
            mess += f3Percent + "%\n";

        mess += "D: ";
        if (arr.get(questionNo - 1).getArrBtn()[0] == 4)
            mess += tPercent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[1] == 4)
            mess += f1Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[2] == 4)
            mess += f2Percent + "%\n";
        else if (arr.get(questionNo - 1).getArrBtn()[3] == 4)
            mess += f3Percent + "%\n";

        my.showDialog( mess, MyFunction.OK, null, 0);
    }

    @Override
    public void onBackPressed() {
        my.showDialog( MyFunction.END, MyFunction.YES, MyFunction.NO, 8);
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicControl(ObjectMusic.STOP);
        music = null;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        music = new ObjectMusic(Play.this);
        music.setCurrSong(musicControl(music.getCurrSong()));
    }
}
