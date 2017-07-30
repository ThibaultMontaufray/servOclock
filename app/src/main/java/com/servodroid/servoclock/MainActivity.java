package com.servodroid.servoclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String SELECTED_MENU = "selectedmenu";

    public Intent _intent;
    private Fragment _fragment;
    private Fragment _fragmentTime;
    private Fragment _fragmentChrono;
    private Fragment _fragmentAlarm;
    private FragmentManager _fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_alarm:
                    _fragment = _fragmentAlarm;
                    break;
                case R.id.navigation_chrono:
                    _fragment = _fragmentChrono;
                    break;
                case R.id.navigation_time:
                    _fragment = _fragmentTime;
                    break;
            }
            final FragmentTransaction transaction = _fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, _fragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        _fragmentManager = getSupportFragmentManager();
        _fragmentTime = new TimeFragment();
        _fragmentAlarm = new AlarmFragment();
        _fragmentChrono = new ChronoFragment();

        _intent = getIntent();
        String requestedMenu = _intent.getStringExtra(SELECTED_MENU);

        if (requestedMenu != null) {
            switch (requestedMenu) {
                case "ALARM":
                    navigation.setSelectedItemId(R.id.navigation_alarm);
                    break;
                case "CHRONO":
                    navigation.setSelectedItemId(R.id.navigation_chrono);
                    break;
                default:
                    navigation.setSelectedItemId(R.id.navigation_time);
                    break;
            }
        }
        else {
            navigation.setSelectedItemId(R.id.navigation_time);
        }
    }
}
