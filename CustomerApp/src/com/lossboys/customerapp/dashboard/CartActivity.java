package com.lossboys.customerapp.dashboard;

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
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class CartActivity extends ListActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cart_layout);

		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("function", "check_cart"));
        
        JSONObject cartJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);

		try {
			JSONArray items = cartJSON.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);

				String name = item.getString("Name");
				String quantity = item.getString("Quantity");
				String price = item.getString("Price");

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Name", name);
				map.put("Quantity", "Quantity: "+quantity);
				map.put("Price", "Price: $"+price);

				itemList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ListAdapter adapter = new SimpleAdapter(this, itemList,
				R.layout.list_item, new String[] { "Name", "Quantity",
						"Price" }, new int[] { R.id.cartName, R.id.cartQuantity,
						R.id.cartPrice });

		setListAdapter(adapter);
	}
}
