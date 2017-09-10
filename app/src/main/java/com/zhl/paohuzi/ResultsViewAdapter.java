package com.zhl.paohuzi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.paohuzi.TianKouResult;

import java.util.List;

/**
 *
 * Created by  on 2017/9/10.
 */

class ResultsViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<TianKouResult> mResults;
    ResultsViewAdapter(List<TianKouResult> results, Context context) {
        super();
        mResults = results;
        mInflater = LayoutInflater.from(context);
    }

    private int[] mIds = {
            R.id.men1, R.id.men2, R.id.men3, R.id.men4, R.id.men5, R.id.men6, R.id.men7
    };

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TianKouResult data = mResults.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.adapter, null);
            for (int i = 0; i < 7; i++) {
                holder.views[i] = (MenView) convertView.findViewById(mIds[i]);
            }

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        for (int i=0; i<7; i++) {
            holder.views[i].setData(data.mMens[i]);
        }
        return convertView;
    }
}

class Holder {
    MenView[] views = new MenView[7];
}
