package com.example.test.AdressService;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class GeoThing {
    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    public static String getAddress(Double lat, Double lng, Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null) {
            return "";
        } else if (list.size() == 0) {
            return "";
        } else {
            return list.get(0).getAddressLine(0);
        }
    }

    public static double getDistance(LatLng a, LatLng b) {
        double lat_a = a.latitude;
        double lng_a = a.longitude;
        double lat_b = b.latitude;
        double lng_b = a.longitude;
        Location locationA = new Location("point A");
        locationA.setLatitude(lat_a);
        locationA.setLongitude(lng_a);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat_b);
        locationB.setLongitude(lng_b);
        double distance = locationA.distanceTo(locationB);
        return distance;
    }
}
