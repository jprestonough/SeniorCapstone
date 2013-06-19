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

public class CustomerRegister extends Activity{
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.HONEYCOMB) {
            //show up button in action bar
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView loginScreen = (TextView) findViewById(R.id.loginLink);
        loginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
