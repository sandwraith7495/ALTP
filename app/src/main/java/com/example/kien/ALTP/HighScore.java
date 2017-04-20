package com.example.kien.ALTP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kien.ALTP.Object.ObjectHighScore;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    private ListView lv;
    private ArrayList<ObjectHighScore> arr = new ArrayList<ObjectHighScore>();
    private ListAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        lv = (ListView) findViewById(R.id.listView);
        Database db = new Database(HighScore.this);
        arr = db.getAllHighScore();
        Adapter = new ListAdapter(HighScore.this, arr);
        lv.setAdapter(Adapter);
    }
}
