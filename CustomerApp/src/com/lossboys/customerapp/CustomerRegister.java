package com.lossboys.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class CustomerRegister extends Activity {
	
	Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.customer_register);
        
     // Importing all assets like buttons, text fields
        inputFirstName = (EditText) findViewById(R.id.reg_firstname);
        inputLastName = (EditText) findViewById(R.id.reg_lastname);
        inputEmail = (EditText) findViewById(R.id.reg_email);
        inputPassword = (EditText) findViewById(R.id.reg_password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
 
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String firstname = inputFirstName.getText().toString();
                String lastname = inputLastName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
            }
        });
 
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        CustomerLogin.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });
    }
}
