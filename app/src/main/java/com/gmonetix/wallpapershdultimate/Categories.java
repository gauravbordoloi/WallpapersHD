package com.gmonetix.wallpapershdultimate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.gmonetix.wallpapershdultimate.app.AppController;
import com.gmonetix.wallpapershdultimate.helper.NavDrawerListAdapter;
import com.gmonetix.wallpapershdultimate.picasa.Category;
import java.util.ArrayList;
import java.util.List;

public class Categories extends Fragment {

    List<Category> albumsList;
    ListView mDrawerList;
    ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private static final String bundleAlbumId = "albumId";
    private static final String bundleAlbumTitle = "albumTitle";

    public Categories() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        mDrawerList = (ListView) view.findViewById(R.id.listView_categories);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // Getting the albums from shared preferences
        albumsList = AppController.getInstance().getPrefManger().getCategories();

        // Loop through albums in add them to navigation drawer adapter
        for (Category a : albumsList) {
            navDrawerItems.add(new NavDrawerItem(a.getId(), a.getTitle()));
        }
        adapter = new NavDrawerListAdapter(getActivity(),navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String albumId = albumsList.get(position).getId();
                String albumTitle = albumsList.get(position).getTitle();
                Intent intent = new Intent(getActivity(),WallpapersOfAlbums.class);
                intent.putExtra(bundleAlbumId,albumId).putExtra(bundleAlbumTitle,albumTitle);
                startActivity(intent);
            }
        });

        return view;

    }

}
