/*
 * Copyright (C) 2010 Peter Dornbach.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oasyss.picturebook.widget;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.oasyss.picturebook.util.BitmapColorSearch;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.FloodFill;
import com.oasyss.picturebook.util.images.BitmapImage;
import com.oasyss.picturebook.util.images.ImageDB;
import com.oasyss.picturebook.util.images.NullImage;
import com.oasyss.picturebook.util.quell.ColorComparator;

import java.util.ArrayList;


public class PaintArea {

    private static final int COLOR_SEARCH_RADIUS = 10;
    private final ViewGroup.LayoutParams layoutParams;
    private Bitmap bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
    private int paintColor;
    private final ImageView view;

    public PaintArea(ImageView view) {
        this.view = view;
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return onTouchEvent(motionEvent);
            }
        });
        layoutParams = view.getLayoutParams();
    }


    public boolean canPaint() {
        return bitmap != null;
    }

    public ImageDB.Image getImage() {
        if (canPaint()) {

            return new BitmapImage(bitmap);
        }
        return new NullImage();
    }
    int bitmapSaveCount = 0;
    Bitmap firstBitMap;

    public void setImageBitmap(final Bitmap bm) {
        if(bitmapSaveCount == 0){
//            Extention.addTouchBitMapList(bm);

            bitmapSaveCount++;
        }
        firstBitMap = bm;

        setImageBitmapWithSameSize(bm);
        view.setLayoutParams(layoutParams);
        view.setRotation(0);
        //뷰 사이즈 측정
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                int bmHeight = bm.getHeight();
                int bmWidth = bm.getWidth();
                int maxWidth = ((View)view.getParent()).getWidth();
                int maxHeight = ((View)view.getParent()).getHeight();
                if ((bmHeight < bmWidth) != (maxHeight < maxWidth)) {
                    // 핸드폰 사이즈에 맞춰 뷰 회전
                    view.setRotation(-90); // bottom to bottom
                    float scale1 = maxHeight / (float)bmWidth;
                    float scale2 = maxWidth / (float)bmHeight;
                    float scale;
                    if (scale1 < scale2) {
                        layoutParams.width = maxHeight;
                        layoutParams.height = maxHeight * bmHeight / bmWidth;
                    } else {
                        layoutParams.width = maxWidth * bmWidth / bmHeight;
                        layoutParams.height = maxWidth;
                    }
                } else {
                    float scale1 = maxHeight / (float)bmHeight;
                    float scale2 = maxWidth / (float)bmWidth;
                    if (scale1 < scale2) {
                        layoutParams.width = maxHeight * bmWidth / bmHeight;
                        layoutParams.height = maxHeight;
                    } else {
                        layoutParams.width = maxWidth;
                        layoutParams.height = maxWidth * bmHeight / bmWidth;
                    }
                }
                view.setLayoutParams(params);
            }
        });
    }

    private void setImageBitmapWithSameSize(Bitmap bitmap) {
        view.setImageBitmap(bitmap);

        this.bitmap = bitmap;
    }

    public void setPaintColor(int color)
    {
        paintColor = color;
        if (paintColor == FloodFill.BORDER_COLOR) {
            paintColor = paintColor ^ 1;
        }
    }
    //색칠되는부분
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            // 터치 사운드
            view.playSoundEffect(SoundEffectConstants.CLICK);

            float eventX = e.getX();
            float eventY = e.getY();

            int x = (int)(eventX * bitmap.getWidth() / view.getWidth());
            int y = (int)(eventY * bitmap.getHeight() / view.getHeight());
            Log.d("touch", "X (" + e.getRawX() + ") " + eventX + " -> " + x + " | Y (" + e.getRawY() + ") " + eventY + " -> " + y);
            if (bitmap.getPixel(x, y) == FloodFill.BORDER_COLOR) {

                BitmapColorSearch search = new BitmapColorSearch(bitmap);
                search.startSearch(x, y, ColorComparator.unequal(FloodFill.BORDER_COLOR, paintColor), COLOR_SEARCH_RADIUS);
                if (!search.wasSuccessful()) {
                    search.startSearch(x, y, ColorComparator.unequal(FloodFill.BORDER_COLOR), COLOR_SEARCH_RADIUS);
                }
                if (search.wasSuccessful()){
                    x = search.getX();
                    y = search.getY();
                }
            }
            Bitmap newBitmap = FloodFill.fill(bitmap, x, y, paintColor);
            Extention.addTouchBitMapList(newBitmap);
            setImageBitmapWithSameSize(newBitmap);
        }
        return true;
    }
    //뒤로가기
    ArrayList bitMapList = new ArrayList();
    public void backBitMap(){
        bitMapList = Extention.getTouchBitMapList();
        int bitMapSize = bitMapList.size()-2;
        if(bitMapSize < 0){
            setImageBitmapWithSameSize(firstBitMap);

            Extention.clearTouchBitMapList();

            Extention.setTouchBitMapList(bitMapList);
        }else{
            setImageBitmapWithSameSize((Bitmap)bitMapList.get(bitMapSize));

            bitMapList.remove(bitMapSize);
            Extention.setTouchBitMapList(bitMapList);
        }



    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public int getWidth() {
        return view.getWidth() == 0 ? view.getWidth() : 640;
    }

    public int getHeight() {
        return view.getHeight() == 0 ? view.getHeight() : 480;
    }
}
