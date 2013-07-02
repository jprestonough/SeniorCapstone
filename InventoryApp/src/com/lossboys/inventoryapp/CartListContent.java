package com.lossboys.inventoryapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lossboys.inventoryapp.CustomHTTP;
import com.lossboys.inventoryapp.R;
import com.lossboys.inventoryapp.CartListContent;

public class CartListContent {
	
	TextView totalView;
	ArrayList<HashMap<String, String>> cartList;
	/**
	 * An array of sample (dummy) items.
	 */
	public static List<Cart> CARTS = new ArrayList<Cart>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, Cart> CART_MAP = new HashMap<String, Cart>();

	static {
		// Add 3 sample items.
		addItem(new Cart("1", "Item 1"));
		addItem(new Cart("2", "Item 2"));
		addItem(new Cart("3", "Item 3"));
	}

	private static void addItem(Cart cart) {
		CARTS.add(cart);
		CART_MAP.put(cart.id, cart);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class Cart {
		public String id;
		public String content;

		public Cart(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
	
	private void updateCartList() {
		cartList.clear();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

		nameValuePair.add(new BasicNameValuePair("function", "check_cart"));

		JSONObject cartJSON = CustomHTTP.makePOST(
				"http://23.21.158.161:4912/cart.php", nameValuePair);

		float total = 0;
		DecimalFormat df = new DecimalFormat("#0.00");

		try {
			JSONArray items = cartJSON.getJSONArray("items");

			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);

				String name = item.getString("Name");
				String quantity = item.getString("Quantity");
				Float price = Float.parseFloat(item.getString("Price"));

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("Name", name);
				map.put("Quantity", quantity);
				map.put("Price", "$" + df.format(price));
				map.put("ItemID", item.getString("ItemID"));
				total += price * Float.parseFloat(quantity);

				cartList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
