package com.example.cardpager;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;

import com.example.cardpager.Fragment.CardBaseFragment;
import com.example.cardpager.Fragment.GridFragment;
import com.example.cardpager.Fragment.ListFragment;
import com.example.cardpager.Fragment.ListMenusFragment;
import com.example.cardpager.Fragment.WebFragment;
import com.example.cardpager.adapter.CardRecycleViewAdapter;
import com.example.cardpager.event.CardPagerEvent;
import com.example.cardpager.helper.CardPagerItemTouchHelperCallback;
import com.example.cardpager.helper.DragEvent;
import com.example.cardpager.helper.MeshMenuTouchHelper;
import com.example.cardpager.helper.RecyclerViewItemClickHelper;
import com.example.cardpager.info.CardInfo;
import com.example.cardpager.info.MenuInfo;
import com.example.cardpager.manager.CardPagertManager;
import com.example.cardpager.manager.SimpleMeshLayoutManager;
import com.example.cardpager.widget.MeshMenusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/3/23.
 */
public class CardPageActivity extends FragmentActivity{

    private RecyclerView recyclerView;
    private MeshMenusView meshMenusView;
    private List<CardInfo> infos = new ArrayList<>();
    private CardPagertManager cardPagertManager;
    private Bitmap emptyBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.RGB_565);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardpagerlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        meshMenusView = (MeshMenusView) findViewById(R.id.menus);
        meshMenusView.setManager(new SimpleMeshLayoutManager());
        meshMenusView.getTouchHelper().setCallBack(menusCallBack);
        meshMenusView.setMenuInfos(getMenuInfo());
        recyclerView.setHasFixedSize(true);
        recyclerView.setBackground(WallpaperManager.getInstance(this).getDrawable());
        CardRecycleViewAdapter adapter = new CardRecycleViewAdapter(infos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper.Callback callback = new CardPagerItemTouchHelperCallback(dragEvent);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        RecyclerViewItemClickHelper helper = new RecyclerViewItemClickHelper(itemClickcallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        cardPagertManager = new CardPagertManager(getSupportFragmentManager());//卡片管理器
        cardPagertManager.setCallBack(cardpagerManagerCallBack);
    }

    private List<MenuInfo> getMenuInfo(){
        List<MenuInfo> menuInfos = new ArrayList<>();
        MenuInfo info = new MenuInfo();
        info.type = "web";
        info.icon = R.mipmap.item_1;
        info.event = webMenuEvent;
        menuInfos.add(info);
        info = new MenuInfo();
        info.type = "grid";
        info.icon = R.mipmap.item_2;
        info.event = gridMenuEvent;
        menuInfos.add(info);
        info = new MenuInfo();
        info.type = "list";
        info.icon = R.mipmap.item_3;
        info.event = listMenuEvent;
        menuInfos.add(info);
        info = new MenuInfo();
        info.type = "listMenus";
        info.icon = R.mipmap.item_4;
        info.event = listMenusMenuEvent;
        menuInfos.add(info);
        info = new MenuInfo();
        info.type = "home";
        info.icon = R.mipmap.item_5;
        info.event = homeMenuEvent;
        menuInfos.add(info);
        return menuInfos;
    }

    //菜单激发事件
    private MenuInfo.MenuEvent webMenuEvent = new MenuInfo.MenuEvent() {
        @Override
        public void open(MenuInfo info) {
            CardBaseFragment fragment = new WebFragment();
            CardInfo cardInfo = new CardInfo();
            cardInfo.type = info.type;
            cardInfo.fragment = fragment;
            cardInfo.bitmap = emptyBitmap;
            cardInfo.UUID = UUID.randomUUID().toString();
            infos.add(cardInfo);
            fragment.setCardInfo(cardInfo);
            cardPagertManager.addFragment(fragment);
        }
    };

    private MenuInfo.MenuEvent gridMenuEvent = new MenuInfo.MenuEvent() {
        @Override
        public void open(MenuInfo info) {
            CardBaseFragment fragment = new GridFragment();
            CardInfo cardInfo = new CardInfo();
            cardInfo.type = info.type;
            cardInfo.fragment = fragment;
            cardInfo.bitmap = emptyBitmap;
            cardInfo.UUID = UUID.randomUUID().toString();
            infos.add(cardInfo);
            fragment.setCardInfo(cardInfo);
            cardPagertManager.addFragment(fragment);
        }
    };

    private MenuInfo.MenuEvent listMenuEvent = new MenuInfo.MenuEvent() {
        @Override
        public void open(MenuInfo info) {
            CardBaseFragment fragment = new ListFragment();
            CardInfo cardInfo = new CardInfo();
            cardInfo.type = info.type;
            cardInfo.fragment = fragment;
            cardInfo.bitmap = emptyBitmap;
            cardInfo.UUID = UUID.randomUUID().toString();
            infos.add(cardInfo);
            fragment.setCardInfo(cardInfo);
            cardPagertManager.addFragment(fragment);
        }
    };

    private MenuInfo.MenuEvent listMenusMenuEvent = new MenuInfo.MenuEvent() {
        @Override
        public void open(MenuInfo info) {
            CardBaseFragment fragment = new ListMenusFragment();
            CardInfo cardInfo = new CardInfo();
            cardInfo.type = info.type;
            cardInfo.fragment = fragment;
            cardInfo.bitmap = emptyBitmap;
            cardInfo.UUID = UUID.randomUUID().toString();
            infos.add(cardInfo);
            fragment.setCardInfo(cardInfo);
            cardPagertManager.addFragment(fragment);
        }
    };

    private MenuInfo.MenuEvent homeMenuEvent = new MenuInfo.MenuEvent() {
        @Override
        public void open(MenuInfo info) {
            cardPagertManager.backHome();
        }
    };

    //点击页事件
    private RecyclerViewItemClickHelper.CallBack itemClickcallBack = new RecyclerViewItemClickHelper.CallBack() {
        @Override
        public void onItemClick(RecyclerView parent, RecyclerView.ViewHolder viewHolder, int position) {
            cardPagertManager.openFragment(infos.get(position).fragment);
        }
    };

    //菜单事件回调
    private MeshMenuTouchHelper.SimpleCallBack menusCallBack = new  MeshMenuTouchHelper.SimpleCallBack(){
        @Override
        public boolean onItemClick(View view) {
            MenuInfo info = (MenuInfo)view.getTag();
            info.event.open(info);
            return super.onItemClick(view);
        }

        @Override
        public void onItemSelected(View view) {
            MenuInfo info = (MenuInfo)view.getTag();
            info.event.open(info);
        }
    };

    //页事件回调
    private DragEvent dragEvent = new DragEvent() {
        @Override
        public void onItemSwap(int fromPosition, int toPosition) {
            Collections.swap(infos,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemRemove(int position) {
            CardInfo info = infos.get(position);
            infos.remove(position);
            recyclerView.getAdapter().notifyItemRemoved(position);
            cardPagertManager.removeFragment(info.fragment);
        }
    };

    //fragment事件回调
    private CardPagertManager.CallBack cardpagerManagerCallBack = new CardPagertManager.CallBack() {
        @Override
        public void showFragment() {
            meshMenusView.setVisibility(View.GONE);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void backhome() {
            meshMenusView.setVisibility(View.VISIBLE);
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshCardView(CardPagerEvent event){
        int position = serch(event.UUID);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(position);
    }

    private int serch(String UUID){
        int count = infos.size();
        for(int i = 0;i < count;i++){
            CardInfo info = infos.get(i);
            if(info.UUID.equals(UUID)){
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if(!cardPagertManager.goBack()) return false;
        }else if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_DOWN){
            meshMenusView.setVisibility(View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }
}
