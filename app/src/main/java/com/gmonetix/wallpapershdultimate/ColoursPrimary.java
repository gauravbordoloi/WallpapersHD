package com.gmonetix.wallpapershdultimate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gmonetix.wallpapershdultimate.app.ColorsArray;
import com.gmonetix.wallpapershdultimate.helper.ColorsHelpAdapter;
import com.gmonetix.wallpapershdultimate.helper.ColorsListAdapter;
import java.util.ArrayList;
import java.util.List;

public class ColoursPrimary extends Fragment {

    private static final String COLOR_NAME = "color_name";
    private static final String COLOR_ID = "color_id";
    private ListView listView;
    private ColorsListAdapter colorsListAdapter;
    private List<ColorsHelpAdapter> mColorsList;

    public ColoursPrimary() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colours, container, false);

        final int[] colorsPrimary = ColorsArray.colorsPrimary;
        final String[] colorsPrimaryName = ColorsArray.colorsPrimaryName;

        listView = (ListView) view.findViewById(R.id.listview_colors);
        mColorsList = new ArrayList<>();
        for(int i=0;i<colorsPrimary.length;i++) {
            mColorsList.add(new ColorsHelpAdapter(colorsPrimary[i],colorsPrimaryName[i]));
        }
        colorsListAdapter = new ColorsListAdapter(getActivity(),mColorsList);
        listView.setAdapter(colorsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == (mColorsList.size()-2) || position == (mColorsList.size()-1)){
                    Intent intent = new Intent(getActivity(),ColorPaletteActivity.class);
                    intent.putExtra(COLOR_ID,colorsPrimary[position]);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ColoursSecondary.class);
                    intent.putExtra(COLOR_NAME, colorsPrimaryName[position]);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
}
