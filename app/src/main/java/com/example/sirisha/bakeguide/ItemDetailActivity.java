package com.example.sirisha.bakeguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {


    ArrayList<IngredientsModel> iPojos;
    String videoURL, thumbnailURL, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey("ingredients")) {
                iPojos = bundle.getParcelableArrayList("ingredients");
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("ingredients", iPojos);
            }
            if (bundle.containsKey("video")) {
                description = bundle.getString("description");
                videoURL = bundle.getString("video");
                thumbnailURL = bundle.getString("thumbnail");
                Bundle bundle1 = new Bundle();
                bundle1.putString("description", description);
                bundle1.putString("video", videoURL);
                bundle1.putString("thumbnail", thumbnailURL);
            }

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}