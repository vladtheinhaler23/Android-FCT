package com.epicodus.foodcarttracker.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartDetailFragment extends Fragment implements View.OnClickListener {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    private ArrayList<Cart> mCarts;
    private int mPosition;
    private String mSource;


    @Bind(R.id.cartImageView)
    ImageView mImageLabel;
    @Bind(R.id.cartNameTextView)
    TextView mNameLabel;
    @Bind(R.id.cuisineTextView)
    TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView)
    TextView mRatingLabel;
    @Bind(R.id.websiteTextView)
    TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView)
    TextView mPhoneLabel;
    @Bind(R.id.addressTextView)
    TextView mAddressLabel;
    @Bind(R.id.saveCartButton)
    TextView mSaveCartButton;
    @Bind(R.id.notesTextView)TextView mNotesTextView;
    @Bind(R.id.notesTextViewLabel)TextView mNotesTextViewLabel;

    private Cart mCart;
    private Context mContext;



    public static CartDetailFragment newInstance(ArrayList<Cart> carts, Integer position, String source) {
        CartDetailFragment cartDetailFragment = new CartDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_CARTS, Parcels.wrap(carts));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);

        cartDetailFragment.setArguments(args);
        return cartDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCarts = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_CARTS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mCart = mCarts.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_detail, container, false);
        ButterKnife.bind(this, view);

        mContext = getActivity();

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
        mRatingLabel.setText(Double.toString(mCart.getRating()) + mContext.getResources().getString(R.string.rating_scale_string));
        mPhoneLabel.setText(mCart.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mCart.getAddress()));
        mNotesTextView.setText(mCart.getNotes());

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            mSaveCartButton.setVisibility(View.GONE);
        } else {
            // This line of code should already exist. Make sure it now resides in this conditional:
            mSaveCartButton.setOnClickListener(this);
            mNotesTextView.setVisibility(View.GONE);
            mNotesTextViewLabel.setVisibility(View.GONE);
        }

        return view;
    }


    @Override
    public void onClick(View v) {
        if (v == mSaveCartButton) {



            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            View view = getActivity().findViewById(R.layout.notes_dialog);
//            alertDialogBuilder.setView(inflater.inflate(R.layout.notes_dialog, null));
            final EditText notesEditText = new EditText(getActivity());
            alertDialogBuilder.setView(notesEditText);


            // set title
            alertDialogBuilder.setTitle(R.string.dialog_title_string);

            // set dialog message
            alertDialogBuilder
                    .setMessage(R.string.dialog_message_string)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dialog_confirm_string,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            String userNotes = notesEditText.getText().toString();
                            DatabaseReference restaurantRef = FirebaseDatabase
                                    .getInstance()
                                    .getReference(Constants.FIREBASE_CHILD_CARTS)
                                    .child(uid);

                            DatabaseReference pushRef = restaurantRef.push();
                            String pushId = pushRef.getKey();
                            mCart.setPushId(pushId);
                            mCart.setNotes(userNotes);

                            pushRef.setValue(mCart);
                        }
                    })
                    .setNegativeButton(R.string.dialog_reject_string,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();






//            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
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
