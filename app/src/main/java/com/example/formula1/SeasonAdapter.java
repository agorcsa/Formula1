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

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private ArrayList<Season> mSeasonsList;
    private Uri uri;
    private ButtonsClick listener;


    public SeasonAdapter(ArrayList<Season> mSeasonsList, ButtonsClick listener) {
        this.mSeasonsList = mSeasonsList;
        this.listener = listener;
    }

    public interface ButtonsClick {
        void onInfoButtonClick(Uri uri, View view);
        void onYearButtonClick(String seasonYear, View view);
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item, parent, false);
        return new SeasonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        final Season currentItem = mSeasonsList.get(position);
        // year
        holder.mSeasonYearTextView.setText(String.valueOf(currentItem.getYear()));
        holder.mSeasonYearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String year = String.valueOf(currentItem.getYear());
               listener.onYearButtonClick(year, v);
            }
        });

        // wiki uri
        holder.mWikiInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(currentItem.getWikiUrl());
                listener.onInfoButtonClick(uri, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSeasonsList.size();
    }

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {

        private TextView mSeasonYearTextView;
        private Button mWikiInfoButton;

        public SeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            mSeasonYearTextView = itemView.findViewById(R.id.season_year_text_view);
            mWikiInfoButton = itemView.findViewById(R.id.wiki_info_button);
        }
    }
}
