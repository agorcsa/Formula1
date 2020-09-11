package com.example.formula1.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.formula1.api.ImageApiResponse;
import com.example.formula1.api.JsonCompetitorApi;
import com.example.formula1.api.JsonImageApi;
import com.example.formula1.databinding.ActivityCompetitorBinding;
import com.example.formula1.imageobject.Item;
import com.example.formula1.object.Driver;
import com.example.formula1.object.DriverTable;
import com.example.formula1.object.MRData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompetitorActivity extends AppCompatActivity {

    private static final String TAG = "CompetitorActivity";
    private static final String API_KEY = "AIzaSyADx9HTfg1vEtKt2KllxBhwpjB5qUvO52k";
    private static final String ENGINE_KEY = "000213537299717655806:fsqehiydnxg";
    private static final String SEARCH_STRING = "driver's full name";

    private Driver currentDriver;
    private String driverId;
    private String givenName;
    private String familyName;
    private String name;
    private String nationality;
    private String birthDate;
    private String startNumber;
    private String code;
    // for wikipedia button
    private String url;
    // for displaying driver's image
    private String link;

    private String currentCompetitor;

    private String alpha3;

    private ActivityCompetitorBinding competitorBinding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitorBinding = DataBindingUtil.setContentView(this, R.layout.activity_competitor);

        getDriverFromIntent();
        displayCompetitorData();
        competitorApiCall();
        imageApiCall();
        loadDriversImage();
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
                Toast.makeText(CompetitorActivity.this, driversList.get(0).getCode(), Toast.LENGTH_LONG).show();

                nationality = driversList.get(0).getNationality();
                birthDate = driversList.get(0).getBirthDate();
                url = driversList.get(0).getDriversWiki();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void imageApiCall() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                // full URL is:
                // https://www.googleapis.com/customsearch/v1?key=AIzaSyADx9HTfg1vEtKt2KllxBhwpjB5qUvO52k&cx=000213537299717655806:fsqehiydnxg&q=name 
                .baseUrl("https://www.googleapis.com/customsearch/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonImageApi jsonImageApi = retrofit.create(JsonImageApi.class);

        Driver currentDriver = Objects.requireNonNull(getIntent().getExtras()).getParcelable(FieldActivity.DRIVER_OBJECT);
        givenName = currentDriver.getSurname();
        familyName = currentDriver.getFamilyName();
        name = givenName + " " + familyName;

        Call<ImageApiResponse> call = jsonImageApi.getItems(API_KEY, ENGINE_KEY, name);

        call.enqueue(new Callback<ImageApiResponse>() {
            @Override
            public void onResponse(Call<ImageApiResponse> call, Response<ImageApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                List<Item> items = response.body().getItemsList();

                Item item = items.get(0);
                url = item.getLink();

                Log.i(TAG,link);
            }

            @Override
            public void onFailure(Call<ImageApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getDriverFromIntent() {
        Driver currentDriver = Objects.requireNonNull(getIntent().getExtras()).getParcelable(FieldActivity.DRIVER_OBJECT);
        if (currentDriver != null) {

            givenName = currentDriver.getFamilyName();
            familyName = currentDriver.getSurname();
            name = givenName + " " + familyName;
            nationality = currentDriver.getNationality();
            birthDate = currentDriver.getBirthDate();
            startNumber = String.valueOf(currentDriver.getStartNumber());
            code = currentDriver.getCode();
        }
    }

    private String convertFullNameToDriverId(String fullName) {
        driverId = fullName.substring(fullName.indexOf(" "));
        return driverId;
    }

    public void displayCompetitorData() {
        competitorBinding.competitorNameTextView.setText(name);
        competitorBinding.birthDateTextView.setText(birthDate);
        if (startNumber.equals("0") || startNumber.isEmpty()) {
            competitorBinding.startNumberTextView.setText("No start number available");
        } else {
            competitorBinding.startNumberTextView.setText(startNumber);
        }

        if (code == null || code.isEmpty()) {
            competitorBinding.codeTextView.setVisibility(View.INVISIBLE);
        } else {
            competitorBinding.codeTextView.setText(code);
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

    public void loadDriversImage() {
        Picasso.get()
                .load(link)
                .into(competitorBinding.driverImageView);
    }
}
