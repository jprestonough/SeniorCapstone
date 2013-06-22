package com.lossboys.customerapp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;

public class CustomerDashboard extends Activity {
	Button btnLogout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

		// Dashboard Places button
		Button btn_places = (Button) findViewById(R.id.btn_places);

		// Dashboard Events button
		Button btn_events = (Button) findViewById(R.id.btn_events);

		// Dashboard Photos button
		Button btn_photos = (Button) findViewById(R.id.btn_photos);

		btn_places.setVisibility(View.INVISIBLE);
		btn_events.setVisibility(View.INVISIBLE);
		btn_photos.setVisibility(View.INVISIBLE);
		
		/**
		 * Handling all button click events
		 * */

		btn_logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
		        CustomHTTP.makePOST("http://23.21.158.161:4912/logout.php", nameValuePair);
				Intent i = new Intent(getApplicationContext(),
	                    com.lossboys.customerapp.CustomerLogin.class);
	            startActivity(i);
	            finish();
			}
		});

		// Listening Friends button click
		btn_scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						ScanActivity.class);
				startActivity(i);
			}
		});

		// Listening Messages button click
		btn_cart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						CartActivity.class);
				startActivity(i);
			}
		});

		// Listening to Places button click
		btn_places.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						PlacesActivity.class);
				startActivity(i);
			}
		});

		// Listening to Events button click
		btn_events.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						EventsActivity.class);
				startActivity(i);
			}
		});

		// Listening to Photos button click
		btn_photos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Intent i = new Intent(getApplicationContext(),
						PhotosActivity.class);
				startActivity(i);
			}
		});
	}
}