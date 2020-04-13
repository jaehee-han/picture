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
package com.oasyss.picturebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.ScreenUtils;
import com.oasyss.picturebook.util.Settings;
import com.oasyss.picturebook.util.images.ImageDB;
import com.oasyss.picturebook.util.images.ImageListener;
import com.oasyss.picturebook.util.images.SectionsAdapter;
import com.oasyss.picturebook.util.images.SettingsImageDB;
import com.oasyss.picturebook.widget.PreCachingLayoutManager;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChoosePictureActivity extends FullScreenActivity
{

    public static final String RESULT_IMAGE = "image";
    public static final String ARG_IMAGE = "image";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ScreenUtils.setFullscreen(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        setContentView(R.layout.choose_picture);
        RecyclerView imagesView = findViewById(R.id.images);
        // 이미지 호출
        int space = getScreenHeight() / 2;
        LinearLayoutManager manager = new PreCachingLayoutManager(this, space);
        imagesView.setLayoutManager(manager);

        // 레이아웃에서 리사이클뷰 사이즈변경 X
        imagesView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        imagesView.setLayoutManager(layoutManager);


        SettingsImageDB imageDB = Settings.of(this).getImageDB();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(ARG_IMAGE)) {
            ImageDB.Image image = extras.getParcelable(ARG_IMAGE);
            if (image.canBePainted()) {
                imageDB.addPaintedImage(image);
            }
        }


        SectionsAdapter adapter = new SectionsAdapter(
                imageDB, R.layout.choose_picture_line,
                new int[]{R.id.image1, R.id.image2});
        adapter.setImageListener(new ImageListener() {
            @Override
            public void onImageChosen(ImageDB.Image image) {
                returnImageToParent(image);
            }

        });
        imagesView.setAdapter(adapter);
    }

    private void returnImageToParent(ImageDB.Image image) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_IMAGE, image);
        setResult(RESULT_OK, intent);
        finish();
    }

    private int getScreenHeight() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
