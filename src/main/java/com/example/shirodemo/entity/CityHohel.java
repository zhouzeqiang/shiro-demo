package com.example.shirodemo.entity;

public class CityHohel {

    private String cityName;
    private String hotelName;

    public CityHohel(){

    }

    public CityHohel(String cityName, String hotelName) {
        this.cityName = cityName;
        this.hotelName = hotelName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}