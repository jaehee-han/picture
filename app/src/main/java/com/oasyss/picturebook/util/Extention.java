package com.oasyss.picturebook.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public static JSONObject bookBitMapObj = new JSONObject();

    public static void addBookBitMapObj(int page, Bitmap bitmap){
        try {
            bookBitMapObj.put(String.valueOf(page), getStringFromBitmap(bitmap));
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public static JSONObject getBookBitMapObj() {
        return bookBitMapObj;
    }

    public static void setBookBitMapObj(JSONObject bookBitMapObj) {
        Extention.bookBitMapObj = bookBitMapObj;
    }

    public static int bookTotal = 0;

    public static int getBookTotal() {
        return bookTotal;
    }

    public static void setBookTotal(int bookTotal) {
        Extention.bookTotal = bookTotal;
    }

    /*
     * pictureDiv : 0 == 캐릭터
     * pictureDiv : 1 == 그림책
     */
    public static int pictureDiv;

    public static int getPictureDiv() {
        return pictureDiv;
    }

    public static void setPictureDiv(int pictureDiv) {
        Extention.pictureDiv = pictureDiv;
    }


    public static int bookPage = 0;

    public static int getBookPage() {
        return bookPage;
    }

    public static void setBookPage(int bookPage) {
        Extention.bookPage = bookPage;
    }

    //string to Bitmap
    public static String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
    public static Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    public static boolean isEmpty(Object s) { if (s == null) { return true; } if ((s instanceof String) && (((String)s).trim().length() == 0)) { return true; } if (s instanceof Map) { return ((Map<?, ?>)s).isEmpty(); } if (s instanceof List) { return ((List<?>)s).isEmpty(); } if (s instanceof Object[]) { return (((Object[])s).length == 0); } return false; }

}
