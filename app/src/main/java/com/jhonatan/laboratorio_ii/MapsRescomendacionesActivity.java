package com.jhonatan.laboratorio_ii;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhonatan.laboratorio_ii.Modelo.Senderos;

import java.util.ArrayList;


public class MapsRescomendacionesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    ArrayList<LatLng> listPoints;
    int puntos= 0;
    private String Nombre, Descripcion;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_rescomendaciones);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsRescomendacionesActivity.this);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("¿Desea recomendar un sendero?")
                .setMessage("para recomendar un sendero primero seleccione la ruta dejando presionado un lugar del mapa" +
                        " , así se marca el punto de inicio y final").
                setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MapsRescomendacionesActivity.this,MainActivity.class);
                        Toast.makeText(MapsRescomendacionesActivity.this,"Se canceló el registro del sendero",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                });
        builder.create();
        builder.show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            puntos =  Integer.parseInt( bundle.getString("puntos"));
            Nombre = bundle.getString("nombre");
            Descripcion = bundle.getString("descripcion");
        }

        listPoints = new ArrayList <>();
        Log.d("puntos", String.valueOf(puntos));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng santaElena = new LatLng(6.210509,-75.49841900000001);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaElena,15));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                //Reset marker when already 2
                if (listPoints.size() == puntos) {
                    listPoints.clear();
                    mMap.clear();
                }
                //Save first point select
                listPoints.add(latLng);
                //Create marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                for(int i =1; i<= puntos; i++) {
                    if (listPoints.size() == i) {
                        //Add first marker to the map
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    }
                    mMap.addMarker(markerOptions);
                }
                if (listPoints.size() == puntos) {
                    for(int i=0; i < (puntos - 1) ; i++) {
                        mMap.addPolyline(new PolylineOptions().add(listPoints.get(i), listPoints.get(i+1)).width(6).color(Color.GREEN));
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsRescomendacionesActivity.this);
                    builder.setIcon(R.mipmap.ic_launcher)
                            .setTitle("¿Desea recomendar el sendero trazado?")
                            .setMessage("El sendero será recomendaro a los demás usuarios").
                            setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Senderos senderos = new Senderos(
                                            Nombre,
                                            Descripcion,
                                            listPoints
                                    );

                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference();

                                    databaseReference.child("Senderos").child(senderos.getNombre()).setValue(senderos);

                                }
                            }).
                            setNegativeButton("Cambiar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    builder.create();
                    builder.show();
                }
            }
        });


    }
}
