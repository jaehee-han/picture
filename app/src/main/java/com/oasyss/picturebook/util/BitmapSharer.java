package com.oasyss.picturebook.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.oasyss.picturebook.R;


public class BitmapSharer extends BitmapSaver
{

    public BitmapSharer(Context context, Bitmap bitmap) {
        super(context, bitmap);
    }

    @Override
    protected void saveToURI()
    {
        super.saveToURI();
        Uri uri = getImageUri();
        shareUri(uri);
    }

    private void shareUri(Uri uri) {
        if (uri != null)
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/png");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(sharingIntent, context.getString( R.string.dialog_share )));
        }
    }

    @Override
    public void alreadySaved(BitmapSaver bitmapSaver) {
        super.alreadySaved(bitmapSaver);
        Uri uri = bitmapSaver.getImageUri();
        shareUri(uri);
    }
}
