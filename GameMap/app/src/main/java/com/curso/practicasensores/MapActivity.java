package com.curso.practicasensores;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class MapActivity extends Activity implements
        SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private TextView titulo;
    private TextView textIzq;
    private TextView textDer;
    private ImageView iv;
    private MiHilo cargaMapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        titulo = (TextView) findViewById(R.id.titulo);
        iv = (ImageView) findViewById(R.id.imagen);
        textIzq = (TextView) findViewById(R.id.textIzq);
        textDer = (TextView) findViewById(R.id.textDerecha);
        Global.mapImage = iv;
        Global.mapTitle = titulo;
        Global.textoDerecha = textDer;
        Global.textoIzquierda = textIzq;
        if (Global.UAVActive == true){
            titulo.setText("UAV ACTIVO");
            textIzq.setText("UAV");
            Global.URL = "http://10.0.2.2:8000/all";
        }
        else {
            switch (Global.team) {
                case "blue":
                    titulo.setText("MAPA EQUIPO AZUL");
                    Global.URL = "http://10.0.2.2:8000/blue";
                    break;
                case "red":
                    titulo.setText("MAPA EQUIPO ROJO");
                    Global.URL = "http://10.0.2.2:8000/red";
                    break;
                default:
                    titulo.setText("Nada");
                    break;
            }
        }
        cargaMapa = MiHilo.crearEIniciar("cargaMapa");
    }

    public void cargaInicial(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cargaMapa.pausarhilo();
        try {
            cargaMapa.hilo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // Nada que hacer
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            Toast.makeText(this, "En el bolsillo",
                    Toast.LENGTH_LONG).show();
            cargaMapa.suspenderhilo();
        }
        else {
            cargaMapa.renaudarhilo();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity,
                SensorManager.SENSOR_DELAY_NORMAL);
        cargaMapa.renaudarhilo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        cargaMapa.suspenderhilo();
    }
}

class MiHilo implements Runnable{
    Thread hilo;
    MapActivity main;
    boolean suspender; //Suspende un hilo cuando es true
    boolean pausar;    //Detiene un hilo cuando es true
    MiHilo (String nombre){
        hilo=new Thread(this,nombre);
        suspender=false;
        pausar=false;
    }
    public static MiHilo crearEIniciar(String nombre){
        MiHilo miHilo=new MiHilo(nombre);

        miHilo.hilo.start(); //Iniciar el hilo
        return miHilo;
    }
    public void run() {
        try {
            while(true){
                if (Global.UAVActive) {
                    Date ahora = new Date();
                    long resTime = Global.UAVEndTime.getTime()-ahora.getTime();
                    if (resTime>0){
                        Global.textoDerecha.setText((int)(resTime/1000)+" s");
                    }
                    else {
                        Global.UAVActive = false;
                        Global.textoDerecha.setText("");
                        Global.textoIzquierda.setText("");
                        switch (Global.team) {
                            case "blue":
                                Global.mapTitle.setText("MAPA EQUIPO AZUL");
                                Global.URL = "http://10.0.2.2:8000/blue";
                                break;
                            case "red":
                                Global.mapTitle.setText("MAPA EQUIPO ROJO");
                                Global.URL = "http://10.0.2.2:8000/red";
                                break;
                            default:
                                Global.mapTitle.setText("Nada");
                                break;
                        }
                    }
                }
                Hacer_peticion_REST();
                Thread.sleep(600);
                synchronized (this) {
                    while (suspender) {
                        wait();
                    }
                    if (pausar) break;
                }
            }
        }catch (InterruptedException exc){
            System.out.println(hilo.getName()+ "interrumpido.");
        }
        System.out.println(hilo.getName()+ " finalizado.");
    }
    //Pausar el hilo
    synchronized void pausarhilo(){
        pausar=true;
        //lo siguiente garantiza que un hilo suspendido puede detenerse.
        suspender=false;
        notify();
    }
    //Suspender un hilo
    synchronized void suspenderhilo(){
        suspender=true;
    }
    //Renaudar un hilo
    synchronized void renaudarhilo(){
        suspender=false;
        notify();
    }

    public void Hacer_peticion_REST () {
        ImageRequest imageRequest = new ImageRequest(
                Global.URL, // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        Global.mapImage.setImageBitmap(response);
                    }
                },
                1920, // Image width
                1080, // Image height
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();
                    }
                }
        );
        // Add ImageRequest to the RequestQueue
        ServiciosWebApplication.getInstance().getRequestQueue().add(imageRequest);
    }
}
