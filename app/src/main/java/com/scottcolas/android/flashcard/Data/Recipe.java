package com.scottcolas.android.flashcard.Data;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class Recipe implements Parcelable {

    //@SerializedName("id")
    private int mId;

   // @SerializedName("name")
    private String mName;

   /// @SerializedName("ingredients")
    private List<String> mIngredients;

    //@SerializedName("steps")
    private List<String> mSteps;

    //@SerializedName("servings")
    private int mServings;

    //@SerializedName("image")
    private String mImage;

    public Recipe(String name, int id, List<String> ingredients, List<String> steps, int servings, String image) {
        mName = name;
        mId = id;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }



    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
