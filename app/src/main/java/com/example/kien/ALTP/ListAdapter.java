package com.example.kien.ALTP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.kien.ALTP.Object.ObjectHighScore;

import java.util.ArrayList;

/**
 * Created by Giang on 4/28/2016.
 */
public class ListAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<ObjectHighScore> arr;
    public ListAdapter(Context context, ArrayList<ObjectHighScore> arr) {
        super(context, R.layout.adapter, arr);
        this.context = context;
        this.arr = arr;
    }
    public static class ViewHolder {
        TextView textView, textView2, textView3;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder vh;
        if (rowView == null) {
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflate.inflate(R.layout.adapter, null);
            vh = new ViewHolder();
            vh.textView = (TextView) rowView.findViewById(R.id.textView);
            vh.textView2 = (TextView) rowView.findViewById(R.id.textView2);
            vh.textView3 = (TextView) rowView.findViewById(R.id.textView3);
            rowView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ObjectHighScore o = arr.get(position);
        vh.textView.setText(position+1+"");
        vh.textView2.setText(o.getName());
        vh.textView3.setText(o.getScore()+"");
        return rowView;
    }
}
