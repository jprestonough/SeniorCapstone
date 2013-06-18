package com.lossboys.customerapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class CustomerHome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void gotoLogin(View view){
        //goto Login Page
        Intent intent = new Intent(this, CustomerLogin.class);
        startActivity(intent);
    }

    public void gotoRegister(View view){
        //goto Register Page
        Intent intent = new Intent(this, CustomerRegister.class);
        startActivity(intent);
    }
    
}
