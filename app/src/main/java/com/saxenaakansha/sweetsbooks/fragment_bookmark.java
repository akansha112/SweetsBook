package com.saxenaakansha.sweetsbooks;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class fragment_bookmark extends Fragment implements RecipeAdapter.RecipeAdapterListener {
    private RecipeAdapter adapter;
    private ArrayList<Recipe> bookmarkedRecipes;

    public fragment_bookmark() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewBookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookmarkedRecipes = new ArrayList<>();
        loadBookmarkedRecipes();

        if (!bookmarkedRecipes.isEmpty()) {
            adapter = new RecipeAdapter(requireContext(), bookmarkedRecipes, this);
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    private void loadBookmarkedRecipes() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("BookmarkPref", Context.MODE_PRIVATE);

        // Clear the list before populating to avoid duplicates on subsequent calls
        bookmarkedRecipes.clear();

        // Iterate through the saved bookmarked recipes in SharedPreferences
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            boolean isBookmarked = (boolean) entry.getValue();
            if (isBookmarked) {
                int position = Integer.parseInt(entry.getKey());
                Recipe recipe = RecipeData.getRecipes().get(position);
                bookmarkedRecipes.add(recipe);
            }
        }
    }

    @Override
    public void onUnbookmarkRecipe(int position) {
        // Remove the unbookmarked recipe from the bookmarkedRecipes list
        bookmarkedRecipes.remove(position);

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();

        // Update the SharedPreferences with the updated bookmarked recipes
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("BookmarkPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(position));
        editor.apply();
    }
}