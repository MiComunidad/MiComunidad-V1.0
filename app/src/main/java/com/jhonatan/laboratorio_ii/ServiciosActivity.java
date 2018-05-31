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

public class ServiciosActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_servicios);

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
    }

    public void goMap(View view) {
        Intent intent3 = new Intent(ServiciosActivity.this, MapsServiciosActivity.class);
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
                    HotelesFragment tab1 = new HotelesFragment();
                    return tab1;
                case 1:
                    BaresFragment tab2 = new BaresFragment();
                    return tab2;
                case 2:
                    TurismoFragment tab3 = new TurismoFragment();
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
