package com.kotech.njoscribe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.kotech.njoscribe.utils.MainSettings;

public class MainActivity extends Activity {

    ListView list;
    String[] web = {"All Notes", "Drawing", "Settings", "About"};
    Integer[] imageId = {R.drawable.ic_action_view_as_list, R.drawable.ic_action_edit,
            R.drawable.ic_action_settings, R.drawable.ic_action_about};
    Integer[] imageIdHighlited = {R.drawable.ic_action_view_as_list_highlight, R.drawable.ic_action_edit_highlight,
            R.drawable.ic_action_settings_highlight,
            R.drawable.ic_action_about_highlight};
    private boolean mCanQuit = false;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomList adapter = new CustomList(MainActivity.this, web, imageId);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ImageView imageView = (ImageView) view.findViewById(R.id.img);
                imageView.setImageResource(imageIdHighlited[position]);
                Intent in;
                switch (position) {
                    case 0:
                        in = new Intent(MainActivity.this, AllNote.class);
                        startActivity(in);
                        MainActivity.this.overridePendingTransition(
                                R.anim.from_left, R.anim.to_right);
                        finish();

                        break;
                    case 1:
                        in = new Intent(MainActivity.this, MarkersActivity.class);
                        startActivity(in);
                        MainActivity.this.overridePendingTransition(
                                R.anim.from_left, R.anim.to_right);
                        finish();
                        break;

                    case 2:
                        in = new Intent(MainActivity.this, MainSettings.class);
                        startActivity(in);
                        MainActivity.this.overridePendingTransition(
                                R.anim.from_left, R.anim.to_right);
                        finish();
                        break;
                    case 3:
                        in = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(in);
                        MainActivity.this.overridePendingTransition(
                                R.anim.from_left, R.anim.to_right);
                        finish();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (mCanQuit) {
            finish();
            return;
        }

        Toast.makeText(this, R.string.back_again_to_quit, Toast.LENGTH_SHORT).show();
        mCanQuit = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCanQuit = false;
            }
        }, 3000);
    }
}