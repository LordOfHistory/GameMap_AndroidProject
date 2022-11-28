package com.curso.practicasensores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity{
    private ImageView iv;
    private TextView title;
    private MiBaseDatos MDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MDB=new MiBaseDatos(getApplicationContext());
        if (Global.added == false) {
            Global.added = true;
            MDB.insertarCONTACTO(1, "Lorenzo", "912668282", "lorenzo@miemail.es", "blue");
            MDB.insertarCONTACTO(2, "Joaquín", "954626262", "joaquin@gmail.com", "blue");
            MDB.insertarCONTACTO(3, "Elena", "607000000", "elena@hotmail.es", "red");
            MDB.insertarCONTACTO(4, "Rocío", "648121212", "rocio@miemail.es", "red");
        }
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView2);
        title = (TextView) findViewById(R.id.titulo);
    }

    @Override
    public void onStart() {
        super.onStart();
        switch (Global.team) {
            case "blue":
                this.EqBlue();
                break;
            case "red":
                this.EqRed();
                break;
            default:
                this.EqNone();
                break;
        }
    }

    public void Ir_Select(View view){
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }
    public void Ir_Mapa(View view){
        if (Global.team =="none") {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Primero debe unirse a un equipo");
            alertDialog.setTitle("Acceso al mapa...");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
        else {
            Global.UAVActive = false;
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }
    }

    public void Ir_ListaJugadores(View view){
        Intent intent = new Intent(this, ListaJugadoresActivity.class);
        startActivity(intent);
    }

    public void Ir_UAV(View view){
        if (Global.team =="none") {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Primero debe unirse a un equipo");
            alertDialog.setTitle("Acceso al mapa...");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
        else {
            int cooldown = 50;
            int duration = 30;
            Date now = new Date();
            long tSinceLastUse = now.getTime() - Global.UAVEndTime.getTime();
            if (tSinceLastUse > 0 && tSinceLastUse < cooldown * 1000) {
                Toast.makeText(this, "Habilidad en enfriamiento, quedan " + (cooldown - tSinceLastUse / 1000) + " s",
                        Toast.LENGTH_LONG).show();
            } else {
                if (tSinceLastUse > 0)
                    Global.UAVEndTime = new Date(now.getTime() + duration * 1000);
                Global.UAVActive = true;
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        }
    }

    public void Ir_Puntuacion(View view){
        Intent intent = new Intent(this, PuntuationActivity.class);
        startActivity(intent);
    }

    public void EqBlue(){
        iv.setImageResource(R.drawable.blue);
        title.setText("Bienvenido jugador azul");
        title.setTextColor(Color.rgb(0, 0, 255));
    }
    public void EqRed(){
        iv.setImageResource(R.drawable.red);
        title.setText("Bienvenido jugador rojo");
        title.setTextColor(Color.rgb(255, 0, 0));
    }
    public void EqNone(){
        iv.setImageResource(R.drawable.none);
        title.setText("Bienvenido jugador");
        title.setTextColor(Color.rgb(100,100,100));
    }
}
