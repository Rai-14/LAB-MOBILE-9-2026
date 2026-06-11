package com.example.finallabh071241050.model;

import com.google.gson.annotations.SerializedName;

public class Meal {
    @SerializedName("idMeal")
    public String idMeal;

    @SerializedName("strMeal")
    public String strMeal;

    @SerializedName("strMealThumb")
    public String strMealThumb;

    @SerializedName("strInstructions")
    public String strInstructions;

    @SerializedName("strCategory")
    public String strCategory;

    // Field Bahan
    @SerializedName("strIngredient1") public String strIngredient1;
    @SerializedName("strIngredient2") public String strIngredient2;
    @SerializedName("strIngredient3") public String strIngredient3;
    @SerializedName("strIngredient4") public String strIngredient4;
    @SerializedName("strIngredient5") public String strIngredient5;
    @SerializedName("strIngredient6") public String strIngredient6;
    @SerializedName("strIngredient7") public String strIngredient7;
    @SerializedName("strIngredient8") public String strIngredient8;
    @SerializedName("strIngredient9") public String strIngredient9;
    @SerializedName("strIngredient10") public String strIngredient10;
    @SerializedName("strIngredient11") public String strIngredient11;
    @SerializedName("strIngredient12") public String strIngredient12;



    // Field Takaran
    @SerializedName("strMeasure1") public String strMeasure1;
    @SerializedName("strMeasure2") public String strMeasure2;
    @SerializedName("strMeasure3") public String strMeasure3;
    @SerializedName("strMeasure4") public String strMeasure4;
    @SerializedName("strMeasure5") public String strMeasure5;
    @SerializedName("strMeasure6") public String strMeasure6;
    @SerializedName("strMeasure7") public String strMeasure7;
    @SerializedName("strMeasure8") public String strMeasure8;
    @SerializedName("strMeasure9") public String strMeasure9;
    @SerializedName("strMeasure10") public String strMeasure10;
    @SerializedName("strMeasure11") public String strMeasure11;
    @SerializedName("strMeasure12") public String strMeasure12;


    public String getIngredients() {
        StringBuilder sb = new StringBuilder();
        if (strIngredient1 != null && !strIngredient1.isEmpty()) sb.append("- ").append(strIngredient1).append(": ").append(strMeasure1).append("\n");
        if (strIngredient2 != null && !strIngredient2.isEmpty()) sb.append("- ").append(strIngredient2).append(": ").append(strMeasure2).append("\n");
        if (strIngredient3 != null && !strIngredient3.isEmpty()) sb.append("- ").append(strIngredient3).append(": ").append(strMeasure3).append("\n");
        if (strIngredient4 != null && !strIngredient4.isEmpty()) sb.append("- ").append(strIngredient4).append(": ").append(strMeasure4).append("\n");
        if (strIngredient5 != null && !strIngredient5.isEmpty()) sb.append("- ").append(strIngredient5).append(": ").append(strMeasure5).append("\n");
        if (strIngredient6 != null && !strIngredient6.isEmpty()) sb.append("- ").append(strIngredient6).append(": ").append(strMeasure6).append("\n");
        if (strIngredient7 != null && !strIngredient7.isEmpty()) sb.append("- ").append(strIngredient7).append(": ").append(strMeasure7).append("\n");
        if (strIngredient8 != null && !strIngredient8.isEmpty()) sb.append("- ").append(strIngredient8).append(": ").append(strMeasure8).append("\n");
        if (strIngredient9 != null && !strIngredient9.isEmpty()) sb.append("- ").append(strIngredient9).append(": ").append(strMeasure9).append("\n");
        if (strIngredient10 != null && !strIngredient10.isEmpty()) sb.append("- ").append(strIngredient10).append(": ").append(strMeasure10).append("\n");
        if (strIngredient11 != null && !strIngredient11.isEmpty()) sb.append("- ").append(strIngredient11).append(": ").append(strMeasure11).append("\n");
        if (strIngredient12 != null && !strIngredient12.isEmpty()) sb.append("- ").append(strIngredient12).append(": ").append(strMeasure12).append("\n");
        return sb.toString();
    }
}

