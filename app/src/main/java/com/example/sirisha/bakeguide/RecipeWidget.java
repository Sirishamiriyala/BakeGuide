package com.example.sirisha.bakeguide;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {
    public static String text = "";
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        ArrayList<IngredientsModel> ingredientsModels = new ArrayList<IngredientsModel>();
        ingredientsModels = ItemListActivity.getIngredients();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        if (!ingredientsModels.isEmpty()) {
            text="";
            Log.i("ing", "" + ingredientsModels);
            String name, quantity, measure;
            for (int i = 0; i < ingredientsModels.size(); i++) {
                name = ingredientsModels.get(i).getIngredient();
                quantity = "" + ingredientsModels.get(i).getQuantity();
                measure = ingredientsModels.get(i).getMeasure();
                text = text + name + "\t" + quantity + "\t" + measure + "\n";
            }
            Log.i("text", text);
            remoteViews.setTextViewText(R.id.appwidget_text, text);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        } else {
            Log.i("ing", "null");
            remoteViews.setTextViewText(R.id.appwidget_text, "Select an item to display ingredients");
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        // Instruct the widget manager to update the widget
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

