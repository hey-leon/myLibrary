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

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    //views
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button loginSignUpButton;


    //state callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //wiring
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginSignUpButton = (Button)findViewById(R.id.loginSignUpButton);

        //set listener
        loginButton.setOnClickListener(this);
        loginSignUpButton.setOnClickListener(this);
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
        switch(v.getId()){
            case R.id.loginButton:
                Login();
                break;
            case R.id.loginSignUpButton:
                CreateUser();
        }
    }


    //intent creators
    private void loginToUser(String username) {
        Intent loginToUser = new Intent(this, UserActivity.class);
        loginToUser.putExtra("logging in", username);
        startActivity(loginToUser);
    }

    private void CreateUser() {
        Intent createUser = new Intent(this, NewUserActivity.class);
        startActivity(createUser);
    }


    //login helper
    private void Login() {

        //grab username
        String username = usernameEditText.getText().toString();
        //find user
        User user = userDb.findUser(username);
        //if user exists
        if(user != null){
          //check password
            if(user.getPassword().equals(passwordEditText.getText().toString())){
                loginToUser(username);
            }else{
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                passwordEditText.setText("");
            }
        //if user null
        }else{
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
            usernameEditText.setText("");
            passwordEditText.setText("");
        }


    }


}
