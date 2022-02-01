package com.example.walkandeco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class c_Record extends AppCompatActivity {

    ListView listview;
    EditText text2;
    TextView text1, text, text3;
    Button add, modify, delete;
    int tanso;
    //어레이 리스트에서 스트링값으로 받고 어레이라는 이름으로 데이터가 담겨져 있다
    //어레이어댑터는 스트링형태로만 담을수가 있다
    ArrayAdapter<String> adapter;
    ArrayList<String> array = new ArrayList<>();

    long mNow;
    Date mDate;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    ImageView imageView;
    Button button;

    // ArrayList -> Json으로 변환
    //어레이리스트를 제이슨형태로 담겠다는 값
    private static final String SETTINGS_PLAYER_JSON = "플로깅";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crecord);

        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        add = findViewById(R.id.add);
        modify = findViewById(R.id.modify);
        delete = findViewById(R.id.delete);
        imageView = (ImageView) findViewById(R.id.image);


        //어레이라는 변수명에 어레이리스트의 값이 어레이라는 변수명에 담겨져 있다
        array = new ArrayList<>();

        array = getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);

        //파라미터로 어레이가 들어가 있는데 어레이 어댑터에 어레이라는 데이터가 들어가게 된다 다음은?
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, array);

        listview = findViewById(R.id.View);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setAdapter(adapter);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 1);
//            }
//        });

        Intent intent = getIntent();
        int i = intent.getIntExtra("step", 0);
        text.setText(String.valueOf(i) + "걸음");

        if (i == 0) {
            text3.setText("탄소절약:0");
        }
        if (i >= 1 && i <= 1000) {
            tanso = 60;
            Log.e("저장하는곳 걸음수 확인", "" + i);
            text3.setText("탄소절약:60");
        }
        if (i > 1000 && i <= 2000) {
            text3.setText("탄소절약:70");
        }
        if (i > 2001 && i <= 3000) {
            text3.setText("탄소절약:80");
        }
        if (i > 3001 && i <= 4000) {
            text3.setText("탄소절약:90");
        }
        if (i > 4001 && i <= 5000) {
            text3.setText("탄소절약:100");
        }
        if (i > 5001 && i <= 6000) {
            text3.setText("탄소절약:110");
        }
        if (i > 6001 && i <= 7000) {
            text3.setText("탄소절약:120");
        }
        if (i > 7001 && i <= 8000) {
            text3.setText("탄소절약:130");
        }
        if (i > 8001 && i <= 9000) {
            text3.setText("탄소절약:140");
        }
        if (i > 9001 && i <= 10000) {
            text3.setText("탄소절약:150");
        } if ( i > 10000) {
            text3.setText("탄소절약:160");
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add:
                        text1.setText(getTime());
                        break;
                    default:
                        break;
                }

//                Intent intent = getIntent();
//                int i = intent.getIntExtra("step", 0);
//                text.setText(String.valueOf(i));
//
//                if(i==0){
//                    text3.setText("0");
//                }
//                if (i >= 1 && i <= 10) {
//                    tanso = 60;
//                    Log.e("저장하는곳 걸음수 확인", "걸음" + i);
//                    text3.setText("60");
//                }
//                if (i > 10 && i <= 20) {
//                    text3.setText("70");
//                }
                array.add(text1.getText() + "           걸음수:" + text.getText() + "           " + text3.getText() + "g");
                adapter.notifyDataSetChanged();

                text1.setText("");
                text.setText("");
                text3.setText("");
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check, count = adapter.getCount();
                if (count > 0) {
                    check = listview.getCheckedItemPosition();
                    if (check > -1 && check < count) {
                        array.remove(check);
                        listview.clearChoices();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }


    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    private ArrayList getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }


    @Override
    protected void onPause() {
        super.onPause();
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, array);
        Log.d(TAG, "Put json");
    }
}