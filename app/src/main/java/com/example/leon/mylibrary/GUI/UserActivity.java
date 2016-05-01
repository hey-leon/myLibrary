package com.example.leon.mylibrary.GUI;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.example.leon.mylibrary.GUI.Fragments.NavigationDrawerFragment;
import com.example.leon.mylibrary.GUI.UserActivityFragments.BookSearchActivityFragment;
import com.example.leon.mylibrary.GUI.UserActivityFragments.FriendSuggestionActivityFragment;
import com.example.leon.mylibrary.GUI.UserActivityFragments.UserMainActivityFragment;
import com.example.leon.mylibrary.OOP.Book;
import com.example.leon.mylibrary.OOP.Review;
import com.example.leon.mylibrary.OOP.RidScanner;
import com.example.leon.mylibrary.OOP.User;
import com.example.leon.mylibrary.R;
import com.example.leon.mylibrary.SQL.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class UserActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    //fragments
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Fragment mActivityFragment;


    //variables
    private CharSequence mTitle;
    private User mUser;
    private Book mBook;


    //adaptors
    private NfcAdapter nfcAdapter;
    private RidScanner ridScanner;



    //state callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //generated code --- sets up nav drawer
        setupDrawer();

        //adaptor wiring
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        ridScanner = new RidScanner(nfcAdapter);

        //load prefs
        String username = loadUsernamePreferences();

        //if have user in prefs
        if (username != null && !username.equals("")) {
            mUser = loadUser(username);
        }else{
            //check if users exist
            if(userDb.hasUsers()){
                //check for login intent
                handleLoginIntent();
            }else {
                //goto new user activity
                gotoNewUserIntent();
            }
        }

        //if book search intent change to book search fragment
        if(ridScanner.containsTag(getIntent())){
            mNavigationDrawerFragment.changeDrawerItem(1);
        }else{
            mNavigationDrawerFragment.changeDrawerItem(0);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_user, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();

        //book search intent
        if(mActivityFragment.getClass() == BookSearchActivityFragment.class){
            if(ridScanner.containsTag(getIntent())){
                handleBookSearchIntent(getIntent());
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        //save prefs
        SharedPreferences.Editor editor = getSharedPreferences("current user", MODE_PRIVATE).edit();
        editor.putString("username", mUser.getUsername());
        editor.apply();

        disableForegroundDispatchSystem();

    }


    //intent handlers
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void handleBookSearchIntent(Intent intent) {
        //grab tag content
        Parcelable[] tagContent = ridScanner.getTagContent(intent);

        //check if tag content contains messages
        if(ridScanner.containsMessages(tagContent)){
            //grab first message
            NdefMessage message = ridScanner.getMessage(tagContent, 0);

            //grab message contents
            NdefRecord[] messageContent = ridScanner.getMessageContent(message);

            //check if message content contains records
            if(ridScanner.containsRecords(messageContent)){
                //grab first record
                NdefRecord record = ridScanner.getRecord(messageContent, 0);
                //grab record contents
                String rid = ridScanner.getStringFromRecord(record);
                //cast fragment
                BookSearchActivityFragment mBookSearchActivityFragment
                        = (BookSearchActivityFragment)mActivityFragment;

                //add book to fragment
                mBookSearchActivityFragment.addBookView(mBook = fetchBook(rid));

            }
        }

    }

    private void handleLoginIntent() {
        String username;
        //check if user login intent exists
        if ((username = getIntent().getStringExtra("logging in"))
                != null) {
            mUser = userDb.findUser(username);
        } else {
            gotoLoginIntent();
        }

    }

    private void gotoLoginIntent() {
        Intent gotoLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(gotoLoginActivity);
        finish();
    }

    private void gotoNewUserIntent() {
        Intent gotoNewUserActivity = new Intent (this, NewUserActivity.class);
        startActivity(gotoNewUserActivity);
        finish();
    }


    //listener handlers
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.action_signout:
                //sets username blank for save username preferaence
                mUser.setUsername("");
                gotoLoginIntent();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position){
            case 0:
                changeActivityFragment(new UserMainActivityFragment(), position);
                break;
            case 1:
                changeActivityFragment(new BookSearchActivityFragment(), position);
                break;
            case 2:
                changeActivityFragment(new FriendSuggestionActivityFragment(), position);
                break;
            default:
                changeActivityFragment(new UserMainActivityFragment(), position);
                break;
        }
    }


    //preference helper
    private String loadUsernamePreferences() {
        SharedPreferences prefs = getSharedPreferences("current user", MODE_PRIVATE);
        return prefs.getString("username", null);
    }

    //ui helpers
    private void setupDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void changeActivityFragment(Fragment nextFragment, int position) {

        mActivityFragment = nextFragment;

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.userActivityContainer, nextFragment)
                .commit();

        changeActivityTitle(position);

    }

    public void changeActivityTitle(int number) {
        switch (number) {
            case 0:
                mTitle = "My Library";
                break;
            case 1:
                mTitle = "Book Search";
                break;
            case 2:
                mTitle = "Friends Suggestions";
                break;
        }
    }


    //nfc helpers
    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, UserActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilters = new IntentFilter[]{};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }


    //asset helpers
    public String fetchAssetString(String location) {
        try {
            //get input stream
            InputStream iS = getAssets().open(location);
            BufferedReader bSR = new BufferedReader(new InputStreamReader(iS));
            //grab string
            String data = bSR.readLine();
            //close stream
            iS.close();
            //return
            return data;
        } catch (IOException ex) {
            return null;
        }
    }

    private Drawable fetchAssetDrawable(String location) {
        try {
            //get input stream
            InputStream iS = getAssets().open(location);
            //grab image as Drawable
            Drawable cover = Drawable.createFromStream(iS, null);
            //close stream
            iS.close();
            //return
            return cover;
        }
        catch(IOException ex) {
            return null;
        }
    }


    //db helpers
    private User loadUser(String username) {
        return userDb.findUser(username);
    }

    private ArrayList<Review> loadReviews(String dbName) {

        ArrayList<Review> reviews;
        if(bookReviewDb.hasBookTable(dbName)){
            reviews = bookReviewDb.findAllReviews(dbName);
        }else{
            bookReviewDb.addBookTable(dbName);
            reviews = bookReviewDb.findAllReviews(dbName);
        }
        return reviews;
    }

    public void addBookReview(Review review){

        bookReviewDb.addBookReview(mBook.getDbName(),review);

    }


    //general helpers
    private Book fetchBook(String rid) {

        //fetch cover
        Drawable cover = fetchAssetDrawable(rid + "/cover.png");

        //fetch name
        String name = fetchAssetString(rid + "/name.txt");

        //fetch by
        String by = fetchAssetString(rid + "/by.txt");

        //fetch pub
        String pub = fetchAssetString(rid + "/pub.txt");

        //fetch book db name
        String dbName = fetchDbName(name);

        //fetch reviews
        ArrayList<Review> reviews = loadReviews(dbName);

        return new Book(cover, name, by, pub, dbName, reviews);

    }

    private String fetchDbName(String bookname) {
        return bookname.replaceAll("[:-@]", "").replaceAll("[!-/]","").replaceAll(" ", "");
    }

    public String getUsername(){
        return mUser.getUsername();
    }
}
