package com.curso.gamemap;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class Global {
    public static String team = "none";
    public static String URL = "";
    public static ImageView mapImage;

    //Gestión de equipo
    public static String lastTeam = "none";
    public static Date lastTeamChange = new Date(0);

    //Gestión UAV
    public static Date UAVEndTime = new Date(0);
    public static boolean UAVActive = false;
    public static TextView textoIzquierda;
    public static TextView textoDerecha;
    public static TextView mapTitle;

    //Gestión puntuación
    public static boolean adminMode = false;
    public static String adminPassword = "admin";

    //Gestión de base de datos
    public static boolean added=false;
    public static int id;
}
