package com.example.inventorymanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by corei3 on 23-07-2017.
 */

public class AutoCustAdapter extends ArrayAdapter<ModelCustomer> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<ModelCustomer> modelcust, tempModels, suggestions;
    String custkey = "", amt = "", custname = "";

    public AutoCustAdapter(Context context, int resource, int textViewResourceId, ArrayList<ModelCustomer> modelcust) {

        super(context, resource, textViewResourceId, modelcust);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.modelcust = modelcust;
        // tempItems = new ArrayList<Model>(items);
        this.tempModels = (ArrayList<ModelCustomer>) modelcust.clone();
        this.suggestions = new ArrayList<ModelCustomer>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autotext_list, parent, false);
        }
        final ModelCustomer model = modelcust.get(position);
        if (model != null) {

            TextView item = (TextView) view.findViewById(R.id.item_name);

            if (item != null)

                item.setText(model.getEiname() + "-" + "( " + model.getEmoney() + " )");
        }
        return view;
    }

    @Override
    public Filter getFilter() {

        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((ModelCustomer) resultValue).getEiname();
            custkey = ((ModelCustomer) resultValue).getKey();
            amt = ((ModelCustomer) resultValue).getEmoney();
            custname = ((ModelCustomer) resultValue).getEiname();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ModelCustomer model : tempModels) {
                    if (model.getEiname().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(model);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<ModelCustomer> filterList = (ArrayList<ModelCustomer>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ModelCustomer model : filterList) {
                    add(model);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public String[] detcust() {
        String[] str = { custkey, amt, custname };
        return str;
    }
}
