package com.oasyss.picturebook.activity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.ScreenUtils;

public class ChoiceFinishPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_finish_popup);
        ScreenUtils.setFullscreen(this);
    }
    public void mCancel(View v){
        finish();
    }
    public void mConfirm(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "true");
        setResult(Extention.FINISH_POPUP_RESULT_CODE, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
