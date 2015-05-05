package com.example.leon.mylibrary.OOP;

import android.graphics.drawable.Drawable;

/**
 * Created by Leon on 5/05/2015.
 */

public class Book {
    private Drawable cover;
    private String name;
    private String by;
    private String pub;

    public Book(Drawable cover, String name, String by, String pub) {
        this.cover = cover;
        this.name = name;
        this.by = by;
        this.pub = pub;
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
}
