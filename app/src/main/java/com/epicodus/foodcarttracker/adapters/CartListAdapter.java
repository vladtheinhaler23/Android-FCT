package com.epicodus.foodcarttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.ui.CartDetailActivity;
import com.epicodus.foodcarttracker.ui.CartDetailFragment;
import com.epicodus.foodcarttracker.util.OnCartSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Joshua on 7/12/16.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Cart> mCarts = new ArrayList<>();
    private Context mContext;
    private OnCartSelectedListener mOnCartSelectedListener;

    public CartListAdapter(Context context, ArrayList<Cart> carts, OnCartSelectedListener cartSelectedListener) {
        mContext = context;
        mCarts = carts;
        mOnCartSelectedListener = cartSelectedListener;
    }

    @Override
    public CartListAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view, mCarts, mOnCartSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartListAdapter.CartViewHolder holder, int position) {
        holder.bindCart(mCarts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCarts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.cartImageView) ImageView mCartImageView;
        @Bind(R.id.cartNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;
        private int mOrientation;
        private ArrayList<Cart> mCarts = new ArrayList<>();
        private OnCartSelectedListener mCartSelectedListener;


        public CartViewHolder(View itemView, ArrayList<Cart> carts, OnCartSelectedListener cartSelectedListener)  {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mOrientation = itemView.getResources().getConfiguration().orientation;
            mContext = itemView.getContext();
            mCarts = carts;
            mCartSelectedListener = cartSelectedListener;

            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);
        }

        private void createDetailFragment(int position) {

            CartDetailFragment detailFragment = CartDetailFragment.newInstance(mCarts, position, Constants.SOURCE_FIND);
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.cartDetailContainer, detailFragment);
            ft.commit();
        }


        public void bindCart(Cart cart) {

            Picasso.with(mContext)
                    .load(cart.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mCartImageView);

            mNameTextView.setText(cart.getName());
            mCategoryTextView.setText(cart.getCategories().get(0));
            mRatingTextView.setText(R.string.rating_label_string + "" + cart.getRating() + R.string.rating_scale_string);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            mCartSelectedListener.onCartSelected(itemPosition, mCarts, Constants.SOURCE_FIND);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, CartDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_CARTS, Parcels.wrap(mCarts));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }
        }
    }

}
