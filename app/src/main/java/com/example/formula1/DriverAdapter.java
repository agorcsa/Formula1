package com.example.formula1;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private ArrayList<Driver> mDriverList;
    private Uri uri;
    private DriverInfoButtonClick driverListener;

    public DriverAdapter(ArrayList<Driver> mDriverList, DriverInfoButtonClick driverListener) {
        this.mDriverList = mDriverList;
        this.driverListener = driverListener;
    }

    public interface DriverInfoButtonClick {
        void onDriverInfoButtonClick(Uri uri, View view);
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_item, parent, false);
        return new DriverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        final Driver currentItem = mDriverList.get(position);
        String fullName = currentItem.getSurname() + " " + currentItem.getName();
        holder.mName.setText(fullName);
        holder.mNationality.setText(currentItem.getNationality());
        holder.mBirthDate.setText(currentItem.getBirthDate());

        String competitorNumber = String.valueOf(currentItem.getCompetitorNumber());
        if (competitorNumber.isEmpty()) {
            holder.mCompetitorNumber.setText("No start number available");
        } else {
            holder.mCompetitorNumber.setText(String.valueOf(currentItem.getCompetitorNumber()));
        }

        holder.mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(currentItem.getDriversWiki());
                driverListener.onDriverInfoButtonClick(uri, v);
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
