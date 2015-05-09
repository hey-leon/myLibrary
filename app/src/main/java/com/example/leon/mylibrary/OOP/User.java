package com.example.leon.mylibrary.OOP;

/**
 * Created by leon on 9/05/2015.
 */
public class User {

    //fields
    private String username;
    private String password;

    //empty constructor
    public User(){}

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
