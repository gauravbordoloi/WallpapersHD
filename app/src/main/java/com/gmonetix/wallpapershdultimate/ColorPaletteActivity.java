package com.gmonetix.wallpapershdultimate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.gmonetix.wallpapershdultimate.util.Utils;

public class ColorPaletteActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String COLOR_ID = "color_id";
    private int color;
    private FloatingActionButton download,setAsWallpaper;
    private ImageView imageViewColor;
    Utils utils;
    private Bitmap image;
    private Toolbar toolbar;
    private String hexValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_palette);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        color = getIntent().getIntExtra(COLOR_ID,R.color.White);
        hexValue = getResources().getString(color).substring(3).toUpperCase();

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(hexValue);
        font.setFont(getApplicationContext(),toolBarTextView);

        download = (FloatingActionButton) findViewById(R.id.fab_download_color);
        setAsWallpaper = (FloatingActionButton) findViewById(R.id.fab_set_as_wallpaper_color);
        imageViewColor = (ImageView) findViewById(R.id.imgFullscreen_color);
        BitmapInitialization();
        utils = new Utils(getApplicationContext());
        imageViewColor.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),color));
        download.setOnClickListener(this);
        setAsWallpaper.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ColorPaletteActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_color_palette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.copy_hex_color_code:
                setClipboard(hexValue);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_download_color:
                utils.saveImageToSDCard(image);
                break;
            case R.id.fab_set_as_wallpaper_color:
                utils.setAsWallpaper(image);
                break;
        }
    }

    public void BitmapInitialization(){
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(screen);
        } else {
            screen.x = display.getWidth();
            screen.y = display.getHeight();
        }
        image = Bitmap.createBitmap(screen.x, screen.y, Bitmap.Config.ARGB_8888);
        image.eraseColor(ContextCompat.getColor(getApplicationContext(),color));
    }

    private void setClipboard(String text) {
        Toast.makeText(getApplicationContext(),"Copied color palette hex value",Toast.LENGTH_SHORT).show();
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied color palette hex value", text);
        clipboard.setPrimaryClip(clip);
    }
}
