package com.gmonetix.wallpapershdultimate.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmonetix.wallpapershdultimate.R;

import java.util.List;

public class ColorsListAdapter extends BaseAdapter{
    private Context _context;
    private List<ColorsHelpAdapter> mColorsList;

    public ColorsListAdapter(Activity context, List<ColorsHelpAdapter> mColorsList) {
        this.mColorsList = mColorsList;
        this._context = context;
    }

    @Override
    public int getCount() {
        return mColorsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mColorsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = View.inflate(_context,R.layout.list_colors_sample_style,null);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.listView_colors_sample);
        TextView textView = (TextView) rowView.findViewById(R.id.listView_colors_sample_name);
        Font font = new Font();
        font.setFont(_context,textView);
        imageView.setBackgroundColor(ContextCompat.getColor(_context,mColorsList.get(position).getColors()));
        textView.setText(mColorsList.get(position).getColorsName());
        return rowView;
    }
}
