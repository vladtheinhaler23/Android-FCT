package com.epicodus.foodcarttracker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.ui.CartDetailActivity;
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

    public CartListAdapter(Context context, ArrayList<Cart> carts) {
        mContext = context;
        mCarts = carts;
    }

    @Override
    public CartListAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
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
    public class CartViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        @Bind(R.id.cartImageView) ImageView mCartImageView;
        @Bind(R.id.cartNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public CartViewHolder(View itemView)  {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }


        public void bindCart(Cart cart) {

            Picasso.with(mContext)
                    .load(cart.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mCartImageView);

            mNameTextView.setText(cart.getName());
            mCategoryTextView.setText(cart.getCategories().get(0));
            mRatingTextView.setText("Rating: " + cart.getRating() + "/5");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, CartDetailActivity.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("carts", Parcels.wrap(mCarts));
            mContext.startActivity(intent);
        }
    }

}
