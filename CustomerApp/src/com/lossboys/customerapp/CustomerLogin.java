package com.lossboys.customerapp;

import com.lossboys.customerapp.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lossboys.customerapp.library.DatabaseHandler;
import com.lossboys.customerapp.library.UserFunctions;

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
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

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
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
				Intent i = new Intent(getApplicationContext(), CustomerRegister.class);
				
				// check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
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
                             
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                startActivity(i); 
//				if (TOGGLE_ON_CLICK) {
//					mSystemUiHider.toggle();
//				} else {
//					mSystemUiHider.show();
//				}
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
