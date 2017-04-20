package com.example.kien.ALTP.Object;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by KIEN on 4/20/2016.
 */
public class ObjectMusic implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private Context context;
    private String currSong = "";

    public static final String STOP = "";
    public static final String THINNKING = "thinking";
    public static final String RIGHT = "right";
    public static final String WRONG = "wrong";
    public static final String END = "end";
    public static final String CHECKING = "checking";
    public static final String TIME_OVER = "time_over";
    public static final String START = "start";
    public static final String READY = "ready";


    public ObjectMusic(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public String play(String song) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
        try {
            if (!song.equals("")) {
                mediaPlayer.setDataSource(context, Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + song));
                if (song.equals(START) || song.equals(THINNKING) || song.equals(CHECKING))
                    mediaPlayer.setLooping(true);
                else
                    mediaPlayer.setLooping(false);
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.prepareAsync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return song;
    }

    public String getCurrSong() {
        return currSong;
    }

    public void setCurrSong(String currSong) {
        this.currSong = currSong;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
