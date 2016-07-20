package com.kotech.njoscribe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.kotech.njoscribe.utils.DataBase;

public class Splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Intent mainIntent;
    private boolean mCanQuit = false;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBase dataBase = new DataBase(this);

        setContentView(R.layout.activity_splash);
        /*
         * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */

        if (dataBase.checkDataBase(this)) {
            mainIntent = new Intent(Splash.this, MainActivity.class);
        } else {
            mainIntent = new Intent(Splash.this, QRdecoder.class);

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
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
}