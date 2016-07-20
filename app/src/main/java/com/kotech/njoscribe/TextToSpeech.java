package com.kotech.njoscribe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by Wassim on 11/07/2016.
 */
@TargetApi(Build.VERSION_CODES.DONUT)
public class TextToSpeech extends Activity implements
        android.speech.tts.TextToSpeech.OnInitListener{


        private android.speech.tts.TextToSpeech tts;
        private Button btnSpeak;
        private EditText txtText;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            tts = new android.speech.tts.TextToSpeech(this, this);

            btnSpeak = (Button) findViewById(R.id.btnSpeak);

            txtText = (EditText) findViewById(R.id.txtText);

            // button on click event
            btnSpeak.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    speakOut();
                }

            });
        }

        @Override
        public void onDestroy() {
            // Don't forget to shutdown tts!
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
            super.onDestroy();
        }

        @Override
        public void onInit(int status) {

            if (status == android.speech.tts.TextToSpeech.SUCCESS) {

                int result = tts.setLanguage(Locale.FRENCH);

                if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA
                        || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "This Language is not supported");
                } else {
                    btnSpeak.setEnabled(true);
                    speakOut();
                }

            } else {
                Log.e("TTS", "Initilization Failed!");
            }

        }

        private void speakOut() {

            String text = txtText.getText().toString();

            tts.speak(text, android.speech.tts.TextToSpeech.QUEUE_FLUSH,null);
        }
    }

