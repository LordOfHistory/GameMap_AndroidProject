package com.curso.practicasensores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MiBaseDatos extends SQLiteOpenHelper {


    private static final int VERSION_BASEDATOS = 1;
    private static final String NOMBRE_BASEDATOS = "mibasedatos.db";
    private static final String TABLA_CONTACTOS ="CREATE TABLE IF NOT EXISTS contactos " +
            " (_id INTEGER PRIMARY KEY, nombre TEXT, telefono TEXT, email TEXT,team TEXT)";

    public MiBaseDatos(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(new StringBuilder().append("DROP TABLE IF EXISTS").append(TABLA_CONTACTOS).toString());
        onCreate(db);
    }




    public boolean insertarCONTACTO(int id, String nom, String tlf, String email,String team) {
        long salida=0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            if(id!=0)
                valores.put("_id", id);
            valores.put("nombre", nom);
            valores.put("telefono", tlf);
            valores.put("email", email);
            valores.put("team", team);
            salida=db.insert("contactos", null, valores);
        }
        db.close();
        return(salida>0);
    }

    public boolean  modificarCONTACTO(int id, String nom, String tlf, String email, String team){
        long salida=0;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("_id", id);
            valores.put("nombre", nom);
            valores.put("telefono", tlf);
            valores.put("email", email);
            valores.put("team", team);
            salida=db.update("contactos", valores, "_id=" + id, null);
        }
        db.close();
        return(salida>0);
    }

    public boolean  borrarCONTACTO(int id) {
        SQLiteDatabase db = getWritableDatabase();
        long salida=0;
        if (db != null) {
            salida=db.delete("contactos", "_id=" + id, null);
        }
        db.close();
        return(salida>0);
    }

    public void  limpiarCONTACTOS() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("Delete from contactos");
        }
        db.close();
    }

    public Contactos recuperarCONTACTO(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"_id", "nombre", "telefono", "email","team"};
        Cursor c = db.query("contactos", valores_recuperar, "_id=" + id, null, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
        }
        Contactos contactos = new Contactos(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4));
        db.close();
        c.close();
        return contactos;
    }


    public ArrayList<Contactos> recuperarCONTACTOS() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Contactos> lista_contactos = new ArrayList<Contactos>();
        String[] valores_recuperar = {"_id", "nombre", "telefono", "email","team"};
        Cursor c = db.query("contactos", valores_recuperar, null, null, null, null, null, null);
        c.moveToFirst();
        do {
            Contactos contactos = new Contactos(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4));
            lista_contactos.add(contactos);
        } while (c.moveToNext());
        db.close();
        c.close();
        return lista_contactos;
    }
}
