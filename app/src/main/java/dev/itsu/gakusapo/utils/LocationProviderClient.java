package dev.itsu.gakusapo.utils;

import android.content.Context;
import android.location.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;

public abstract class LocationProviderClient implements OnSuccessListener<Location> {

    private final WeakReference<FusedLocationProviderClient> client;

    public LocationProviderClient(Context context) {
        client = new WeakReference<>(LocationServices.getFusedLocationProviderClient(context));
    }

    public void startListener() throws SecurityException {
        client.get().getLastLocation().addOnSuccessListener(this);
    }

}
