package com.curso.practicasensores;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ServiciosWebApplication extends Application {
    private static ServiciosWebApplication sInstance;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }
    public synchronized static ServiciosWebApplication getInstance() {

        return sInstance;
    }
    public RequestQueue getRequestQueue() {

        return mRequestQueue;
    }
}
