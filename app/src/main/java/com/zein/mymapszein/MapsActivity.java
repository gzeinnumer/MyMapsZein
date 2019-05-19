package com.zein.mymapszein;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private static final int REQUEST_PERMITION_ACCESS=1;
    private MarkerOptions markerOptions = new MarkerOptions();
    ArrayList<LatLng> latLngs = new ArrayList<>();
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        enableMyLocation();
        setMapLongCLick(mMap);
        setPoiOnCLick(mMap);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.normal_maps:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.terrain_maps:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.hybrid_maps:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_maps:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
                default:
            return onOptionsItemSelected(item);
        }
    }

    private void setPoiOnCLick(GoogleMap googleMap) {
        /*
        googleMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {
                Marker poiMarker =  mMap.addMarker(new MarkerOptions()
                        .position(pointOfInterest.latLng)
                        .title(pointOfInterest.name));
                poiMarker.showInfoWindow();
                poiMarker.setTag(getString(R.string.poi));
            }
        });
        */
    }

    private void setMapLongCLick(final GoogleMap googleMap) {
        /*
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.addMarker(new MarkerOptions().position(latLng));
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                String myLocation = String.format(
                        Locale.getDefault(),
                        "Latitude %1$.5f, Longitude %2$.5f",
                        latLng.latitude,
                        latLng.longitude);
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.location))
                        .snippet(myLocation)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            }
        });
        */
    }

    private void enableMyLocation() {
        /*
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
                //mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMITION_ACCESS);
        }
        */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMyMapsStyle(googleMap);


        // Add a marker in Sydney and move the camera
        LatLng BaskoPadang = new LatLng(-0.901145, 100.350509);
        mMap.addMarker(new MarkerOptions().position(BaskoPadang).title("BaskoPadang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(BaskoPadang));
        latLngs.add(new LatLng(-0.9048979,100.3803406));
        latLngs.add(new LatLng(-0.9081326,100.3769503));
        latLngs.add(new LatLng(-0.9088516,100.3725403));
        for (LatLng point : latLngs){
            markerOptions.position(point).title("Point Of Intrest").snippet("Tempat Penting di Padang");
            googleMap.addMarker(markerOptions);
        }
    }

    private void setMyMapsStyle(GoogleMap map) {
        try {
            boolean mapStyleReady = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this,R.raw.maps_style));
            if(!mapStyleReady){
                Log.e(TAG, "Map Style Not Found");
            }
        }catch (Resources.NotFoundException ex){
            Log.e(TAG,"Parsing Style Erros , Maps Style Not Found" );
        }
    }


}
