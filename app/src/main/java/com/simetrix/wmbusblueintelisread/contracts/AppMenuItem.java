package com.simetrix.wmbusblueintelisread.contracts;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class AppMenuItem {
    public Drawable imgIcon;
    public String title;

    public AppMenuItem(){}

    public AppMenuItem(Drawable imgIcon, String title){
        this.imgIcon = imgIcon;
        this.title = title;
    }

    public static ArrayList<AppMenuItem> buildMainHomeItemsList(){
        ArrayList<AppMenuItem> menuList = new ArrayList<>();

        AppMenuItem item = new AppMenuItem();

//        menuList.add(new AppMenuItem())

        return menuList;
    }
}
