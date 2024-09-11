package com.saxenaakansha.sweetsbooks;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class fragment_home extends Fragment implements RecipeAdapter.RecipeAdapterListener {
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    ArrayList<Recipe> myFoodList;
    Button bookmark;
    SearchView txt_search;

    public fragment_home() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txt_search = view.findViewById(R.id.searchtxt);

        txt_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myFoodList = new ArrayList<>();
        myFoodList = RecipeData.getRecipes(); // Retrieve the recipe data from RecipeData class

        adapter = new RecipeAdapter(requireContext(), myFoodList, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void filterList(String text) {
        ArrayList<Recipe> filterList = new ArrayList<>();

        for (Recipe item : myFoodList) {
            if (item.getItemName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }

        try {
            adapter.filteredList(filterList);
        } catch (Exception e) {
            Log.e("FilteredListError", "Error filtering list: " + e.getMessage());
        }
    }


    @Override
    public void onUnbookmarkRecipe(int position) {
        // Notify the adapter that a recipe has been bookmarked
        adapter.notifyItemChanged(position);
    }

}
