package com.gmonetix.wallpapershdultimate;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import com.gmonetix.wallpapershdultimate.helper.RuntimePermission;

public class PermissionTransferToMainActivity extends RuntimePermission {

    private static final int REQUEST_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE},R.string.permission,REQUEST_PERMISSION);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        startActivity(new Intent(PermissionTransferToMainActivity.this,MainActivity.class));
        finish();
    }
}
