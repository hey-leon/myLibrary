package com.example.leon.mylibrary.SQL;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by leon on 10/05/2015.
 */
public class BaseActivity extends Activity {
    protected static UserDb userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDb = new UserDb(this, null);
    }
}
