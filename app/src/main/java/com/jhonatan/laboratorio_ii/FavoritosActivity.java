package com.jhonatan.laboratorio_ii;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jhonatan.laboratorio_ii.Modelo.Servicios;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity {
    private ArrayList<Servicios> serviciosListH;
    private ArrayList<Servicios> serviciosListT;
    private ArrayList<Servicios> serviciosListR;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Boolean> favoritosH;
    private ArrayList<Boolean> favoritosR;
    private ArrayList<Boolean> favoritosT;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        serviciosListH = new ArrayList<>();
        serviciosListT = new ArrayList<>();
        serviciosListR = new ArrayList<>();
        favoritosH = new ArrayList<>();
        favoritosR = new ArrayList<>();
        favoritosT = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Servicio").child("Hoteles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviciosListH.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Servicios servicios= snapshot.getValue(Servicios.class);
                        serviciosListH.add(servicios);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Servicio").child("Turismo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviciosListT.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Servicios servicios= snapshot.getValue(Servicios.class);
                        serviciosListT.add(servicios);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.child("Servicio").child("Restaurantes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviciosListR.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Servicios servicios= snapshot.getValue(Servicios.class);
                        serviciosListR.add(servicios);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void goMap(View view) {
        Intent intent3 = new Intent(FavoritosActivity.this, MapsServiciosActivity.class);
        serviciosListH.get(0).getFavoritos().toString();


        boolean mr1=true,mr2=true,mr3=true,mr4 = true,mh1=true,mh2=true,mh3=true,mh4=true,mt1=true,mt2=true,mt3=true;
        intent3.putExtra("mr1",mr1);
        intent3.putExtra("mr2",mr2);
        intent3.putExtra("mr3",mr3);
        intent3.putExtra("mr4",mr4);
        intent3.putExtra("mh1",mh1);
        intent3.putExtra("mh2",mh2);
        intent3.putExtra("mh3",mh3);
        intent3.putExtra("mh4",mh4);
        intent3.putExtra("mt1",mt1);
        intent3.putExtra("mt2",mt2);
        intent3.putExtra("mt3",mt3);
        startActivity(intent3);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FavoritosHFragment tab1 = new FavoritosHFragment();
                    return tab1;
                case 1:
                    FavoritosRFragment tab2 = new FavoritosRFragment();
                    return tab2;
                case 2:
                    FavoritosTFragment tab3 = new FavoritosTFragment();
                    return tab3;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
