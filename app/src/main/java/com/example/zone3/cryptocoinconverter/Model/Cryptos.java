package com.example.zone3.cryptocoinconverter.Model;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import com.example.zone3.cryptocoinconverter.R;

import java.util.ArrayList;

/**
 * Created by Gtwatt on 10/31/17.
 */

public enum Cryptos {

    BITCOIN("BTC", R.drawable.bitcoin, "Bitcoin"),
    ETHERIUM("ETH", R.drawable.etherium,"Etherium");


    private String code;
    private int imageID;
    private String name;

    Cryptos(String code, @DrawableRes int imageID, String name){
        this.imageID = imageID;
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getImageUrl() {
        return imageID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
