package com.marty.track.SendLocation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LocationService extends Service {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("Location", "Lat is : "+locationResult.getLastLocation().getLatitude()
                        +"Lng is : "+locationResult.getLastLocation().getLongitude());

                Retrofit retrofit = SendLocationClient.sendLocation();
                SendLocationService service = retrofit.create(SendLocationService.class);
                Call<SendLocationResponse> call = service.sendLocation(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude(), "5", "TMS");
                System.out.println(call.toString());
                call.enqueue(new Callback<SendLocationResponse>() {
                    @Override
                    public void onResponse(Call<SendLocationResponse> call, Response<SendLocationResponse> response) {
                        System.out.println(response.toString());
                        if (response.isSuccessful()){
                            String latitude = "";
                            String longitude = "";
                            SendLocationResponse resOb = response.body();
                            if (resOb.getStatus().equals("true")){
                                System.out.println("Location Posted");
                                Toast.makeText(LocationService.this, "Location Posted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LocationService.this, "Not Posted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LocationService.this, "Not Posted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendLocationResponse> call, Throwable t) {

                    }
                });

                Intent intent = new Intent("ACT_LOC");
                intent.putExtra("latitude", locationResult.getLastLocation().getLatitude());
                intent.putExtra("longitude", locationResult.getLastLocation().getLongitude());
                sendBroadcast(intent);

            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
}
