package com.oasyss.picturebook.util.imports;

import android.graphics.Bitmap;
import android.net.Uri;

import com.oasyss.picturebook.util.FloodFill;
import com.oasyss.picturebook.widget.LoadImageProgress;


public class BlackAndWhiteImageImport extends UriImageImport {

    public BlackAndWhiteImageImport(Uri imageUri, LoadImageProgress progress, ImagePreview imagePreview) {
        super(imageUri, progress, imagePreview);
    }

    @Override
    protected void runWithBitmap(Bitmap image) {
        imagePreview.setImage(image);
        progress.stepConvertingToBinaryImage();
        Bitmap binaryImage = FloodFill.asBlackAndWhite(image);
        super.runWithBitmap(binaryImage);
    }
}
