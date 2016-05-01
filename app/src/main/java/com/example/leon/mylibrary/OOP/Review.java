package com.example.leon.mylibrary.OOP;

/**
 * Created by leon on 11/05/2015.
 */
public class Review {
    private final String username;
    private final String time;
    private final String review;

    public Review(String username, String time, String review) {
        this.username = username;
        this.time = time;
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public String getTime() {
        return time;
    }

    public String getReview() {
        return review;
    }

}
