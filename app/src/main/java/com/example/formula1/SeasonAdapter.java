package com.example.formula1;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private ArrayList<Season> mSeasonsList;
    private Uri uri;
    private InfoButtonClick listener;

    public SeasonAdapter(ArrayList<Season> mSeasonsList, InfoButtonClick listener) {
        this.mSeasonsList = mSeasonsList;
        this.listener = listener;
    }

    public interface InfoButtonClick {
        void onInfoButtonClick(Uri uri, View view);
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
        String seasonYear = String.valueOf(currentItem);
        holder.mSeasonYearTextView.setText(seasonYear);

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
        private ImageButton mWikiInfoButton;

        public SeasonViewHolder(@NonNull View itemView) {
            super(itemView);

            mSeasonYearTextView = itemView.findViewById(R.id.season_year_text_view);
            mWikiInfoButton = itemView.findViewById(R.id.wiki_info_image_button);
        }
    }
}
