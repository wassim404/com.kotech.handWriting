package com.kotech.njoscribe;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.*;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotech.njoscribe.utils.DataBase;
import com.kotech.njoscribe.utils.Textnote;
import com.kotech.njoscribe.utils.WritePadFlagManager;
import com.kotech.njoscribe.utils.WritePadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class NoteActivity extends Activity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private ImageButton btnSpeak;
    private EditText txtText;
    String newString;
    private static final int CLEAR_MENU_ID = Menu.FIRST + 1;
    private static final int SETTINGS_MENU_ID = Menu.FIRST + 2;
    private static final int action_Enter = Menu.FIRST;
    private static Menu menu;
    public RecognizerService mBoundService;
    long id = 0;
    long ID;
    boolean IsSaved = false;
    String teste;
    String Title = null;
    String Body = null;
    LinearLayout recognizedTextContainer;
    TextView readyText;
    EditText mEditTitleTextView;
    DataBase db = new DataBase(this);
    ProgressDialog progress;
    boolean check = true;
    SharedPreferences prefs;
    TextView txt;
    MenuItem itemSpace;
    private boolean mRecoInit;
    private InkView inkView;
    private ServiceConnection mConnection;

    public static void updateMenuTitle(boolean isSpace) {
        MenuItem bedMenuItem = menu.findItem(R.id.action_Space);
        if (isSpace) {
            bedMenuItem.setTitle("Space");
        } else {
            bedMenuItem.setTitle("Insert");
        }
    }

    @Override
    protected void onResume() {
        if (inkView != null) {
            inkView.cleanView(true);

        }

        WritePadFlagManager.initialize(this);
        super.onResume();
    }

    public void OnBack(View v) {
        Intent in = new Intent(this, LevelsAlphabet.class);
        startActivity(in);
        finish();
        this.overridePendingTransition(R.anim.from_top, R.anim.to_bottom);
    }

    public void OnEdit(View v) {
        inkView.cleanView(true);
    }


    private void speakOut() {

        String text = readyText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        tts.setSpeechRate((float)0.8);
    }
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.FRENCH);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        readyText = (TextView) findViewById(R.id.ready_text);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("pos");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("pos");
        }

        tts = new TextToSpeech(this, this);

        btnSpeak = (ImageButton) findViewById(R.id.speechButt);

        txtText = (EditText) findViewById(R.id.txtText);

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                readyText.setText("C'est la lettre "+newString+" en miniscule ,essayer de l'écrire correctement");
                speakOut();
            }

        });


        Intent intent2 = getIntent();
        teste = intent2.getStringExtra("ids");
        if (teste != null) {
            trait trait = new trait();
            trait.execute();


        }

        readyText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                IsSaved=false;            }


        });
//        Log.i("carac",newString);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView
                .findViewById(R.id.title_text);
        mTitleTextView.setVisibility(View.GONE);
        mEditTitleTextView = (EditText) mCustomView
                .findViewById(R.id.edittexttitle);

        mEditTitleTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                IsSaved=false;            }


        });

        mEditTitleTextView.setVisibility(View.VISIBLE);
        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        ImageButton returnButton = (ImageButton) mCustomView
                .findViewById(R.id.imageView1);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ID = Save();
            }


        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        prefs = getSharedPreferences("STATE", Context.MODE_PRIVATE);
        Editor edit = prefs.edit();
        edit.putInt("state", 1);
        edit.commit();
        String lName = WritePadManager.getLanguageName();
        WritePadManager.setLanguage(lName, this);

        // initialize ink inkView class
        inkView = (InkView) findViewById(R.id.ink_view);

      //  recognizedTextContainer = (LinearLayout) findViewById(R.id.recognized_text_container);

       /* Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int screenHeight = defaultDisplay.getHeight();
        int textViewHeight = 10 * screenHeight / 100;
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, textViewHeight);
        recognizedTextContainer.setLayoutParams(params);
        inkView.setRecognizedTextContainer(recognizedTextContainer);*/
        inkView.setReadyText(readyText);
       // Button suppButt = (Button)findViewById(R.id.confirmButt);
       // suppButt.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View view) {
                mConnection = new ServiceConnection() {
                    public void onServiceConnected(ComponentName className,
                                                   IBinder service) {
                        // This is called when the connection with the service has been
                        // established, giving us the service object we can use to
                        // interact with the service. Because we have bound to a
                        // explicit
                        // service that we know is running in our own process, we can
                        // cast its IBinder to a concrete class and directly access it.
                        mBoundService = ((RecognizerService.RecognizerBinder) service)
                                .getService();
                       // Log.i("jjj","hhhhhhh");
                        mBoundService.mHandler = inkView.getHandler();

                    }

                    public void onServiceDisconnected(ComponentName className) {
                        // This is called when the connection with the service has been
                        // unexpectedly disconnected -- that is, its process crashed.
                        // Because it is running in our same process, we should never
                        // see this happen.
                        mBoundService = null;
                    }
                };
               // Log.i("fff","aaa");
                Intent in = new Intent(NoteActivity.this, RecognizerService.class);
                in.putExtra("name", "book");
                //Log.i("fff","bbb");
                bindService(in, mConnection, Context.BIND_AUTO_CREATE);
                Log.i("fff",mConnection.toString());
        Log.i("b=",inkView.b.toString());
        if(inkView.b){
            Intent i = new Intent(NoteActivity.this,  AllNote.class);
            i.putExtra("car",newString);
            startActivity(i);
            finish();
            this.overridePendingTransition(R.anim.from_top, R.anim.to_bottom);

        }
            }


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
        if (mRecoInit) {
            WritePadManager.recoFree();
        }
        mRecoInit = false;
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();

    }

    public void writeToFile(String sFileName, String sBody) {
        try {
            String path = Environment.getExternalStorageDirectory().getPath()
                    + "/" + sFileName + ".txt";
            File myFile = new File(path);
            Log.i(path, path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(sBody);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(), "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.note, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case SETTINGS_MENU_ID:

                returnHome();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                returnHome();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long Save() {
        // TODO Auto-generated method stub

        EditText edittexttitle = (EditText) findViewById(R.id.edittexttitle);
        String title = edittexttitle.getText().toString();

        if (teste != null || !check) {
            if (teste != null) {
                id = Integer.parseInt(teste);
            }
            String body = readyText.getText().toString();
            Log.i("id2", id + "");
            Textnote noteTextnote = db.getOneTextNote((int) id);

            noteTextnote.setBody(body);
            noteTextnote.setTitle(title);
            db.updateTextnote(noteTextnote);

            Toast toast = Toast.makeText(getBaseContext(),
                    "Successfully updated!", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            String body = readyText.getText().toString();
            Textnote noteTextnote = new Textnote();
            noteTextnote.setBody(body);
            noteTextnote.setTitle(title);
            id = db.addTextnote(noteTextnote);
            check = false;
            Toast toast = Toast.makeText(getBaseContext(),
                    "Successfully saved!", Toast.LENGTH_SHORT);
            toast.show();
        }
        IsSaved = true;
        return id;
    }

    private void returnHome() {
        if (!IsSaved) {

            EditText title = (EditText) findViewById(R.id.edittexttitle);
            if (teste != null) {

                saveAlertDialog(!Title.equals(mEditTitleTextView.getText().toString()) || !Body.equals(readyText.getText().toString()));
            } else {
                saveAlertDialog(!title.getText().toString().isEmpty() || !readyText.getText().toString().isEmpty());

            }
        } else {
            saveAlertDialog(false);

        }


    }

    private void saveAlertDialog(boolean isChanged) {

        final Intent in = new Intent(NoteActivity.this, LevelsAlphabet.class);
        if (isChanged) {
            final AlertDialog alert;

            AlertDialog.Builder dialog2 = new AlertDialog.Builder(NoteActivity.this);
            alert = dialog2.create();
            alert.setTitle("Save Changes ?");
            alert.setMessage("Do you want to save your changes ?");

            alert.setButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    alert.dismiss();

                    Save();
                    startActivity(in);
                    NoteActivity.this.overridePendingTransition(R.anim.from_right,
                            R.anim.to_left);
                    finish();
                }
            });

            alert.setButton2("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    alert.dismiss();
                    startActivity(in);
                    NoteActivity.this.overridePendingTransition(R.anim.from_right,
                            R.anim.to_left);
                    finish();
                }
            });

            alert.show();
        } else {
            startActivity(in);
            NoteActivity.this.overridePendingTransition(R.anim.from_right,
                    R.anim.to_left);
            finish();
        }

    }

    public void action_Enter(MenuItem item) {
        inkView.Word = readyText.getText().toString();

        readyText.setText(inkView.sendText(null, 4));
        readyText.setText(readyText.getText().length());

    }

    public void action_Space(MenuItem item) {
        if (inkView.checkCleanView) {
            inkView.Word = readyText.getText().toString();
            readyText.setText(inkView.sendText(null, 1));
            readyText.setText(readyText.getText().length());

        } else {
            inkView.cleanView(true);
            updateMenuTitle(true);

        }


    }

    public void action_Delete(MenuItem item) {
        inkView.Word = readyText.getText().toString();
        readyText.setText(inkView.sendText(null, 3));
        readyText.setText(readyText.getText().length());

    }

    public interface OnInkViewListener {
        void cleanView(boolean emptyAll);

        Handler getHandler();
    }

    private class trait extends AsyncTask<Void, Void, Void> {
        String ids;
        String titles;
        String bodys;

        protected void onPreExecute() {
            Intent intent = getIntent();
            ids = intent.getStringExtra("ids");
            progress = new ProgressDialog(NoteActivity.this);
            progress.setMessage("Loading...");
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();

            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {
            readyText.setText(bodys);
            mEditTitleTextView.setText(titles);
            if (progress.isShowing())
                progress.dismiss();
            super.onPostExecute(result);
            readyText.setText(readyText.getText().length());

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            int med = Integer.parseInt(ids);
            Textnote text = new Textnote();
            text = db.getOneTextNote(med);
            bodys = text.getBody();
            titles = text.getTitle();
            Body = bodys.toString();
            Title = titles.toString();
            return null;
        }
    }
}
