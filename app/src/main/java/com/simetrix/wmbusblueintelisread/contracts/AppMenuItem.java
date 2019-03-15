package com.simetrix.wmbusblueintelisread.contracts;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class AppMenuItem {
    public Drawable imgIcon;
    public String title;
    public String key;

    public AppMenuItem(){}


    public AppMenuItem(Drawable icon, String title, String key) {
        this.imgIcon = icon;
        this.title = title;
        this.key = key;
    }

    public static ArrayList<AppMenuItem> buildMainHomeItemsList(){
        ArrayList<AppMenuItem> menuList = new ArrayList<>();

        AppMenuItem item = new AppMenuItem();

//        menuList.add(new AppMenuItem())

        return menuList;
    }
}
