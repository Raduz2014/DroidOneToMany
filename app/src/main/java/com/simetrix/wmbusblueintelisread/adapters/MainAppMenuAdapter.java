package com.simetrix.wmbusblueintelisread.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simetrix.wmbusblueintelisread.R;
import com.simetrix.wmbusblueintelisread.contracts.AppMenuItem;

import java.util.ArrayList;

public class MainAppMenuAdapter extends ArrayAdapter<AppMenuItem> {
    public MainAppMenuAdapter(Context mContext, ArrayList<AppMenuItem> menuItems) {
        super(mContext, 0, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppMenuItem menuItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.homemenu_item, parent, false);
        }

        ImageView img = (ImageView)convertView.findViewById(R.id.item_image);
        TextView  txt = (TextView)convertView.findViewById(R.id.item_text);
        img.setImageDrawable(menuItem.imgIcon);
        txt.setText(menuItem.title);
        return convertView;
    }
}
