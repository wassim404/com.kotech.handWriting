package com.kotech.njoscribe.circularFloatingButton;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.kotech.njoscribe.R;
import com.kotech.njoscribe.utils.DBHelper;

import java.util.ArrayList;

/**
 * Created by Wassim on 01/07/2016.
 */
public class ImageAdapter extends BaseAdapter {

private Context mContext;

public ImageAdapter(Context c) {
        mContext = c;
        }

public int getCount() {
        return mThumbIds.length;
        }

public Object getItem(int position) {
        return null;
        }

public long getItemId(int position) {
        return 0;
        }

// create a new ImageView for each item referenced by the Adapter
public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
        // if it's not recycled, initialize some attributes
        imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);
        } else {
        imageView = (ImageView) convertView;
        }
        DBHelper mydb;
        mydb = new DBHelper(mContext);
        ArrayList<String> a = mydb.getAllCotacts();
        //Log.i("kkk",a.get(0).toString());
        // if(a.get(0).equals(null)){
        if (mydb.getAllCotacts().toString().equals("[]"))
                for (int i = 0; i < 35; i++) {
                        mydb.insertContact("n");
                }
        //}
        Log.i("hhh", mydb.getAllCotacts().toString());
        for (int i = 0; i < 35; i++) {
                if (mydb.getAllCotacts().get(i).equals("n")) {
                        mThumbIds[i]=R.drawable.sample_2;
                }
                else  mThumbIds[i]=R.drawable.mute;


        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
        }




    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2, R.drawable.sample_2,
            R.drawable.sample_2
    };


}
