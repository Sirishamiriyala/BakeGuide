package com.example.sirisha.bakeguide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sirisha on 14-06-2018.
 */

public class IngredientsModel implements Parcelable {

    public static final Creator<IngredientsModel> CREATOR = new Creator<IngredientsModel>() {
        @Override
        public IngredientsModel createFromParcel(Parcel in) {
            return new IngredientsModel(in);
        }

        @Override
        public IngredientsModel[] newArray(int size) {
            return new IngredientsModel[size];
        }
    };
    double quantity;
    String measure, ingredient;


    public IngredientsModel(double fquantity, String fmeasure, String fingredient) {
        this.ingredient = fingredient;
        this.measure = fmeasure;
        this.quantity = fquantity;

    }

    protected IngredientsModel(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }}
