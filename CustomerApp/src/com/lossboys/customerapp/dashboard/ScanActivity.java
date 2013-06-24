package com.lossboys.customerapp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.lossboys.customerapp.CustomHTTP;
import com.lossboys.customerapp.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ScanActivity extends Activity {
	Button btnItemID;
    EditText inputItemID;
    TextView scanErrorMsg;
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        
		// Importing all assets like buttons, text fields
        inputItemID = (EditText) findViewById(R.id.ItemIdField);
        btnItemID = (Button) findViewById(R.id.btnItemID);
        scanErrorMsg = (TextView) findViewById(R.id.scan_error);
                
        btnItemID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String ItemID = inputItemID.getText().toString();

                // Building post parameters
                // key and value pair
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                nameValuePair.add(new BasicNameValuePair("ItemID", ItemID));
                
                JSONObject scanJSON = CustomHTTP.makePOST("http://23.21.158.161:4912/get_item.php", nameValuePair);
                
                if(scanJSON != null){
    				try {
    					if(!scanJSON.isNull("Error")){
    						scanErrorMsg.setText("Item does not exist.");
    					}else{
    						Intent i = new Intent(getApplicationContext(),
    				                com.lossboys.customerapp.dashboard.ProductActivity.class);

    						i.putExtra("Name",scanJSON.getString("Name"));
    						i.putExtra("Department",scanJSON.getString("Department"));
    						i.putExtra("Description",scanJSON.getString("Description"));
    						i.putExtra("Price",scanJSON.getString("Price"));
    						i.putExtra("Quantity",scanJSON.getString("Quantity"));
    						i.putExtra("ItemID",ItemID);
    						
        	                startActivity(i);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    					scanErrorMsg.setText("Lookup failed.");
    				}
                } else
                	scanErrorMsg.setText("Lookup failed.");
			}
		});
    }
}