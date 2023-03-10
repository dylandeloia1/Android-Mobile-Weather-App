// Group 11
// Homework 6
// ForecastAdapter.java

package edu.uncc.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<Weather> {
    public ForecastAdapter(@NonNull Context context, int resource, @NonNull List<Weather> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_row_item,parent,false);
        }

        Weather weather = getItem(position);
        TextView textViewDateTime;
        TextView textViewTemp;
        TextView textViewTempMax;
        TextView textViewTempMin;
        TextView textViewHumidity;
        TextView textViewDesc;
        ImageView imageView;

        textViewDateTime = convertView.findViewById(R.id.textViewDateTime);
        textViewTemp = convertView.findViewById(R.id.textViewTemp);
        textViewTempMax = convertView.findViewById(R.id.textViewTempMax);
        textViewTempMin = convertView.findViewById(R.id.textViewTempMin);
        textViewHumidity = convertView.findViewById(R.id.textViewHumidity);
        textViewDesc = convertView.findViewById(R.id.textViewDesc);
        imageView = convertView.findViewById(R.id.imageViewWeatherIcon);

        textViewDateTime.setText(weather.getDateTime());
        textViewTemp.setText("Temp: " + weather.getTemp() + " F");
        textViewTempMax.setText("Max: " + weather.getTempMax() + " F");
        textViewTempMin.setText("Min: " + weather.getTempMin() + " F");
        textViewHumidity.setText("Humidity: " + weather.getHumidity() + " %");
        textViewDesc.setText(weather.getDescription());
        Picasso.get().load("https://openweathermap.org/img/wn/" + weather.getIconCode() + "@2x.png").into(imageView);

        return convertView;
    }
}
