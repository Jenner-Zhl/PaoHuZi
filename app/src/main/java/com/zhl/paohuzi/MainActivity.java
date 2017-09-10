package com.zhl.paohuzi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.paohuzi.PaoHuZi;
import com.paohuzi.TianKouResult;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<TianKouResult> results = PaoHuZi.calculate();
        ResultsViewAdapter adapter = new ResultsViewAdapter(results, this);

        ListView mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(adapter);
    }
}
