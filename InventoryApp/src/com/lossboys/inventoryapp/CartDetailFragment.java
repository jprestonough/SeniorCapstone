package com.lossboys.inventoryapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_cart_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mCart != null) {
			((TextView) rootView.findViewById(R.id.cart_detail))
					.setText(mCart.content);
		}

		return rootView;
	}
}
