package com.example.sirisha.bakeguide;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String s = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    @BindView(R.id.main_rv)
    RecyclerView recyclerView;
    TextView tv;
    ArrayList<Model> pojo = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv=(TextView)findViewById(R.id.name_receipe);
        new JsonAsync().execute();

    }

    public class JsonAsync extends AsyncTask<String, Void, String> {

        String response;

        @Override
        protected String doInBackground(String... strings) {
            Utilities connection = new Utilities();
            URL url = connection.buildUrl(s);
            Log.i("url", url.toString());

            try {
                response = connection.getResponseFromHttpUrl(url);
                Log.i("response", response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String json) {
            JSONArray jsonArray = null;
            super.onPostExecute(json);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.optInt("id");
                    String name = jsonObject.optString("name");
                    String image = jsonObject.optString("image");
                    int servings = jsonObject.optInt("servings");
                    String ingredients = jsonObject.optJSONArray("ingredients").toString();
                    String steps = jsonObject.optJSONArray("steps").toString();
                    Model model = new Model(id, name, ingredients, steps, servings, image);
                    pojo.add(model);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            RecipeAdapter recipeAdapter = new RecipeAdapter(MainActivity.this, pojo);
            Log.i("jsonAdapter", pojo.toString());
            recyclerView.setAdapter(recipeAdapter);
        }

    }
}





