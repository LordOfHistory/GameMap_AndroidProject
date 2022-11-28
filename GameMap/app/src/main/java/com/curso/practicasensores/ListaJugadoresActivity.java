package com.curso.practicasensores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListaJugadoresActivity extends Activity {

    private ListView lista;
    private Context contexto = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listajugadores);

        final MiBaseDatos MDB=new MiBaseDatos(getApplicationContext());

        ArrayList<Contactos> datos = MDB.recuperarCONTACTOS();
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.contacto, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    ImageView imagen_contacto = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_contacto != null){
                        String equipo = ((Contactos) entrada).getTeam();
                        if (equipo.equals("blue"))
                            imagen_contacto.setImageResource(R.drawable.blue);
                        if (equipo.equals("red"))
                            imagen_contacto.setImageResource(R.drawable.red);
                    }

                    TextView texto_contacto = (TextView) view.findViewById(R.id.textView_contacto);
                    if (texto_contacto != null)
                        texto_contacto.setText(((Contactos) entrada).getNOMBRE());

                    TextView texto_telefono = (TextView) view.findViewById(R.id.textView_telefono);
                    if (texto_telefono != null)
                        texto_telefono.setText(((Contactos) entrada).getTELEFONO());

                    TextView texto_email = (TextView) view.findViewById(R.id.textView_email);
                    if (texto_email != null)
                        texto_email.setText(((Contactos) entrada).getEMAIL());

                    TextView texto_ID = (TextView) view.findViewById(R.id.textView_ID);
                    if (texto_ID != null)
                        texto_ID.setText(Integer.toString(((Contactos) entrada).getID()));

                    TextView texto_team = (TextView) view.findViewById(R.id.textView_team);
                    if (texto_team != null)
                        texto_team.setText(((Contactos) entrada).getTeam());

                }
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView textoID = (TextView) view.findViewById(R.id.textView_ID);
                Global.id = Integer.parseInt(textoID.getText().toString());
                //Mostrar un mensaje de confirmación antes de realizar la llamada
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListaJugadoresActivity.this);
                alertDialog.setMessage("¿Desea borrar el contacto?");
                alertDialog.setTitle("Borrar contacto...");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        MDB.borrarCONTACTO(Global.id);
                        Toast.makeText(getApplicationContext(),
                                "El contacto a sido borrado",
                                Toast.LENGTH_LONG).show();
                        Intent intent=new Intent();
                        intent.setClass(contexto, contexto.getClass());
                        //llamamos a la actividad
                        contexto.startActivity(intent);
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getApplicationContext(),
                                "Operación cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.show();
            }
        });

    }

    public void Ir_NuevoJugador(View view){
        Intent intent = new Intent(this, NuevoJugadorActivity.class);
        startActivity(intent);
    }
    public void VolverAlMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}