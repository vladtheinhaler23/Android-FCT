package com.epicodus.foodcarttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.ui.CartDetailActivity;
import com.epicodus.foodcarttracker.ui.CartDetailFragment;
import com.epicodus.foodcarttracker.util.ItemTouchHelperAdapter;
import com.epicodus.foodcarttracker.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Joshua on 7/18/16.
 */
public class FirebaseCartListAdapter
        extends FirebaseRecyclerAdapter<Cart, FirebaseCartViewHolder>
        implements ItemTouchHelperAdapter {

    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Cart> mCarts = new ArrayList<>();
    private int mOrientation;

    public FirebaseCartListAdapter(Class<Cart> modelClass, int modelLayout,
                                   Class<FirebaseCartViewHolder> viewHolderClass,
                                   Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mCarts.add(dataSnapshot.getValue(Cart.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseCartViewHolder viewHolder, Cart model, int position) {
        viewHolder.bindCart(model);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }
        viewHolder.mCartImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = viewHolder.getAdapterPosition();
                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else {
                    Intent intent = new Intent(mContext, CartDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                    intent.putExtra(Constants.EXTRA_KEY_CARTS, Parcels.wrap(mCarts));
                    intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_SAVED);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mCarts, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mCarts.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Cart cart : mCarts) {
            int index = mCarts.indexOf(cart);
            DatabaseReference ref = getRef(index);
            cart.setIndex(Integer.toString(index));
            ref.setValue(cart);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mRef.removeEventListener(mChildEventListener);
    }

    private void createDetailFragment(int position) {
        CartDetailFragment detailFragment = CartDetailFragment.newInstance(mCarts, position, Constants.SOURCE_SAVED);
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.cartDetailContainer, detailFragment);
        ft.commit();
    }

}
