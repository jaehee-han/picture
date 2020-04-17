package com.oasyss.picturebook.activity.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oasyss.picturebook.R;
import com.oasyss.picturebook.activity.AbstractColoringActivity;
import com.oasyss.picturebook.activity.PaintActivity;
import com.oasyss.picturebook.activity.popup.ChoiceFinishPopupActivity;
import com.oasyss.picturebook.util.BitmapSaver;
import com.oasyss.picturebook.util.Extention;
import com.oasyss.picturebook.util.images.ImageDB;
import com.oasyss.picturebook.util.images.ResourceImageDB;
import com.oasyss.picturebook.util.imports.ImagePreview;
import com.oasyss.picturebook.widget.ColorButton;
import com.oasyss.picturebook.widget.LoadImageProgress;
import com.oasyss.picturebook.widget.PaintArea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookPaintActivity extends AbstractColoringActivity {
    private static final int REQUEST_CHOOSE_PICTURE = 0;
    private static final int REQUEST_PICK_COLOR = 1;
    public static final String ARG_IMAGE = "bitmap";
    private PaintArea paintArea;

    private ColorButtonManager colorButtonManager;
    boolean doubleBackToExitPressedOnce = false;
    private BitmapSaver bitmapSaver = null;

    private int lastSavedHash;
    private ImageView paintView;

    private ImageView eraserBtn, back_btn;
    private ColorButton eraserColorBtn;

    private LinearLayout eraserLayout, backLayout;

    private Button finishBtn, img_backBtn;
    private TextView pageTextView;

    int bookNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_paint);

        Extention.setPictureDiv(1);

        pageTextView = (TextView)findViewById(R.id.book_page_text);
        bookNumber = getIntent().getIntExtra("num",1);


        paintView = (ImageView) findViewById(R.id.paint_view);
        paintArea = new PaintArea(paintView);

        eraserLayout = (LinearLayout)findViewById(R.id.eraser_layout);
        backLayout = (LinearLayout)findViewById(R.id.back_layout);

        colorButtonManager = new ColorButtonManager();
        eraserColorBtn = (ColorButton)findViewById(R.id.eraser_color_btn);
        eraserBtn = (ImageView)findViewById(R.id.eraser_img_view);
        back_btn = (ImageView)findViewById(R.id.back_btn);

        //지우개
        eraserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraserColorBtn.callOnClick();

                eraserLayout.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.border_line_all_red));
            }
        });
        //뒤로가기
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paintArea.backBitMap();
            }
        });
        //이미지 뒤로
        img_backBtn = (Button)findViewById(R.id.book_back_btn);
        img_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookNumber = bookNumber - 1;

                if(bookNumber < 1){
                    bookNumber = 1;
                }
                loadImageFromIntent(bookNumber);
            }
        });
        //완료
        finishBtn = (Button)findViewById(R.id.paint_finish_btn) ;
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookNumber = bookNumber + 1;

                if(bookNumber > Extention.bookTotal){
                    bookNumber = Extention.bookTotal;


                }else{
                    loadImageFromIntent(bookNumber);
                }

            }
        });

        loadImageFromIntent(bookNumber);
    }
    private void loadImageFromIntent(final int number) {
        Extention.clearTouchBitMapList();
        if(number == 1){
            img_backBtn.setVisibility(View.GONE);
        }else{
            img_backBtn.setVisibility(View.VISIBLE);
        }

        if(Extention.getBookTotal() == number){
            finishBtn.setText(getString(R.string.book_finish_btn_end));
        }else{
            finishBtn.setText(getString(R.string.book_finish_btn_next));
        }

        Extention.setBookPage(number);

        paintView.post(new Runnable() {
            @Override
            public void run() {



                ImageDB.Image image = null;
                String bookTitle = getIntent().getStringExtra("book");
                try{
                    JSONObject obj = new JSONObject();
                    obj = Extention.getBookBitMapObj();
                    Extention.Log("obj ::: " + obj);
                    if(Extention.isEmpty(obj)){
                        Extention.Log("마사카????????????");
                        image = new ResourceImageDB(BookPaintActivity.this).bookOriginImage(bookTitle, number,BookPaintActivity.this);
                    }else if(!bookTitle.equals("")){
                        image = new ResourceImageDB(BookPaintActivity.this).bookSelectImage(bookTitle, number,BookPaintActivity.this);
                    }else{
                        image = null;
                    }
                    // TODO: 이미지 호출될동안 로딩프로그레스
                    image.asPaintableImage(new Preview(), new LoadImageProgress(null, null));
                    if(obj.getString(String.valueOf(number)) != null){
                        Timer m_timer = new Timer();
                        final JSONObject finalObj = obj;
                        TimerTask m_task = new TimerTask() {
                            @Override
                            public void run() {
                                Bitmap originBitmap;
                                try {
                                    originBitmap = Extention.getBitmapFromString(finalObj.getString(String.valueOf(number)));
                                    paintArea.originBitmap(originBitmap);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        m_timer.schedule(m_task, 100);

                    }

                }catch (JSONException e){

                    e.printStackTrace();
                }


                pageTextView.setText(number + "/" + Extention.bookTotal);

            }
        });
    }

    private class ColorButtonManager implements View.OnClickListener
    {

        public ColorButtonManager()
        {
            findAllColorButtons(_colorButtons);
            _usedColorButtons.addAll(_colorButtons);
            _selectedColorButton = _usedColorButtons.getFirst();
            _selectedColorButton.setSelected(true);
            Iterator<ColorButton> i = _usedColorButtons.iterator();
            while (i.hasNext())
            {
                i.next().setOnClickListener(ColorButtonManager.this);
            }
            setPaintViewColor();
        }

        public void onClick(View view)
        {
            if (view instanceof ColorButton)
            {
                selectButton((ColorButton) view);
            }
        }

        // 선택한 컬러버튼
        public void selectColor(int color)
        {
            _selectedColorButton = selectAndRemove(color);
            if (_selectedColorButton == null)
            {
                // 마지막으로 사용한 컬러버튼
                _selectedColorButton = _usedColorButtons.removeLast();
                _selectedColorButton.setColor(color);
                _selectedColorButton.setSelected(true);
            }
            _usedColorButtons.addFirst(_selectedColorButton);
            setPaintViewColor();
        }

        // 뿌려지는 버튼
        private void selectButton(ColorButton button)
        {
            _selectedColorButton = selectAndRemove(button.getColor());
            _usedColorButtons.addFirst(_selectedColorButton);
            setPaintViewColor();
        }

        // 팔넷 컬러보기
        private void setPaintViewColor()
        {
            int selectedColor = _selectedColorButton.getColor();
            paintArea.setPaintColor(selectedColor);
            setBackgroundColorOfButtons(selectedColor);
        }
        //팔넷 터치하면 주변 색상바꾸기
        private void setBackgroundColorOfButtons(int color) {
//            int backgroundColor = (color & 0xffffff) | 0x70000000;
//            View buttonHolder = findViewById(R.id.color_buttons_container);
//            buttonHolder.setBackgroundColor(backgroundColor);
        }

        //선택한 컬러버튼으로 색상 인식
        private ColorButton selectAndRemove(int color)
        {
            ColorButton result = null;
            Iterator<ColorButton> i = _usedColorButtons.iterator();
            while (i.hasNext())
            {
                ColorButton b = i.next();
                if (b.getColor() == color)
                {
                    result = b;
                    b.setSelected(true);
                    i.remove();
                    eraserLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_line_all_black));
                }
                else
                {
                    b.setSelected(false);
                }
            }
            return result;
        }
        // 컬러버튼 목록
        private List<ColorButton> _colorButtons = new ArrayList<ColorButton>();
        private LinkedList<ColorButton> _usedColorButtons = new LinkedList<ColorButton>();
        private ColorButton _selectedColorButton;
    }

    private class Preview implements ImagePreview {
        @Override
        public void setImage(final Bitmap image) {
            paintView.post(new Runnable() {
                @Override
                public void run() {
                    paintArea.setImageBitmap(image);
                }
            });
        }

        @Override
        public int getWidth() {
            return paintArea.getWidth();
        }

        @Override
        public int getHeight() {
            return paintArea.getHeight();
        }

        @Override
        public InputStream openInputStream(Uri uri) throws FileNotFoundException {
            return getContentResolver().openInputStream(uri);
        }

        @Override
        public void done(final Bitmap bitmap) {
            setImage(bitmap);
        }
    }
}
