package com.example.leon.mylibrary.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.leon.mylibrary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LoginActivity extends Activity implements View.OnClickListener {


    //views
    Button loginButton;
    EditText usernameEditText;
    EditText passwordEditText;


    //state callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //wiring
        loginButton = (Button)findViewById(R.id.loginButton);
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        //enable listeners
        loginButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    //listener handlers
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == findViewById(R.id.loginButton)) {
            loginClickedHandler();
        }

    }

    private void loginClickedHandler() {
        //uid
        int uid = 0;

        //grab username
        String username = usernameEditText.getText().toString();

        //test
        uid = authenticateUsername(username, uid);

        //grab password
        String password = passwordEditText.getText().toString();

        //test
        if(authenticatePassword(password, uid)){
            Intent loginIntent = new Intent(this, UserActivity.class);
            loginIntent.putExtra("logged in", true);
            startActivity(loginIntent);
        }

    }


    //io helpers
    public int authenticateUsername(String username, int uid) {
        try {
            boolean uidFound = false;
            String line = null;
            //get input stream
            InputStream iS = getAssets().open("uid/usernames.txt");
            BufferedReader bSR = new BufferedReader(new InputStreamReader(iS));
            //loop through usernames
            while((line = bSR.readLine()) != null && !uidFound){
                //check username
                if(username.equals(line)){
                    uidFound = true;
                }else{
                    uid++;
                }
            }
            String data = bSR.readLine();
            //close stream
            iS.close();
            //return
            if(!uidFound){
                return -1;
            }else{
                return uid;
            }
        } catch (IOException ex) {
            return -2;
        }
    }

    private boolean authenticatePassword(String password, int uid) {
        try {
            String line = null;
            //get input stream
            InputStream iS = getAssets().open("uid/passwords.txt");
            BufferedReader bSR = new BufferedReader(new InputStreamReader(iS));
            //loop through to uid location
            for(int i = 0; i < uid; i++){
                bSR.readLine();
            }
            //grab password
            line = bSR.readLine();
            //close stream
            iS.close();
            //check password
            if(password.equals(line)){
                return true;
            }else{
                return false;
            }
        } catch (IOException ex) {
            return false;
        }
    }

    public void starAuthenticatedUsername(String username){
        String usernames = "";
        //read usernames
        try{
            String line = null;
            //get input stream
            InputStream iS = getAssets().open("uid/usernames.txt");
            BufferedReader bSR = new BufferedReader(new InputStreamReader(iS));
            //loop through usernames
            while((line = bSR.readLine()) != null){

                usernames += line + '\n';

            }
        }catch (Exception e){
        }
        // add star to username
        usernames.replaceAll(username, '*' + username);
        //write usernames
        try{
            String line = null;
            //get input stream
            InputStream iS = getAssets().open("uid/usernames.txt");
            BufferedReader bSR = new BufferedReader(new InputStreamReader(iS));
            //loop through usernames
            while((line = bSR.readLine()) != null){

                usernames += line + '\n';

            }
        }catch (Exception e){
        }
        // add star to username
        usernames.replaceAll(username, '*' + username);

    }


}