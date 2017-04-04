package com.gmonetix.wallpapershdultimate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.gmonetix.wallpapershdultimate.app.ColorsArray;
import com.gmonetix.wallpapershdultimate.helper.ColorsHelpAdapter;
import com.gmonetix.wallpapershdultimate.helper.ColorsListAdapter;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class ColoursSecondary extends AppCompatActivity {

    private static final String COLOR_NAME = "color_name";
    private static final String COLOR_ID = "color_id";
    private ListView listView;
    private ColorsListAdapter colorsListAdapter;
    private List<ColorsHelpAdapter> mColorsList;
    private String ColorName;
    int[] colorsSecondary;
    String[] colorsSecondaryName;
    private Toolbar toolbar;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colours_secondary_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ColorName = getIntent().getStringExtra(COLOR_NAME);

        MobileAds.initialize(getApplicationContext(),getResources().getString(R.string.ad1_unit_id_ColoursSecondary));
        adView = (AdView) findViewById(R.id.adViewColoursSecondary);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(ColorName);
        font.setFont(getApplicationContext(),toolBarTextView);

        conditionalStatements();

        listView = (ListView) findViewById(R.id.listview_colors_secondary);
        mColorsList = new ArrayList<>();
        for(int i=0;i<colorsSecondary.length;i++) {
            mColorsList.add(new ColorsHelpAdapter(colorsSecondary[i],colorsSecondaryName[i]));
        }
        colorsListAdapter = new ColorsListAdapter(ColoursSecondary.this,mColorsList);
        listView.setAdapter(colorsListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ColoursSecondary.this,ColorPaletteActivity.class);
                intent.putExtra(COLOR_ID,colorsSecondary[position]);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColoursSecondary.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void conditionalStatements() {
        if(ColorName.equals("Red")) {
            colorsSecondary = ColorsArray.colorsSecondaryRED;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Pink")){
            colorsSecondary = ColorsArray.colorsSecondaryPINK;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Purple")){
            colorsSecondary = ColorsArray.colorsSecondaryPURPLE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Deep Purple")){
            colorsSecondary = ColorsArray.colorsSecondaryDEEP_PURPLE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Indigo")){
            colorsSecondary = ColorsArray.colorsSecondaryINDIGO;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Blue")){
            colorsSecondary = ColorsArray.colorsSecondaryBLUE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Light Blue")){
            colorsSecondary = ColorsArray.colorsSecondaryLIGHT_BLUE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Cyan")){
            colorsSecondary = ColorsArray.colorsSecondaryCYAN;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Teal")){
            colorsSecondary = ColorsArray.colorsSecondaryTEAL;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Green")){
            colorsSecondary = ColorsArray.colorsSecondaryGREEN;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Light Green")){
            colorsSecondary = ColorsArray.colorsSecondaryLIGHT_GREEN;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Lime")){
            colorsSecondary = ColorsArray.colorsSecondaryLIME;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Yellow")){
            colorsSecondary = ColorsArray.colorsSecondaryYELLOW;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Amber")){
            colorsSecondary = ColorsArray.colorsSecondaryAMBER;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Orange")){
            colorsSecondary = ColorsArray.colorsSecondaryORANGE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Deep Orange")){
            colorsSecondary = ColorsArray.colorsSecondaryDEEP_ORANGE;
            colorsSecondaryName = ColorsArray.colorsSecondaryName;
        } else if(ColorName.equals("Brown")){
            colorsSecondary = ColorsArray.colorsSecondaryBROWN;
            colorsSecondaryName = ColorsArray.colorsSecondaryNameWithoutaTint;
        } else if(ColorName.equals("Grey")){
            colorsSecondary = ColorsArray.colorsSecondaryGREY;
            colorsSecondaryName = ColorsArray.colorsSecondaryNameWithoutaTint;
        } else if(ColorName.equals("Blue Grey")){
            colorsSecondary = ColorsArray.colorsSecondaryBLUE_GREY;
            colorsSecondaryName = ColorsArray.colorsSecondaryNameWithoutaTint;
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
