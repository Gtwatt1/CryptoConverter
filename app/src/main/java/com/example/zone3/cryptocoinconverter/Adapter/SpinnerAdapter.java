package com.example.zone3.cryptocoinconverter.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zone3.cryptocoinconverter.Model.Cryptos;
import com.example.zone3.cryptocoinconverter.Model.Currencies;
import com.example.zone3.cryptocoinconverter.R;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by Gtwatt on 10/27/17.
 */


public class SpinnerAdapter extends BaseAdapter {
    Context context;
    Object[] itemEnumClass;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext, Object[] values) {
        this.context = applicationContext;
        this.itemEnumClass = values;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return itemEnumClass.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflter.inflate(R.layout.each_row, null);
            viewHolder = new ViewHolder(view);

        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        view.setTag(viewHolder);

        Object value = itemEnumClass[i];
        if (value instanceof Currencies) {
            Currencies cryptos = (Currencies)value;
            viewHolder.getFlag().setImageDrawable(ContextCompat.getDrawable(view.getContext(),cryptos.getImageUrl()));
            viewHolder.getName().setText(cryptos.getName());
        } else {
            Cryptos cryptos = (Cryptos)value;
            viewHolder.getFlag().setImageDrawable(ContextCompat.getDrawable(view.getContext(),cryptos.getImageUrl()));
            viewHolder.getName().setText(cryptos.getName());
        }

        return view;
    }


    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }


    private class ViewHolder {
        private ImageView flag;
        private TextView name;

        public ViewHolder(View view) {
            flag = (ImageView) view.findViewById(R.id.icon);
            name = (TextView) view.findViewById(R.id.value);
        }

        public ImageView getFlag() {
            return flag;
        }

        public TextView getName() {
            return name;
        }
    }
}

