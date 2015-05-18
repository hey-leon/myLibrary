package com.example.leon.mylibrary.GUI.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leon.mylibrary.GUI.UserActivity;
import com.example.leon.mylibrary.OOP.Book;
import com.example.leon.mylibrary.OOP.Review;
import com.example.leon.mylibrary.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Leon on 3/05/2015.
 */
public class BookViewFragment extends Fragment implements View.OnClickListener {

    UserActivity activity;
    //views
    private View view;
    private RelativeLayout descriptionLayout;

    private ImageView coverImageView;
    private TextView nameTextView;
    private TextView byTextView;
    private TextView pubTextView;
    private ImageButton addReviewButton;
    private ListView reviewsListView;
    private BookReviewAdapter reviewAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (UserActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book_view, container, false);
        descriptionLayout = (RelativeLayout)view.findViewById(R.id.descriptionLayout);

        coverImageView = (ImageView) view.findViewById(R.id.cover);
        nameTextView = (TextView) view.findViewById(R.id.name);
        byTextView = (TextView) view.findViewById(R.id.by);
        pubTextView = (TextView) view.findViewById(R.id.pub);

        reviewsListView = (ListView) view.findViewById(R.id.reviewListView);
        reviewAdapter = new BookReviewAdapter(view.getContext());
        reviewsListView.setAdapter(reviewAdapter);

        addReviewButton = (ImageButton) view.findViewById(R.id.addReviewButton);
        addReviewButton.setOnClickListener(this);

        return view;

    }

    public void addContents(Book book) {
        //elevate description
        descriptionLayout.setElevation(32);
        //general book details
        coverImageView.setImageDrawable(book.getCover());
        nameTextView.setText(book.getName());
        byTextView.setText(book.getBy());
        pubTextView.setText(book.getPub());
        //reviews list view
        reviewAdapter.addAll(book.getReviews());
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addReviewButton.getId()) {
            getReview();
        }
    }

    private void getReview() {

        //the dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Create Review");
        alert.setMessage("Type Away...");
        // Create EditText
        final EditText reviewInput = new EditText(activity);
        reviewInput.setMinHeight(100);
        alert.setView(reviewInput);

        // Button Responses
        alert.setPositiveButton("Submit", (dialog, whichButton) -> {
            String time = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
            Review review = new Review(activity.getUsername(), time
                    , reviewInput.getText().toString());
            submitReview(review);
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // Canceled.
        });
        alert.show();
    }


    private void submitReview(Review review) {
        activity.addBookReview(review);
        reviewAdapter.add(review);
        reviewAdapter.notifyDataSetChanged();
    }

}
