package com.kotech.njoscribe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kotech.njoscribe.utils.WritePadManager;
import com.phatware.android.RecoInterface.WritePadAPI;

import java.util.LinkedList;

@SuppressLint("NewApi")
public class InkView2 extends View implements RecoTest.OnInkViewListener {
    private static final float TOUCH_TOLERANCE = 2;
    private final float GRID_GAP = 20;
    public boolean test = false;
    public ImageButton refresh;
    MenuItem item;
    String Word = "";
    boolean checkCleanView = true;
    int sdk = android.os.Build.VERSION.SDK_INT;
    SharedPreferences pref;
    int x1, x2, y1, y2;
    int w, h;
    RectF rect = new RectF(0, 0, w, h);
    private Path mPath;
    private int mCurrStroke;
    private Paint mPaint;
    private Paint mResultPaint;
    private LinearLayout recognizedTextContainer;
    private TextView readyText;
    private LinkedList<Path> mPathList;
    private float mX, mY;
    private boolean mMoved;
    private String lastResult = "";
    Button suppButt;

    // Define the Handler that receives messages from the thread and update the
    // progress
    public final Handler mHandler = new Handler() {
        @SuppressLint("ResourceAsColor")
        public void handleMessage(Message msg) {
            NoteActivity2 rt = (NoteActivity2) getContext();
            String carac=  rt.newString;

         /*   recognizedTextContainer.removeAllViews();
            refresh = new ImageButton(getContext());
            refresh.setImageResource(R.drawable.ic_action_eraser);

            refresh.setBackground(null);
            refresh.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            Log.i("refreshsize", refresh.getWidth() + "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    recognizedTextContainer.getWidth() - 60, ViewGroup.LayoutParams.FILL_PARENT);

            Bundle b = msg.getData();
            if (lastResult != "" || lastResult != null) {
                lastResult = b.getString("result");
            }

            HorizontalScrollView scrollView = new HorizontalScrollView(
                    getContext());

            LinearLayout AlternativeGroup = new LinearLayout(
                    getContext());
            recognizedTextContainer.addView(scrollView);

            scrollView.addView(AlternativeGroup);

            int words = WritePadManager.recoResultColumnCount();
            for (int w = 0; w < words; w++) {
                int alternatives = WritePadManager.recoResultRowCount(w);
                if (alternatives > 0) {

                    final CharSequence[] alternativesCollection = new CharSequence[alternatives];
                    for (int a = 0; a < alternatives; a++) {
                        String word = WritePadManager.recoResultWord(w, a);
                        alternativesCollection[a] = word;
                    }
                    lastResult = (WritePadManager.recoResultWord(w, 0));
                    Log.i("mytag",WritePadManager.recoResultWord(0, 0));

                    // word.setText(WritePadManager.recoResultWord(w, 0));
                    // readyText.setText(sendText(lastResult, 0));

                    readyText.setSelection(readyText.getText().length());


                    for (CharSequence charSequence : alternativesCollection) {
                        final TextView word = new TextView(getContext());
                        //  word.setPadding(20, 20, 20, 20);
                        word.setPadding(20, 0, 20, 0);
                        word.setHeight(recognizedTextContainer.getHeight());
                        word.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                        word.setTextSize(18);
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            word.setBackgroundDrawable(getResources()
                                    .getDrawable(R.drawable.shape));
                        } else {
                            word.setBackground(getResources().getDrawable(
                                    R.drawable.shape));
                        }
                        word.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lastResult = (word.getText().toString());
                                NoteActivity.updateMenuTitle(true);
                                cleanView(true);
                            }
                        });*/

            w=0;

            if((WritePadManager.recoResultWord(w, 0)).toString().equals(carac.toUpperCase())){

                //  Log.i("eeee",WritePadManager.recoResultWord(w, 0));
                Toast.makeText(getContext(), "Bravo", Toast.LENGTH_SHORT).show();
                cleanView(true);
                lastResult = (WritePadManager.recoResultWord(w, 0));
                readyText.setText(sendText(lastResult, 0));
                w++;

            }




                       /* word.setText(charSequence);
                        AlternativeGroup.addView(word);
                        scrollView.setLayoutParams(params);

                    }
                    // mPaint.clearShadowLayer();
                    // cleanView(true);

                }
            }
            //refresh.setLayoutParams(params);

            refresh.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    cleanView(false);
                    NoteActivity.updateMenuTitle(true);
                }
            });
            recognizedTextContainer.addView(refresh);*/

        }

    };
    private Path gridpath = new Path();
    private int state;
    private Paint nPaint;

    public InkView2(Context context) {
        this(context, null, 0);
    }

    public InkView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public InkView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        pref = context.getSharedPreferences("STATE", Context.MODE_PRIVATE);
        state = pref.getInt("state", 0);
        mPath = new Path();
        mPathList = new LinkedList<Path>();
        mCurrStroke = -1;
        mPaint = new Paint();
        nPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF0000FF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);

        mResultPaint = new Paint();
        mResultPaint.setTextSize(32);
        mResultPaint.setAntiAlias(true);
        mResultPaint.setARGB(0xff, 0x00, 0x00, 0x00);
    }

    public void cleanView(boolean emptyAll) {
        WritePadManager.recoResetInk();
        mCurrStroke = -1;
        mPathList.clear();
        mPath.reset();
        // recognizedTextContainer.removeAllViews();
        if (emptyAll) {
            readyText.setText(sendText(lastResult, 0));
        }
//        readyText.setText(readyText.getText().length());
        checkCleanView = true;
        invalidate();
    }


    public Handler getHandler() {
        return mHandler;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        rect = new RectF(20, 20, w - 20, h - 20);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(0xFF0a0a0a);
        mPaint.setStrokeWidth(1);
        x1 = canvas.getWidth() / 8;
        x2 = canvas.getWidth() * 7 / 8;
        y1 = canvas.getHeight() / 6;
        y2 = canvas.getHeight() * 5 / 6;
        for (float y = GRID_GAP; y < canvas.getHeight(); y += GRID_GAP) {
            gridpath.reset();
            gridpath.moveTo(0, y);
            gridpath.lineTo(canvas.getWidth(), y);
            canvas.drawPath(gridpath, mPaint);
        }
        mPaint.setColor(0xFF0d0d0d);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawRoundRect(rect, 15, 15, mPaint);
        nPaint.setColor(0x220d0d0d);
        nPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rect, 15, 15, nPaint);
        mPaint.setColor(0xFF0000FF);
        mPaint.setStrokeWidth(3);

        for (Path aMPathList : mPathList) {
            canvas.drawPath(aMPathList, mPaint);
        }
        canvas.drawPath(mPath, mPaint);
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        mMoved = false;
        mCurrStroke = WritePadManager.recoNewStroke(3, 0xFF0000FF);
        if (mCurrStroke >= 0) {
            int res = WritePadManager.recoAddPixel(mCurrStroke, x, y);
            if (res < 1) {
                // TODO: error
            }
        }
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mMoved = true;
            mX = x;
            mY = y;
            if (mCurrStroke >= 0) {
                int res = WritePadManager.recoAddPixel(mCurrStroke, x, y);
                if (res < 1) {
                    // TODO: error
                }
            }
        }
    }

    private void touch_up() {
        // stopRecognizer();

        mCurrStroke = -1;
        if (!mMoved)
            mX++;
        mMoved = false;
        mPath.lineTo(mX, mY);
        mPathList.add(mPath);
        mPath = new Path();
        invalidate();


        NoteActivity2 rt = (NoteActivity2) getContext();
        int nStrokeCnt = WritePadManager.recoStrokeCount();
        if (nStrokeCnt == 1) {
            int gesturetype = WritePadAPI.GEST_DELETE + WritePadAPI.GEST_RETURN
                    + WritePadAPI.GEST_SPACE + WritePadAPI.GEST_TAB
                    + WritePadAPI.GEST_BACK + WritePadAPI.GEST_UNDO;
            gesturetype = WritePadManager.recoGesture(gesturetype);
            if (gesturetype != WritePadAPI.GEST_NONE) {
                // TODO: process gesture
                WritePadManager.recoDeleteLastStroke();
                mPathList.removeLast();
                return;
            }
        } else if (nStrokeCnt > 1) {
            int gesturetype = WritePadAPI.GEST_CUT + WritePadAPI.GEST_BACK
                    + WritePadAPI.GEST_RETURN;
            gesturetype = WritePadManager.recoGesture(gesturetype);
            if (gesturetype != WritePadAPI.GEST_NONE
                    && gesturetype != WritePadAPI.GEST_BACK) {
                // TODO: process gesture
                WritePadManager.recoDeleteLastStroke();
                mPathList.removeLast();
                switch (gesturetype) {
                    // case WritePadAPI.GEST_BACK:
                    case WritePadAPI.GEST_BACK_LONG:
                        WritePadManager.recoDeleteLastStroke();
                        mPathList.removeLast();
                        if (WritePadManager.recoStrokeCount() < 1) {
                            recognizedTextContainer.removeAllViews();
                        }

                        rt.mBoundService.dataNotify(WritePadManager
                                .recoStrokeCount());

                        return;

                    case WritePadAPI.GEST_CUT:
                        cleanView(false);
                        return;

                    case WritePadAPI.GEST_RETURN:
                        cleanView(false);
                        return;
                    default:
                        break;
                }
            }
        }

        // notify recognizer thread about data availability
        rt.mBoundService.dataNotify(nStrokeCnt);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (x < rect.left || x > rect.right)
            return false;
        if (y < rect.top || y > rect.bottom)
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                NoteActivity.updateMenuTitle(false);
                Word = readyText.getText().toString();
                checkCleanView = false;

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0, n = event.getHistorySize(); i < n; i++) {
                    touch_move(event.getHistoricalX(i), event.getHistoricalY(i));
                }

                touch_move(x, y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                touch_up();

                invalidate();
                break;

        }
        return true;
    }

    public void setRecognizedTextContainer(LinearLayout recognizedTextContainer) {
        this.recognizedTextContainer = recognizedTextContainer;

    }

    public void setReadyText(TextView readyText) {
        this.readyText = readyText;
    }


    public String sendText(String pivot, int option) {

        switch (option) {
            case 0:
                Word += pivot;
                break;
            case 1:
                Word += " ";
                break;
            case 2:
                Word = Word.substring(0, Word.length() - pivot.length()) + pivot;
                break;
            case 3:
                if (Word.length() != 0) {
                    Word = Word.substring(0, Word.length() - 1);

                }

                Log.i("Word", Word);
                break;
            case 4:
                Word += "\r\n";
                break;
            default:
                break;
        }

        return Word;

    }
}
