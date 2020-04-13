package com.oasyss.picturebook.util.images;

import android.os.Parcel;
import android.os.Parcelable;


import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.util.imports.UriImageImport;
import com.oasyss.picturebook.widget.LoadImageProgress;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

class ThumbNailImage extends UrlImage {
    private final int maxWidth;

    public ThumbNailImage(URL thumbUrl, String thumbId, Date thumbLastModified, int maxWidth, RetrievalOptions retrievalOptions) {
        super(thumbUrl, thumbId, thumbLastModified, retrievalOptions);
        this.maxWidth = maxWidth;
    }

    @Override
    public void asPreviewImage(ImagePreview preview, LoadImageProgress progress) {
        new UriImageImport(getUri(), progress, preview).startWith(getCache());
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
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(maxWidth);
    }

    public static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public ImageDB.Image createFromParcel(Parcel parcel) {
            String urlString = parcel.readString();
            String id = parcel.readString();
            Date lastModified = new Date(parcel.readLong());
            Parcelable retrievalOptions = parcel.readParcelable(RetrievalOptions.class.getClassLoader());
            int maxWidth = parcel.readInt();
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return new NullImage();
            }
            return new ThumbNailImage(url, id, lastModified, maxWidth, (RetrievalOptions) retrievalOptions);
        }

        @Override
        public ImageDB.Image[] newArray(int i) {
            return new ImageDB.Image[0];
        }
    };

    public int getWidth() {
        return maxWidth;
    }
}
