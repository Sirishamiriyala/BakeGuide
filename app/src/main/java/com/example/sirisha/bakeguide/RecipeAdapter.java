package com.example.sirisha.bakeguide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sirisha on 14-06-2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder>{

    ArrayList<Model> recipe = new ArrayList<Model>();
    int id;
    Context context;

    public RecipeAdapter(Context mainActivity, ArrayList<Model> pojo) {
        this.context=mainActivity;
        this.recipe=pojo;
    }


    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        id = R.layout.recipe_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(id, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecipeAdapter.MyViewHolder holder, int position) {

        holder.tv.setText(recipe.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        CardView cv;
        public MyViewHolder(View itemView) {
            super(itemView);
            cv=itemView.findViewById(R.id.cardView);
            tv = itemView.findViewById(R.id.name_receipe);
            cv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemListActivity.class);
                    Model jf = recipe.get(getAdapterPosition());
                    intent.putExtra("ingredients", jf.getIngredients());
                    Log.i("ingredients-data",jf.getIngredients());
                    intent.putExtra("steps", jf.getSteps());
                    Log.i("steps",jf.getSteps());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
}

