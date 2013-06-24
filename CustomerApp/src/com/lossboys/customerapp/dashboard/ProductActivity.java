package com.lossboys.customerapp.dashboard;

import java.net.URL;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends Activity {
	TextView itemName,itemDepartment,itemDescription,itemPrice,itemQuantity;
	ImageView itemImage;
	EditText inputQuantity;
	Button addToCart;
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_layout);
        
        itemName = (TextView) findViewById(R.id.item_name);
        itemDepartment = (TextView) findViewById(R.id.item_department);
        itemDescription = (TextView) findViewById(R.id.item_description);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemQuantity = (TextView) findViewById(R.id.item_quantity);
        itemImage = (ImageView) findViewById(R.id.item_image);
        inputQuantity = (EditText) findViewById(R.id.productQuantity);
        addToCart = (Button) findViewById(R.id.btnAddtoCart);
        
        itemName.setText(getIntent().getStringExtra("Name"));
        itemDepartment.setText(getIntent().getStringExtra("Department"));
        itemDescription.setText(getIntent().getStringExtra("Description"));
        itemPrice.setText("$"+getIntent().getStringExtra("Price"));
        itemQuantity.setText(getIntent().getStringExtra("Quantity"));
        
        URL url;
		try {
			url = new URL("http://23.21.158.161:4912/item_images/"+getIntent().getStringExtra("ItemID")+".jpg");
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			itemImage.setImageBitmap(bmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		addToCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String quantity = inputQuantity.getText().toString();

                // Building post parameters
                // key and value pair
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
                nameValuePair.add(new BasicNameValuePair("function","add_cart"));
                nameValuePair.add(new BasicNameValuePair("itemid", getIntent().getStringExtra("ItemID")));
                nameValuePair.add(new BasicNameValuePair("quantity",quantity));
                
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
                finish();
			}
		});
    }
}