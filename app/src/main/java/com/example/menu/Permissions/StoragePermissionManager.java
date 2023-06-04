package com.example.menu.Permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StoragePermissionManager {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 100;

    private Activity activity;

    public StoragePermissionManager(Activity activity) {
        this.activity = activity;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionResult = ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return permissionResult == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                // You can perform your operations here
            } else {
                // Permission denied
                // Handle the situation when the user denies the permission
            }
        }
    }
}
