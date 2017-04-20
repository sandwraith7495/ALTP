package com.example.kien.ALTP;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public abstract class MyFunction implements MyDialog {

    public static final String WIN = "Congratulation";
    public static final String GAME_OVER_NO_HIGHSCORE = "Game over";
    public static final String GAME_OVER_HIGHSCORE = "Game over\n New Score";
    public static final String ANSWER_A = "Your final answer is A";
    public static final String ANSWER_B = "Your final answer is B";
    public static final String ANSWER_C = "Your final answer is C";
    public static final String ANSWER_D = "Your final answer is D";
    public static final String WATCHER = "Do you want to ask people ?";
    public static final String SWITCH = "Do you want to switch question ?";
    public static final String END = "Do you want to exit? Your game will be end";
    public static final String READY = "Are you ready";
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String OK = "OK";
    public static final String EXIT = "Do you want to exit";

    private static EditText edit;
    private Context context;

    public MyFunction(Context context){
        this.context = context;
    }

    public static int myRandom(int max, int min) {
        Random r = new Random();
        return r.nextInt(max-0) + min;
    }

    public void showDialog( String mess, String ok, String cancel, final int code) {

        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(100, 100);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        TextView txt = (TextView) dialog.findViewById(R.id.content);
        txt.setText(mess);

        edit = (EditText) dialog.findViewById(R.id.editText);
        if (mess.equals(GAME_OVER_HIGHSCORE) || mess.equals(WIN)) {
        } else {
            edit.setVisibility(View.GONE);
        }

        Button btnOk = (Button) dialog.findViewById(R.id.ok);
        btnOk.setText(ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                functionOK(code);
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.cancel);
        if (cancel == null) {
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setText(cancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    functionCancel(code);
                }
            });
        }
        dialog.show();
    }

    public EditText getEditText() {
        return edit;
    }
}
