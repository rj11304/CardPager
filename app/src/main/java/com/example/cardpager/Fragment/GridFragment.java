package com.example.cardpager.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cardpager.R;
import com.example.cardpager.adapter.GridRecyclerViewAdapter;
import com.example.cardpager.helper.DragEvent;
import com.example.cardpager.helper.RecyclerViewItemClickHelper;
import com.example.cardpager.helper.SimpleItemTouchHelperCallback;
import com.example.cardpager.info.GridItemInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2017/3/23.
 * 便签样式Fragment
 */
public class GridFragment extends CardBaseFragment {

    private RecyclerView recyclerView;
    private List<GridItemInfo> strList = new ArrayList<GridItemInfo>();
    private ItemTouchHelper itemTouchHelper;
    private int[] icons = new int[]{R.mipmap.note_blue,R.mipmap.note_green,R.mipmap.note_pink,R.mipmap.note_yellow};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(inflater.getContext());
        recyclerView.setBackgroundResource(R.mipmap.notepad_background);
        recyclerView.setHasFixedSize(true);
        initData();
        GridLayoutManager layoutManager = new GridLayoutManager(inflater.getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        GridRecyclerViewAdapter adapter = new GridRecyclerViewAdapter(strList);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(event);
        itemTouchHelper = new ItemTouchHelper(callback);
        RecyclerViewItemClickHelper helper = new RecyclerViewItemClickHelper(itemClickcallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private void initData(){
        GridItemInfo info = new GridItemInfo();
        info.icon = R.mipmap.note_pad;
        info.text = "";
        strList.add(info);
        for(int i = 1;i < 22;i++){
            info = new GridItemInfo();
            info.icon = icons[i%4];
            info.text = "hello, I am "+i;
            strList.add(info);
        }
    }

    //拖动事件
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

    //点击Item事件回调
    private RecyclerViewItemClickHelper.CallBack itemClickcallBack = new RecyclerViewItemClickHelper.CallBack() {
        @Override
        public void onItemClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position) {
            Toast.makeText(parent.getContext(),strList.get(position).text,Toast.LENGTH_LONG).show();
        }
    };
}
