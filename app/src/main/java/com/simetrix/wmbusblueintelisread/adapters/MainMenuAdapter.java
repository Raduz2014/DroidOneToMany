package com.simetrix.wmbusblueintelisread.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simetrix.wmbusblueintelisread.R;
import com.simetrix.wmbusblueintelisread.contracts.AppMenuItem;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MainMenuAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<AppMenuItem> dataItems;

    public MainMenuAdapter(Context ctx, ArrayList<AppMenuItem> menuItems){
        mContext = ctx;
        dataItems = menuItems;
    }

    @Override
    public int getCount() {
        return dataItems.size();
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
        final AppMenuItem itemMenu = dataItems.get(position);
        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.homemenu_item, null);
            final ImageView imgViewIcon = (ImageView)convertView.findViewById(R.id.item_image);
            final TextView tvIconTitle = (TextView) convertView.findViewById(R.id.item_text);

            final ViewHolder viewholder = new ViewHolder(imgViewIcon,tvIconTitle);
            convertView.setTag(viewholder);
        }

        final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.imgViewIcon.setImageDrawable(itemMenu.imgIcon);
        viewHolder.txtViewTitle.setText(itemMenu.title);
        return convertView;
    }

    private class ViewHolder {
        private final ImageView imgViewIcon;
        private final TextView txtViewTitle;

        public ViewHolder(ImageView imageViewIcon,TextView titleTextView) {
            this.imgViewIcon = imageViewIcon;
            this.txtViewTitle = titleTextView;
        }
    }
}
