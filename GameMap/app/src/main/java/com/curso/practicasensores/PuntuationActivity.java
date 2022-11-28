package com.curso.practicasensores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class PuntuationActivity extends Activity {

    String URL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuation);
        cargaBotones();
        URL="http://10.0.2.2:8000/points";
        Hacer_peticion_REST();
    }

    public void cargaBotones(){
        Button subered = (Button) findViewById(R.id.up_red);
        Button bajared = (Button) findViewById(R.id.down_red);
        Button subeblue = (Button) findViewById(R.id.up_blue);
        Button bajablue = (Button) findViewById(R.id.down_blue);
        Switch adminButton = (Switch) findViewById(R.id.admin_switch);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        if (Global.adminMode) {
            adminButton.setChecked(true);
            password.setEnabled(false);
            subered.setEnabled(true);
            bajared.setEnabled(true);
            subeblue.setEnabled(true);
            bajablue.setEnabled(true);
        }
        else{
            adminButton.setChecked(false);
            password.setEnabled(true);
            subered.setEnabled(false);
            bajared.setEnabled(false);
            subeblue.setEnabled(false);
            bajablue.setEnabled(false);
        }
    }

    public void Be_Admin (View view){
        Switch adminButton = (Switch) findViewById(R.id.admin_switch);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword);
        if(adminButton.isChecked()){
           if (password.getText().toString().equals(Global.adminPassword)) {
               Global.adminMode=true;
               Toast.makeText(getApplicationContext(), "Modo admin activado", Toast.LENGTH_LONG).show();
           }
           else {
               Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();

           }
        }
        else {
            Global.adminMode = false;
        }
        cargaBotones();
    }
    public void UpRed (View view){
        URL="http://10.0.2.2:8000/upRed";
        Hacer_peticion_REST();
    }
    public void DownRed (View view){
        URL="http://10.0.2.2:8000/downRed";
        Hacer_peticion_REST();
    }
    public void UpBlue (View view){
        URL="http://10.0.2.2:8000/upBlue";
        Hacer_peticion_REST();
    }
    public void DownBlue (View view){
        URL="http://10.0.2.2:8000/downBlue";
        Hacer_peticion_REST();
    }

    public void Hacer_peticion_REST () {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String redp = null;
                        String bluep = null;
                        try {
                            redp = response.getString("redPoints");
                            bluep = response.getString("bluePoints");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //int puntosRojo=response.getString("redPoints");
                        //int puntosAzul=response.getString("bluePoints");
                        TextView pRojo = (TextView) findViewById(R.id.puntos_rojo);
                        TextView pAzul = (TextView) findViewById(R.id.puntos_azul);
                        pRojo.setText(redp);
                        pAzul.setText(bluep);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "No se ha recibido la previsión", Toast.LENGTH_SHORT).show();
            }
        });
        // add the request object to the queue to be executed
        ServiciosWebApplication.getInstance().getRequestQueue().add(request);
    }
}