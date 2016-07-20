package com.kotech.njoscribe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.QRCodeReaderView.Utils.VerifyQRCode;
import com.QRCodeReaderView.Utils.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.QRCodeReaderView.Utils.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;

public class QRdecoder extends Activity implements OnQRCodeReadListener {
    boolean CheckOnce = true;
    private TextView myTextView;
    private QRCodeReaderView mydecoderview;
    private ImageView line_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrdecoder);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        myTextView = (TextView) findViewById(R.id.exampleTextView);

        line_image = (ImageView) findViewById(R.id.red_line_image);

        TranslateAnimation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0.5f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(-1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        line_image.setAnimation(mAnimation);

    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        myTextView.setVisibility(View.VISIBLE);
        if (VerifyQRCode.VerifySerial(text.toUpperCase()) && CheckOnce) {
            myTextView.setText("Valid Code");
            myTextView.setTextColor(Color.GREEN);
            mydecoderview.setVisibility(View.GONE);
            Intent mainIntent = new Intent(QRdecoder.this, ValidQRcode.class);
            QRdecoder.this.startActivity(mainIntent);
            QRdecoder.this.finish();
            CheckOnce = false;
        } else {
            CheckOnce = true;
            myTextView.setText("Invalid Code");
            myTextView.setTextColor(Color.RED);
        }
    }


    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
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
