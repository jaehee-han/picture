package com.oasyss.picturebook.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.oasyss.picturebook.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String CHANGELOG_FOLDER = "changelogs";
    private int permissionRequest = 0;
    private Button mButtonPlay;
    LinearLayout bgLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 가로
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        //단말기 유니크값
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
// 몰입 모드를 꼭 적용해야 한다면 아래의 3가지 속성을 모두 적용시켜야 합니다
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);



        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        checkForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, R.string.permission_read_external_storage);
        checkForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.permission_write_external_storage);
//        checkForPermission(Manifest.permission.EXPAND_STATUS_BAR, R.string.permission_expand_status_bar);
//        checkForPermission(Manifest.permission.SYSTEM_ALERT_WINDOW, R.string.permission_expand_status_bar);
//        if (Settings.of(this).requireInternetConnection()) {
//            checkForPermission(Manifest.permission.ACCESS_WIFI_STATE, R.string.permission_access_wifi_state);
//        }

        bgLayout = (LinearLayout)findViewById(R.id.go_layout);
        bgLayout.setOnClickListener(this);
    }
    public void checkForPermission(final String permissionName, int explanationResourceId) {
        // check for permissions
        // see https://developer.android.com/training/permissions/requesting#java
        if (ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED) {
            Log.e("되있음", "성공~5555555");
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)) {
                Log.e("되있음", "성공~33333");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // see https://stackoverflow.com/a/2115770
                new AlertDialog.Builder(this)
                        .setMessage(explanationResourceId)

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{permissionName},
                                        permissionRequest++);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{permissionName},
                        permissionRequest++);
                Log.e("되있음", "성공~222");
            }
        } else {
            // Permission has already been granted
            Log.e("되있음", "성공~");
        }
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_layout:
                Log.e("console log : ", "로그~~~~~~~~");
                Intent intent = new Intent(getApplicationContext(), PicCharChoiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}


