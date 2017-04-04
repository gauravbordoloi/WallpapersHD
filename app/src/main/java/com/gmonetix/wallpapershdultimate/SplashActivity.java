package com.gmonetix.wallpapershdultimate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gmonetix.wallpapershdultimate.app.AppConst;
import com.gmonetix.wallpapershdultimate.app.AppController;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.gmonetix.wallpapershdultimate.picasa.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {
    private ProgressBar progressBar;
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final String TAG_FEED = "feed", TAG_ENTRY = "entry",
            TAG_GPHOTO_ID = "gphoto$id", TAG_T = "$t",
            TAG_ALBUM_TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        TextView WallPapersHD = (TextView) findViewById(R.id.tvWallpapersHD);
        TextView ULTIMATE = (TextView) findViewById(R.id.tvULTIMATE);
        TextView Gmonetix = (TextView) findViewById(R.id.tvGMONETIX);
        progressBar = (ProgressBar) findViewById(R.id.progressBarSplashScreen);
        progressBar.setVisibility(View.VISIBLE);
        Font font = new Font();
        font.setFont(getApplicationContext(),WallPapersHD);
        font.setFont(getApplicationContext(),ULTIMATE);
        font.setFont(getApplicationContext(),Gmonetix);

        // Picasa request to get list of albums
        String url = AppConst.URL_PICASA_ALBUMS
                .replace("_PICASA_USER_", AppConst.PICASA_USER);

        Log.d(TAG, "Albums request url: " + url);

        // Preparing volley's json object request
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
                (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Albums Response: " + response.toString());
                List<Category> albums = new ArrayList<Category>();
                try {
                    // Parsing the json response
                    JSONArray entry = response.getJSONObject(TAG_FEED)
                            .getJSONArray(TAG_ENTRY);

                    // loop through albums nodes and add them to album
                    // list
                    for (int i = 0; i < entry.length(); i++) {
                        JSONObject albumObj = (JSONObject) entry.get(i);
                        // album id
                        String albumId = albumObj.getJSONObject(
                                TAG_GPHOTO_ID).getString(TAG_T);

                        // album title
                        String albumTitle = albumObj.getJSONObject(
                                TAG_ALBUM_TITLE).getString(TAG_T);

                        Category album = new Category();
                        album.setId(albumId);
                        album.setTitle(albumTitle);

                        // add album to list
                        albums.add(album);

                        Log.d(TAG, "Album Id: " + albumId
                                + ", Album Title: " + albumTitle);
                    }

                    // Store albums in shared pref
                    AppController.getInstance().getPrefManger()
                            .storeCategories(albums);
                    progressBar.setVisibility(View.GONE);
                    // String the main activity
                    Intent intent = new Intent(getApplicationContext(),
                            PermissionTransferToMainActivity.class);
                    startActivity(intent);
                    // closing splash activity
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.msg_unknown_error),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Volley Error: " + error.getMessage());

                // show error toast
                Toast.makeText(getApplicationContext(),
                        getString(R.string.splash_error),
                        Toast.LENGTH_LONG).show();

                // Unable to fetch albums
                // check for existing Albums data in Shared Preferences
                if (AppController.getInstance().getPrefManger()
                        .getCategories() != null && AppController.getInstance().getPrefManger()
                        .getCategories().size() > 0) {
                    // String the main activity
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(),
                            PermissionTransferToMainActivity.class);
                    startActivity(intent);
                    // closing splash activity
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    // Albums data not present in the shared preferences
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.msg_wall_fetch_error),Toast.LENGTH_LONG).show();
                }

            }
        });

        // disable the cache for this request, so that it always fetches updated
        // json
        jsonObjReq.setShouldCache(false);

        // Making the request
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}