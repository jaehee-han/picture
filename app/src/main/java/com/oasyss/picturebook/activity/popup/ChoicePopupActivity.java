package com.oasyss.picturebook.activity.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.ScreenUtils;

public class ChoicePopupActivity extends AppCompatActivity {
    TextView msgTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_popup);
        ScreenUtils.setFullscreen(this);

        msgTextView = (TextView)findViewById(R.id.choice_popup_text);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        msgTextView.setText(data+"를 선택 하시겠습니까?");


    }
    public void mCancel(View v){
        finish();
    }
    public void mConfirm(View v){
        Intent intent = new Intent();
        intent.putExtra("result", "true");
        setResult(Extention.CHOICE_POPUP_RESULT_CODE, intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}
