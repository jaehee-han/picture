package com.oasyss.picturebook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.ScreenUtils;

public class PicCharChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton charImgBtn, picImgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_char_choice);
        ScreenUtils.setFullscreen(this);

        charImgBtn = (ImageButton) findViewById(R.id.choice_char);
        picImgBtn = (ImageButton) findViewById(R.id.choice_pic);

        charImgBtn.setOnClickListener(this);
        picImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choice_char:
                Intent intent = new Intent(getApplicationContext(), CharacterChoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.choice_pic:
                Toast.makeText(getApplicationContext(), R.string.function_coming_soon, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
