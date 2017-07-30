package com.servodroid.servoclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.servodroid.servoclock.R.drawable.control;

public class AlarmFragment extends Fragment implements View.OnClickListener {

    public static final String NEW_TIME = "newtime";

    private View _view;
    private Button _monday, _tuesday, _wednesday, _thursday, _friday, _saturday, _sunday;
    private List<Boolean> _activedDays;
    private CheckBox _vibrate, _repeat;
    private TextView _time;

    public AlarmFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (_view == null)
        {
            _view = inflater.inflate(R.layout.fragment_alarm, container, false);
            Init();
        }
        String newTime = getActivity().getIntent().getStringExtra(NEW_TIME);
        if (newTime != null){
            _time.setText(newTime);
        }
        return _view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonMonday:
                SwitchButton(0, _monday);
                break;
            case R.id.buttonThuesday:
                SwitchButton(1, _tuesday);
                break;
            case R.id.buttonWednesday:
                SwitchButton(2, _wednesday);
                break;
            case R.id.buttonThursday:
                SwitchButton(3, _thursday);
                break;
            case R.id.buttonFriday:
                SwitchButton(4, _friday);
                break;
            case R.id.buttonSaturday:
                SwitchButton(5, _saturday);
                break;
            case R.id.buttonSunday:
                SwitchButton(6, _sunday);
                break;
            case R.id.checkBoxVibrate:
                break;
            case R.id.checkBoxRepeat:
                SwitchRepeat();
                break;
            case R.id.textViewAlarm:
                ChangeTime();
                break;
        }
    }

    public void ChangeTime() {
        Intent intent = new Intent(_view.getContext() , AlarmTimePicker.class);
        intent.putExtra(NEW_TIME, _time.getText());
        startActivity(intent);
    }

    private void Init() {
        _vibrate = (CheckBox) _view.findViewById(R.id.checkBoxVibrate);
        _repeat = (CheckBox) _view.findViewById(R.id.checkBoxRepeat);
        _time = (TextView) _view.findViewById(R.id.textViewAlarm);

        _vibrate.setOnClickListener(this);
        _repeat.setOnClickListener(this);
        _time.setOnClickListener(this);

        _monday = (Button) _view.findViewById(R.id.buttonMonday);
        _tuesday = (Button) _view.findViewById(R.id.buttonThuesday);
        _wednesday = (Button) _view.findViewById(R.id.buttonWednesday);
        _thursday = (Button) _view.findViewById(R.id.buttonThursday);
        _friday = (Button) _view.findViewById(R.id.buttonFriday);
        _saturday = (Button) _view.findViewById(R.id.buttonSaturday);
        _sunday = (Button) _view.findViewById(R.id.buttonSunday);

        _monday.setOnClickListener(this);
        _tuesday.setOnClickListener(this);
        _wednesday.setOnClickListener(this);
        _thursday.setOnClickListener(this);
        _friday.setOnClickListener(this);
        _saturday.setOnClickListener(this);
        _sunday.setOnClickListener(this);

        _activedDays = new ArrayList<Boolean>();
        _activedDays.add(0, true);
        _activedDays.add(1, true);
        _activedDays.add(2, true);
        _activedDays.add(3, true);
        _activedDays.add(4, true);
        _activedDays.add(5, true);
        _activedDays.add(6, true);
    }

    private void SwitchButton(int id, Button button) {
        _activedDays.set(id, !_activedDays.get(id));
        if (_activedDays.get(id)){
            button.setBackgroundResource(control);
            button.setTextColor(_view.getResources().getColor(R.color.colorPrimaryDark));
        }
        else {
            button.setBackground(null);
            button.setTextColor(_view.getResources().getColor(R.color.Gray));
        }
    }

    private void SwitchRepeat() {
        if (_repeat.isChecked()) {
            _monday.setVisibility(View.VISIBLE);
            _tuesday.setVisibility(View.VISIBLE);
            _wednesday.setVisibility(View.VISIBLE);
            _thursday.setVisibility(View.VISIBLE);
            _friday.setVisibility(View.VISIBLE);
            _saturday.setVisibility(View.VISIBLE);
            _sunday.setVisibility(View.VISIBLE);
        }
        else {
            _monday.setVisibility(View.INVISIBLE);
            _tuesday.setVisibility(View.INVISIBLE);
            _wednesday.setVisibility(View.INVISIBLE);
            _thursday.setVisibility(View.INVISIBLE);
            _friday.setVisibility(View.INVISIBLE);
            _saturday.setVisibility(View.INVISIBLE);
            _sunday.setVisibility(View.INVISIBLE);
        }
    }
}
