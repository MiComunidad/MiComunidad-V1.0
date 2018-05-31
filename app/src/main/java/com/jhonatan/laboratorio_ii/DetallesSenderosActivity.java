package com.jhonatan.laboratorio_ii;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Modelo.LatLng;
import com.jhonatan.laboratorio_ii.Modelo.Senderos;

import java.util.ArrayList;

public class DetallesSenderosActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<LatLng> rutaSenderos;
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_senderos);

        rutaSenderos = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Bundle senderosel = getIntent().getExtras();
        Senderos senderos = null;
        if(senderosel != null) {
            senderos = (Senderos) senderosel.getSerializable("senderos");
            Bundle sen = new Bundle();
            sen.putSerializable("sen",senderos);
            MapSenderosFragment mapSenderosFragment = (MapSenderosFragment) getSupportFragmentManager().findFragmentById(R.id.Detalles);
            mapSenderosFragment.mostrarInfo(senderos.getNombre().toString(),senderos.getUbicacion().toString(),senderos.getDescripcion().toString());

            databaseReference.child("Senderos").child("Puntos").child(senderos.getNombre()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    rutaSenderos.clear();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            LatLng latLng = snapshot.getValue(LatLng.class);
                            rutaSenderos.add(latLng);
                        }
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.MapSenderos);
                        mapFragment.getMapAsync(DetallesSenderosActivity.this);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        for(int i = 0 ; i< rutaSenderos.size();i++){
            Log.d("Puntos","  "+ rutaSenderos.get(i).getLatitude().toString() + " " + rutaSenderos.get(i).getLongitude().toString());
            drawMarker(new com.google.android.gms.maps.model.LatLng(rutaSenderos.get(i).getLatitude(),rutaSenderos.get(i).getLongitude()));
        }
        for(int i = 0; i<(rutaSenderos.size() - 1); i++){
            com.google.android.gms.maps.model.LatLng pos1= new com.google.android.gms.maps.model.LatLng(rutaSenderos.get(i).getLatitude(),rutaSenderos.get(i).getLongitude());
            com.google.android.gms.maps.model.LatLng pos2= new com.google.android.gms.maps.model.LatLng(rutaSenderos.get(i+1).getLatitude(),rutaSenderos.get(i+1).getLongitude());
            map.addPolyline(new PolylineOptions().add(pos1,pos2).width(6).color(Color.GREEN));
        }
        com.google.android.gms.maps.model.LatLng pos1= new com.google.android.gms.maps.model.LatLng(rutaSenderos.get(0).getLatitude(),rutaSenderos.get(0).getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos1,15));
    }

    private void drawMarker(com.google.android.gms.maps.model.LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        map.addMarker(markerOptions);
    }
}
