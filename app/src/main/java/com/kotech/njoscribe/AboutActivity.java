package com.kotech.njoscribe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String title;

        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView
                .findViewById(R.id.title_text);
        mTitleTextView.setText("About");

        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);

        imageButton.setVisibility(View.INVISIBLE);
        ImageButton returnButton = (ImageButton) mCustomView
                .findViewById(R.id.imageView1);
        returnButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                returnHome();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            title = "NJO version " + info.versionName;
        } catch (NameNotFoundException unused) {
            title = "NJO";
        }
        TextView titleView = (TextView) findViewById(R.id.about_title);
        titleView.setText(title);
        TextView contentView = (TextView) findViewById(R.id.about_content);

    }

    private void returnHome() {

        Intent in = new Intent(AboutActivity.this, MainActivity.class);
        startActivity(in);
        AboutActivity.this.overridePendingTransition(R.anim.from_right,
                R.anim.to_left);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                returnHome();
        }
        return true;
    }


}
