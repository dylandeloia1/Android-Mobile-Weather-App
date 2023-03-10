// Group 11
// Homework 6
// MainActivity.java

package edu.uncc.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener, CurrentWeatherFragment.CurrentWeatherListener, WeatherForecastFragment.ForecastListener {

    private final OkHttpClient client = new OkHttpClient();
    String apiKey = "8ba9a55d1914a1a6b09aa317172837c5";
    String TAG = "DEMO";
    Weather weather;
    ArrayList<Weather> weatherForecast = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();
    }

    @Override
    public void gotoCurrentWeather(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, CurrentWeatherFragment.newInstance(city), "current-weather-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoForecastFragment(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance(city), "forecast-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void getCurrentWeather(DataService.City city){
        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("lat",String.valueOf(city.getLat()))
                .addQueryParameter("lon",String.valueOf(city.getLon()))
                .addQueryParameter("appid",apiKey)
                .addQueryParameter("units","imperial")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONObject jsonMain = json.getJSONObject("main");
                        JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
                        JSONObject jsonWind = json.getJSONObject("wind");
                        JSONObject jsonClouds = json.getJSONObject("clouds");

                        weather = new Weather(
                                jsonMain.getString("temp"),
                                jsonMain.getString("temp_max"),
                                jsonMain.getString("temp_min"),
                                jsonWeather.getString("description"),
                                jsonMain.getString("humidity"),
                                jsonWind.getString("speed"),
                                jsonWind.getString("deg"),
                                jsonClouds.getString("all"),
                                jsonWeather.getString("icon"),
                                json.getString("dt"));

                        Log.d(TAG, "onResponse: " + weather.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CurrentWeatherFragment currentWeatherFragment = (CurrentWeatherFragment) getSupportFragmentManager().findFragmentByTag("current-weather-fragment");
                            currentWeatherFragment.setWeather(weather);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getWeatherForecast(DataService.City city){
        Log.d(TAG, "getWeatherForecast: ");

        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/forecast").newBuilder()
                .addQueryParameter("lat",String.valueOf(city.getLat()))
                .addQueryParameter("lon",String.valueOf(city.getLon()))
                .addQueryParameter("appid",apiKey)
                .addQueryParameter("units","imperial")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray jsonArray = json.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonEntry = jsonArray.getJSONObject(i);

                            JSONObject jsonMain = jsonEntry.getJSONObject("main");
                            JSONObject jsonWeather = jsonEntry.getJSONArray("weather").getJSONObject(0);
                            JSONObject jsonWind = jsonEntry.getJSONObject("wind");
                            JSONObject jsonClouds = jsonEntry.getJSONObject("clouds");

                            weather = new Weather(
                                    jsonMain.getString("temp"),
                                    jsonMain.getString("temp_max"),
                                    jsonMain.getString("temp_min"),
                                    jsonWeather.getString("description"),
                                    jsonMain.getString("humidity"),
                                    jsonWind.getString("speed"),
                                    jsonWind.getString("deg"),
                                    jsonClouds.getString("all"),
                                    jsonWeather.getString("icon"),
                                    jsonEntry.getString("dt_txt"));


                            weatherForecast.add(weather);
                        }
                     }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WeatherForecastFragment weatherForecastFragment = (WeatherForecastFragment) getSupportFragmentManager().findFragmentByTag("forecast-fragment");
                            weatherForecastFragment.setForecast(weatherForecast);
                        }
                    });
                }
            }
        });

    }




}