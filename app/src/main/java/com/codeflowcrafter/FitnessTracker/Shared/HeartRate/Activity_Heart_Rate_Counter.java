package com.codeflowcrafter.FitnessTracker.Shared.HeartRate;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codeflowcrafter.FitnessTracker.R;
import com.codeflowcrafter.FitnessTracker.Shared.HeartRateService;

import static com.codeflowcrafter.FitnessTracker.Services.ActivityService.GetConcreteView;

public class Activity_Heart_Rate_Counter extends AppCompatActivity {
    public static final String RESULT_HEART_RATE = "Resulting Heart Rate";
    public static final int REQUEST_CODE = 143;

    private int _currentHeartRate = 0;
    private Boolean _counting = false;

    private View _view;
    private TextView _txtHeartRatePerMinute;
    private TextView _txtHeartRate;
    private TextView _txtSeconds;
    private Button _btnStart_Increment;
    private CountDownTimer _timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _view = findViewById(android.R.id.content);
        _txtHeartRatePerMinute = GetConcreteView(TextView.class, _view, R.id.txtHeartRatePerMinute);
        _txtHeartRate = GetConcreteView(TextView.class, _view, R.id.txtHeartRate);
        _txtSeconds = GetConcreteView(TextView.class, _view, R.id.txtSeconds);
        _btnStart_Increment = GetConcreteView(Button.class, _view, R.id.btnStart_Increment);

        SetViewHandlers();
    }

    public void SetViewHandlers()
    {
        View view = findViewById(android.R.id.content);

        GetConcreteView(Button.class, view, R.id.btnStart_Increment)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Increment();
                    }
                });
        GetConcreteView(Button.class, view, R.id.btnReset)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Reset();
                    }
                });
        GetConcreteView(Button.class, view, R.id.btnFinish)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Finish();
                    }
                });
        _timer = new CountDownTimer(
                HeartRateService.COUNTER_LIMIT_SECONDS * 1000,
                1000
        ) {
            @Override
            public void onTick(long millisUntilFinished) {
                _txtSeconds.setText(String.valueOf(
                        millisUntilFinished / 1000
                ));
            }

            @Override
            public void onFinish() {
                _txtSeconds.setText("Done");
                _btnStart_Increment.setEnabled(false);
            }
        };
    }

    private void Finish()
    {
        if(_counting) Stop();

        Intent resultIntent = new Intent();

        resultIntent.putExtra(
                RESULT_HEART_RATE,
                GetHeartRatePerMinute());

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void Increment(){
        if(!_counting) _timer.start();

        _counting = true;
        _currentHeartRate += 1;

        UpdateViews();
    }

    private void Stop(){
        if(_counting) _timer.cancel();

        _counting = false;
    }

    private void Reset(){
        _timer.cancel();
        _currentHeartRate = 0;
        _counting = false;

        UpdateViews();
        _txtSeconds.setText(String.valueOf(HeartRateService.COUNTER_LIMIT_SECONDS));
        _btnStart_Increment.setEnabled(true);
    }

    private void UpdateViews()
    {
        _txtHeartRate.setText(String.valueOf(_currentHeartRate));
        _txtHeartRatePerMinute.setText(
                String.valueOf(GetHeartRatePerMinute())
        );
    }

    private int GetHeartRatePerMinute()
    {
        return _currentHeartRate * HeartRateService.HEART_RATE_PER_SECOND_MULTIPLIER;
    }
}
