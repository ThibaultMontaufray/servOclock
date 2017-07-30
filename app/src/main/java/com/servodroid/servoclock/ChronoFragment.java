package com.servodroid.servoclock;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.method.BaseMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChronoFragment extends Fragment implements View.OnClickListener {

    private View _view;
    private ImageButton _btnStart, _btnStop, _btnPause, _btnTarget, _btnReset;
    private Chronometer _chronometer;
    private TextView _textViewTarget;
    private List<Long> _targetDates;
    private HoloCircularProgressBar _progressBar;

    public ChronoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (_view == null) {
            _view = inflater.inflate(R.layout.fragment_chrono, container, false);
            Init();
        }

        return _view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonStart:
                Start();
                break;
            case R.id.imageButtonPause:
                Pause();
                break;
            case R.id.imageButtonStop:
                Stop();
                break;
            case R.id.imageButtonTarget:
                _targetDates.add(SystemClock.elapsedRealtime() - _chronometer.getBase());
                UpdateTargetList();
                break;
            case R.id.imageButtonReset:
                ResetChrono();
                break;
        }
    }

    private void Init() {
        _btnStart = (ImageButton) _view.findViewById(R.id.imageButtonStart);
        _btnPause = (ImageButton) _view.findViewById(R.id.imageButtonPause);
        _btnStop = (ImageButton) _view.findViewById(R.id.imageButtonStop);
        _btnTarget = (ImageButton) _view.findViewById(R.id.imageButtonTarget);
        _btnReset = (ImageButton) _view.findViewById(R.id.imageButtonReset);
        _progressBar = (HoloCircularProgressBar) _view.findViewById(R.id.holoCircularProgressBarChrono);

        _textViewTarget = (TextView) _view.findViewById(R.id.textViewChrono);
        _textViewTarget.setMovementMethod( new BaseMovementMethod());

        _targetDates = new ArrayList<Long>();

        _btnStart.setOnClickListener(this);
        _btnPause.setOnClickListener(this);
        _btnStop.setOnClickListener(this);
        _btnTarget.setOnClickListener(this);
        _btnReset.setOnClickListener(this);

        _chronometer = (Chronometer) _view.findViewById(R.id.chronometer);
        _chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s = (int)(time - h*3600000- m*60000)/1000 ;
                int y = (int)(time - h*3600000- m*60000 - s*1000) ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                String my;
                if (y > 100) { my = y+""; }
                else if (y > 10) { my = "0"+y+""; }
                else { my = "00"+y+""; }

                //if (h > 0 && m > 0) { cArg.setText(hh+":"+mm+":"+ss+"."+my); }
                //else if (h <= 0 && m > 0) { cArg.setText(mm+":"+ss+"."+my); }
                //else { cArg.setText(ss+"."+my); }

                if (h > 0 && m > 0) { cArg.setText(hh+":"+mm+":"+ss); }
                else if (h <= 0 && m > 0) { cArg.setText(mm+":"+ss); }
                else { cArg.setText(ss); }

                float progress = (s*100) / 60;
                progress = progress / 100;
                _progressBar.setProgress(progress);
            }
        });
        _chronometer.setBase(SystemClock.uptimeMillis());

    }

    private void Start() {
        _chronometer.start();
        _btnStart.setVisibility(View.INVISIBLE);
        _btnStop.setVisibility(View.VISIBLE);
        _btnPause.setVisibility(View.VISIBLE);
        _btnTarget.setVisibility(View.VISIBLE);
        _btnReset.setVisibility(View.INVISIBLE);
    }

    private void Pause() {
        _chronometer.stop();
        _btnStart.setVisibility(View.VISIBLE);
        _btnStop.setVisibility(View.INVISIBLE);
        _btnPause.setVisibility(View.INVISIBLE);
        _btnTarget.setVisibility(View.INVISIBLE);
        _btnReset.setVisibility(View.VISIBLE);
    }

    private void Stop() {
        ResetChrono();
        _btnStart.setVisibility(View.VISIBLE);
        _btnStop.setVisibility(View.INVISIBLE);
        _btnPause.setVisibility(View.INVISIBLE);
        _btnTarget.setVisibility(View.INVISIBLE);
        _btnReset.setVisibility(View.INVISIBLE);
    }

    private void ResetChrono(){
        _chronometer.stop();
        _chronometer.setBase(SystemClock.elapsedRealtime());
        _textViewTarget.setText("");
        _targetDates.clear();
    }

    private  void UpdateTargetList(){
        SimpleDateFormat sdf;
        Integer count = 0;
        Long lastVal = Long.valueOf(0);
        Long diffVal, currentVal, maxVal;
        String finalText = "";

        if (_targetDates.size() > 1) { maxVal = _targetDates.get(_targetDates.size() - 1); }
        else { maxVal = Long.valueOf(0); }

        if (_targetDates.size() < 2 || maxVal < 60000) { sdf = new SimpleDateFormat("ss.SSS"); }
        else if (maxVal < 3600000) { sdf = new SimpleDateFormat("mm ss.SSS"); }
        else { sdf = new SimpleDateFormat("HH mm ss.SSS"); }

        for (Long target : _targetDates) {
            diffVal = (target - lastVal);
            currentVal = target;
            finalText = String.format("#%s   %s   %s\r\n", count, sdf.format(diffVal),  sdf.format(currentVal)) + finalText;
            lastVal = target;
            count ++;
        }
        _textViewTarget.setText(finalText);
    }
}
