package com.example.walkandeco;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class BroadcastService extends Service {
    private String TAG = "BroadcastService";
    public static final String COUNTDOWN_BR = "com.example.backgoundtimercount";
    Intent intent = new Intent(COUNTDOWN_BR);



    @Override
    public void onCreate() {
        super.onCreate();


        Log.i(TAG, "온크리에이트 시작하는 부분!!!!!!!!!!!!!!");
//        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE); //두번째로 쉐어드 만든곳
//        Log.i(TAG, "서비스 쉐어드부분!!!!!!");
//        int second = sharedPreferences.getInt("time", 0);
//        SharedPreferences.Editor editor1 = sharedPreferences.edit();
//        editor1.putInt("time", second);
//        editor1.apply();
        Log.i(TAG, "밀리스 쉐어드 부분!!!!!!!!!!");



        sendBroadcast(intent);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("서비스", "디스토리 실행");

    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

