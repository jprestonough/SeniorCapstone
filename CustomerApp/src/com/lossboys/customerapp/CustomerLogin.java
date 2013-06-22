package com.lossboys.customerapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.widget.Button;
import android.widget.EditText;


public class CustomerLogin extends Activity {
	
	Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    
    boolean disableLogin = false;
 
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
    	 if (android.os.Build.VERSION.SDK_INT > 9) {
    	     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	     StrictMode.setThreadPolicy(policy);
    	   }
    	
		super.onCreate(savedInstanceState);

		// Check to see if already logged in
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
        JSONObject checkJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/check_login.php", nameValuePair);
        if(checkJSON != null){
			try {
				String jsonResult = checkJSON.getString("login");
				if(jsonResult.equals("true") || disableLogin){
					Intent i = new Intent(getApplicationContext(),
		                    com.lossboys.customerapp.dashboard.CustomerDashboard.class);
		            startActivity(i);
		            finish();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

		setContentView(R.layout.customer_login);
		
		// Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
		
		// Set up the user interaction to manually show or hide the system UI.
        btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // Building post parameters
                // key and value pair
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("email", email));
                nameValuePair.add(new BasicNameValuePair("password",password));
                
                JSONObject loginJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/login.php", nameValuePair);
         
                if(loginJSON != null){
    				try {
    					String jsonResult = loginJSON.getString("login");
        				if(jsonResult.equals("false"))
        					loginErrorMsg.setText("Invalid email or password!");
        				else{
        					Intent i = new Intent(getApplicationContext(),
        	                        com.lossboys.customerapp.dashboard.CustomerDashboard.class);
        	                startActivity(i);
        	                finish();
        				}
    				} catch (Exception e) {
    					e.printStackTrace();
    					loginErrorMsg.setText("Login failed.");
    				}
                } else
					loginErrorMsg.setText("Login failed.");
			}
		});
        
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        CustomerRegister.class);
                startActivity(i);
            }
        });
	}
}
