package com.test.contentprovider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class RequestPermission {

    private static String TAG;
    enum PERMISSIONS{

        READ_CONTACTS(Manifest.permission.READ_CONTACTS),
        WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS);
        private String value ;
        PERMISSIONS(String value ){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }



    public static void requestPermissions(Context activityContext){
        requestPermissionFromList(activityContext, new PERMISSIONS[]{PERMISSIONS.READ_CONTACTS, PERMISSIONS.WRITE_CONTACTS});
    }
    public static void requestPermissionFromList(Context activityContext, PERMISSIONS[] permissions){
        try {

            if (activityContext != null && permissions != null && permissions.length > 0) {
                ArrayList<String> requiredPermissions = new ArrayList<>();

                for (PERMISSIONS permission : permissions) {
                    // Check if permission is already granted or not
                    if (ContextCompat.checkSelfPermission(activityContext,
                            permission.getValue()) != PackageManager.PERMISSION_GRANTED) {
                        // Add this permission to required permissions list
                        requiredPermissions.add(permission.getValue());
                    }
                }

                if (requiredPermissions.size() > 0) {
                    // Convert list to array
                    String[] perms = requiredPermissions.toArray(new String[requiredPermissions.size()]);
                    // Request Permissions
                    if (perms.length > 0 && activityContext instanceof Activity) {
                        // Show native Android request permission dialogs
                        ActivityCompat.requestPermissions((Activity) activityContext, perms, 111);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Activity context not passed to ZaprHelper.requestRuntimePermissions() method");
            e.printStackTrace();
        }
    }



}
