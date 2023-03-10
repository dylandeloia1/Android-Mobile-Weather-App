// Group 11
// Homework 6
// WeatherForecastFragment.java

package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.uncc.weather.databinding.FragmentWeatherForecastBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment {

    FragmentWeatherForecastBinding binding;
    private static final String ARG_PARAM_CITY = "city";
    DataService.City city;
    ArrayList<Weather> weatherForecast = new ArrayList<>();
    ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WeatherForecastFragment newInstance(DataService.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }

        mListener.getWeatherForecast(city);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);

        getActivity().setTitle("Hourly Forecast");

        binding.textViewCityName.setText(city.getCity() + " ," + city.getCountry());

        return binding.getRoot();
    }

    ForecastListener mListener;

    public interface ForecastListener{
        void getWeatherForecast(DataService.City city);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ForecastListener){
            mListener = (ForecastListener) context;
        }
    }

    public void setForecast(ArrayList<Weather> weatherForecast){
        this.weatherForecast = weatherForecast;
        adapter = new ForecastAdapter(getContext(),R.layout.forecast_row_item,weatherForecast);
        binding.listView.setAdapter(adapter);
    }
}