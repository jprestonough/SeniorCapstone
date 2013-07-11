package com.lossboys.customerapp.dashboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.widget.TextView;

public class CheckoutActivity2 extends Activity {
	/** Called when the activity is first created. */

	Button btn_submit;
	EditText inputCardName;
	EditText inputCardAddress;
	TextView cartTotal;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout2_layout);

		inputCardName = (EditText) findViewById(R.id.cardName);
		inputCardAddress = (EditText) findViewById(R.id.cardAddress);
		btn_submit = (Button) findViewById(R.id.btnSubmit);
		cartTotal = (TextView) findViewById(R.id.cartTotal);

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

		nameValuePair.add(new BasicNameValuePair("function", "check_cart"));

		JSONObject cartJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);

		float total = 0;
		DecimalFormat df = new DecimalFormat("#0.00");

		try {
			JSONArray items = cartJSON.getJSONArray("items");

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

		btn_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				String cardName = inputCardName.getText().toString();
				String cardAddress = inputCardAddress.getText().toString();

				if (cardName.length() == 0 || cardAddress.length() == 0) {
					Toast toast = Toast.makeText(CheckoutActivity2.this, "Please fill out all information.", Toast.LENGTH_SHORT);
					LinearLayout toastLayout = (LinearLayout) toast.getView();
					TextView toastTV = (TextView) toastLayout.getChildAt(0);
					toastTV.setTextSize(20);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
					nameValuePair.add(new BasicNameValuePair("function", "checkout_cart"));

					JSONObject checkJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);
					if (checkJSON != null) {
						try {
							String jsonResult = checkJSON.getString("error");
							if (jsonResult.equals("false")) {
								Toast toast = Toast
										.makeText(
												CheckoutActivity2.this,
												"Your order has been received. You will receive a confirmation email and a notification when your order is ready for pickup!",
												Toast.LENGTH_SHORT);
								LinearLayout toastLayout = (LinearLayout) toast.getView();
								TextView toastTV = (TextView) toastLayout.getChildAt(0);
								toastTV.setTextSize(20);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								finish();
							} else
								Toast.makeText(CheckoutActivity2.this, "Server error.", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(CheckoutActivity2.this, "Server error.", Toast.LENGTH_SHORT).show();
						}
					}
				}

			}
		});

	}
}
