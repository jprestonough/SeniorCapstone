package com.lossboys.customerapp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeEmail extends Activity {
	Button btnApply;
	EditText newEmail;
	EditText confirmEmail;
	TextView changeEmailErrorMsg;

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
		setContentView(R.layout.change_email_layout);

		//actionbar stuff
		ActionBar bar = this.getActionBar();
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);

		
		newEmail = (EditText) findViewById(R.id.new_email);
		confirmEmail = (EditText) findViewById(R.id.new_confemail);
		btnApply = (Button) findViewById(R.id.btnApply);

		btnApply.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String new_email = newEmail.getText().toString();
				String conf_email = confirmEmail.getText().toString();
				Context context = ChangeEmail.this;
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
				boolean done = false;

				if (new_email.compareTo(conf_email) == 0 && new_email.length() != 0) {
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
					nameValuePair.add(new BasicNameValuePair("email", new_email));
					nameValuePair.add(new BasicNameValuePair("function", "change_email"));

					JSONObject registerJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/account.php", nameValuePair);

					if (registerJSON != null) {
						try {
							String jsonResult = registerJSON.getString("error");
							if (jsonResult.equals("1"))
								text = "Invalid email address.";
							else if (jsonResult.equals("2"))
								text = "Server Error.";
							else{
								text = "Update successful.";
								done = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
							text = "Server Error.";
						}
					} else
						text = "Server Error.";
				} else {
					text = "Emails do not match.";
				}
				
				Toast toast = Toast.makeText(context, text, duration);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(20);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
				if(done)
					finish();
			}
		});
	}

}
