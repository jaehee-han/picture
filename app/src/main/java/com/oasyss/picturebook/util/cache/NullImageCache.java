package com.oasyss.picturebook.util.cache;


import com.oasyss.picturebook.util.images.ImageDB;
import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.widget.LoadImageProgress;

public class NullImageCache implements ImageCache {
    @Override
    public boolean asPreviewImage(ImageDB.Image image, ImagePreview preview, LoadImageProgress progress) {
        image.asPreviewImage(preview, progress);
        return false;
    }
}
