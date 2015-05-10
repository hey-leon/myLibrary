package com.example.leon.mylibrary.OOP;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * Created by Leon on 5/05/2015.
 */

public class Book {
    private Drawable cover;
    private String name;
    private String by;
    private String pub;
    private String dbName;
    private ArrayList<String> reviews;

    public Book(Drawable cover, String name, String by, String pub, String dbName, ArrayList<String> reviews) {
        this.cover = cover;
        this.name = name;
        this.by = by;
        this.pub = pub;
        this.dbName = dbName;
        this.reviews = reviews;
    }

    public Drawable getCover() {
        return cover;
    }

    public String getName() {
        return name;
    }

    public String getBy() {
        return by;
    }

    public String getPub() {
        return pub;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public String getDbName() {
        return dbName;
    }
}
