package com.example.leon.mylibrary.GUI.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leon.mylibrary.OOP.Review;
import com.example.leon.mylibrary.R;

import java.util.ArrayList;

/**
 * Created by leon on 11/05/2015.
 */
public class BookReviewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Review> reviews;

    public BookReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    public BookReviewAdapter(Context context) {
        this.reviews = new ArrayList<Review>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_bookreview, null);

        TextView usernameTextView = (TextView)view.findViewById(R.id.bookReviewUsernameTextView);
        TextView timeTextView = (TextView)view.findViewById(R.id.bookReviewTimeTextView);
        TextView reviewTextView = (TextView)view.findViewById(R.id.bookReviewReviewTextView);

        Review review = reviews.get( (getCount() - 1) - position);

        usernameTextView.setText(review.getUsername());
        timeTextView.setText(review.getTime());
        reviewTextView.setText(review.getReview());

        return view;

    }


    public void addAll(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    public void add(Review review) {
        reviews.add(review);
    }
}
