package com.jhonatan.laboratorio_ii.Modelo;

/**
 * Created by Jhonatan on 26/05/2018.
 */

public  class  LatLng {
    private  Double latitude;
    private  Double longitude;

    public LatLng() {}

    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
