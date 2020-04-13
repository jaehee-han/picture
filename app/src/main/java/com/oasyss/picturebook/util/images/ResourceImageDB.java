package com.oasyss.picturebook.util.images;

import android.content.Context;
import android.util.Log;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.Extention;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class ResourceImageDB implements ImageDB {
    private static final String IMAGE_PREFIX = "outline";
    private final List<Image> images = new ArrayList<>();

    public ResourceImageDB(Context context) {
        Field[] drawables = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < drawables.length; i++) {
            String name = drawables[i].getName();
            try {
                if (name.startsWith(IMAGE_PREFIX))
                {
                    images.add(PreparedUriImage.fromResourceId(context, drawables[i].getInt(null)));
                }
            } catch (IllegalAccessException e) {}
        }

    }

    @Override
    public int size() {
        return images.size();
    }

    @Override
    public Image get(int index) {
        if (index > images.size()) {
            return new NullImage();
        }
        return images.get(index);
    }

    @Override
    public void attachObserver(Subject.Observer observer) {
    }

    public Image randomImage() {
        int index = new Random().nextInt(size());
        return get(index);
    }

    public Image charatorSelectImage(String choiceChar, Context context){
        Image returnImg = null;
        Field[] drawables = R.drawable.class.getDeclaredFields();
        for (int i = 0; i < drawables.length; i++) {
            String name = drawables[i].getName();
            try {
                if (name.startsWith(IMAGE_PREFIX)){

                    if(name.indexOf("charator_"+choiceChar) != -1){
                        returnImg = PreparedUriImage.fromResourceId(context, drawables[i].getInt(null));
                    }
                }
            } catch (IllegalAccessException e) {}
        }

        return returnImg;
    }
}
