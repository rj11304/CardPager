package com.example.cardpager.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cardpager.adapter.ListRecyclerViewAdapter;
import com.example.cardpager.helper.DragEvent;
import com.example.cardpager.helper.RecyclerViewItemClickHelper;
import com.example.cardpager.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 * 列表Fragment（支持拖动换位，和滑动删除）
 */
public class ListFragment extends CardBaseFragment {

    private RecyclerView recyclerView;
    private List<String> strList = new ArrayList<String>();
    private ItemTouchHelper itemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(inflater.getContext());
        recyclerView.setBackgroundColor(Color.WHITE);
        recyclerView.setHasFixedSize(true);
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ListRecyclerViewAdapter adapter = new ListRecyclerViewAdapter(strList);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(event);
        itemTouchHelper = new ItemTouchHelper(callback);
        RecyclerViewItemClickHelper helper = new RecyclerViewItemClickHelper(itemClickcallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private void initData(){
        for(int i = 0;i < 24;i++){
            strList.add("strList"+i);
        }
    }

    private DragEvent event = new DragEvent() {
        @Override
        public void onItemSwap(int fromPosition, int toPosition) {
            Collections.swap(strList,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemRemove(int position) {
            strList.remove(position);
            recyclerView.getAdapter().notifyItemRemoved(position);
        }
    };

    private RecyclerViewItemClickHelper.CallBack itemClickcallBack = new RecyclerViewItemClickHelper.CallBack() {
        @Override
        public void onItemClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position) {
            Toast.makeText(parent.getContext(),strList.get(position),Toast.LENGTH_LONG).show();
        }
    };
}
