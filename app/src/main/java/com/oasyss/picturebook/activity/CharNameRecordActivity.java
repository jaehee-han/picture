package com.oasyss.picturebook.activity;

/*
 * 오디오로 텍스트 뽑아내는것과 녹음은 동시 작업이 불가
 * 파워포인트 5페이지 메뉴구성은 잘못된구성
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.ScreenUtils;

import java.util.ArrayList;
import java.util.Locale;

public class CharNameRecordActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView recordImgView, recordMikeImgView;
    Button recordFinishBtn;
    TextView recordCharNameTextView;

    Intent intent;
    SpeechRecognizer stt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_name_record);
        ScreenUtils.setFullscreen(this);

        recordFinishBtn = (Button)findViewById(R.id.record_finish_btn);
        recordCharNameTextView = (TextView)findViewById(R.id.record_char_name);

        recordImgView = (ImageView)findViewById(R.id.record_img_view);

        if(Extention.getTouchBitMapList().size() == 0){
            recordImgView.setImageBitmap(Extention.getOrigin_charator());
        }else{
            recordImgView.setImageBitmap((Bitmap)Extention.getTouchBitMapList().get(Extention.getTouchBitMapList().size()-1));
        }


        recordMikeImgView = (ImageView)findViewById(R.id.record_mike);
        recordMikeImgView.setOnClickListener(this);
        //음성입력 호출
        try{
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

            stt = SpeechRecognizer.createSpeechRecognizer(this);
            stt.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle bundle) {
                    //음성인식 준비완료
                    Toast.makeText(getApplicationContext(), getString(R.string.record_start), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBeginningOfSpeech() {
                    //음성감지됨
//                    Toast.makeText(getApplicationContext(), getString(R.string.record_start_toast_mag), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRmsChanged(float v) {

                }

                @Override
                public void onBufferReceived(byte[] bytes) {

                }

                @Override
                public void onEndOfSpeech() {
                    //종료부분
                }

                @Override
                public void onError(int i) {
                    Extention.Log("err ::: " + i);
                }

                @Override
                public void onResults(Bundle bundle) {
                    ArrayList<String> result = (ArrayList<String>) bundle.get(SpeechRecognizer.RESULTS_RECOGNITION);

                    for(int i=0; i<result.size(); i++){
                        Extention.Log(result.get(i));
                    }

                    recordCharNameTextView.setText(getString(R.string.record_char_name_start) + " " + result.get(0)+ " " +getString(R.string.record_char_name_end));
                }

                @Override
                public void onPartialResults(Bundle bundle) {

                }

                @Override
                public void onEvent(int i, Bundle bundle) {

                }
            });
        }catch (Exception e){
            Extention.Log(e.toString());
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_mike:
                stt.startListening(intent);
                break;
            case R.id.record_finish_btn:

                break;
        }
    }
}
