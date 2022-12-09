package tn.isetr_mpdam.weatherapp;

import java.util.ArrayList;

public class ResponseItem{
    public String temperature;
    public String wind;
    public String description;
    public ArrayList<Forecast> forecast;

    public class Forecast{
        public String day;
        public String temperature;
        public String wind;
    }
}