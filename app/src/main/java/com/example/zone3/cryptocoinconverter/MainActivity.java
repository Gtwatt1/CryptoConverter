package com.example.zone3.cryptocoinconverter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.zone3.cryptocoinconverter.Adapter.CryptoAdapter;
import com.example.zone3.cryptocoinconverter.Adapter.SpinnerAdapter;
import com.example.zone3.cryptocoinconverter.Model.CryptoConver;
import com.example.zone3.cryptocoinconverter.Model.Cryptos;
import com.example.zone3.cryptocoinconverter.Model.Currencies;
import com.example.zone3.cryptocoinconverter.database.DataBaseHandler;
import com.example.zone3.cryptocoinconverter.helper.GridDecoration;
import com.example.zone3.cryptocoinconverter.network.NetworkHelper;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private List<CryptoConver> cryptoConverList;
    DataBaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        db = new DataBaseHandler(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        cryptoConverList = db.getAllCryptoConv();
        if (cryptoConverList.size() < 0) {
            prepareDummyData();
        }
        Collections.reverse(cryptoConverList);
        adapter = new CryptoAdapter(this, cryptoConverList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridDecoration(1, 10, true, this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        try {
//            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
                final Spinner currencySpinner = (Spinner) mView.findViewById(R.id.currencySpinner);
                final Spinner cryptoSpinner = (Spinner) mView.findViewById(R.id.cryptoSpinner);


                SpinnerAdapter currencySpinnerAdapter = new SpinnerAdapter(MainActivity.this, Currencies.values());
                currencySpinner.setAdapter(currencySpinnerAdapter);

                SpinnerAdapter cryptoSpinnerAdapter = new SpinnerAdapter(MainActivity.this, Cryptos.values());
                cryptoSpinner.setAdapter(cryptoSpinnerAdapter);
                final TextView titleView = new TextView(MainActivity.this);
                titleView.setText("Add New Conversion");
                titleView.setTextSize(14);
                titleView.setTextColor(getResources().getColor(R.color.colorAccent));
                titleView.setGravity(Gravity.CENTER);
                builder.setCustomTitle(titleView);
                builder.setTitle("Add New Conversion");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cryptos selectedCrypto = Cryptos.values()[cryptoSpinner.getSelectedItemPosition()];
                        Currencies selectedCurrency = Currencies.values()[currencySpinner.getSelectedItemPosition()];
                        CryptoConver cc = new CryptoConver(selectedCurrency, selectedCrypto, "");
                        db.addCryptoConv(cc);
                        cryptoConverList.add(0, cc);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(mView);
                builder.show();
            }
        });


    }

    void prepareDummyData() {
        cryptoConverList.add(new CryptoConver(Currencies.NAIRA, Cryptos.ETHERIUM, "0"));
        cryptoConverList.add(new CryptoConver(Currencies.USD, Cryptos.BITCOIN, "0"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reload) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the crypto when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
