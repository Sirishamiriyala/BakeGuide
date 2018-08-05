package com.example.sirisha.bakeguide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sirisha on 14-06-2018.
 */

public class Model implements Parcelable{
    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
    int id;
    String name, ingredients, steps;
    int servings;
    String image;

    protected Model(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.readString();
        steps = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public Model(int id, String name, String ingredients, String steps, int servings, String image) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(ingredients);
        dest.writeString(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
