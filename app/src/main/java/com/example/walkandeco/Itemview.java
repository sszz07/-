package com.example.walkandeco;



import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//3333333333333333
public class Itemview extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;


    public Itemview(Context context) {
        super(context);

        init(context);
    }

    public Itemview(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        //인플레이터는 엑스엠엘을 객체로 만든다는것인데
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_customer_item_view, this, true);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

    }

    //이름,모바일,주소를 셋에다가 모든 값을 넣어둠
    public void setName(String name) {
        textView.setText(name);
    }

    public void setMobile(String mobile) {
        textView2.setText(mobile);
    }

    public void setAddress(String address) {
        textView3.setText(address);
    }


}
