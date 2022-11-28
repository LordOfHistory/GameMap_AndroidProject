package com.curso.practicasensores;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class NuevoJugadorActivity extends Activity {

    ImageView teamImage;
    TextView editNombre,editTelefono,editEmail;
    ToggleButton team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevojugador);

        teamImage = (ImageView) findViewById(R.id.teamImagen);
        editNombre   = (EditText) findViewById(R.id.txtNombre);
        editTelefono = (EditText) findViewById(R.id.txtTelefono);
        editEmail = (EditText) findViewById(R.id.txtEmail);
        team = (ToggleButton) findViewById(R.id.teamButton);
    }

    public void Switched(View view){
        String value = (String) team.getText();
        if (value.equals("ROJO"))
            teamImage.setImageResource(R.drawable.red);
        if (value.equals("AZUL"))
            teamImage.setImageResource(R.drawable.blue);
    }

    public void Submit(View view){
        String name = editNombre.getText().toString();
        String tel = editTelefono.getText().toString();
        String mail = editEmail.getText().toString();
        String teamStr = (String) team.getText();

        if (!name.equals("")){
            if (teamStr.equals("ROJO"))
                teamStr = "red";
            if (teamStr.equals("AZUL"))
                teamStr = "blue";
            if (tel.equals(""))
                tel = "unknown";
            if (mail.equals(""))
                mail = "unknown@mail.com";
            final MiBaseDatos MDB=new MiBaseDatos(getApplicationContext());
            MDB.insertarCONTACTO(0,name,tel,mail,teamStr);
            Toast.makeText(this, "Jugador añadido correctamente",
                    Toast.LENGTH_LONG).show();
            editNombre.setText("");
            editTelefono.setText("");
            editEmail.setText("");
        }
        else {
            Toast.makeText(this, "Campo nombre sin completar",
                    Toast.LENGTH_LONG).show();
        }


    }
    public void Cancel(View view){
        Intent intent = new Intent(this, ListaJugadoresActivity.class);
        startActivity(intent);
    }
}