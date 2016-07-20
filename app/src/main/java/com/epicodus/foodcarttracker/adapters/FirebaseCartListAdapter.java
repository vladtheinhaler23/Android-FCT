package com.epicodus.foodcarttracker.adapters;

import android.content.Context;

import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.util.ItemTouchHelperAdapter;
import com.epicodus.foodcarttracker.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Joshua on 7/18/16.
 */
//public class FirebaseCartListAdapter
//        extends FirebaseRecyclerAdapter<Cart, FirebaseCartViewHolder>
//        implements ItemTouchHelperAdapter {
//
//    private DatabaseReference mRef;
//    private OnStartDragListener mOnStartDragListener;
//    private Context mContext;
//
//    public FirebaseCartListAdapter(Class<Cart> modelClass, int modelLayout,
//                                   Class<FirebaseCartViewHolder> viewHolderClass,
//                                   Query ref, OnStartDragListener onStartDragListener, Context context) {
//        super(modelClass, modelLayout, viewHolderClass, ref);
//        mRef = ref.getRef();
//        mOnStartDragListener = onStartDragListener;
//        mContext = context;
//    }
//
//}
