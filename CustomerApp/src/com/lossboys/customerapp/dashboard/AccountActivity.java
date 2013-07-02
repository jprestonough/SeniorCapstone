package com.lossboys.customerapp.dashboard;

import com.lossboys.customerapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends Activity {

	Button btn_changeEmail;
	Button btn_changePassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_layout);

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
