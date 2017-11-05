package com.example.zone3.cryptocoinconverter.Model;

/**
 * Created by Gtwatt on 10/25/17.
 */

public class CryptoConver {

    private Currencies currency;
    private Cryptos cryptoType;
    private String price;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CryptoConver(Currencies currency, Cryptos cryptoType, String price) {
        this.currency = currency;
        this.cryptoType = cryptoType;
        this.price = price;
    }

    public CryptoConver() {
    }

    public Currencies getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = Currencies.valueOf(currency);
    }

    public Cryptos getCryptoType() {
        return cryptoType;
    }

    public void setCryptoType(String cryptoType) {
        this.cryptoType = Cryptos.valueOf(cryptoType.toUpperCase());
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
