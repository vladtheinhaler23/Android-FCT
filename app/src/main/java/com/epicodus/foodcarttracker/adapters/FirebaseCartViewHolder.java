package com.epicodus.foodcarttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.ui.CartDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Joshua on 7/14/16.
 */
public class FirebaseCartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseCartViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindCart(Cart cart) {
        ImageView cartImageView = (ImageView) mView.findViewById(R.id.cartImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.cartNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);
        TextView notesTextView = (TextView) mView.findViewById(R.id.notesTextView);

        Picasso.with(mContext)
                .load(cart.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(cartImageView);

        nameTextView.setText(cart.getName());
        categoryTextView.setText(cart.getCategories().get(0));
        ratingTextView.setText("Rating: " + cart.getRating() + "/5");
        notesTextView.setText(cart.getNotes());

    }

    @Override
    public void onClick(View view) {
        final ArrayList<Cart> carts = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CARTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    carts.add(snapshot.getValue(Cart.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, CartDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("carts", Parcels.wrap(carts));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
