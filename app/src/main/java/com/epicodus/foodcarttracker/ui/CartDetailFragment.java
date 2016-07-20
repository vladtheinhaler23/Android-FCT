package com.epicodus.foodcarttracker.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartDetailFragment extends Fragment implements View.OnClickListener {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;


    @Bind(R.id.cartImageView) ImageView mImageLabel;
    @Bind(R.id.cartNameTextView) TextView mNameLabel;
    @Bind(R.id.cuisineTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveCartButton) TextView mSaveCartButton;
    @Bind(R.id.cartNotesEditText) EditText mNotesEditText;

    private Cart mCart;

    public static CartDetailFragment newInstance(Cart cart) {
        CartDetailFragment cartDetailFragment = new CartDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("cart", Parcels.wrap(cart));
        cartDetailFragment.setArguments(args);
        return cartDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCart = Parcels.unwrap(getArguments().getParcelable("cart"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_detail, container, false);
        ButterKnife.bind(this, view);

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);


        mSaveCartButton.setOnClickListener(this);

        Picasso.with(view.getContext())
                .load(mCart.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mCart.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mCart.getCategories()));
        mRatingLabel.setText(Double.toString(mCart.getRating()) + "/5");
        mPhoneLabel.setText(mCart.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mCart.getAddress()));

        return view;
    }



    @Override
    public void onClick(View v) {
        if (v == mSaveCartButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            String userNotes = mNotesEditText.getText().toString();


            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_CARTS)
                    .child(uid);

            DatabaseReference pushRef = restaurantRef.push();
            String pushId = pushRef.getKey();
            mCart.setPushId(pushId);
            mCart.setNotes(userNotes);

            pushRef.setValue(mCart);

            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
        if (v == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mCart.getWebsite()));
            startActivity(webIntent);
        }
        if (v == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mCart.getPhone()));
            startActivity(phoneIntent);
        }
        if (v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mCart.getLatitude()
                            + "," + mCart.getLongitude()
                            + "?q=(" + mCart.getName() + ")"));
            startActivity(mapIntent);
        }
    }


}
