package com.example.whetheractivity.model;

public class Weather {
    private double lat, lon;
    private String timezone;
    private WeatherItem current;

    public Weather(double lat, double lon, String timeZone, WeatherItem current){
        this.lat = lat;
        this.lon = lon;
        this.timezone = timeZone;
        this.current = current;
    }

    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    public String getTimeZone() { return timezone; }
    public void setTimeZone(String timeZone) { this.timezone = timeZone; }
    public WeatherItem getCurrent() { return current; }
    public void setCurrent(WeatherItem current) { this.current = current; }
}
