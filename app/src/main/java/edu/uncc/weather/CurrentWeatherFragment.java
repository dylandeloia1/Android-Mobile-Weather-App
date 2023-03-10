// Group 11
// Homework 6
// CurrentWeatherFragment.java

package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentWeatherFragment extends Fragment {

    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    Weather weather;
    CurrentWeatherListener mListener;

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);

        getActivity().setTitle("Current Weather");

        mListener.getCurrentWeather(mCity);

        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoForecastFragment(mCity);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
    }

    public void setWeather(Weather weather_){
        weather = weather_;
        binding.textViewCityName.setText(mCity.getCity() + ", " + mCity.getCountry());
        binding.textViewTemp.setText(weather.getTemp() + " F");
        binding.textViewTempMax.setText(weather.getTempMax() + " F");
        binding.textViewTempMin.setText(weather.getTempMin() + " F");
        binding.textViewDesc.setText(weather.getDescription());
        binding.textViewHumidity.setText(weather.getHumidity() + " %");
        binding.textViewWindSpeed.setText(weather.getWindSpeed() + " miles/hr");
        binding.textViewWindDegree.setText(weather.getWindDegree() + " degrees");
        binding.textViewCloudiness.setText(weather.getCloudiness() + " %");
        Picasso.get().load("https://openweathermap.org/img/wn/" + weather.getIconCode() + "@2x.png").into(binding.imageViewWeatherIcon);
    }

    public interface CurrentWeatherListener{
        void getCurrentWeather(DataService.City city);

        void gotoForecastFragment(DataService.City city);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof CurrentWeatherListener){
            mListener = (CurrentWeatherListener) context;
        }
    }

}