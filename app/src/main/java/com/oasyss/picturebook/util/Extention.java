package com.oasyss.picturebook.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

public class Extention {
    public static int CHOICE_POPUP_RESULT_CODE = 1;

    public static void Log(String msg){
        Log.e("Log : ", msg);
    }

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
