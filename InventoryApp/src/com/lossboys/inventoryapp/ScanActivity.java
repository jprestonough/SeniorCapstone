package com.lossboys.inventoryapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.ActionBar;
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
	String orderID;

	private Camera mCamera;
	private InventoryCameraPreview mPreview;
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
		
		orderID = getIntent().getStringExtra("orderID");
				
		//Actionbar stuff
		ActionBar bar = this.getActionBar();
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayHomeAsUpEnabled(true);

		autoFocusHandler = new Handler();
		mCamera = getCameraInstance();

		/* Instance barcode scanner */
		scanner = new ImageScanner();
		scanner.setConfig(0, Config.X_DENSITY, 3);
		scanner.setConfig(0, Config.Y_DENSITY, 3);

		mPreview = new InventoryCameraPreview(this, mCamera, previewCb, autoFocusCB);
		FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
		preview.addView(mPreview);

		scanText = (TextView) findViewById(R.id.scanText);

		scanButton = (Button) findViewById(R.id.ScanButton);

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
	}

	public void onPause() {
		super.onPause();
		releaseCamera();
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
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
				
				Intent returnIntent = new Intent();
				returnIntent.putExtra("ItemID", itemID);
				returnIntent.putExtra("orderID", orderID);
				if (getParent() == null) {
				    setResult(Activity.RESULT_OK, returnIntent);
				} else {
				    getParent().setResult(Activity.RESULT_OK, returnIntent);
				}
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