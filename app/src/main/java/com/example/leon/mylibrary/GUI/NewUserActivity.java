package com.example.leon.mylibrary.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leon.mylibrary.OOP.User;
import com.example.leon.mylibrary.R;
import com.example.leon.mylibrary.SQL.BaseActivity;

public class NewUserActivity extends BaseActivity implements View.OnClickListener {

    //views
    EditText usernameEditText;
    EditText passwordEditText;
    Button createUserButton;


    //state callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //wiring
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        createUserButton = (Button)findViewById(R.id.createUserButton);

        //set listener
        createUserButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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

        switch(v.getId()){
            case R.id.createUserButton:
                //trim user details
                trimUserDetails();
                //grab user details
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //authenticate the user details
                if(authenticateUser(username, password)){
                    //create user and login
                    createUser(username, password);
                }
                break;
        }

    }


    //intent creator
    private void loginToUser(String username) {
        Intent loginToUser = new Intent(this, UserActivity.class);
        loginToUser.putExtra("logging in", username);
        startActivity(loginToUser);
        finish();
    }


    //create user helpers
    private boolean authenticateUser(String username, String password) {
        boolean hasCap = false;
        boolean hasNum = false;

        // ###TEST 1#### --- check username for null or empty
        if(username.equals("") || username == null){
            Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        // ###TEST 2#### --- check password length
        if(password.length() < 8){
            Toast.makeText(this, "Password must be at least eight characters", Toast.LENGTH_LONG).show();
            passwordEditText.setText("");
            return false;
        }
        //  ###TEST 3#### --- password must include 1 capital and numeric
        for(int i = 0; i < password.length(); i++){
            //check for cap
            if(Character.isUpperCase(password.charAt(i))){
                hasCap = true;
            //check for digit
            }else if(Character.isDigit(password.charAt(i))){
                hasNum = true;
            }
        }

        //return true if all tests pass
        if(hasCap || hasNum){
            return true;
        }else{
            //if TEST 3 failed...
            Toast.makeText(this, "Password must include atleist one uppercase and one numeric", Toast.LENGTH_LONG).show();
            passwordEditText.setText("");
            return false;
        }

    }

    private void createUser(String username, String password) {
        //check for user
        User user = userDb.findUser(username);
        if(user == null){
            //if no user
            user = new User(username, password);
            userDb.addUser(user);
            loginToUser(username);
        }else{
            //if user exists
            Toast.makeText(this, "User already exists, please try another username", Toast.LENGTH_LONG).show();
            usernameEditText.setText("");
        }
    }


    //general helper
    private void trimUserDetails() {
        usernameEditText.setText(usernameEditText.getText().toString().trim());
        passwordEditText.setText(passwordEditText.getText().toString().trim());
    }
}
