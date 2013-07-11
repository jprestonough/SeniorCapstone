package com.lossboys.customerapp.dashboard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity {
	TextView itemName, itemDescription, itemPrice, itemQuantity, itemAvailability;
	ImageView itemImage;
	Button addToCart, buttonDescription, buttonSpecification, buttonRating;
	ProgressDialog pd = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_layout);

		pd = ProgressDialog.show(this, "Working..", "Downloading Data...", true, false);

		new DownloadTask().execute(getIntent().getStringExtra("ItemID"));

		addToCart = (Button) findViewById(R.id.btnAddtoCart);

		addToCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String quantity = "1";

				// Building post parameters
				// key and value pair
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
				nameValuePair.add(new BasicNameValuePair("function", "add_cart"));
				nameValuePair.add(new BasicNameValuePair("itemid", getIntent().getStringExtra("ItemID")));
				nameValuePair.add(new BasicNameValuePair("quantity", quantity));

				JSONObject resultJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);

				Context context = getApplicationContext();
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;

				if (resultJSON != null) {
					try {
						String jsonResult = resultJSON.getString("error");
						if (jsonResult.equals("true"))
							text = "Add to cart failed.";
						else
							text = "Add to cart succeeded.";
					} catch (Exception e) {
						e.printStackTrace();
						text = "Add to cart failed.";
					}
				} else
					text = "Add to cart failed.";
				Toast toast = Toast.makeText(context, text, duration);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(20);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				Intent returnIntent = new Intent();
				setResult(RESULT_OK, returnIntent);
				finish();
			}
		});
	}

	private class DownloadTask extends AsyncTask<String, Void, Object> {
		protected Object doInBackground(String... args) {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
			nameValuePair.add(new BasicNameValuePair("ItemID", args[0]));

			JSONObject scanJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/get_item.php", nameValuePair);

			return scanJSON;
		}

		protected void onPostExecute(Object result) {
			itemName = (TextView) findViewById(R.id.item_name);
			itemDescription = (TextView) findViewById(R.id.item_description);
			itemPrice = (TextView) findViewById(R.id.item_price);
			//itemQuantity = (TextView) findViewById(R.id.item_quantity);
			itemAvailability = (TextView) findViewById(R.id.item_availability_color);
			itemImage = (ImageView) findViewById(R.id.item_image);
			buttonDescription = (Button) findViewById(R.id.button_description);
			buttonSpecification = (Button) findViewById(R.id.button_specification);
			buttonRating = (Button) findViewById(R.id.button_rating);
			
			int quantity;

			final JSONObject scanJSON = (JSONObject) result;

			if (scanJSON != null) {
				try {
					if (scanJSON.isNull("Error")) {
						itemName.setText(scanJSON.getString("Name"));
						//itemDescription.setText(scanJSON.getString("Description"));
						itemPrice.setText("$" + scanJSON.getString("Price"));
						itemDescription.setText(scanJSON.getString("Description"));
						quantity = Integer.parseInt(scanJSON.getString("Quantity"));
						if (quantity == 0) {
				        	itemAvailability.setText("  Red");
				        	itemAvailability.setTextColor(Color.RED);
				        } else if (quantity < 5) {
				        	itemAvailability.setText("  Yellow");
				        	itemAvailability.setTextColor(Color.YELLOW);
				        } else {
				        	itemAvailability.setText("  Green");
				        	itemAvailability.setTextColor(Color.GREEN);
				        }
						
						buttonDescription.setOnClickListener(new View.OnClickListener() {
						    public void onClick(View v) {
						    	try {
									itemDescription.setText(scanJSON.getString("Description"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						    }
						});
						
						buttonSpecification.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								itemDescription.setText("Dummy Specs\n Stuff stuff stuff \n stuff stuff stuff");
							}
						});
						
						buttonRating.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								itemDescription.setText("five starts");
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			URL url;
			try {
				url = new URL("http://23.21.158.161:4912/item_images/" + getIntent().getStringExtra("ItemID") + ".jpg");
				Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				itemImage.setImageBitmap(bmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (ProductActivity.this.pd != null) {
				ProductActivity.this.pd.dismiss();
			}
		}
	}
}