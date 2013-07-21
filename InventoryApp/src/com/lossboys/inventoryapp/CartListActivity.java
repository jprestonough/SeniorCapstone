package com.lossboys.inventoryapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An activity representing a list of Carts. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link CartDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CartListFragment} and the item details (if present) is a
 * {@link CartDetailFragment}.
 * <p>
 * This activity also implements the required {@link CartListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class CartListActivity extends FragmentActivity implements
		CartListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		super.onCreate(savedInstanceState);

		CartListContent.updateCartList();
		
		setContentView(R.layout.activity_cart_list);

		if (findViewById(R.id.cart_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((CartListFragment) getSupportFragmentManager().findFragmentById(
					R.id.cart_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link CartListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(CartDetailFragment.ARG_ITEM_ID, id);
			CartDetailFragment fragment = new CartDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.cart_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CartDetailActivity.class);
			detailIntent.putExtra(CartDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data == null){
			return;
		}
		
		String itemID = data.getStringExtra("ItemID");
		String orderID = data.getStringExtra("orderID");
		
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
		nameValuePair.add(new BasicNameValuePair("function", "pick_item"));
		nameValuePair.add(new BasicNameValuePair("orderid", orderID));
		nameValuePair.add(new BasicNameValuePair("itemid", itemID));
		JSONObject checkJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/inventory.php", nameValuePair);
		
		String text = "Server error";
		
		if (checkJSON != null) {
			try {
				String jsonResult = checkJSON.getString("error");

				if (jsonResult.equals("false")) {
					text = "Item added";
				}else if (jsonResult.equals("done")) {
					text = "Warning: Item is already fulfilled.";
				}else if (jsonResult.equals("invalid")) {
					text = "Warning: Item is not in order.";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Toast toast = Toast.makeText(CartListActivity.this, text, Toast.LENGTH_SHORT);
		LinearLayout toastLayout = (LinearLayout) toast.getView();
		TextView toastTV = (TextView) toastLayout.getChildAt(0);
		toastTV.setTextSize(20);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		CartDetailFragment f = (CartDetailFragment)getSupportFragmentManager().findFragmentById(R.id.cart_detail_container);
		f.updateList();		
		
		CartListContent.updateCartList();
	}
}
