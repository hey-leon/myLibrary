package com.example.leon.mylibrary.SQL;

import android.app.Activity;

/**
 * Created by leon on 9/05/2015.
 */
public class BaseActivity extends Activity {

    //stores the app wide userDB
    protected UserDb userDb = new UserDb(this, null);

}
