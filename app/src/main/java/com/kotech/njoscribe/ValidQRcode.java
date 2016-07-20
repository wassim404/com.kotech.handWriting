package com.kotech.njoscribe;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.Toast;

import com.kotech.njoscribe.utils.DataBase;


public class ValidQRcode extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private boolean mCanQuit = false;
    private Toast toast;
    private String DATABASE_NAME = "NJO_DATABASE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_valid_qrcode);
        DataBase dataBase = new DataBase(this);

        if (!dataBase.checkDataBase(this)) {
            SQLiteDatabase myDB = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(ValidQRcode.this, MainActivity.class);
                ValidQRcode.this.startActivity(mainIntent);
                ValidQRcode.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
        }
        return true;
    }
}