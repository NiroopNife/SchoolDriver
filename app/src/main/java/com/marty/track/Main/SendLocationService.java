package com.marty.track.Main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class SendLocationService extends Service {

    FusedLocationProviderClient client;
    LocationCallback callback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        client = LocationServices.getFusedLocationProviderClient(this);
        callback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                System.out.println("Latitude is : "+locationResult.getLastLocation().getLatitude());
                System.out.println("Longitude is : "+locationResult.getLastLocation().getLongitude());
                Intent intent = new Intent("ACTION_LOCATION");
                intent.putExtra("latitude", locationResult.getLastLocation().getLatitude());
                intent.putExtra("longitude", locationResult.getLastLocation().getLongitude());
                sendBroadcast(intent);

//                Intent intent1 = new Intent("STOP_LOCATION");
//                stopService(intent1);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLocation(){
        LocationRequest request = new LocationRequest();
        request.setInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        client.requestLocationUpdates(request, callback, Looper.myLooper());
    }
}
