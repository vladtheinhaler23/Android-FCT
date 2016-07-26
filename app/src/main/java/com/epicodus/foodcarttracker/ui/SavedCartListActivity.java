package com.epicodus.foodcarttracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.epicodus.foodcarttracker.Constants;
import com.epicodus.foodcarttracker.R;
import com.epicodus.foodcarttracker.adapters.FirebaseCartListAdapter;
import com.epicodus.foodcarttracker.adapters.FirebaseCartViewHolder;
import com.epicodus.foodcarttracker.models.Cart;
import com.epicodus.foodcarttracker.util.OnStartDragListener;
import com.epicodus.foodcarttracker.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedCartListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cart_list);
    }
}
