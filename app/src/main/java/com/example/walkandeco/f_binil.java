package com.example.walkandeco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class f_binil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binil);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("생명주기","onstart()호출 비닐");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("생명주기","onStop()호출 비닐");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("생명주기","ondestory()호출 비닐");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("생명주기","onpasue()호출 비닐");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("생명주기","onresume()호출 비닐");
    }
}