package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;

import java.util.ArrayList;

public class MapsServiciosActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Marker mH1,mH2,mH3,mH4,mT1,mT2,mT3,mR1,mR2,mR3,mR4;
    private ArrayList<Servicios> serviciosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterServicios;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_servicios);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        serviciosList = new ArrayList<>();
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
        //Database

        LatLng santaElena = new LatLng(6.210509, -75.49841900000001);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santaElena,15));

        //Marcadores Restaurantes
        LatLng rCanela = new LatLng(6.210020889980011,-75.49846798181534);
        mR3 = googleMap.addMarker(new MarkerOptions()
        .position(rCanela)
        .title("Restaurante Canela")
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mrestaurante)));

        LatLng rMonasterio = new LatLng(6.210057553940865,-75.4987408965826);
        mR4 = googleMap.addMarker(new MarkerOptions()
                .position(rMonasterio)
                .title("Restaurante El Monasterio")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mrestaurante)));

        LatLng rMontañita = new LatLng(6.2149431,-75.50231694444444);
        mR1 = googleMap.addMarker(new MarkerOptions()
                .position(rMontañita)
                .title("Restaurante la Montalita")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mrestaurante)));

        LatLng rCañaBrava = new LatLng(6.216212396844197,-75.50327181816101);
        mR2 = googleMap.addMarker(new MarkerOptions()
                .position(rCañaBrava)
                .title("Restaurante Caña Brava")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mrestaurante)));

        //Marcadores Hoteles
        LatLng hBosque = new LatLng(6.252634684726873,-75.50547122955322);
        mH1 = googleMap.addMarker(new MarkerOptions()
                .position(hBosque)
                .title("Hotel El Bosque")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mhotel)));

        LatLng hMontañaMagica = new LatLng(6.23454650885971,-75.49542903900146);
        mH2 = googleMap.addMarker(new MarkerOptions()
                .position(hMontañaMagica)
                .title("Hotel Montaña Magica")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mhotel)));

        LatLng hComfenalco = new LatLng(6.296422199999999,-75.5020672);
        mH3 = googleMap.addMarker(new MarkerOptions()
                .position(hComfenalco)
                .title("Hotel Comfenalco")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mhotel))
                );

        LatLng hMontevivo = new LatLng(6.208586327180418,-75.49116969108582);
        mH4 = googleMap.addMarker(new MarkerOptions()
                .position(hMontevivo)
                .title("Hotel MonteVivo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mhotel)));

        //Marcadores Turismo
        LatLng tComfama = new LatLng(6.2617425,-75.49454930000002);
        mT1 = googleMap.addMarker(new MarkerOptions()
                .position(tComfama)
                .title("Turismo Comfama")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mturismo)));

        LatLng tReservaMontevivo = new LatLng(6.208582,-75.49116500000002);
        mT2 = googleMap.addMarker(new MarkerOptions()
                .position(tReservaMontevivo)
                .title("Turismo Reserva MonteVivo")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mturismo)));

        LatLng tComfenalco = new LatLng(6.2947181,-75.5016789);
        mT3 = googleMap.addMarker(new MarkerOptions()
                .position(tComfenalco)
                .title("Turismo Comfenalco")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mturismo)));

        googleMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(mH1)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Hoteles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(0));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }else if(marker.equals(mH2)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Hoteles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mH3)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Hoteles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(2));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mH4)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Hoteles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(3));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mR1)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Restaurantes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(0));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mR2)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Restaurantes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mR3)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Restaurantes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(2));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mR4)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Restaurantes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(3));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mT1)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Turismo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(0));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mT2)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Turismo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(1));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(marker.equals(mT3)){
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();
            databaseReference.child("Servicios").child("Turismo").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    serviciosList.clear();
                    if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Servicios servicios= snapshot.getValue(Servicios.class);

                            serviciosList.add(servicios);

                        }

                    }

                    Intent intent = new Intent(MapsServiciosActivity.this,DetallesActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("servicios", serviciosList.get(3));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
