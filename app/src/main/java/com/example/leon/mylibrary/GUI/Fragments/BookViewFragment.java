package com.example.leon.mylibrary.GUI.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leon.mylibrary.GUI.UserActivity;
import com.example.leon.mylibrary.OOP.Book;
import com.example.leon.mylibrary.R;

import java.util.ArrayList;

/**
 * Created by Leon on 3/05/2015.
 */
public class BookViewFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> reviews;
    UserActivity activity;
    //views
    private View view;
    private ImageView coverImageView;
    private TextView nameTextView;
    private TextView byTextView;
    private TextView pubTextView;
    private Button addReviewButton;
    private ListView reviewsListView;
    private ArrayAdapter<String> reviewAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (UserActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book_view, container, false);
        coverImageView = (ImageView) view.findViewById(R.id.cover);
        nameTextView = (TextView) view.findViewById(R.id.name);
        byTextView = (TextView) view.findViewById(R.id.by);
        pubTextView = (TextView) view.findViewById(R.id.pub);

        reviews = new ArrayList<String>();
        reviewsListView = (ListView) view.findViewById(R.id.reviewListView);
        reviewAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, reviews);
        reviewsListView.setAdapter(reviewAdapter);

        addReviewButton = (Button) view.findViewById(R.id.addReviewButton);
        addReviewButton.setOnClickListener(this);

        return view;

    }

    public void addContents(Book book) {
        //general book details
        coverImageView.setImageDrawable(book.getCover());
        nameTextView.setText(book.getName());
        byTextView.setText(book.getBy());
        pubTextView.setText(book.getPub());
        //reviews listview
        reviewAdapter.addAll(book.getReviews());
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addReviewButton.getId()) {
            createReview();
        }
    }

    private void createReview() {

        //the dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Type Away...");
        // Create EditText
        final EditText reviewInput = new EditText(activity);
        alert.setView(reviewInput);
        // Button Responses
        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                submitReview(reviewInput.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    private void submitReview(String review) {
        activity.addBookReview(review);
        reviewAdapter.add(review);
        reviewAdapter.notifyDataSetChanged();
    }

}
