package com.example.cardpager.manager;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.cardpager.Fragment.CardBaseFragment;
import com.example.cardpager.R;

/**
 * Created by admin on 2017/3/23.
 * 卡片布局管理器
 */
public class CardPagertManager {

    private CardBaseFragment showFragment;
    private FragmentManager fragmentManager;
    private CallBack callBack;

    public CardPagertManager(@NonNull FragmentManager manager){
        this.fragmentManager = manager;
    }

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }

    public void addFragment(CardBaseFragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.out_fragment,R.anim.in_fragment);
        if(showFragment != null){
            transaction.hide(showFragment);
        }
        transaction.add(R.id.fragment,fragment,fragment.getInfo().UUID);
        transaction.commit();
        showFragment = fragment;
        if(callBack != null) callBack.showFragment();
    }

    public void openFragment(CardBaseFragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(showFragment != null){
            transaction.hide(showFragment);
        }
        transaction.show(fragment);
        transaction.commit();
        showFragment = fragment;
        if(callBack != null) callBack.showFragment();
    }

    public void removeFragment(CardBaseFragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void backHome(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(showFragment != null){
            transaction.hide(showFragment);
        }
        transaction.commit();
        showFragment = null;
        if(callBack != null) callBack.backhome();
    }

    public boolean goBack(){
        if(showFragment != null){
            if(!showFragment.backPress()){
                backHome();
            }
            return false;
        }else{
            return true;
        }
    }

    public interface CallBack{
        public void showFragment();
        public void backhome();
    }

}
