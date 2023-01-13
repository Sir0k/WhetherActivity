package com.example.whetheractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whetheractivity.api.Api;
import com.example.whetheractivity.model.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Api api;
    TextView temp, timeZone, LocationText;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(Api.class);

        temp = findViewById(R.id.temp);
        timeZone = findViewById(R.id.timeZone);
        LocationText = findViewById(R.id.coordinates);

        locationManager = getSystemService(LocationManager.class);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LocationText.setText("широта: " + location.getLatitude() + "\n долгота: " + location.getLongitude());
                api.getWeather(location.getLatitude(), location.getLongitude(), "e012f24a2044e4fe9504ac4db488f059", "metric").enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.code() == 200) {
                            Weather weather = response.body();
                            temp.setText("температура: " + weather.getCurrent().getTemp() + "\nчувствуется как " + weather.getCurrent().getFeels_like());
                            timeZone.setText("Часовой пояс: " + weather.getTimeZone());
                        }
                        else {
                            Toast.makeText(getApplicationContext(), response.code() + "", Toast.LENGTH_LONG).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            };
        });
    }
}