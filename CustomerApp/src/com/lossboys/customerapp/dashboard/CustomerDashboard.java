package com.lossboys.customerapp.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.lossboys.customerapp.CustomerLogin;
import com.lossboys.customerapp.R;
import com.lossboys.customerapp.R.id;
import com.lossboys.customerapp.R.layout;

public class CustomerDashboard extends Activity {
	Button btnLogout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * Dashboard Screen for the application
         * */        
        if(true){
        	setContentView(R.layout.dashboard_layout);
        	
        	/**
             * Creating all buttons instances
             * */
            // Dashboard News feed button
            Button btn_newsfeed = (Button) findViewById(R.id.btn_news_feed);
             
            // Dashboard Friends button
            Button btn_friends = (Button) findViewById(R.id.btn_friends);
             
            // Dashboard Messages button
            Button btn_messages = (Button) findViewById(R.id.btn_messages);
             
            // Dashboard Places button
            Button btn_places = (Button) findViewById(R.id.btn_places);
             
            // Dashboard Events button
            Button btn_events = (Button) findViewById(R.id.btn_events);
             
            // Dashboard Photos button
            Button btn_photos = (Button) findViewById(R.id.btn_photos);
             
            /**
             * Handling all button click events
             * */
             
            // Listening to News Feed button click
            btn_newsfeed.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    startActivity(i);
                }
            });
             
           // Listening Friends button click
            btn_friends.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), FriendsActivity.class);
                    startActivity(i);
                }
            });
             
            // Listening Messages button click
            btn_messages.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), MessagesActivity.class);
                    startActivity(i);
                }
            });
             
            // Listening to Places button click
            btn_places.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), PlacesActivity.class);
                    startActivity(i);
                }
            });
             
            // Listening to Events button click
            btn_events.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), EventsActivity.class);
                    startActivity(i);
                }
            });
             
            // Listening to Photos button click
            btn_photos.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View view) {
                    // Launching News Feed Screen
                    Intent i = new Intent(getApplicationContext(), PhotosActivity.class);
                    startActivity(i);
                }
            });
            
//        	btnLogout = (Button) findViewById(R.id.btnLogout);
//        	
//        	btnLogout.setOnClickListener(new View.OnClickListener() {
//    			
//    			public void onClick(View arg0) {
//    				// TODO Auto-generated method stub
//    				userFunctions.logoutUser(getApplicationContext());
//    				Intent login = new Intent(getApplicationContext(), CustomerLogin.class);
//    	        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//    	        	startActivity(login);
//    	        	// Closing dashboard screen
//    	        	finish();
//    			}
//    		});
        	
        }else{
        	// user is not logged in show login screen
        	Intent login = new Intent(getApplicationContext(), CustomerLogin.class);
        	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(login);
        	// Closing dashboard screen
        	finish();
        }
    }
    
    
    
// // Initiating Menu XML file (menu.xml)
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.layout.menu, menu);
//        return true;
//    }
//     
//    /**
//     * Event Handling for Individual menu item selected
//     * Identify single menu item by it's id
//     * */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//         
//        switch (item.getItemId())
//        {
//        case R.id.menu_bookmark:
//            // Single menu item is selected do something
//            // Ex: launching new activity/screen or show alert message
//            Toast.makeText(CustomerDashboard.this, "Bookmark is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        case R.id.menu_save:
//            Toast.makeText(CustomerDashboard.this, "Save is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        case R.id.menu_search:
//            Toast.makeText(CustomerDashboard.this, "Search is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        case R.id.menu_share:
//            Toast.makeText(CustomerDashboard.this, "Share is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        case R.id.menu_delete:
//            Toast.makeText(CustomerDashboard.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        case R.id.menu_preferences:
//            Toast.makeText(CustomerDashboard.this, "Preferences is Selected", Toast.LENGTH_SHORT).show();
//            return true;
// 
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//    }    
}