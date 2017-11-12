package com.example.zone3.cryptocoinconverter.Model;

import android.support.annotation.DrawableRes;

import com.example.zone3.cryptocoinconverter.R;

import java.util.ArrayList;

/**
 * Created by Gtwatt on 10/27/17.
 */

public enum Currencies {

    EURO("EUR", R.drawable.flagofeu, "European Euro", "€"),
    YEN("JPY",R.drawable.flagofjapan,"Japanese yen", "¥"),
    POUND("GBP",R.drawable.flagofunitedkingdom, "Pound Sterling", "£"),
    SWISS("CHF",R.drawable.flagofswitzerland, "Swiss Franc", "CHF"),
    NAIRA("NGR",R.drawable.flagofnigeria, "Nigerian Naira", "₦"),
    ADOLLAR("AUD",R.drawable.flagofaustralia, "Australain Dollar", "A$"),
    USD("USD",R.drawable.flagofusa, "US Dollar", "$"),
    HKD("HKD",R.drawable.flagofhong, "Hong Kong Dollar", "$"),
    RUPEE("INR",R.drawable.flagofindia, "Indian rupee", "₹"),
    PESO("MXN",R.drawable.flagofmexico, "Mexican Peso", "₱"),
    YUAN("CNY",R.drawable.flagofchina, "Chinese Yuan", "¥"),
    CAD("CAD",R.drawable.flagofcanada,"Canadian Dollar", "$"),
    KRONA("SEK",R.drawable.flagofsweden, "Swedish Krona", "kr"),
    KRONE("NOK",R.drawable.flagofnorway,"Danish Krone", "kr"),
    WON("KRW",R.drawable.flagofkoreasouth,"South Korean Won", "₩"),
    LIRA("TRY",R.drawable.flagofturkey,"Turkish Lira", "₤"),
    REAL("BRL",R.drawable.flagofbrazil,"Brazilian real", "R$"),
    BAHT("THB", R.drawable.flagofthailand,"Turkish Baht", "฿"),
    ZLOTY("PLN",R.drawable.flagofpoland, "Polish Zloty", "zł"),
    RUBLE("RUB",R.drawable.flagofrussia, "Russian Ruble", "руб");



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String code;
    private int imageUrl;
    private String name;
    private String sign;




    Currencies(String code, @DrawableRes int imageUrl, String name, String sign){
        this.imageUrl = imageUrl;
        this.code = code;
        this.name = name;
        this.sign = sign;

    }

    public String getSign() {
        return sign;
    }


    public String getCode() {
        return code;
    }



    public int getImageUrl() {
        return imageUrl;
    }


}
