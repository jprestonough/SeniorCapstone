package com.lossboys.customerapp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends Activity {

	Button btn_changeEmail;
	Button btn_changePassword;
	
	//actionbar stuff (listener)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println(item.getItemId());
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	onBackPressed();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_layout);
		
		//actionbar stuff
		ActionBar bar = this.getActionBar();
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);

		btn_changeEmail = (Button) findViewById(R.id.btnChangeEmail);
		btn_changePassword = (Button) findViewById(R.id.btnChangePassword);

		btn_changeEmail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), ChangeEmail.class);
				startActivity(i);
			}
		});

		btn_changePassword.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), ChangePassword.class);
				startActivity(i);
			}
		});
	}
}
