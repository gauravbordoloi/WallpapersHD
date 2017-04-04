package com.gmonetix.wallpapershdultimate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gmonetix.wallpapershdultimate.helper.Font;
import com.gmonetix.wallpapershdultimate.util.Utils;

import java.io.IOException;

public class Camera extends AppCompatActivity{

    Intent iCamera;
    final static int cameraData = 0;
    Bitmap bitmap;
    ImageView imageCamera;
    LinearLayout setBackground;
    android.support.v7.widget.Toolbar toolbar;
    TextView tv_Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Font font = new Font();
        TextView toolBarTextView = (TextView) findViewById(R.id.toolbarTextView);
        toolBarTextView.setText(getResources().getString(R.string.fab_camera));
        font.setFont(getApplicationContext(),toolBarTextView);

        TextView tvCamera = (TextView) findViewById(R.id.tvCamera);
        tv_Camera = (TextView) findViewById(R.id.tv_camera);
        font.setFont(getApplicationContext(),tvCamera);
        font.setFont(getApplicationContext(),tv_Camera);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Camera.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imageCamera = (ImageView) findViewById(R.id.imageView);
        setBackground = (LinearLayout) findViewById(R.id.btn_camera);
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, cameraData);
            }
        });
        setBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utils = new Utils(getApplicationContext());
                utils.setAsWallpaper(bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageCamera.setImageBitmap(bitmap);
            tv_Camera.setVisibility(View.GONE);
        }
    }
}
