package com.lossboys.customerapp.dashboard;

import com.lossboys.customerapp.CustomerCameraPreview;

import com.lossboys.customerapp.R;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.FrameLayout;
/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class ScanActivity extends Activity {
	Button btnItemID;
    EditText inputItemID;
    TextView scanErrorMsg;
    
    TextView scanText;
    Button scanButton;

    ImageScanner scanner;
    
    String itemID;
    
	private Camera mCamera;
	private CustomerCameraPreview mPreview;
	private Handler autoFocusHandler;
	
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    }
    
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        
        //setContentView(R.layout.customer_camera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        
        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);
        
        mPreview = new CustomerCameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });
        /* 
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
		}); */
    }
    
    public void onPause() {
        super.onPause();
        releaseCamera();
    }
    
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }
    
    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
    
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

	PreviewCallback previewCb = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);
            itemID = "1";
            
            int result = scanner.scanImage(barcode);
            
            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                
                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                	// sym.getData() contains the url
                    scanText.setText("barcode result " + sym.getData());
                    barcodeScanned = true;
                    itemID = sym.getData();
                }
                
				Intent i = new Intent(getApplicationContext(),
		                com.lossboys.customerapp.dashboard.ProductActivity.class);

				i.putExtra("ItemID",itemID);
				
                startActivity(i);
                finish();
            }
        }
    };
	
	// Mimic continuous auto-focusing
	AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
}