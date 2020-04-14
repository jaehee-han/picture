package com.oasyss.picturebook.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Extention {
    public static int CHOICE_POPUP_RESULT_CODE = 1001;
    public static final int FINISH_POPUP_RESULT_CODE = 1002;

    public static void Log(String msg){
        Log.e("Log : ", msg);
    }

    //색칠안된 오리진 캐릭터
    public static Bitmap origin_charator = null;

    public static Bitmap getOrigin_charator() {
        return origin_charator;
    }

    public static void setOrigin_charator(Bitmap origin_charator) {
        Extention.origin_charator = origin_charator;
    }

    //색칠 리스트
    public static ArrayList touchBitMapList = new ArrayList();


    public static ArrayList getTouchBitMapList() {
        return touchBitMapList;
    }

    public static void setTouchBitMapList(ArrayList touchBitMapList) {
        Extention.touchBitMapList = touchBitMapList;
    }
    public static void addTouchBitMapList(Bitmap bitmap) {
        Extention.touchBitMapList.add(bitmap);
    }
    public static void clearTouchBitMapList() {
        Extention.touchBitMapList.clear();
    }
}
