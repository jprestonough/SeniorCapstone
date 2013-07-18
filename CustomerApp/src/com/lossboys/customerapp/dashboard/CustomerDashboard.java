package com.lossboys.customerapp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;

public class CustomerDashboard extends Activity {
	Button btnLogout;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    //setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
	    return true;
	    
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println(item.getItemId());
	    switch (item.getItemId()) {
	            
	        case R.id.actionbar_logout:
	        	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
				CustomHTTP.makePOST("http://23.21.158.161:4912/logout.php", nameValuePair);
	            Intent intnt = new Intent(getApplicationContext(), com.lossboys.customerapp.CustomerLogin.class);
				intnt.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
	            intnt.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
				startActivity(intnt);
				finish();
	        	return true;
	        	
	        case R.id.actionbar_settings:
	        	Intent i = new Intent(getApplicationContext(), AccountActivity.class);
				startActivity(i);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		ActionBar bar = this.getActionBar();
		bar.setDisplayShowTitleEnabled(false);
		
		
		setContentView(R.layout.dashboard_layout);

		/**
		 * Creating all buttons instances
		 * */
		// Dashboard News feed button
		Button btn_logout = (Button) findViewById(R.id.btn_logout);

		// Dashboard Friends button
		Button btn_scan = (Button) findViewById(R.id.btn_scan);

		// Dashboard Messages button
		Button btn_cart = (Button) findViewById(R.id.btn_cart);

		// Dashboard Checkout button
		Button btn_checkout = (Button) findViewById(R.id.btn_checkout);

		// Dashboard Events button
		Button btn_account = (Button) findViewById(R.id.btn_account);

		// Dashboard Photos button
		Button btn_photos = (Button) findViewById(R.id.btn_photos);

		btn_photos.setVisibility(View.INVISIBLE);

		/**
		 * Handling all button click events
		 * */

		btn_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
				CustomHTTP.makePOST("http://23.21.158.161:4912/logout.php", nameValuePair);
				Intent i = new Intent(getApplicationContext(), com.lossboys.customerapp.CustomerLogin.class);
				startActivity(i);
				finish();
			}
		});

		// Listening Friends button click
		btn_scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), ScanActivity.class);
				startActivity(i);
			}
		});

		// Listening Messages button click
		btn_cart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), CartActivity.class);
				startActivity(i);
			}
		});

		
		// Listening to Places button click
		btn_checkout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), CheckoutActivity.class);
				startActivity(i);
			}
		});
		
		
		// Listening to Events button click
		btn_account.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(), AccountActivity.class);
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
	}
}