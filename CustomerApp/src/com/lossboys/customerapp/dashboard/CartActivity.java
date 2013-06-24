package com.lossboys.customerapp.dashboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CartActivity extends ListActivity {
	TextView totalView;
	ArrayList<HashMap<String, String>> itemList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_layout);

		totalView = (TextView) findViewById(R.id.cartTotal);

		itemList = new ArrayList<HashMap<String, String>>();

		updateCart();

		ListAdapter adapter = new SimpleAdapter(this, itemList,
				R.layout.list_item,
				new String[] { "Name", "Quantity", "Price" }, new int[] {
						R.id.cartName, R.id.cartQuantity, R.id.cartPrice });

		setListAdapter(adapter);
	}

	private void updateCart() {
		itemList.clear();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("function", "check_cart"));

		JSONObject cartJSON = CustomHTTP.makePOST(
				"http://23.21.158.161:4912/cart.php", nameValuePair);

		float total = 0;
		DecimalFormat df = new DecimalFormat("#.00");

		try {
			JSONArray items = cartJSON.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);

				String name = item.getString("Name");
				String quantity = item.getString("Quantity");
				Float price = Float.parseFloat(item.getString("Price"));

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Name", name);
				map.put("Quantity", "Quantity: " + quantity);
				map.put("Price", "Price: $" + df.format(price));
				map.put("ItemID", item.getString("ItemID"));
				total += price * Float.parseFloat(quantity);

				itemList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		totalView.setText("Total: $" + df.format(total));
	}

	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		// String selection = list.getItemAtPosition(position).toString();
		HashMap<String, String> item = (HashMap<String, String>) list
				.getItemAtPosition(position);

		final Dialog itemDialog = new Dialog(CartActivity.this);
		itemDialog.setContentView(R.layout.cartdialog_layout);
		itemDialog.setTitle(item.get("Name"));
		
		((TextView) itemDialog.findViewById(R.id.dialogItemID)).setText(item
				.get("ItemID"));

		Button update = (Button) itemDialog.findViewById(R.id.dialogButtonUpdate);
		Button remove = (Button) itemDialog.findViewById(R.id.dialogButtonRemove);
		Button cancel = (Button) itemDialog.findViewById(R.id.dialogButtonCancel);

		update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
				nameValuePair.add(new BasicNameValuePair("function", "modify_cart"));
				nameValuePair.add(new BasicNameValuePair("itemid",((TextView) itemDialog.findViewById(R.id.dialogItemID)).getText().toString()));
				nameValuePair.add(new BasicNameValuePair("quantity",((TextView) itemDialog.findViewById(R.id.dialogQuantity)).getText().toString()));
	
				JSONObject resultJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php",nameValuePair);
	
				Context context = CartActivity.this;
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
	
				if (resultJSON != null) {
					try {
						String jsonResult = resultJSON.getString("error");
						if (jsonResult.equals("true"))
							text = "Update failed.";
						else
							text = "Update succeeded.";
					} catch (Exception e) {
						e.printStackTrace();
						text = "Update failed.";
					}
				} else
					text = "Update failed.";
	
				Toast toast = Toast.makeText(context, text,duration);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(20);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				updateCart();
				itemDialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				itemDialog.dismiss();
			}
		});
		remove.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
				nameValuePair.add(new BasicNameValuePair("function", "remove_cart"));
				nameValuePair.add(new BasicNameValuePair("itemid",((TextView) itemDialog.findViewById(R.id.dialogItemID)).getText().toString()));
	
				JSONObject resultJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php",nameValuePair);
	
				Context context = CartActivity.this;
				CharSequence text;
				int duration = Toast.LENGTH_SHORT;
	
				if (resultJSON != null) {
					try {
						String jsonResult = resultJSON.getString("error");
						if (jsonResult.equals("true"))
							text = "Remove failed.";
						else
							text = "Remove succeeded.";
					} catch (Exception e) {
						e.printStackTrace();
						text = "Remove failed.";
					}
				} else
					text = "Remove failed.";
	
				Toast toast = Toast.makeText(context, text,duration);
				LinearLayout toastLayout = (LinearLayout) toast.getView();
				TextView toastTV = (TextView) toastLayout.getChildAt(0);
				toastTV.setTextSize(20);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				updateCart();
				itemDialog.dismiss();
			}
		});

		itemDialog.show();
	}
}
