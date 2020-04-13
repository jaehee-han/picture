package com.oasyss.picturebook.util.images;

import android.os.Parcelable;

import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.widget.LoadImageProgress;

public interface ImageDB {
    int size();
    Image get(int index);
    void attachObserver(Subject.Observer observer);

    interface Image extends Parcelable {
        void asPreviewImage(ImagePreview preview, LoadImageProgress progress);
        boolean canBePainted();
        void asPaintableImage(ImagePreview preview, LoadImageProgress progress);
    }
}
