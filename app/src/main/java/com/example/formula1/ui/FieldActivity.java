package com.example.formula1.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.formula1.api.ApiResponse;
import com.example.formula1.databinding.ActivityFieldBinding;
import com.example.formula1.object.Driver;
import com.example.formula1.adapter.DriverAdapter;
import com.example.formula1.object.DriverTable;
import com.example.formula1.api.JsonDriverApi;
import com.example.formula1.object.MRData;
import com.example.formula1.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FieldActivity extends AppCompatActivity implements DriverAdapter.OnClick {

    public static final String COMPETITOR_NAME = "driver's name";
    public static final String COMPETITOR_NUMBER = "driver's start number";
    //public static final String DRIVER_CODE = "driver's short code";
    public static final String NATIONALITY = "driver's nationality";

    public static final String DRIVER_OBJECT = "driver_object";

    private String currentSeason;

    private ActivityFieldBinding fieldBinding;

    private RecyclerView mDriverRecyclerView;
    private DriverAdapter mDriverAdapter;
    private RecyclerView.LayoutManager mDriverLayoutManager;
    private ArrayList<Driver> mDriverArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fieldBinding = DataBindingUtil.setContentView(this, R.layout.activity_field);

        getSeasonFromIntent();
        fieldApiCall();
        buildRecyclerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getSeasonFromIntent() {
        currentSeason = Objects.requireNonNull(getIntent().getExtras()).getString(MainActivity.SEASON_YEAR_KEY);
        Objects.requireNonNull(getSupportActionBar()).setTitle(currentSeason);
    }

    public void fieldApiCall() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1/" + currentSeason + "/")
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
                for (String nationality : uniqueNationalities) {
                    int count = Collections.frequency(nationalities, nationality);
                    fieldBinding.counterTextView.append(nationality + ": " + count + "\n");
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
    }


    public void buildRecyclerView() {
        mDriverRecyclerView = findViewById(R.id.field_recycler_view);
        mDriverRecyclerView.setHasFixedSize(true);
        mDriverLayoutManager = new LinearLayoutManager(this);
        mDriverAdapter = new DriverAdapter(getApplicationContext(), mDriverArrayList, this);
        mDriverRecyclerView.setLayoutManager(mDriverLayoutManager);
        mDriverRecyclerView.setAdapter(mDriverAdapter);
    }

    @Override
    public void onDriverInfoButtonClick(Uri uri, View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Driver currentDriver) {
        Intent intent = new Intent(FieldActivity.this, CompetitorActivity.class);
        intent.putExtra(DRIVER_OBJECT, currentDriver);
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
                mDriverAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    // we have an Array of String nationalities
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void sortDriversByNationality(String[] inputArray) {
        //Create LinkedHashMap with elements as keys and their occurrences as values
        // nationality as String
        // occurrence as Integer
        Map<String, Integer> nationalityCountMap = new LinkedHashMap<>();

        // check presence of each element in nationalityCountMap
        for (int i = 0; i < inputArray.length; i++) {
            if (nationalityCountMap.containsKey(inputArray[i])) {
                //If element is present in nationalityCountMap, increment its value by 1
                nationalityCountMap.put(inputArray[i], nationalityCountMap.get(inputArray[i]) + 1);
            } else {
                //If element is not present, insert this element with 1 as its value
                nationalityCountMap.put(inputArray[i], 1);
            }
        }

        //Create an ArrayList to hold sorted elements
        ArrayList sortedNationalities = new ArrayList();

        //Java 8 code to sort nationalityCountMap by values in descending order
        //put keys into sortedNationalities list
        /*nationalityCountMap.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    class get value {
                            while(
                        int i = 1)
                        i <=entry.getValue();
                        i++;
                    }
                    sortedNationalities.add(entry.getKey());
                });*/
    }
}
