package com.example.zone3.cryptocoinconverter.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Gtwatt on 10/27/17.
 */

public class NetworkHelper {

    public void loadJsonObject(Context context, String url, int action, JSONObject jsonRequest, final Map<String, String> params, Response.Listener<JSONObject> volleyCallbackResponse, Response.ErrorListener volleyErrorResponse) {


        JsonObjectRequest request = new JsonObjectRequest(action, url, jsonRequest, volleyCallbackResponse, volleyErrorResponse) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null)
                    return params;
                return super.getParams();
            }

        };
        NetworkController.getInstance(context).addToRequestQueue(request);
    }


}
