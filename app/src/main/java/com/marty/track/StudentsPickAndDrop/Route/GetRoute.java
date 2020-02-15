package com.marty.track.StudentsPickAndDrop.Route;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.marty.track.R;
import com.marty.track.StudentsPickAndDrop.Route.DirectionHelpers.FetchURL;
import com.marty.track.StudentsPickAndDrop.Route.DirectionHelpers.TaskLoadedCallback;

public class GetRoute extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    GoogleMap map;
    Button getDirections;
    MarkerOptions placeOne, placeTwo;
    Polyline currentPolyline;
    String schoollat, schoollng, pdlat, pdlng;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    Double currentLat;
    Double currentLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_route);

        Bundle bundle = getIntent().getExtras();
        schoollat = bundle.getString("school_lat");
        schoollng = bundle.getString("school_lng");
        pdlat = bundle.getString("pd_latitude");
        pdlng = bundle.getString("pd_longitude");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location == null){
                    requestNewLocationData();
                } else {
                    currentLat = location.getLatitude();
                    currentLng = location.getLongitude();
                    System.out.println("Current Latitude: "+location.getLatitude());
                    System.out.println("Current Longitude: "+location.getLongitude());
                }
            }
        });

        Double clat = currentLat;
        System.out.println("Current Latitude : "+clat);

        Double schoollatitude = new Double(schoollat);
        Double schoollongitude = new Double(schoollng);
        System.out.println("School latitude : "+schoollatitude + ", School Longitude : "+schoollongitude);
        Double pdlatitude = new Double(pdlat);
        Double pdlongitude = new Double(pdlng);

        getDirections = findViewById(R.id.getdirection);
        getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(GetRoute.this).execute(getUrl(placeOne.getPosition(), placeTwo.getPosition(), "driving"), "driving");
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        placeOne = new MarkerOptions().position(new LatLng(schoollatitude, schoollongitude)).title("Palace");
        placeTwo = new MarkerOptions().position(new LatLng(pdlatitude, pdlongitude)).title("Office");

        String url = getUrl(placeOne.getPosition(), placeTwo.getPosition(), "driving");
        new FetchURL(GetRoute.this).execute(url, "driving");

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(placeOne);
        map.addMarker(placeTwo);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + "driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

}
