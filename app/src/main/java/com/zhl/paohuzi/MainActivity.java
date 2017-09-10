package com.zhl.paohuzi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.paohuzi.PaoHuZi;
import com.paohuzi.TianKouResult;
import com.paohuzi.Zi;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "PaoHuZi";
    private LinearLayout mLine1, mLine2, mLine3, mLine4;
    private int[] mData = {12, 12, 12, 12, 17, 17, 17, 20, 20, 10, 10, 9, 8, 7, 7, 6, 5, 4, 3, 2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrance);

        initView();
    }

    private void initView() {
        mLine1 = (LinearLayout) findViewById(R.id.l1);
        mLine2 = (LinearLayout) findViewById(R.id.l2);
        mLine3 = (LinearLayout) findViewById(R.id.l3);
        mLine4 = (LinearLayout) findViewById(R.id.l4);
        int i=1;
        for(; i< 11; i++) {
            ZiView v = new ZiView(this, new Zi(i));
            mLine1.addView(v);
            v.setOnClickListener(this);
        }
        while (i<21) {
            ZiView v = new ZiView(this, new Zi(i));
            mLine2.addView(v);
            v.setOnClickListener(this);
            i++;
        }
    }

    private void addZiView(ZiView v) {
        ZiView ziView = new ZiView(this, v);
        if(mLine3.getChildCount() < 10) {
            mLine3.addView(ziView);
        } else if (mLine4.getChildCount() < 10) {
            mLine4.addView(ziView);
        }

        if(mLine4.getChildCount() == 10) {
            findViewById(R.id.bt).setEnabled(true);
        }
    }


    public void showResult(View v) {

        List<TianKouResult> results = getResults();
        if (results == null || results.size() == 0) {
            Toast.makeText(this, R.string.noTianKou, Toast.LENGTH_LONG).show();
            return;
        }
        setContentView(R.layout.activity_main);
        ResultsViewAdapter adapter = new ResultsViewAdapter(results, this);

        ListView mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(adapter);
    }

    private List<TianKouResult> getResults() {
        int i=0;
        while (i<10) {
            ZiView v = (ZiView) mLine3.getChildAt(i);
            mData[i] = v.getData().getId();
            i++;
        }
        while (i<20) {
            ZiView v = (ZiView) mLine4.getChildAt(i - 10);
            mData[i] = v.getData().getId();
            i++;
        }
        return PaoHuZi.calculate(mData);
    }

    @Override
    public void onClick(View v) {
        addZiView((ZiView) v);
        if (v.getTag() == null) {
            Log.d(TAG, "onClick: NULL");
            int i = 1;
            v.setTag(i);
        } else {
            int i = (int) v.getTag();
            if(i < 3) {
                i++;
                v.setTag(i);
            } else {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.list) != null) {
            setContentView(R.layout.entrance);
            initView();
            return;
        }
        super.onBackPressed();
    }
}
