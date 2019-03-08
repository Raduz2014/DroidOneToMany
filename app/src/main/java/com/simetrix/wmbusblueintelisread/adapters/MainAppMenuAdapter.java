package com.simetrix.wmbusblueintelisread.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simetrix.wmbusblueintelisread.contracts.AppMenuItem;

public class MainAppMenuAdapter extends BaseAdapter {
    private final Context mContext;
    private final AppMenuItem[] dataItems;

    public MainAppMenuAdapter(Context mContext, AppMenuItem[] menuItems) {
        this.mContext = mContext;
        this.dataItems = menuItems;
    }

    @Override
    public int getCount() {
        return dataItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;
    }
}
