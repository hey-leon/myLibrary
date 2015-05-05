package com.example.leon.mylibrary.GUI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leon.mylibrary.OOP.Book;
import com.example.leon.mylibrary.R;

/**
 * Created by Leon on 3/05/2015.
 */
public class BookViewFragment extends Fragment {

    //views
    View view;
    ImageView cover;
    TextView name;
    TextView by;
    TextView pub;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book_view, container, false);
        cover = (ImageView)view.findViewById(R.id.cover);
        name = (TextView)view.findViewById(R.id.name);
        by = (TextView)view.findViewById(R.id.by);
        pub = (TextView)view.findViewById(R.id.pub);

        return view;

    }

    public void addContents(Book book) {
        cover.setImageDrawable(book.getCover());
        name.setText(book.getName());
        by.setText(book.getBy());
        pub.setText(book.getPub());
    }
}
