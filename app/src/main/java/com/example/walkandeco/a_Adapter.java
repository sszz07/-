package com.example.walkandeco;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class a_Adapter extends RecyclerView.Adapter<a_Adapter.CustomViewHolder> {//리사이클러뷰 어댑터 상속<뷰홀더 지정>
    private ArrayList<a_item> memo;


    public a_Adapter(ArrayList<a_item> memo) {
        this.memo = memo;
    }

    public void addItem(a_item list) {
        memo.add(list);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//뷰홀더 생성(레이아웃 생성)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(a_Adapter.CustomViewHolder holder, int position) {//뷰홀더가 재활용될때 실행되는 메서드
        holder.title.setText(memo.get(position).getTitle());
        holder.content.setText(memo.get(position).getContent());
    }

    @Override
    public int getItemCount() {//아이템 개수 조회
        return memo.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {//뷰홀더 클래스 생성 , 리사이클러뷰 뷰홀더 상속 ,콘텍스트메뉴(롱클릭에서 메뉴뜨게)구현


        protected TextView title;
        protected TextView content;
        protected View item_view;

        public CustomViewHolder(View itemView) {
            super(itemView);


            item_view = itemView.findViewById(R.id.item_view);
            title = itemView.findViewById(R.id.title1);
            content = itemView.findViewById(R.id.content1);
            itemView.setOnCreateContextMenuListener(this);


        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {//컨텍스트메뉴 생성 , 호출되었을때의 호출되야하는 리스너 등록 , ID (상수)로 어떤 메뉴를 선택했는지 리스너에서 구분하게 해준다

            MenuItem Delete = menu.add(Menu.NONE, 1002, 1, "삭제");

            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            // 컨텍스트메뉴에서 할 동작을 설정


            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case 1002:
                        memo.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), memo.size());
                        break;
                }
                return true;
            }
        };
    }
}




