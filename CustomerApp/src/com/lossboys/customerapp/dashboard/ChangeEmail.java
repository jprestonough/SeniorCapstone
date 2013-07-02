package com.lossboys.customerapp.dashboard;

import com.lossboys.customerapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeEmail extends Activity{
	Button btnApply;
    EditText newEmail;
    EditText confirmEmail;
    TextView changeEmailErrorMsg;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_email_layout);
        
        newEmail = (EditText) findViewById(R.id.new_email);
        confirmEmail = (EditText) findViewById(R.id.new_confemail);
        btnApply = (Button) findViewById(R.id.btnApply);
        changeEmailErrorMsg = (TextView) findViewById(R.id.changeEmail_error);

        btnApply.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
                String new_email = newEmail.getText().toString();
                String conf_email = confirmEmail.getText().toString();
                
                changeEmailErrorMsg.setText("Changes Saved!");
            }
        });
    }

}
