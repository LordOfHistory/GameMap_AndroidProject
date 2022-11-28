package com.curso.practicasensores;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class SelectActivity extends Activity {
    private ImageView iv;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        iv = (ImageView) findViewById(R.id.imageView);
        title = (TextView) findViewById(R.id.titulo);
        switch (Global.team){
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

    public void ClickAzul(View view){
        if (Global.team=="blue"){
            Global.team = "none";
            this.EqNone();
        }
        else {
            Date ahora = new Date();
            long diff = ahora.getTime()-Global.lastTeamChange.getTime();
            if (Global.lastTeam != "blue" &&  diff<100*1000){
                Toast.makeText(this, "Tienes que esperar para cambiar de equipo, quedan " + (100 - diff / 1000) + " s",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Global.team = "blue";
                if (Global.lastTeam != "blue")
                    Global.lastTeamChange = new Date();
                Global.lastTeam = "blue";
                this.EqBlue();
            }
        }
    }
    public void ClickRojo(View view){
        if (Global.team=="red"){
            Global.team = "none";
            this.EqNone();
        }
        else {
            Date ahora = new Date();
            long diff = ahora.getTime()-Global.lastTeamChange.getTime();
            if (Global.lastTeam != "red" &&  diff<60*1000){
                Toast.makeText(this, "Tienes que esperar para cambiar de equipo, quedan " + (60 - diff / 1000) + " s",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Global.team = "red";
                if (Global.lastTeam != "red")
                    Global.lastTeamChange = new Date();
                Global.lastTeam = "red";
                this.EqRed();
            }
        }
    }
    public void EqBlue(){
        iv.setImageResource(R.drawable.blue);
        title.setText("Equipo azul");
        title.setTextColor(Color.rgb(0, 0, 255));
    }
    public void EqRed(){
        iv.setImageResource(R.drawable.red);
        title.setText("Equipo rojo");
        title.setTextColor(Color.rgb(255, 0, 0));
    }
    public void EqNone(){
        iv.setImageResource(R.drawable.none);
        title.setText("Seleccione equipo");
        title.setTextColor(Color.rgb(100,100,100));
    }
}