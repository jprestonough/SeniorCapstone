package com.lossboys.customerapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static android.R.id.home;

public class CustomerLogin extends Activity{
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB) {
            //show up button in action bar
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView registerScreen = (TextView) findViewById(R.id.registerLink);
        registerScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), CustomerRegister.class);
                startActivity(registerIntent);
            }
        });

    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
