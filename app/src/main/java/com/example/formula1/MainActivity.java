package com.example.formula1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SeasonItem> mSeasonsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeasonsArrayList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonErgastApi jsonErgastApi = retrofit.create(JsonErgastApi.class);

        Call<SeasonTable> call = jsonErgastApi.getSeasons();
        // executes the call on the background thread
        call.enqueue(new Callback<SeasonTable>() {
            @Override
            public void onResponse(Call<SeasonTable> call, Response<SeasonTable> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                SeasonTable seasonTable = response.body();
                List<Season> seasons = seasonTable.getSeasonList();


                // for each place in our places list, execute the code
                for (Season  place: seasons){

                    int year = place.getYear();
                    String wikiUrl = place.getWikiUrl();
                    // inform the adapter and the recyclerview shows the values
                }
            }

            @Override
            public void onFailure(Call<SeasonTable> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        // dummy/test data
        // mSeasonsArrayList.add(new SeasonItem("Line1"));

        buildRecyclerView();
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.season_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SeasonAdapter(mSeasonsArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
