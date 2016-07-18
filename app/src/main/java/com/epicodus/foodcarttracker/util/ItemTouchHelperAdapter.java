package com.epicodus.foodcarttracker.util;

/**
 * Created by Joshua on 7/18/16.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
