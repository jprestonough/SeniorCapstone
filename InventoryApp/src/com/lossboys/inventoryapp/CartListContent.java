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
	
	public static void updateCartList() {
		CARTS.clear();
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);

		nameValuePair.add(new BasicNameValuePair("function", "check_orders"));

		JSONObject cartJSON = CustomHTTP.makePOST(
				"http://23.21.158.161:4912/inventory.php", nameValuePair);

		try {
			JSONArray items = cartJSON.getJSONArray("orders");

			for (int i = 0; i < items.length(); i++) {
				JSONObject order = items.getJSONObject(i);

				String remaining = order.getString("Items");
				String orderID = order.getString("OrderID");
				
				addItem(new Cart(orderID,"Order ID: "+orderID+", Items remaining: "+remaining));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
