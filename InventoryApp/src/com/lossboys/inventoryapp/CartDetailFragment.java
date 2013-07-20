package com.lossboys.inventoryapp;

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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lossboys.inventoryapp.CartListContent;

/**
 * A fragment representing a single Cart detail screen. This fragment is either
 * contained in a {@link CartListActivity} in two-pane mode (on tablets) or a
 * {@link CartDetailActivity} on handsets.
 */
public class CartDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private CartListContent.Cart mCart;
	private ArrayList<HashMap<String, String>> itemList;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CartDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mCart = CartListContent.CART_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
		itemList = new ArrayList<HashMap<String, String>>();
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_cart_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mCart != null) {
			//((TextView) rootView.findViewById(R.id.cart_detail))
			//		.setText(mCart.content);
			Button btn_scan = (Button) rootView.findViewById(R.id.scanButton);
			btn_scan.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent i = new Intent(getActivity().getApplicationContext(), ScanActivity.class);
					startActivityForResult(i,1);
				}
			});
		}

		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		
		super.onActivityCreated(savedInstanceState);
		
		itemList.clear();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

		nameValuePair.add(new BasicNameValuePair("function", "check_items"));
		nameValuePair.add(new BasicNameValuePair("orderid", getArguments().getString(
				ARG_ITEM_ID)));

		JSONObject cartJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/inventory.php", nameValuePair);

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

				itemList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ListView listview = (ListView) getActivity().findViewById(R.id.list);
		
		ListAdapter adapter = new SimpleAdapter(listview.getContext(), itemList, R.layout.cart_item_layout, new String[] { "Name", "Quantity", "Price",
				"ItemID" }, new int[] { R.id.cartName, R.id.cartQuantity, R.id.cartPrice, R.id.cartItemID });

		listview.setAdapter(adapter);
	}
	

}
