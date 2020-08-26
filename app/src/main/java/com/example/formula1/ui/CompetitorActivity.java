package com.example.formula1.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.formula1.R;
import com.example.formula1.api.ApiResponse;
import com.example.formula1.api.JsonCompetitorApi;
import com.example.formula1.api.JsonDriverApi;
import com.example.formula1.object.Competitor;
import com.example.formula1.object.Driver;
import com.example.formula1.object.DriverTable;
import com.example.formula1.object.MRData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompetitorActivity extends AppCompatActivity {

    private String currentCompetitor;
    private String nationality;
    private int startNumber;
    private String abbreviation;
    private String url;
    private String driverId;
    private int position = 0;

    private TextView mCompetitorName;
    private ImageView mFlag;
    private TextView mStartNumber;
    private TextView mCode;
    private Button mInfoButton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitor);

        mCompetitorName = findViewById(R.id.competitor_name_textView);
        mFlag = findViewById(R.id.flag_imageView);
        mStartNumber = findViewById(R.id.start_number_textView);
        mCode = findViewById(R.id.code_textView);
        mInfoButton = findViewById(R.id.competitor_info_button);

        currentCompetitor = (String) Objects.requireNonNull(getIntent().getExtras()).get(FieldActivity.COMPETITOR_NAME);
        Objects.requireNonNull(getSupportActionBar()).setTitle(currentCompetitor);

        displayCompetitorData();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1/drivers/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonCompetitorApi jsonCompetitorApi = retrofit.create(JsonCompetitorApi.class);
        Call<ApiResponse> call = jsonCompetitorApi.getCompetitorDetails();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                MRData mrData = response.body().getMRData();
                DriverTable driverTable = mrData.getDriverTable();

                driverId = driverTable.getDriverId();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void displayCompetitorData() {
        mCompetitorName.setText(currentCompetitor);
    }
}
