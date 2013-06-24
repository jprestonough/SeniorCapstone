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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

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
	
	private void updateCart(){
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

	protected void onListItemClick(ListView list, View view, int position, long id) {
		//String selection = list.getItemAtPosition(position).toString();
		HashMap<String, String> item = (HashMap<String, String>)list.getItemAtPosition(position);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CartActivity.this);
		 
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.cartdialog_layout,null);
		((TextView) v.findViewById(R.id.dialogItemID)).setText(item.get("ItemID"));
		
		alertDialogBuilder.setView(v);
		
		alertDialogBuilder.setTitle(item.get("Name"));
		
		alertDialogBuilder
			.setMessage("Enter quantity: ")
			.setCancelable(false)
			.setPositiveButton("Update cart",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					AlertDialog adialog = (AlertDialog) dialog;
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
	                nameValuePair.add(new BasicNameValuePair("function","modify_cart"));
	                nameValuePair.add(new BasicNameValuePair("itemid", ((TextView)adialog.findViewById(R.id.dialogItemID)).getText().toString()));
	                nameValuePair.add(new BasicNameValuePair("quantity",((TextView)adialog.findViewById(R.id.dialogQuantity)).getText().toString()));
	                
	                JSONObject resultJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/cart.php", nameValuePair);
	                
	                Context context = getApplicationContext();
	                CharSequence text;
	                int duration = Toast.LENGTH_SHORT;

	                if(resultJSON != null){
	    				try {
	    					String jsonResult = resultJSON.getString("error");
	        				if(jsonResult.equals("true"))
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
	                updateCart();
				}
			  })
			.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		alertDialog.show();
	}
}
