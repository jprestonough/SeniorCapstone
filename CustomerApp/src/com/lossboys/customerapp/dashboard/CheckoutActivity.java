package com.lossboys.customerapp.dashboard;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CheckoutActivity extends Activity {
	/** Called when the activity is first created. */
	EditText inputCardNumber;
	EditText inputSecurityCode;
	TextView checkoutErrorMsg, cartTotal;
	Button btn_next;

	//actionbar dropdown
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    //setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
	    return true;
	    
	}
	
	//actionbar stuff (listener)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println(item.getItemId());
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	onBackPressed();
	            return true;
	            
	        case R.id.actionbar_logout:
	        	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(0);
				CustomHTTP.makePOST("http://23.21.158.161:4912/logout.php", nameValuePair);
	            Intent intnt = new Intent(getApplicationContext(), com.lossboys.customerapp.CustomerLogin.class);
				intnt.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
	            intnt.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
				startActivity(intnt);
				finish();
	        	return true;
	        	
	        case R.id.actionbar_settings:
	        	Intent i = new Intent(getApplicationContext(), AccountActivity.class);
				startActivity(i);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout_layout);
		
		//actionbar stuff
		ActionBar bar = this.getActionBar();
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);


		inputCardNumber = (EditText) findViewById(R.id.cardNumber);
		inputSecurityCode = (EditText) findViewById(R.id.securityCode);
		checkoutErrorMsg = (TextView) findViewById(R.id.checkout_error);
		cartTotal = (TextView) findViewById(R.id.cartTotal);

		// Button for Next
		btn_next = (Button) findViewById(R.id.btnNext);

		// Spinner element
		Spinner cardType_spinner = (Spinner) findViewById(R.id.cardtype_spinner);
		// Spinner element
		Spinner cardExpMM_spinner = (Spinner) findViewById(R.id.cardexpMM_spinner);
		// Spinner element
		Spinner cardExpYYYY_spinner = (Spinner) findViewById(R.id.cardexpYYYY_spinner);

		// Spinner Drop down elements
		List<String> cardTypes = new ArrayList<String>();
		cardTypes.add("Visa");
		cardTypes.add("Master Card");
		cardTypes.add("Discover");
		cardTypes.add("American Express");

		// Spinner Drop down elements
		List<String> cardExpMM = new ArrayList<String>();
		cardExpMM.add("01");
		cardExpMM.add("02");
		cardExpMM.add("03");
		cardExpMM.add("04");
		cardExpMM.add("05");
		cardExpMM.add("06");
		cardExpMM.add("07");
		cardExpMM.add("08");
		cardExpMM.add("09");
		cardExpMM.add("10");
		cardExpMM.add("11");
		cardExpMM.add("12");

		// Spinner Drop down elements
		List<String> cardExpYYYY = new ArrayList<String>();
		cardExpYYYY.add("2013");
		cardExpYYYY.add("2014");
		cardExpYYYY.add("2015");
		cardExpYYYY.add("2016");
		cardExpYYYY.add("2017");
		cardExpYYYY.add("2018");
		cardExpYYYY.add("2019");
		cardExpYYYY.add("2020");
		cardExpYYYY.add("2021");
		cardExpYYYY.add("2022");
		cardExpYYYY.add("2023");
		cardExpYYYY.add("2024");
		cardExpYYYY.add("2025");
		cardExpYYYY.add("2026");
		cardExpYYYY.add("2027");
		cardExpYYYY.add("2028");
		cardExpYYYY.add("2029");
		cardExpYYYY.add("2030");
		cardExpYYYY.add("2031");
		cardExpYYYY.add("2032");
		cardExpYYYY.add("2033");

		// Creating adapter for spinner
		ArrayAdapter<String> cardtypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardTypes);
		// Creating adapter for spinner
		ArrayAdapter<String> cardExpMMAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardExpMM);
		// Creating adapter for spinner
		ArrayAdapter<String> cardExpYYYYAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardExpYYYY);

		// Drop down layout style - list view with radio button
		cardtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Drop down layout style - list view with radio button
		cardExpMMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Drop down layout style - list view with radio button
		cardExpYYYYAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		cardType_spinner.setAdapter(cardtypeAdapter);
		// attaching data adapter to spinner
		cardExpMM_spinner.setAdapter(cardExpMMAdapter);
		// attaching data adapter to spinner
		cardExpYYYY_spinner.setAdapter(cardExpYYYYAdapter);

		btn_next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				String cardNumber = inputCardNumber.getText().toString();
				String securityCode = inputSecurityCode.getText().toString();
				
				if (cardNumber.length() == 0 || securityCode.length() == 0){
					Toast toast = Toast.makeText(CheckoutActivity.this, "Please fill out all information.", Toast.LENGTH_SHORT);
					LinearLayout toastLayout = (LinearLayout) toast.getView();
					TextView toastTV = (TextView) toastLayout.getChildAt(0);
					toastTV.setTextSize(20);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else {
					Intent i = new Intent(getApplicationContext(), CheckoutActivity2.class);
					startActivity(i);
				}
			}
		});

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

		nameValuePair.add(new BasicNameValuePair("function", "check_cart"));

		JSONObject cartJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);

		float total = 0;
		DecimalFormat df = new DecimalFormat("#0.00");

		try {
			JSONArray items = cartJSON.getJSONArray("items");

			if(items.length() == 0){
				Toast toast = Toast.makeText(CheckoutActivity.this, "Your cart is empty.", Toast.LENGTH_SHORT);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(20);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				finish();
			}
			
			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);

				String quantity = item.getString("Quantity");
				Float price = Float.parseFloat(item.getString("Price"));

				total += price * Float.parseFloat(quantity);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		cartTotal.setText("Total: $" + df.format(total));
	}
}
