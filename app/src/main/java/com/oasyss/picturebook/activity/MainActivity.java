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
            Log.i("Is on?", "몰입 끄기");
        } else {
            Log.i("Is on?", "몰입 켜기");
        }
        //단말기 유니크값(EB저장용
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

        bgLayout = (LinearLayout)findViewById(R.id.go_layout);
        bgLayout.setOnClickListener(this);
    }
    public void checkForPermission(final String permissionName, int explanationResourceId) {
        if (ContextCompat.checkSelfPermission(this, permissionName) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)) {
                new AlertDialog.Builder(this)
                        .setMessage(explanationResourceId)

                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{permissionName},
                                        permissionRequest++);
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{permissionName},
                        permissionRequest++);
            }
        } else {
        }
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.go_layout:
                Intent intent = new Intent(getApplicationContext(), PicCharChoiceActivity.class);
                startActivity(intent);
                break;
        }
    }
}


