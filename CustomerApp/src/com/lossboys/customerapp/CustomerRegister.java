package com.lossboys.customerapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
 
import com.lossboys.customerapp.library.DatabaseHandler;
import com.lossboys.customerapp.library.UserFunctions;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerRegister extends Activity {
	
	Button btnRegister;
    Button btnLinkToLogin;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;
     
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    
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
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.registerUser(firstname, lastname, email, password);
                 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        registerErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1){
                            // user successfully registred
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                             
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), CustomerDashboard.class);
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in registration
                            registerErrorMsg.setText("Error occured in registration");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
