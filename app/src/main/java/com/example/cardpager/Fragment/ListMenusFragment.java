package com.example.cardpager.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardpager.R;
import com.example.cardpager.adapter.ListMenuRecycleViewAdapter;
import com.example.cardpager.helper.ListItemTouchHelper;
import com.example.cardpager.helper.ListItemTouchHelperCallBack;
import com.example.cardpager.viewholder.ListItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 * 含Item菜单的列表Fragment
 */
public class ListMenusFragment extends CardBaseFragment {

    private RecyclerView recyclerView;
    private List<String> strList = new ArrayList<String>();
    private ListItemTouchHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listmenu_layout,null);
        initData();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        ListMenuRecycleViewAdapter adapter = new ListMenuRecycleViewAdapter(view.getContext(),strList);
        adapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);
        helper = new ListItemTouchHelper(new ListItemTouchHelperCallBack());
        helper.attRecyclerView(recyclerView);
        return view;
    }

    private void initData(){
        for(int i = 0;i < 50;i++){
            strList.add("Come on baby : "+i);
        }
    }

    //Item菜单点击事件监听
    private ListItemViewHolder.OnItemClickListener itemClickListener = new ListItemViewHolder.OnItemClickListener() {
        @Override
        public void onItemClickListener(RecyclerView.ViewHolder holder, View view) {
            switch(view.getId()){
                case R.id.text:
                    TextView textView = (TextView) view;
                    Toast.makeText(view.getContext(),textView.getText().toString(),Toast.LENGTH_LONG).show();
                    break;
                case R.id.cancel:
                    ListItemViewHolder myViewHolder = (ListItemViewHolder) holder;
                    myViewHolder.reset();
                    break;
                case R.id.delete:
                    strList.remove(holder.getAdapterPosition());
                    recyclerView.getAdapter().notifyItemRemoved(holder.getAdapterPosition());
                    break;
            }
        }
    };
}
