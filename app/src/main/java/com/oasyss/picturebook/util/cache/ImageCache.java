package com.oasyss.picturebook.util.cache;

import com.oasyss.picturebook.util.images.ImageDB;
import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.widget.LoadImageProgress;


public interface ImageCache {
    boolean asPreviewImage(ImageDB.Image image, ImagePreview thumbPreview, LoadImageProgress progress);
}
