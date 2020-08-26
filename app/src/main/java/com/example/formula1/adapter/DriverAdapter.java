package com.example.formula1.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formula1.R;
import com.example.formula1.object.Driver;

import java.util.ArrayList;


public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Driver> mDriverList;
    // copy of the mDriverList used for the SearchView, containing all the items
    private ArrayList<Driver> mDriverListFull;

    private Uri uri;
    private OnClick listener;

    public DriverAdapter(Context context, ArrayList<Driver> mDriverList, OnClick listener) {
        this.context = context;
        this.mDriverList = mDriverList;
        mDriverListFull = new ArrayList<>(mDriverList);
        this.listener = listener;
    }

    private Filter recipeFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Driver> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(mDriverListFull);
                } else {
                    // takes our input, transforms it to String, turns it to lower case,
                    // to avoid case sensitivity and removes the blank space before and after our input
                    String filteredPattern = constraint.toString().toLowerCase().trim();
                    // checks if the what recipe name contains the filter pattern
                    for (Driver item : mDriverListFull) {
                        if (item.getNationality().toLowerCase().contains(filteredPattern)) {
                            // if yes, add the item to the list
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDriverList.clear();
                mDriverList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };

    @Override
    public Filter getFilter() {
        return recipeFilter;
    }


    public interface OnClick {
        void onDriverInfoButtonClick(Uri uri, View view);
        void onItemClick(String competitorName, View view);
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_item, parent, false);
        return new DriverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, final int position) {
        final Driver currentItem = mDriverList.get(position);
        final String fullName = currentItem.getSurname() + " " + currentItem.getName();
        holder.mName.setText(fullName);
        holder.mNationality.setText(currentItem.getNationality());
        holder.mBirthDate.setText(currentItem.getBirthDate());

        String competitorNumber = String.valueOf(currentItem.getCompetitorNumber());

        if (competitorNumber.isEmpty() || Integer.parseInt(competitorNumber)== 0 ) {
            holder.mCompetitorNumber.setText("No ranking number available");
        } else {
            holder.mCompetitorNumber.setText(String.valueOf(currentItem.getCompetitorNumber()));
        }

        holder.mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(currentItem.getDriversWiki());
                listener.onDriverInfoButtonClick(uri, v);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(fullName,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDriverList.size();
    }


    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        // season year
        // name, nationality, date of birth and booked start number of each competitor.
        private TextView mSeason;

        private TextView mName;

        private TextView mNationality;

        private TextView mBirthDate;

        private TextView mCompetitorNumber;

        private Button mInfoButton;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.driver_name_text_view);
            mNationality = itemView.findViewById(R.id.nationality_text_view);
            mBirthDate = itemView.findViewById(R.id.birth_date_text_view);
            mCompetitorNumber = itemView.findViewById(R.id.competitor_number_text_view);
            mInfoButton = itemView.findViewById(R.id.info_button);
        }
    }
}
