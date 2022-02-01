package com.example.walkandeco;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class a_Dialog extends Activity {
    private EditText title, cotent;
    //버튼은 왜 변수로 설정을 하지
    private Button Ok_bnt, Cancel_bnt ,del_bnt;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        savedInstanceState-받아온 데이터를 저장을 하는것
        //왜 super로 관리를 하는거지?
//        super-부모클래스의 생성자를 호출할때 사용
//                왜 생성자를 호출할까?
//                생성자는 객체를 특성을 바꿔주기 위해서 만든것인데

        super.onCreate(savedInstanceState);
        //받아온 데이터를 바꿔주기 위해서 수퍼로 받다가 크리에이트에서 관리를 시작하게 만들기 위해서 만듬
        setContentView(R.layout.activity_dialog);
        title = (EditText) findViewById(R.id.word_dialog);
        cotent = (EditText) findViewById(R.id.mean_dialog);
        Ok_bnt = (Button) findViewById(R.id.OkButton_dialog);
        Cancel_bnt = (Button) findViewById(R.id.CancelButton_dialog);
        Intent intent = getIntent();
//        Intent intent = getIntent();-인텐트 가져오기 그래서 다이얼로그로 할때 이미지를 가져올수가 있다
        String data1 = intent.getStringExtra("");
//        getStringExtra(넣을 내용 이름 정하기)-넣지 않아도 되지만 ""<-는 넣어줘야 함
        title.setText(data1);
        //setText는 화면 값을 넣을수 있도록 한다
        String data2 = intent.getStringExtra("");
        cotent.setText(data2);
        int count = intent.getIntExtra("count", 0);


//set-셋팅한다
//        on-위에
//                click-누른다
//                        listener-듣는사람
//셋팅한다 위에 버튼을 클릭을 해서 듣고싶어하는 사람을 위해서 왜 듣다라고 표현???
//        setOnClickListener-정확히는 데이터값을 넘어가게 하기 위해서

//        리스너-개발자
//                클릭한사람도 -개발자
//                        -세팅하는 사람-개발자
//            정보를 들은것을 개발자가 클릭을 함으로 데이터 값을 세팅을 한다
        Ok_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String word_data = title.getText().toString();
                String mean_data = cotent.getText().toString();
                Intent intent_result = new Intent();
                intent_result.putExtra("revise_word", word_data);
                intent_result.putExtra("revise_mean", mean_data);
                intent_result.putExtra("count" , count );
                setResult(1355, intent_result);
                finish();
            }

        });
        Cancel_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}



//리사이클러뷰 순서
//1.메인
//2.아이템
//3.어뎁터
