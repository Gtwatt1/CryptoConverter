package com.example.zone3.cryptocoinconverter.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.zone3.cryptocoinconverter.Model.CryptoConver;
import com.example.zone3.cryptocoinconverter.network.NetworkController;
import com.example.zone3.cryptocoinconverter.network.NetworkHelper;
import com.example.zone3.cryptocoinconverter.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Gtwatt on 10/26/17.
 */



public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.MyViewHolder> {

    private Context mContext;
    private List<CryptoConver> cryptoConverList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView crypto, currency;
        public TextView price, overflow;
        public View containerView;
        private Context mContext;

        public MyViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            crypto = (ImageView) view.findViewById(R.id.crypto);
            currency = (ImageView) view.findViewById(R.id.currency);
            price = (TextView) view.findViewById(R.id.price);
            containerView = (View)view.findViewById(R.id. container_view);
        }

        public Context getContext() {
            return mContext;
        }
    }


    public CryptoAdapter(Context mContext, List<CryptoConver> cryptoConverList) {
        this.mContext = mContext;
        this.cryptoConverList = cryptoConverList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CryptoConver cryptoConver = cryptoConverList.get(position);
        holder.currency.setImageDrawable(ContextCompat.getDrawable(mContext,cryptoConver.getCurrency().getImageUrl()));
        holder.crypto.setImageDrawable(ContextCompat.getDrawable(mContext,cryptoConver.getCryptoType().getImageUrl()));

//        Glide.with(mContext).load(ContextCompat.getDrawable(mContext,cryptoConver.getCurrency().getImageUrl())).into(holder.currency);
//        Glide.with(mContext).load(ContextCompat.getDrawable(mContext,cryptoConver.getCryptoType().getImageUrl())).into(holder.crypto);

        new NetworkHelper().loadJsonObject(this.mContext, NetworkController.url + cryptoConver.getCryptoType().getCode() +"&tsyms=" + cryptoConver.getCurrency().getCode(), Request.Method.GET, null, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("regester", "onResponse:" + response);
                try {
                    holder.price.setText(cryptoConver.getCurrency().getSign() + " " + response.getString(cryptoConver.getCurrency().getCode()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        holder.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showConverionScreen(cryptoConver,holder);
            }
        });

        holder.price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConverionScreen(cryptoConver,holder);
            }
        });



    }

    public void showConverionScreen(final CryptoConver cryptoConver, MyViewHolder holder ){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View mView = inflater.inflate(R.layout.conversion_dialog_layout, null);

        ImageView cryptImg = (ImageView)mView.findViewById(R.id.crypto_img);
        Button convertButton = (Button) mView.findViewById(R.id.convert);
        ImageView currImg = (ImageView)mView.findViewById(R.id.currency_img);
        TextView convSummary = (TextView)mView.findViewById(R.id.conversion_summary);
        currImg.setImageDrawable(ContextCompat.getDrawable(mContext,cryptoConver.getCurrency().getImageUrl()));
        cryptImg.setImageDrawable(ContextCompat.getDrawable(mContext,cryptoConver.getCryptoType().getImageUrl()));




        final EditText crypto_c = (EditText) mView.findViewById(R.id.crypto_c);
        convSummary.setText("1 " + cryptoConver.getCryptoType().getName() + " = " + holder.price.getText());
        final TextView currency_c = (TextView) mView.findViewById(R.id.currency_c);
        currency_c.setText(cryptoConver.getCurrency().getSign() + " 0");
        final String currentPrice = holder.price.getText().toString().replace(cryptoConver.getCurrency().getSign(),"");

        builder.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double value = Integer.parseInt(crypto_c.getText().toString()) * Double.parseDouble(currentPrice);
                currency_c.setText(cryptoConver.getCurrency().getSign() + " " +Double.toString(value));
                Toast.makeText(mContext, Double.toString(value), Toast.LENGTH_SHORT).show();
            }
        });
        final TextView titleView = new TextView(mContext);
        titleView.setText("Convert " + cryptoConver.getCryptoType().getName() + "  to  " + cryptoConver.getCurrency().getName());
        titleView.setTextSize(14);
        titleView.setTextColor(mContext.getResources().getColor( R.color.colorAccent));
        titleView.setGravity(Gravity.CENTER);
        builder.setCustomTitle(titleView);
        builder.setView(mView);
        builder.show();
    }
    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return cryptoConverList.size();
    }

    public int getImage(String imageName) {

        int drawableResourceId =  mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        return drawableResourceId;
    }
}