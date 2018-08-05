package com.example.sirisha.bakeguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    public static ArrayList<IngredientsModel> widgetPojos = new ArrayList<>();
    Model model;
    ArrayList<IngredientsModel> inPojos = new ArrayList<>();
    ArrayList<StepsModel> stPojos = new ArrayList<>();
    private boolean mTwoPane;

    public static ArrayList<IngredientsModel> getIngredients() {
        return widgetPojos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String ingredients;
        JSONArray jsonArray = null;
        String steps;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), "click on fab to add widget", Toast.LENGTH_SHORT).show();
        ingredients = intent.getStringExtra("ingredients");
        steps = intent.getStringExtra("steps");
        try {
            jsonArray = new JSONArray(ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double quantity = jsonObject.optDouble("quantity");
                String measure = jsonObject.optString("measure");
                String ingredientName = jsonObject.optString("ingredient");
                IngredientsModel ingredientsModel = new IngredientsModel(quantity, measure, ingredientName);
                inPojos.add(ingredientsModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jsonArray = new JSONArray(steps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.optInt("id");
                String shortDescription = jsonObject.optString("shortDescription");
                String description = jsonObject.optString("description");
                String videourl = jsonObject.optString("videoURL");
                String thumbnailurl = jsonObject.optString("thumbnailURL");
                StepsModel stepsModel = new StepsModel(id, shortDescription, description, videourl, thumbnailurl);
                stPojos.add(stepsModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widgetPojos = new ArrayList<IngredientsModel>();
                Toast.makeText(getApplicationContext(), "recepie added to widwget", Toast.LENGTH_SHORT).show();
                widgetPojos = inPojos;
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, inPojos, stPojos, mTwoPane));
        recyclerView.setFocusable(false);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
        private final ArrayList<IngredientsModel> ingredientPojoList;
        private final ArrayList<StepsModel> stepsPojoList;
        private final ItemListActivity mParentActivity;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent, ArrayList<IngredientsModel> ingredientPojos, ArrayList<StepsModel> stepsPojos
                , boolean twoPane) {
            this.ingredientPojoList = ingredientPojos;
            this.stepsPojoList = stepsPojos;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (position == 0) {
                holder.mIdView.setVisibility(View.GONE);
                holder.mContentView.setText("Receipe Ingredients");
                holder.mContentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelableArrayList("ingredients", ingredientPojoList);
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra("ingredients", ingredientPojoList);
                            context.startActivity(intent);
                        }
                    }
                });

            } else {
                holder.mIdView.setText("" + stepsPojoList.get(position - 1).getId());
                holder.mContentView.setText(stepsPojoList.get(position - 1).getShortdescription());
                //holder.itemView.setOnClickListener(mOnClickListener);
                holder.mContentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelableArrayList("widget", ingredientPojoList);
                            arguments.putString("description", stepsPojoList.get(position - 1).getDescription());
                            arguments.putString("video", stepsPojoList.get(position - 1).getVideourl());
                            arguments.putString("thumbnail", stepsPojoList.get(position - 1).getThumbnailurl());
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            Bundle bundle = new Bundle();
                            intent.putExtra("video", stepsPojoList.get(position - 1).getVideourl());
                            intent.putExtra("widget", ingredientPojoList);
                            intent.putExtra("description", stepsPojoList.get(position - 1).getDescription());
                            intent.putExtra("thumbnail", stepsPojoList.get(position - 1).getThumbnailurl());
                            context.startActivity(intent);
                        }
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return stepsPojoList.size() + 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
