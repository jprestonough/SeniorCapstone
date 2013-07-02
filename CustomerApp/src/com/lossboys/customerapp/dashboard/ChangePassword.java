package com.lossboys.customerapp.dashboard;

import com.lossboys.customerapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePassword extends Activity{
	Button btnApply;
    EditText newPassword;
    EditText confirmPassword;
    TextView changePasswordErrorMsg;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.new_password);
        btnApply = (Button) findViewById(R.id.btnApply);
        changePasswordErrorMsg = (TextView) findViewById(R.id.changePassword_error);
        
        btnApply.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String new_password = newPassword.getText().toString();
                String conf_password = confirmPassword.getText().toString();
                
                changePasswordErrorMsg.setText("Changes Saved!");
            }
        });
    }

}
