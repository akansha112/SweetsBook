package com.saxenaakansha.sweetsbooks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.FoodViewHolder> {
    private final Context context;
    private List<Recipe> myFoodList;
    private final SharedPreferences sharedPreferences;
    private final RecipeAdapterListener adapterListener;
    private View lastClickedView; // Reference to the previously clicked card view

    // Interface to communicate the unbookmark action to the fragment_home
    public interface RecipeAdapterListener {
        void onUnbookmarkRecipe(int position);
    }

    public RecipeAdapter(Context context, List<Recipe> myFoodList, RecipeAdapterListener adapterListener) {
        this.context = context;
        this.myFoodList = myFoodList;
        this.sharedPreferences = context.getSharedPreferences("BookmarkPref", Context.MODE_PRIVATE);
        this.adapterListener = adapterListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_homepage, parent, false);
        return new FoodViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Recipe recipe = myFoodList.get(position);

        holder.imageView.setImageResource(recipe.getItemImage());
        holder.mtitle.setText(recipe.getItemName());
        holder.msomelines.setText(recipe.getSomelines());
        boolean isBookmarkFragment = (adapterListener instanceof fragment_bookmark);

        // Set bookmark button state based on saved bookmark status
        boolean isBookmarked = sharedPreferences.getBoolean(String.valueOf(position), false);
        setBookmarkButtonState(holder.bookmark, isBookmarked, isBookmarkFragment);


        holder.bookmark.setOnClickListener(v -> {
            boolean currentBookmarkStatus = sharedPreferences.getBoolean(String.valueOf(position), false);
            boolean newBookmarkStatus = !currentBookmarkStatus;

            // Notify the fragment_home to handle the unbookmark action
            if (newBookmarkStatus) {

                adapterListener.onUnbookmarkRecipe(position);
            }

            // Update the bookmark status in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(String.valueOf(position), newBookmarkStatus);
            editor.apply();

            // Update the bookmark button state
            // Update the bookmark button state
            setBookmarkButtonState(holder.bookmark, newBookmarkStatus, isBookmarkFragment);
            LayoutInflater inflater = LayoutInflater.from(context);
            View toastView = inflater.inflate(R.layout.custom_toast, null);

            TextView toastMessage = toastView.findViewById(R.id.toast_message);
            toastMessage.setText(newBookmarkStatus ? "Recipe added to favourites!" : "Recipe removed from favourites!");

            Toast customToast = new Toast(context);
            customToast.setDuration(Toast.LENGTH_SHORT);
            customToast.setView(toastView);
            customToast.show();
            // Show toast message based on bookmark status


        });

        holder.msharebtn.setOnClickListener(v -> {
            // Create an implicit intent to share the recipe's data
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            // Create a string to hold the recipe's data
            String appLink = "https://play.google.com/store/apps/details?id=com.saxenaakansha.sweetsbooks";

            String recipeData = appLink + "\n" + recipe.getItemName() + "\n" +
                    "Ingredients: " + recipe.getItemIngredients() + "\n" +
                    "Preparation: " + recipe.getItemDescription() + "\n";

            // Set the recipe's data as the message of the share intent
            shareIntent.putExtra(Intent.EXTRA_TEXT, recipeData);

            // Launch the share intent chooser
            context.startActivity(Intent.createChooser(shareIntent, "Share Recipe"));
        });

        holder.mCardView.setOnClickListener(v -> {
            if (lastClickedView != null) {
                lastClickedView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            }

            // Change the background color of the clicked card view
            holder.mCardView.setBackgroundColor(context.getResources().getColor(R.color.pink));
            lastClickedView = holder.mCardView;

            Intent intent = new Intent(context, RecipeDescriptionActivity.class);

            intent.putExtra("Image", recipe.getItemImage());
            intent.putExtra("RecipeName", recipe.getItemName());
            intent.putExtra("RecipeIngredients", recipe.getItemIngredients());
            intent.putExtra("RecipeDescription", recipe.getItemDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }
    public boolean isRecipeBookmarked(int position) {
        boolean isBookmarked = sharedPreferences.getBoolean(String.valueOf(position), false);
        return isBookmarked;
    }

    private void setBookmarkButtonState(Button bookmarkButton, boolean isBookmarked, boolean isBookmarkFragment) {
        if (isBookmarkFragment) {
            bookmarkButton.setEnabled(false);
        } else {
            if (isBookmarked) {
                bookmarkButton.setBackgroundResource(R.drawable.bookmark_full);
                bookmarkButton.setEnabled(true);
            } else {
                bookmarkButton.setBackgroundResource(R.drawable.ic_bookmarkborder);
                bookmarkButton.setEnabled(true);
            }
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    public void filteredList(ArrayList<Recipe> filterlist) {
        myFoodList=filterlist;
        notifyDataSetChanged();

    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView msomelines;
        TextView mtitle;
        CardView mCardView;
        Button bookmark;
        Button msharebtn;

        public FoodViewHolder(View itemView) {
            super(itemView);
            msomelines = itemView.findViewById(R.id.somelinestext);
            imageView = itemView.findViewById(R.id.ivImage);
            mtitle = itemView.findViewById(R.id.tvTitle);
            mCardView = itemView.findViewById(R.id.myCardView);
            msharebtn = itemView.findViewById(R.id.sharebtnRecipe);
            bookmark = itemView.findViewById(R.id.bookmark);
        }

    }
}

