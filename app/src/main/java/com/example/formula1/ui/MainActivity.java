package com.example.formula1.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.formula1.api.ApiResponse;
import com.example.formula1.api.JsonErgastApi;
import com.example.formula1.object.MRData;
import com.example.formula1.R;
import com.example.formula1.object.Season;
import com.example.formula1.adapter.SeasonAdapter;
import com.example.formula1.object.SeasonTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SeasonAdapter.ButtonsClick {

    public final static String SEASON_YEAR_KEY = "season_year";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Season> mSeasonsArrayList;

    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeasonsArrayList = new ArrayList<>();

        Gson gson = new GsonBuilder()
                // To avoid error with malformed JSON
                // https://stackoverflow.com/questions/39918814/use-jsonreader-setlenienttrue-to-accept-malformed-json-at-line-1-column-1-path
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonErgastApi jsonErgastApi = retrofit.create(JsonErgastApi.class);

        Call<ApiResponse> call = jsonErgastApi.getSeasons();
        // executes the call on the background thread
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                MRData mrData = response.body().getMRData();
                SeasonTable seasonTable = mrData.getSeasonTable();

                mSeasonsArrayList.clear();
                mSeasonsArrayList.addAll(seasonTable.getSeasonList());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        buildRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.season_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SeasonAdapter(mSeasonsArrayList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onInfoButtonClick(Uri uri, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onYearButtonClick(String seasonYear, View view) {
        Intent intent = new Intent(MainActivity.this, FieldActivity.class);
        intent.putExtra(SEASON_YEAR_KEY, seasonYear);
        startActivity(intent);
    }
}
