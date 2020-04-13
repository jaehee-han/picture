package com.oasyss.picturebook.util.images;

import android.os.Parcel;
import android.os.Parcelable;

import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.widget.LoadImageProgress;


public class NullImage implements ImageDB.Image {

    @Override
    public void asPreviewImage(ImagePreview preview, LoadImageProgress progress) {
        progress.stepFail();
    }

    @Override
    public boolean canBePainted() {
        return false;
    }

    @Override
    public void asPaintableImage(ImagePreview preview, LoadImageProgress progress) {
        progress.stepFail();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ImageDB.Image createFromParcel(Parcel parcel) {
            return new NullImage();
        }

        @Override
        public ImageDB.Image[] newArray(int i) {
            return new ImageDB.Image[0];
        }
    };
}
