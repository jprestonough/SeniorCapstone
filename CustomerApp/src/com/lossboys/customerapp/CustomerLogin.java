package com.lossboys.customerapp;

import com.lossboys.customerapp.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class CustomerLogin extends Activity {
	
	Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
 
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.customer_login);
		
		// Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
		
		//TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
//		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//				HIDER_FLAGS);
//		mSystemUiHider.setup();
//		mSystemUiHider
//				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
//					// Cached values.
//					int mControlsHeight;
//					int mShortAnimTime;
//
//					@Override
//					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//					public void onVisibilityChange(boolean visible) {
//						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//							// If the ViewPropertyAnimator API is available
//							// (Honeycomb MR2 and later), use it to animate the
//							// in-layout UI controls at the bottom of the
//							// screen.
//							if (mControlsHeight == 0) {
//								mControlsHeight = controlsView.getHeight();
//							}
//							if (mShortAnimTime == 0) {
//								mShortAnimTime = getResources().getInteger(
//										android.R.integer.config_shortAnimTime);
//							}
//							controlsView
//									.animate()
//									.translationY(visible ? 0 : mControlsHeight)
//									.setDuration(mShortAnimTime);
//						} else {
//							// If the ViewPropertyAnimator APIs aren't
//							// available, simply show or hide the in-layout UI
//							// controls.
//							controlsView.setVisibility(visible ? View.VISIBLE
//									: View.GONE);
//						}
//
//						if (visible && AUTO_HIDE) {
//							// Schedule a hide().
//							delayedHide(AUTO_HIDE_DELAY_MILLIS);
//						}
//					}
//				});

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
        				else
        					loginErrorMsg.setText("Login successful");
    				} catch (JSONException e) {
    					e.printStackTrace();
    					loginErrorMsg.setText("Login successful");
    				}
                } else
					loginErrorMsg.setText("Login successful");
			}
		});
        
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        CustomerRegister.class);
                startActivity(i);
                finish();
            }
        });

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
//		findViewById(R.id.dummy_button).setOnTouchListener(
//				mDelayHideTouchListener);
	}

//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//
//		// Trigger the initial hide() shortly after the activity has been
//		// created, to briefly hint to the user that UI controls
//		// are available.
//		delayedHide(100);
//	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
//	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//		@Override
//		public boolean onTouch(View view, MotionEvent motionEvent) {
//			if (AUTO_HIDE) {
//				delayedHide(AUTO_HIDE_DELAY_MILLIS);
//			}
//			return false;
//		}
//	};
//
//	Handler mHideHandler = new Handler();
//	Runnable mHideRunnable = new Runnable() {
//		@Override
//		public void run() {
//			mSystemUiHider.hide();
//		}
//	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
//	private void delayedHide(int delayMillis) {
//		mHideHandler.removeCallbacks(mHideRunnable);
//		mHideHandler.postDelayed(mHideRunnable, delayMillis);
//	}
}
