package com.kotech.njoscribe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toolbar;

import com.kotech.njoscribe.circularFloatingButton.ImageAdapter;
import com.kotech.njoscribe.utils.DBHelper;

import java.util.ArrayList;

public class LevelsAlphabet extends Activity {
    //Integer[] a=new Integer[50];
    private DBHelper mydb;
    String[] alphaTab = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9"};
  //  private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet_activity);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        // for(int i=1;i<25;i++){
            /*Cursor rs = mydb.getData(0);
            if(rs.moveToFirst()){
        Log.i("suc","qqqqq");
            }
        if( rs != null ) {
            String etat = rs.getString(rs.getColumnIndex(DBHelper.LEVELS_COLUMN_PASSED));
            if(etat.equals("0")){
                Log.i("success","yes");
            }

        }
        else Log.i("zzz","eeee");
        rs.close();*/
       // }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.i("lk",alphaTab[position]);
                Intent in = new Intent(LevelsAlphabet.this, NoteActivity.class);
                in.putExtra("pos",alphaTab[position]);
                startActivity(in);
                finish();
            //    this.overridePendingTransition(R.anim.from_top, R.anim.to_bottom);

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_level, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
