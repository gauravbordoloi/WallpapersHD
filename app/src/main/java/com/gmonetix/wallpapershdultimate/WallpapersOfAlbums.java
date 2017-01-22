package com.gmonetix.wallpapershdultimate;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gmonetix.wallpapershdultimate.app.AppConst;
import com.gmonetix.wallpapershdultimate.app.AppController;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.gmonetix.wallpapershdultimate.helper.GridViewAdapter;
import com.gmonetix.wallpapershdultimate.picasa.Wallpaper;
import com.gmonetix.wallpapershdultimate.util.PrefManager;
import com.gmonetix.wallpapershdultimate.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class WallpapersOfAlbums extends AppCompatActivity {

    private static final String TAG = WallpapersOfAlbums.class.getSimpleName();
    private Utils utils;
    private GridViewAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    private static final String bundleAlbumId = "albumId";
    private static final String bundleAlbumTitle = "albumTitle";
    private String selectedAlbumId;
    private List<Wallpaper> photosList;
    private ProgressBar pbLoader;
    private PrefManager pref;
    private Toolbar toolbar;
    private AdView adView;

    // Picasa JSON response node keys
    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_MEDIA_GROUP = "media$group",
            TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
            TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height", TAG_ID = "id",
            TAG_T = "$t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_album_pics);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.ad1_unit_id_WallpapersOfAlbums));
        adView = (AdView) findViewById(R.id.adViewWallpapersOfAlbums);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(getIntent().getStringExtra(bundleAlbumTitle));
        font.setFont(getApplicationContext(),toolBarTextView);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        photosList = new ArrayList<Wallpaper>();
        pref = new PrefManager(getApplicationContext());

        if (getIntent().getStringExtra(bundleAlbumId) != null) {
            selectedAlbumId = getIntent().getStringExtra(bundleAlbumId);
            Log.d(TAG,
                    "Selected album id: "
                            + getIntent().getStringExtra(bundleAlbumId));
        } else {
            Log.d(TAG, "No album id selected");
            selectedAlbumId = null;
        }

        // Preparing the request url
        String url = null;
        if (selectedAlbumId == null) {
            // Recently added album url
            url = AppConst.URL_ALBUM_PHOTOS.replace("_PICASA_USER_",
                    pref.getGoogleUserName()).replace("_ALBUM_ID_",
                    "6363441302732923313");
        } else {
            // Selected an album, replace the Album Id in the url
            url = AppConst.URL_ALBUM_PHOTOS.replace("_PICASA_USER_",
                    pref.getGoogleUserName()).replace("_ALBUM_ID_",
                    selectedAlbumId);
        }

        Log.d(TAG, "Final request url: " + url);

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setVisibility(View.GONE);
        pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
        pbLoader.setVisibility(View.VISIBLE);

        utils = new Utils(getApplicationContext());



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,
                        "List of photos json reponse: "
                                + response.toString());
                try {
                    // Parsing the json response
                    JSONArray entry = response.getJSONObject(TAG_FEED)
                            .getJSONArray(TAG_ENTRY);

                    // looping through each photo and adding it to list
                    // data set
                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject photoObj = (JSONObject) entry.get(i);
                        JSONArray mediacontentArry = photoObj
                                .getJSONObject(TAG_MEDIA_GROUP)
                                .getJSONArray(TAG_MEDIA_CONTENT);

                        if (mediacontentArry.length() > 0) {
                            JSONObject mediaObj = (JSONObject) mediacontentArry
                                    .get(0);

                            String url = mediaObj
                                    .getString(TAG_IMG_URL);

                            String photoJson = photoObj.getJSONObject(
                                    TAG_ID).getString(TAG_T)
                                    + "&imgmax=d";

                            int width = mediaObj.getInt(TAG_IMG_WIDTH);
                            int height = mediaObj
                                    .getInt(TAG_IMG_HEIGHT);

                            Wallpaper p = new Wallpaper(photoJson, url, width,
                                    height);

                            // Adding the photo to list data set
                            photosList.add(p);

                            Log.d(TAG, "Photo: " + url + ", w: "
                                    + width + ", h: " + height);
                        }
                    }

                    // Notify list adapter about dataset changes. So
                    // that it renders grid again
                    adapter.notifyDataSetChanged();

                    // Hide the loader, make grid visible
                    pbLoader.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.msg_unknown_error),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                // unable to fetch wallpapers
                // either google username is wrong or
                // devices doesn't have internet connection
                Toast.makeText(getApplicationContext(),
                        getString(R.string.msg_wall_fetch_error),
                        Toast.LENGTH_LONG).show();

            }
        });

        // Remove the url from cache
        AppController.getInstance().getRequestQueue().getCache().remove(url);

        // Disable the cache for this url, so that it always fetches updated
        // json
        jsonObjReq.setShouldCache(false);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

        // Initiliazing Grid View
        InitiliazeGridLayout();

        // Gridview adapter
        adapter = new GridViewAdapter(WallpapersOfAlbums.this, photosList, columnWidth);

        // setting grid view adapter
        gridView.setAdapter(adapter);

        // Grid item select listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // On selecting the grid image, we launch fullscreen activity
                Intent i = new Intent(getApplicationContext(),
                        FullScreenViewActivity.class);

                // Passing selected image to fullscreen activity
                Wallpaper photo = photosList.get(position);

                i.putExtra(FullScreenViewActivity.TAG_SEL_IMAGE, photo);
                startActivity(i);
            }
        });
    }

    private void InitiliazeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConst.GRID_PADDING, r.getDisplayMetrics());

        // Column width
        columnWidth = (int) ((utils.getScreenWidth() - ((pref
                .getNoOfGridColumns() + 1) * padding)) / pref
                .getNoOfGridColumns());

        // Setting number of grid columns
        gridView.setNumColumns(pref.getNoOfGridColumns());
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);

        // Setting horizontal and vertical padding
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
