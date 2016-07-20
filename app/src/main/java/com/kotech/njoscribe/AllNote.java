package com.kotech.njoscribe;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kotech.njoscribe.utils.DataBase;
import com.kotech.njoscribe.utils.ExportGenerator;
import com.kotech.njoscribe.utils.Textnote;

import java.util.ArrayList;

public class AllNote extends Activity {

    DataBase db = new DataBase(this);
    ProgressDialog progress;
    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> body = new ArrayList<String>();
    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mTitleTextView = (TextView) mCustomView
                .findViewById(R.id.title_text);
        mTitleTextView.setText("All Notes");

        ImageButton imageButton = (ImageButton) mCustomView
                .findViewById(R.id.imageButton);
        ImageButton addNoteButton = (ImageButton) mCustomView
                .findViewById(R.id.addNote);

        imageButton.setVisibility(View.INVISIBLE);
        addNoteButton.setVisibility(View.VISIBLE);

        ImageButton returnButton = (ImageButton) mCustomView
                .findViewById(R.id.imageView1);
        returnButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
        addNoteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent in = new Intent(AllNote.this, NoteActivity.class);
                startActivity(in);
                finish();
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        trait trait = new trait();
        trait.execute();
        l1 = (ListView) findViewById(R.id.ListNote);

        l1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String ids = ((TextView) view.findViewById(R.id.text3)).getText().toString();

                // Starting new intent
                Intent in = new Intent(AllNote.this, NoteActivity.class);
                in.putExtra("ids", ids);
                startActivity(in);
                finish();
                //	Toast.makeText(getApplicationContext(),ids,Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void returnHome() {

        Intent in = new Intent(AllNote.this, MainActivity.class);
        startActivity(in);
        AllNote.this.overridePendingTransition(R.anim.from_right,
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

    class dataListAdapter extends BaseAdapter {


        ArrayList<String> id1;
        ArrayList<String> name1;
        ArrayList<String> title1;
        ArrayList<String> date1;
        ArrayList<String> body1;

        public dataListAdapter() {
            // TODO Auto-generated constructor stub
            id1 = null;
            name1 = null;
            title1 = null;
            date1 = null;
            body1 = null;

        }

        public dataListAdapter(ArrayList<String> text1, ArrayList<String> text2, ArrayList<String> text3,
                               ArrayList<String> text4, ArrayList<String> text5) {
            // TODO Auto-generated constructor stub

            id1 = text1;
            name1 = text2;
            title1 = text3;
            date1 = text4;
            body1 = text5;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return id1.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.list_single_note, parent, false);
            final TextView id2;
            final TextView name2;
            final TextView date2;

            id2 = (TextView) row.findViewById(R.id.text3);
            name2 = (TextView) row.findViewById(R.id.text1);
            date2 = (TextView) row.findViewById(R.id.text2);

            id2.setText(id1.get(position));
            name2.setText(name1.get(position));
            date2.setText(date1.get(position));
            l1.setOnItemLongClickListener(new OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               final int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    final CharSequence[] items = {"Share note", "Export note", "Delete note"};
                    TextView id = (TextView) arg1.findViewById(R.id.text3);

                    final int idint = Integer.parseInt(id.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(AllNote.this);
                    builder.setTitle("Make your choice");

                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            dialog.dismiss();
                            // Do something with the selection
                            Textnote selectedItem = db.getOneTextNote(idint);
                            switch (item) {
                                case 0:
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    if (!selectedItem.getTitle().isEmpty()) {
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, selectedItem.getTitle() + "\n" + selectedItem.getBody());
                                    } else {
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, selectedItem.getBody());
                                    }
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                    break;
                                case 1:
                                    ExportGenerator.PdfGenerator.newPDF(selectedItem.getTitle(), selectedItem.getBody());

                                    //  ExportGenerator.WordGenerator.newWordDoc(selectedItem.getTitle(), selectedItem.getBody());

                                    Toast.makeText(getApplicationContext(), "Successfully exported!", Toast.LENGTH_SHORT).show();

                                    break;
                                case 2:
                                    final AlertDialog alert;

                                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(AllNote.this);
                                    alert = dialog2.create();
                                    alert.setTitle("Delete " + selectedItem.getTitle());
                                    alert.setMessage("Are you sure you want to delete this ?");

                                    alert.setButton("Yes", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            db.deleteTextnote(idint);
                                            Intent in = new Intent(getApplicationContext(), AllNote.class);
                                            startActivity(in);

                                            finish();
                                        }
                                    });

                                    alert.setButton2("No", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            alert.dismiss();
                                        }
                                    });

                                    alert.show();


                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
            });

            return (row);
        }

    }

    private class trait extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            progress = new ProgressDialog(AllNote.this);
            progress.setMessage("Loading...");
            progress.setIndeterminate(false);
            progress.setCancelable(false);
            progress.show();
            super.onPreExecute();
        }

        protected void onPostExecute(Void result) {
            l1.setAdapter(new dataListAdapter(id, name, title, date, body));
            if (progress.isShowing())
                progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<Textnote> textnotes = new ArrayList<Textnote>();
            textnotes = db.getAlltextnote();
            int k = textnotes.size();
            for (int i = 0; i < k; i++)
                id.add(textnotes.get(i).getId() + "");

            for (int i = 0; i < k; i++)
                name.add(textnotes.get(i).getTitle());

            for (int i = 0; i < k; i++)
                title.add(textnotes.get(i).getTitle());

            for (int i = 0; i < k; i++)
                date.add(textnotes.get(i).getDate());

            for (int i = 0; i < k; i++)
                body.add(textnotes.get(i).getBody());


            return null;
        }
    }

}