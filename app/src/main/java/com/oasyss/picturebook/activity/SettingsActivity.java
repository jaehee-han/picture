package com.oasyss.picturebook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.util.BitmapSaver;
import com.oasyss.picturebook.util.ScreenUtils;
import com.oasyss.picturebook.util.Settings;
import com.oasyss.picturebook.util.images.SettingsImageDB;

import org.androidsoft.utils.ui.BasicActivity;

import java.io.File;

import androidx.core.content.FileProvider;

public class SettingsActivity extends BasicActivity {

    private Settings settings;
    private SettingsImageDB imageDBs;
    private EditText newUrlInput;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_settings);
        ScreenUtils.setFullscreen(this);

        settings = Settings.of(this);

        // 위치 저장
        File saveLocation = BitmapSaver.getSavedImagesDirectory(this);
        TextView saveLocationText = findViewById(R.id.save_location);
        saveLocationText.setText(getString(R.string.settings_save_location, saveLocation.toString()));
        Button openSaveLocation = findViewById(R.id.open_save_location);
        Uri saveLocationUri =  FileProvider.getUriForFile(this, this.getPackageName() + ".provider", saveLocation);;
        final Intent openSaveLocationIntent = new Intent(Intent.ACTION_VIEW);
        openSaveLocationIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openSaveLocationIntent.setDataAndType(saveLocationUri, "vnd.android.document/directory");
        openSaveLocationIntent.putExtra("org.openintents.extra.ABSOLUTE_PATH", saveLocation.toString());
        openSaveLocationIntent.putExtra("com.android.documentsui.FILE", saveLocation.toString());
        openSaveLocationIntent.putExtra("com.android.documentsui.IS_ROOT", false); // is the root folder?
        openSaveLocationIntent.putExtra("com.android.documentsui.IS_PRIMARY", true); // is the primary volume? SD-Card should be false
        openSaveLocationIntent.putExtra("com.android.documentsui.APP_LABEL", getTitle());
        if (openSaveLocationIntent.resolveActivityInfo(getPackageManager(), 0) != null) {
            openSaveLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(openSaveLocationIntent);
                }
            });
        } else  {
            openSaveLocation.setVisibility(View.GONE);
        }

        // GALLERIES
        imageDBs = settings.getImageDB();
        final View newUrlButton = findViewById(R.id.button_add_url);
        newUrlInput = findViewById(R.id.input_new_url);
        newUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = newUrlInput.getText().toString();
                Uri uri;
                try {
                    uri = Uri.parse(url);
                } catch (Exception e) {
                    badUrl(newUrlInput);
                    return;
                }
                if (!uri.getScheme().startsWith("http")) {
                    badUrl(newUrlInput);
                    return;
                }
                if (!imageDBs.addUserDefinedGallery(url)) {
                    badUrl(newUrlInput);
                    return;
                }
                loadGalleries();
                goodUrl(newUrlInput);
            }

            private void goodUrl(EditText newUrlInput) {
                newUrlInput.setBackgroundColor(Color.TRANSPARENT);
            }

            private void badUrl(EditText newUrlInput) {
                newUrlInput.setBackgroundColor(Color.RED);
            }
        });
    }

    private void loadGalleries() {
        LinearLayout galleries = findViewById(R.id.galleries);
        galleries.removeAllViews();
        for (final SettingsImageDB.Entry db : imageDBs.entries()) {
            LinearLayout galleryLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.gallery, null);
            CheckBox checkBox = galleryLayout.findViewById(R.id.in_use);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean activated) {
                    db.setActivation(activated);
                }
            });
            checkBox.setText(db.getName());
            checkBox.setChecked(db.isActivated());
            TextView description = galleryLayout.findViewById(R.id.text_description);
            description.setText(db.getDescription());
            ImageButton deleteButton = galleryLayout.findViewById(R.id.button_delete);
            deleteButton.setVisibility(db.canBeDeleted() ? View.VISIBLE : View.GONE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newUrlInput.setText(db.getId());
                    db.delete();
                    loadGalleries();

                }
            });
            ImageButton browseButton = galleryLayout.findViewById(R.id.button_browse);
            browseButton.setVisibility(db.canBrowse() ? View.VISIBLE : View.GONE);
            browseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.browse(SettingsActivity.this);
                }
            });
            galleries.addView(galleryLayout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGalleries();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ScreenUtils.setFullscreen(this);
        }
    }

    @Override
    public int getMenuResource() {
        return R.menu.menu_close;
    }

    @Override
    public int getMenuCloseId() {
        return R.id.menu_close;
    }
}