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
                String username = usernameEditText.getText().toString();
                if(!username.equals(username.trim())){
                    username = username.trim();
                    Toast.makeText(this, "warning! Leading and trailing white space is removed from username and password", Toast.LENGTH_LONG).show();
                }
                String password = passwordEditText.getText().toString();
                password = password.trim();
                if(authenticateUser(username, password)){
                    createUser(username, password);
                }
                break;
        }

    }

    private boolean authenticateUser(String username, String password) {
        //check username for null or empty
        username = username.trim();
        if(username.equals("") || username == null){
            Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_LONG).show();
            return false;
        }
        //check password length
        if(password.length() < 8){
            Toast.makeText(this, "Password must be atleast eight characters", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private void createUser(String username, String password) {
        //check for user
        User user = userDb.findUser(username);
        if(user == null){
            user = new User(username, password);
            userDb.addUser(user);
            Intent loginToUser = new Intent(this, UserActivity.class);
            loginToUser.putExtra("logging in", username);
            startActivity(loginToUser);
        }else{
            Toast.makeText(this, "User already exists, please try another username", Toast.LENGTH_LONG).show();
            usernameEditText.setText("");
        }
    }
}
