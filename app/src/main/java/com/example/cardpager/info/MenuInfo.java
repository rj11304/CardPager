package com.example.cardpager.info;

/**
 * Created by admin on 2017/3/23.
 */
public class MenuInfo {

    public String type;
    public int icon;
    public MenuEvent event;

    public interface MenuEvent{
        public void open(MenuInfo info);
    }
}
