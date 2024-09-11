package com.saxenaakansha.sweetsbooks;




public class Recipe {
    private final String itemName;
    private final String itemDescription;
    private final String itemIngredients;
    private final int itemImage;




    private final String somelines;

    private boolean isBookmarked;



    public Recipe(String somelines, String itemName, String itemDescription, String itemIngredients, int itemImage) {
        this.itemName = itemName;

        this.somelines = somelines;
        this.itemDescription = itemDescription;
        this.itemIngredients = itemIngredients;
        this.itemImage = itemImage;
        this.isBookmarked=false;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemIngredients() {
        return itemIngredients;
    }

    public int getItemImage() {
        return itemImage;
    }

    public String getSomelines() {
        return somelines;
    }
}
