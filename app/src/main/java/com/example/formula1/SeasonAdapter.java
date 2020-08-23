package com.example.formula1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private ArrayList<SeasonItem> mSeasonsList;
    private OnItemClickListener mListener;

    public SeasonAdapter(ArrayList<SeasonItem> mSeasonsList) {
        this.mSeasonsList = mSeasonsList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.season_item, parent, false);
        SeasonViewHolder seasonViewHolder = new SeasonViewHolder(v, mListener);
        return seasonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonViewHolder holder, int position) {
        SeasonItem currentItem = mSeasonsList.get(position);
        holder.mSeasonYearTextView.setText(currentItem.getSeasonYear());
       // holder.mInfoButton.setImageResource(currentItem.getInfoButton());
    }

    @Override
    public int getItemCount() {
        return mSeasonsList.size();
    }

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {

        private TextView mSeasonYearTextView;
       // private ImageButton mInfoButton;


        public SeasonViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            mSeasonYearTextView = itemView.findViewById(R.id.season_year_text_view);
           // mInfoButton = itemView.findViewById(R.id.info_image_button);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (listener != null) {
                       int position = getAdapterPosition();
                       if (position != RecyclerView.NO_POSITION) {
                           listener.onItemClick(position);
                       }
                   }
               }
           });
        }
    }
}
