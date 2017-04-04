package com.gmonetix.wallpapershdultimate;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.gmonetix.wallpapershdultimate.util.PrefManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class SettingsActivity extends AppCompatActivity {
    private PrefManager pref;
    private TextView txtNoOfGridColumns, txtGalleryName;
    private Button btnSave;
    private Toolbar toolbar;
    private AdView adView;
    private FloatingActionButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.ad1_unit_id_SettingsActivity));
        adView = (AdView) findViewById(R.id.adViewSettingsActivity);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(getResources().getString(R.string.action_settings));
        font.setFont(getApplicationContext(),toolBarTextView);

        txtNoOfGridColumns = (TextView) findViewById(R.id.txtNoOfColumns);
        txtGalleryName = (TextView) findViewById(R.id.txtGalleryName);
        info = (FloatingActionButton) findViewById(R.id.fab_settings_info);

        font.setFont(getApplicationContext(),txtGalleryName);
        font.setFont(getApplicationContext(),txtNoOfGridColumns);

        btnSave = (Button) findViewById(R.id.btnSave);

        pref = new PrefManager(getApplicationContext());

        // Display edittext values stored in shared preferences
        // Number of grid columns
        txtNoOfGridColumns.setText(String.valueOf(pref.getNoOfGridColumns()));

        // Gallery name
        txtGalleryName.setText(pref.getGalleryName());

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this,R.style.DialogTheme);
                builder.setIcon(R.drawable.ic_launcher);
                builder.setTitle(R.string.info);
                builder.setMessage(R.string.info_message);
                builder.setNegativeButton("View Source code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/gmonetix")));
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        // Save settings button click listener
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Validating the data before saving to shared preferences
                // validate number of grid columns
                String no_of_columns = txtNoOfGridColumns.getText().toString()
                        .trim();
                if (no_of_columns.length() == 0 || !isInteger(no_of_columns)) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_enter_valid_grid_columns),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // validate gallery name
                String galleryName = txtGalleryName.getText().toString().trim();
                if (galleryName.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.toast_enter_gallery_name),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Check for setting changes
                if (!no_of_columns.equalsIgnoreCase(String.valueOf(pref
                        .getNoOfGridColumns()))
                        || !galleryName.equalsIgnoreCase(pref.getGalleryName())) {
                    // User changed the settings
                    // save the changes and launch SplashScreen to initialize
                    // the app again
                    pref.setNoOfGridColumns(Integer.parseInt(no_of_columns));
                    pref.setGalleryName(galleryName);

                    // start the app from SplashScreen
                    Intent i = new Intent(SettingsActivity.this,
                            SplashActivity.class);
                    // Clear all the previous activities
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // user not modified any values in the form
                    // skip saving to shared preferences
                    // just go back to previous activity
                    onBackPressed();
                }

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
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