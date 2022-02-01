package com.example.walkandeco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class a_memo extends AppCompatActivity {
    //    AppCompatActivity-안드로이드 하위 버전을 지원하는 액티비티
    //쓰는 이유는 api를 받아올때 항상 상위버전으로 지원을 해주는것이 아니다
    private ArrayList<a_item> memo;
    private a_Adapter aAdpater;
    private static int count;
    private String title;
    private String content;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_word);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        memo = new ArrayList<>();
        aAdpater = new a_Adapter(memo);
        recyclerView.setAdapter(aAdpater);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(a_memo.this).setTitle("삭제").setMessage("삭제하시겠습니까").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                memo.remove(viewHolder.getLayoutPosition());
                                aAdpater.notifyItemRemoved(viewHolder.getLayoutPosition());
                                Log.d("삭제", viewHolder.getPosition() + "");
                            }
                        }
                ).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog msgDlg = msgBuilder.create();
                msgDlg.show();


            }
        };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("클릭이벤트", "아이템 터치 리스너 온클릭" + position);
            }


            @Override
            public void onLongClick(View view, int position) {  //수정액티비티 다이얼로그
                Intent intent = new Intent(a_memo.this, a_Dialog.class);
                intent.putExtra("제목", memo.get(position).getTitle());
                intent.putExtra("내용", memo.get(position).getContent());
                intent.putExtra("count", position);
                startActivityForResult(intent, 1107);
            }
        }));





        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        Button b = (Button) findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(a_memo.this, a_Dialog.class);
                startActivityForResult(intent, 1);
                Log.d("클릭이벤트", "추가하기");
            }
        });


//        try-catch 문-예외처리
//        sharedPreferences-저장관리 하는것
//        sharedPreferences-string in boolean으로 저장이 안됨
//        MODE_PRIVATE-값을 현재 어플에만 저장을 하겠다
        try {
            sharedPreferences = getSharedPreferences("메모장", MODE_PRIVATE);
            String JsonArrayData = sharedPreferences.getString("메모", "");
            JSONArray ja = new JSONArray(JsonArrayData);


            for (int i = 0; i < ja.length(); i++) {
                JSONObject order = ja.getJSONObject(i);
                String title = order.getString("word");
                String content = order.getString("mean");
                a_item data = new a_item(title + "", content + "");
                memo.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }








    private static final String TAG = "클래스명";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent_result) {
        super.onActivityResult(requestCode, resultCode, intent_result);

        if (requestCode == 1) {
            if (resultCode != 1355) {
                return;
            }
            String get_key = intent_result.getExtras().getString("key");
            title = intent_result.getExtras().getString("revise_word");
            content = intent_result.getExtras().getString("revise_mean");
            a_item data = new a_item(title + "", content + "");
            memo.add(data);
            aAdpater.notifyDataSetChanged();
        } else if (requestCode == 1107) {
            if (resultCode != 1355) {
                return;
            }
            title = intent_result.getExtras().getString("revise_word");
            content = intent_result.getExtras().getString("revise_mean");
            count = intent_result.getIntExtra("count", count);
            a_item data = new a_item(title + "", content + "");
            memo.set(count, data);
            aAdpater.notifyDataSetChanged();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {


        super.onResume();


    }

    public interface ClickListener {


        void onClick(View view, int position);

        void onLongClick(View view, int position);


    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private a_memo.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final a_memo.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        try {
//            sharedPreferences = getSharedPreferences("메모장", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            JSONArray jArray = new JSONArray();//배열이 필요할때
//            for (int i = 0; i < memo.size(); i++)//배열
//            {
//                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
//                sObject.put("word", memo.get(i).getTitle());
//                sObject.put("mean", memo.get(i).getContent());
//                jArray.put(sObject);
//
//            }
//            editor.putString("메모", String.valueOf(jArray));
//            editor.apply();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            sharedPreferences = getSharedPreferences("메모장", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < memo.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("word", memo.get(i).getTitle());
                sObject.put("mean", memo.get(i).getContent());
                jArray.put(sObject);

            }
            editor.putString("메모", String.valueOf(jArray));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}





