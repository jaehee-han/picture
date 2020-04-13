package com.oasyss.picturebook.util;

import android.graphics.Bitmap;

import com.oasyss.picturebook.util.quell.ColorSearch;


public class BitmapColorSearch extends ColorSearch {
    public BitmapColorSearch(Bitmap bitmap) {
        super(new BitmapConverter(bitmap).getPixelsOfBitmap(), bitmap.getWidth(), bitmap.getHeight());
    }
}
