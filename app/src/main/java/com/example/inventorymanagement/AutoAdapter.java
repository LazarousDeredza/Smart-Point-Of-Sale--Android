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

public class AutoAdapter extends ArrayAdapter<StockModel> {

    Context context;
    int resource, textViewResourceId;
    ArrayList<StockModel> items, tempItems, suggestions;
    String item = "",  unit = "", expdate = "", batch = "", key = "", oqty, ecode = "",amount="";

    public AutoAdapter(Context context, int resource, int textViewResourceId, ArrayList<StockModel> items) {

        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        this.tempItems = (ArrayList<StockModel>) items.clone();
        this.suggestions = new ArrayList<StockModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autotext_list, parent, false);
        }
        final StockModel model = items.get(position);
        if (model != null) {

            TextView item = (TextView) view.findViewById(R.id.item_name);

            if (item != null)

                item.setText(model.getProductName() + " (" + model.getBarcode() + ") " +model.getUnit());
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
            String str = ((StockModel) resultValue).getProductName() + " (" + ((StockModel) resultValue).getBarcode() + ") "+((StockModel) resultValue).getUnit();
            item = ((StockModel) resultValue).getProductName();
            //mrp = ((StockModel) resultValue).getEmrp();
            amount = ((StockModel) resultValue).getSellingPrice();
            expdate = ((StockModel) resultValue).getExpiryDate();
            batch = ((StockModel) resultValue).getBatchNumber();
            key = ((StockModel) resultValue).getId();
            oqty = ((StockModel) resultValue).getQuantity();
            unit = ((StockModel) resultValue).getUnit();
            ecode = ((StockModel) resultValue).getBarcode();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StockModel model : tempItems) {
                    if (model.getProductName().toLowerCase().contains(constraint.toString().toLowerCase())||
                            model.getBarcode().toLowerCase().contains(constraint.toString().toLowerCase()) ) {

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
            List<StockModel> filterList = (ArrayList<StockModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (StockModel model : filterList) {
                    add(model);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public String[] det() {
        String[] str = { item,  batch, expdate, key, oqty, unit, ecode ,amount};
        return str;
    }
}
