package com.servodroid.servoclock;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.TimePicker;

public class AlarmTimePicker extends AppCompatActivity {

    private int _hour;
    private int _minute;
    private TimePicker _timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_time_picker);

        Intent intent = getIntent();
        String newTime = intent.getStringExtra(AlarmFragment.NEW_TIME);
        _hour = Integer.parseInt(newTime.split(":")[0]);
        _minute = Integer.parseInt(newTime.split(":")[1]);

        _timePicker = (TimePicker) findViewById(R.id.timePicker);
        _timePicker.setHour(_hour);
        _timePicker.setMinute(_minute);
    }

    public void Save(View view) {
        _hour = _timePicker.getCurrentHour();
        _minute = _timePicker.getCurrentMinute();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AlarmFragment.NEW_TIME, _hour+":"+_minute);
        intent.putExtra(MainActivity.SELECTED_MENU, "ALARM");
        startActivity(intent);
    }
    public void Cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AlarmFragment.NEW_TIME, _hour+":"+_minute);
        intent.putExtra(MainActivity.SELECTED_MENU, "ALARM");
        startActivity(intent);
    }
}
