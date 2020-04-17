package com.oasyss.picturebook.activity.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.oasyss.picturebook.R;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
    RecyclerView bookListRecyclerView;
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> hideNameList = new ArrayList<>();
    ArrayList<Drawable> imgList = new ArrayList<>();

    RecyclerView.LayoutManager recyclerManager;
    BookListAdapter adapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        toolbar = (Toolbar) findViewById(R.id.bookListToolbar);
        toolbar.setTitle(getString(R.string.book_list_toolbar_title));
        toolbar.setTitleTextColor(Color.WHITE);
        addBookList();

        adapter = new BookListAdapter(imgList, nameList, hideNameList);

        bookListRecyclerView = (RecyclerView)findViewById(R.id.bookListRecyclerView);
        recyclerManager = new GridLayoutManager(this, 2);
        bookListRecyclerView.setLayoutManager(recyclerManager);
        bookListRecyclerView.setItemAnimator(new DefaultItemAnimator());

        bookListRecyclerView.setAdapter(adapter);


    }

    public void addBookList(){
        for(int i=0; i<10; i++){
            imgList.add(getResources().getDrawable(R.drawable.outline002_chali_1));
            nameList.add("찰리와 친구들");

            //drawble 이미지 파일명
            hideNameList.add("chali");
        }

    }
}
