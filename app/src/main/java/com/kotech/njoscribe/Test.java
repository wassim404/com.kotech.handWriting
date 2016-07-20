package com.kotech.njoscribe;

import android.app.Activity;
import android.os.Bundle;

import com.kotech.njoscribe.utils.DataBase;
import com.kotech.njoscribe.utils.Textnote;

public class Test extends Activity {
    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Textnote txt1 = new Textnote(1, "name", "title", "date", "body");
        db.addTextnote(txt1);

        //	db.deleteTextnote(txt1);
//		
//		List<Textnote> textnotes = new LinkedList<Textnote>();
//		textnotes = db.getAlltextnote();
//		int k = textnotes.size();
//		Log.i("bayern1",k+"");
//
//		Log.i("bayern2",textnotes.get(0).getTitle());
//		
//		Textnote txt2 = new Textnote(1, "mohamed", "benamor", "date", "body");
//		db.updateTextnote(txt2);
    }
}
