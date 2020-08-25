package com.example.formula1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FieldActivity extends AppCompatActivity implements DriverAdapter.OnClick{

    private RecyclerView mDriverRecyclerView;
    private RecyclerView.Adapter mDriverAdapter;
    private RecyclerView.LayoutManager mDriverLayoutManager;
    private ArrayList<Driver> mDriverArrayList;
    private TextView counter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        counter = findViewById(R.id.thai_counter_text_view);

        String clickedSeason = Objects.requireNonNull(getIntent().getExtras()).getString(MainActivity.SEASON_YEAR_KEY);
        Objects.requireNonNull(getSupportActionBar()).setTitle(clickedSeason);

        mDriverArrayList = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1/" + clickedSeason + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonDriverApi jsonDriverApi = retrofit.create(JsonDriverApi.class);

        Call<ApiResponse> call = jsonDriverApi.getDrivers();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                MRData mrData = response.body().getMRData();
                DriverTable driverTable = mrData.getDriverTable();

                List<Driver> list = driverTable.getDriverList();
                ArrayList<String> nationalities = new ArrayList<>();
                for (Driver driver : list) {
                    nationalities.add(driver.getNationality());
                }
                List<String> uniqueNationalities = new ArrayList<>(new HashSet<>(nationalities));
                for (String nationality: uniqueNationalities) {
                    int count = Collections.frequency(nationalities, nationality);
                    counter.append(nationality +": " + count + "\n");

                    //Comparator c = Collections.reverseOrder();
                    //Collections.sort(list,c);
                }

                mDriverArrayList.clear();
                mDriverArrayList.addAll(driverTable.getDriverList());
                mDriverAdapter.notifyDataSetChanged();
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
        mDriverRecyclerView = findViewById(R.id.field_recycler_view);
        mDriverRecyclerView.setHasFixedSize(true);
        mDriverLayoutManager = new LinearLayoutManager(this);
        mDriverAdapter = new DriverAdapter(mDriverArrayList, this);
        mDriverRecyclerView.setLayoutManager(mDriverLayoutManager);
        mDriverRecyclerView.setAdapter(mDriverAdapter);
    }

    @Override
    public void onDriverInfoButtonClick(Uri uri, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View v) {
        Intent intent = new Intent(FieldActivity.this, DriverProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(), "SearchView", Toast.LENGTH_LONG).show();

                //mDriverAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
