package com.example.inventorymanagement;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by corei3 on 09-07-2017.
 */

public class CustomFilterUser extends Filter {

    MyAdapterUser adapter;
    ArrayList<UserModel> filterList;

    public CustomFilterUser(ArrayList<UserModel> filterList, MyAdapterUser adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        // CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            // CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            // STORE OUR FILTERED PLAYERS
            ArrayList<UserModel> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                // CHECK
                if (filterList.get(i).getFirstName().toUpperCase().contains(constraint)||
                        filterList.get(i).getLastName().toUpperCase().contains(constraint)||
                        filterList.get(i).getUserName().toUpperCase().contains(constraint)
                ) {
                    // ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models = (ArrayList<UserModel>) results.values;
        // REFRESH
        adapter.notifyDataSetChanged();
    }
}
