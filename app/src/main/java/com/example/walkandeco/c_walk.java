package com.example.walkandeco;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class c_walk extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    Button resetButton;
    String TAG = "Main";
    public int second = 0;
    static boolean running;
    // 현재 걸음 수
    int currentSteps;
    int tanso;
    TextView timer;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);


        Log.e("이프문 실행 보기 위에꺼", "" + running);
        Intent intent = new Intent(this, BroadcastService.class);
        Log.i(TAG, "인텐트 전달되는 부분!!!!!!!!!!" + intent.toString());
        startService(intent);
        startTimer();
        Log.i(TAG, "메인 온크리에이트 시작하는 부분!!!!!!!!!!!!!!!!!!!!");

        Log.e("이프문 실행 보기 아래꺼", "" + running);


        Button ef = (Button) findViewById(R.id.record);
        stepCountView = findViewById(R.id.stepCountView);


        ef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), c_Record.class);
                intent.putExtra("step", currentSteps);
                startActivity(intent);
            }
        });


        Button f = (Button) findViewById(R.id.stop);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;

                Log.e("플래그", "사용됨");

            }
        });

        Button e = (Button) findViewById(R.id.start);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;

                Log.e("플래그11111111", "사용됨");

            }
        });


        resetButton = findViewById(R.id.resetButton);
        // 활동 퍼미션 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        // 리셋 버튼 추가 - 리셋 기능
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));
                running = false;
                second = 0;
            }
        });


        SharedPreferences pref = getSharedPreferences("time", MODE_PRIVATE);
        second = pref.getInt("time1", 0);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", currentSteps);
        currentSteps = sharedPreferences.getInt("stepCount", 0);
        stepCountView.setText(String.valueOf(currentSteps));



    }


//    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //Update GUI
//            startTimer(intent);
//            Log.e("브로드서비스 거치나?", "");
//
//        }
//    };


    public void onStart() {
        super.onStart();
        if (stepCountSensor != null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);


        }
    }


    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            if (event.values[0] == 1.0f) {
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));

            }

        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void startTimer() {

        TextView timer = findViewById(R.id.timer);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hrs = second / 3600;
                int mins = (second % 3600) / 60;
                int sec = second % 60;
                String time = String.format("%02d:%02d:%02d", hrs, mins, sec);
                timer.setText(time);

                if (running) {
                    second++;
                }

                handler.postDelayed(this, 1000);

//                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE); //여기가 먼저 쉐어드 만듬
//                Log.i(TAG, "메인에서의 쉐어드 부분 저장11111");
//                sharedPreferences.edit().putInt("time", second).apply();
//                Log.i(TAG, "메인에서의 쉐어드 부분 저장222222");

            }

        });

    }


//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
////        super.onBackPressed();
//        Intent intent = new Intent(c_walk.this, menu2.class);
//        onBackPressed();
//        startActivity(intent);
//    }


    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(broadcastReceiver);
        Log.e("포즈", "홈버튼을 눌렀을때");
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", currentSteps);
        editor.apply();
//        running = true;


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", currentSteps);
        editor.apply();


        Log.e("스탑:", "홈버튼을 눌렀을때");
    }


    @Override
    //화면에 들어가자마자 실행이 된느구간
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", currentSteps);
        currentSteps = sharedPreferences.getInt("stepCount", 0);


//        registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.e("온리즘", "홈에서 다시들어갈때");

    }


    @Override
    protected void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "메인 디스토리");
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("time", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("time1", second);
        editor.apply();


    }
}



