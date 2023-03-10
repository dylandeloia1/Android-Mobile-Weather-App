// Group 11
// Homework 6
// Weather.java

package edu.uncc.weather;

import java.io.Serializable;

public class Weather implements Serializable {

    String temp,tempMax,tempMin,description,humidity,windSpeed,windDegree,cloudiness,iconCode,dateTime;

    public Weather(String temp, String tempMax, String tempMin, String description, String humidity, String windSpeed, String windDegree, String cloudiness, String icon, String dateTime) {
        this.temp = temp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.cloudiness = cloudiness;
        this.iconCode = icon;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp='" + temp + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", description='" + description + '\'' +
                ", humidity='" + humidity + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDegree='" + windDegree + '\'' +
                ", cloudiness='" + cloudiness + '\'' +
                ", iconCode='" + iconCode + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getDescription() {
        return description;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDegree() {
        return windDegree;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public String getIconCode() {
        return iconCode;
    }

    public String getDateTime() {
        return dateTime;
    }
}
