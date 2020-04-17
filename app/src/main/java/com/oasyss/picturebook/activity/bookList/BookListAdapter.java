package com.oasyss.picturebook.activity.bookList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oasyss.picturebook.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookList> {
    private List<Drawable> imgList;
    private List<String> nameList;
    private List<String> hideNameList;

    @NonNull
    @Override
    public BookList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new BookList(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookList holder, final int position) {
        holder.bookImgView.setImageDrawable(imgList.get(position));
        holder.bookTextView.setText(nameList.get(position));

        holder.bookImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookPaintActivity.class);
                intent.putExtra("book", hideNameList.get(position));
                intent.putExtra("num", 1);

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class BookList extends RecyclerView.ViewHolder{
        TextView bookTextView;
        ImageView bookImgView;

        public BookList(@NonNull View itemView) {
            super(itemView);
            bookImgView = (ImageView) itemView.findViewById(R.id.bookListImgView);
            bookTextView = (TextView) itemView.findViewById(R.id.bookListTextView);
        }
    }

    public BookListAdapter(List<Drawable> img, List<String> name, List<String> hide){
        this.imgList = img;
        this.nameList = name;
        this.hideNameList = hide;
    }
}
