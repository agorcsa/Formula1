package com.example.formula1.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blongho.country_data.World;
import com.example.formula1.R;
import com.example.formula1.api.ApiResponse;
import com.example.formula1.api.JsonCompetitorApi;
import com.example.formula1.databinding.ActivityCompetitorBinding;
import com.example.formula1.object.Driver;
import com.example.formula1.object.DriverTable;
import com.example.formula1.object.MRData;
import com.example.formula1.util.FlagImageRatio;
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
    private String alpha3;
    private String birthDate;
    private String startNumber;
    private String code;
    private String url;
    private String driverId;

    private ActivityCompetitorBinding competitorBinding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitorBinding = DataBindingUtil.setContentView(this, R.layout.activity_competitor);

        getCompetitorFromIntent();
        displayCompetitorData();
        competitorApiCall();
    }

    public void competitorApiCall() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ergast.com/api/f1/drivers/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonCompetitorApi jsonCompetitorApi = retrofit.create(JsonCompetitorApi.class);
        Call<ApiResponse> call = jsonCompetitorApi.getCompetitorDetails(driverId);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }

                MRData mrData = response.body().getMRData();
                DriverTable driverTable = mrData.getDriverTable();

                driverId = driverTable.getDriverId();

                List<Driver> driversList = driverTable.getDriverList();
                // we have a list of arrays with a single object, therefor I use position = 0
                nationality = driversList.get(0).getNationality();
                birthDate = driversList.get(0).getBirthDate();
                url = driversList.get(0).getDriversWiki();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getCompetitorFromIntent() {
        currentCompetitor = (String) Objects.requireNonNull(getIntent().getExtras()).get(FieldActivity.COMPETITOR_NAME);
        Objects.requireNonNull(getSupportActionBar()).setTitle(currentCompetitor);
        driverId = convertFullNameToDriverId(currentCompetitor);
        startNumber = (String) getIntent().getExtras().get(FieldActivity.COMPETITOR_NUMBER);
        nationality = (String) getIntent().getExtras().get(FieldActivity.NATIONALITY);
    }

    private String convertFullNameToDriverId(String fullName) {
        driverId = fullName.substring(fullName.indexOf(" "));
        return driverId;
    }

    public void displayCompetitorData() {
        competitorBinding.competitorNameTextView.setText(currentCompetitor);
        competitorBinding.birthDateTextView.setText(birthDate);
        if (startNumber.equals("0") || startNumber.isEmpty()) {
            competitorBinding.startNumberTextView.setText("No start number available");
        } else {
            competitorBinding.startNumberTextView.setText(startNumber);
        }

        competitorBinding.competitorInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        switch (nationality) {
            case "American":
                setCountryFlag("usa");
                break;
            case "British":
                setCountryFlag("united kingdom");
                break;
            case "German":
                setCountryFlag("Germany");
                break;
            case "New Zeelander":
                setCountryFlag("New Zeeland");
                break;
            default:
                setCountryFlag(convertNationalityToAlpha3(nationality));
                break;
        }
    }

    public void setCountryFlag(String nationality) {
        // Initializes the library and loads all data for the world's flags
        World.init(getApplicationContext());

        // gets the flag of the specific country
        final int flag = World.getFlagOf(nationality);

        // Sets the image of an imageView
        ImageView flagImage = findViewById(R.id.flag_imageView);
        flagImage.setImageResource(flag);
    }

    public String convertNationalityToAlpha3(String nationality) {
        alpha3 = nationality.substring(0, 2);
        return alpha3;
    }
}
