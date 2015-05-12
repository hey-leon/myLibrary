package com.example.leon.mylibrary.GUI.UserActivityFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.leon.mylibrary.GUI.Fragments.BookViewFragment;
import com.example.leon.mylibrary.OOP.Book;
import com.example.leon.mylibrary.OOP.Review;
import com.example.leon.mylibrary.R;

import java.util.ArrayList;

/**
 * Created by Leon on 5/05/2015.
 */
public class BookSearchActivityFragment extends Fragment {

    //view
    private View view;
    //layout
    FrameLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book_search_activity, container, false);
        layout = (FrameLayout)view.findViewById(R.id.bookSearchContainer);

        return view;
    }

    public void addBookView(Book book) {

        BookViewFragment newBook = new BookViewFragment();

        //create book card view
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(layout.getId(), newBook)
                .commit();

        fragmentManager.executePendingTransactions();

        //add contents
        newBook.addContents(book);

    }

}
