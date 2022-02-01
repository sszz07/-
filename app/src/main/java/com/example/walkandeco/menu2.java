package com.example.walkandeco;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class menu2 extends AppCompatActivity {
    TextView dateView;
    TextView cityView1;
    TextView weatherView1;
    TextView weatherView;
    TextView tempView;
    TextView good, good1;
    static RequestQueue requestQueue;

    //프레임레이아웃을 왜 객체를 만들었을까?
    //뷰를 여러개 추가하게 하면서 나머지 뷰들이 가장 마지막에 추가하는 뷰이다
    //프레임 레이아웃으로 만든것은 객체들이 계속 바뀌는 애니메이션으로 만들었기에 컨테이너 즉 계속 바뀌는 애니메이션의 박스이다 그래서 프레임레이아웃으로 하고 컨테이너 박스를 아이템으로 정하였다
    FrameLayout container;

    //애니메이션의 인아웃을 동작하기 위한 변수설정 변수를 만들어 줬다는 것은 변수명 안에 어떠한 값이 들어가있다는 뜻인데 아직 정확하게 모르겠다
    Animation translateIn;
    Animation translateOut;

    //커스텀아이템뷰의 클래스를 왜 만들었을까?
    Itemview view;
    Itemview view2;
    Itemview view3;
    Itemview view4;


    //왜 셀렉트를 1로 정했을까?

    int selected = 1;

    //불리언 값으로 런닝으로 변수명으로 하고 펄스로 했을까?
    boolean running = false;

    //핸들러의 정확한 역할이 무엇일까?

    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();


        Button e = (Button) findViewById(R.id.bunri);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu2.this, youtube.class);
                startActivity(intent);
            }
        });


        Button v = (Button) findViewById(R.id.tip);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu2.this, a_memo.class);
                startActivity(intent);
            }
        });


        Button n = (Button) findViewById(R.id.plug);
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(menu2.this, c_walk.class);
                startActivity(intent);
            }
        });


        Button a = (Button) findViewById(R.id.record);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu2.this, c_Record.class);
                startActivity(intent);
            }
        });


        tempView = findViewById(R.id.tempView);
        weatherView = findViewById(R.id.weatherView);


        //volley를 쓸 때 큐가 비어있으면 새로운 큐 생성하기
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=b293b678306247cdab233927bf3cb15a";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {

                    //System의 현재 시간(년,월,일,시,분,초까지)가져오고 Date로 객체화함


                    //년, 월, 일 형식으로. 시,분,초 형식으로 객체화하여 String에 형식대로 넣음


                    //getDate에 개행을 포함한 형식들을 넣은 후 dateView에 text설정

                    //api로 받은 파일 jsonobject로 새로운 객체 선언
                    JSONObject jsonObject = new JSONObject(response);


                    //도시 키값 받기


                    //날씨 키값 받기
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);
                    String weather = weatherObj.getString("description");

                    weatherView.setText(weather);

                    //기온 키값 받기
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));

                    //기온 받고 켈빈 온도를 섭씨 온도로 변경
                    double tempDo = (Math.round((tempK.getDouble("temp") - 273.15) * 100) / 100.0);
                    tempView.setText(tempDo + "°C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);


        good = findViewById(R.id.good);
        good1 = findViewById(R.id.good1);
        cityView1 = findViewById(R.id.cityView1);
        weatherView1 = findViewById(R.id.weatherView1);
//        미세먼지 api
//        volley를 쓸 때 큐가 비어있으면 새로운 큐 생성하기
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        String url1 = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=wRf0p7kHdRYFLu%2BAqCGGgkb3s4pivRhsYBIXGb7ACUbB4X%2BM52i5wFF7SXhVoa0Ti5B2%2BeW0pyOHMHh9daYvAw%3D%3D&returnType=json&numOfRows=100&pageNo=1&stationName=%EB%8F%99%EC%9E%91%EA%B5%AC&dataTerm=DAILY&ver=1.0";

        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String Response1) {

                try {

                    //System의 현재 시간(년,월,일,시,분,초까지)가져오고 Date로 객체화함


                    //년, 월, 일 형식으로. 시,분,초 형식으로 객체화하여 String에 형식대로 넣음


                    //getDate에 개행을 포함한 형식들을 넣은 후 dateView에 text설정

//                    {"A":{"B":{"C":160,"D":[{
                    JSONObject jsonObject = new JSONObject(Response1);
                    Log.e("jsonObject", String.valueOf(jsonObject));
                    JSONObject res = (JSONObject) jsonObject.get("response");
                    Log.e("jsonObject", String.valueOf(res));
                    JSONObject bo = (JSONObject) res.get("body");
                    Log.e("jsonObject", String.valueOf(bo));
                    JSONArray dustarray = bo.getJSONArray("items");
                    Log.e("jsonObject", String.valueOf(dustarray));



                    //도시 키값 받기
//                    JSONArray dustarray = jsonObject.getJSONArray("items");
//                    Log.e("jsonObject", String.valueOf(dustarray));

                    JSONObject cityObj = dustarray.getJSONObject(0);
                    double city = cityObj.getDouble("pm10Value");
                    if (city <= 30) {
                        good.setText("좋음");
                    } else if (city < 30 || city >= 80) {
                        good.setText("보통");
                    } else if (city < 81 || city >= 150) {
                        good.setText("나쁨");
                    } else if (city > 151) {
                        good.setText("매우 나쁨");
                    }
                    cityView1.setText("" + city);


                    JSONObject choObj = dustarray.getJSONObject(0);
                    Log.e("초미세먼지", String.valueOf(choObj));
                    double cho = choObj.getDouble("pm25Value");
                    Log.e("초미세먼지 나오는지", Double.toString(cho));
                    if (cho <= 15) {
                        good1.setText("좋음");
                    } else if (cho < 16 || cho >= 35) {
                        good1.setText("보통");
                    } else if (cho < 36 || cho >= 75) {
                        good1.setText("나쁨");
                    } else if (cho > 75) {
                        good1.setText("매우 나쁨");
                    }

                    weatherView1.setText("" + cho);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request1.setShouldCache(false);
        requestQueue.add(request1);


        container = findViewById(R.id.container);

        view = new Itemview(this);
        view.setName("탄소 줄이는법");
        view.setMobile("샤워를 1분씩 줄여도");
//        view.setAddress("탄소 7kg");


        //에드뷰의 정확한 메소드를 알기
        container.addView(view);

        view2 = new Itemview(this);
        view2.setName("탄소 줄이는법");
        view2.setMobile("탄소는 7kg 줄어듭니다");
//        view2.setAddress("승요차 2시간");
        container.addView(view2);

        //애니메이션 인아웃 을 넣는 메소드
        translateIn = AnimationUtils.loadAnimation(this, R.anim.translate_in);
        translateOut = AnimationUtils.loadAnimation(this, R.anim.translate_out);

        //애니메이션 스레드를 만들었음
        AnimationThread thread = new AnimationThread();
        thread.start();


    }

    class AnimationThread extends Thread {
        public void run() {
            running = true;
            while (running) {
                handler.post(new Runnable() {
                    public void run() {
                        if (selected == 0) {
                            view.startAnimation(translateIn);
                            view2.startAnimation(translateOut);
                        } else if (selected == 1) {
                            view.startAnimation(translateOut);
                            view2.startAnimation(translateIn);
                        }
                    }
                });

                selected += 1;
                if (selected > 1) {
                    selected = 0;
                }

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        }
    }
}


