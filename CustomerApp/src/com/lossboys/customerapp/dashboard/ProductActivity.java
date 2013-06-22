package com.lossboys.customerapp.dashboard;

import java.net.URL;

import com.lossboys.customerapp.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends Activity {
	TextView itemName,itemDepartment,itemDescription,itemPrice,itemQuantity;
	ImageView itemImage;
	
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
        
    }
}