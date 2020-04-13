package com.oasyss.picturebook.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.activity.popup.ChoicePopupActivity;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.ScreenUtils;

public class CharacterChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linearMonkey, linearMouse, linearRabbit, linearDog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 가로
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_character_choice);
        ScreenUtils.setFullscreen(this);

        linearMonkey = (LinearLayout)findViewById(R.id.linearMonkey);
        linearMouse = (LinearLayout)findViewById(R.id.linearMouse);
        linearRabbit = (LinearLayout)findViewById(R.id.linearRabbit);
        linearDog = (LinearLayout)findViewById(R.id.linearDog);

        linearMonkey.setOnClickListener(this);
        linearMouse.setOnClickListener(this);
        linearRabbit.setOnClickListener(this);

        linearDog.setOnClickListener(this);
    }

//    protected void alertDialog(String name){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(CharacterChoiceActivity.this);
//        builder.setTitle("알림").setMessage(name + "를 캐릭터로 선택 하시겠습니까?");
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int id)
//            {
//                Toast.makeText(getApplicationContext(), "확인", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int id)
//            {
//                Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ChoicePopupActivity.class);

        switch (view.getId()){
            case R.id.linearMonkey:
                linearMonkey.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all_red));

                linearMouse.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearRabbit.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearDog.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));

//                alertDialog("원숭이");

                intent.putExtra("data", "원숭이");
                startActivityForResult(intent, Extention.CHOICE_POPUP_RESULT_CODE);
            break;
            case R.id.linearMouse:
                linearMouse.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all_red));

                linearMonkey.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearRabbit.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearDog.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));

                intent.putExtra("data", "원숭이");
                startActivityForResult(intent, Extention.CHOICE_POPUP_RESULT_CODE);
            break;
            case R.id.linearRabbit:
                linearRabbit.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all_red));

                linearMouse.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearMonkey.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearDog.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));

                intent.putExtra("data", "토끼");
                startActivityForResult(intent, Extention.CHOICE_POPUP_RESULT_CODE);
                break;
            case R.id.linearDog:
                linearDog.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all_red));

                linearMouse.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearRabbit.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));
                linearMonkey.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all));

                intent.putExtra("data", "강아지");
                startActivityForResult(intent, Extention.CHOICE_POPUP_RESULT_CODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode == Extention.CHOICE_POPUP_RESULT_CODE){
                //데이터 받기
                String result = data.getStringExtra("result");
                if(result.equals("true")){
                    Intent intent = new Intent(this, PaintActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
