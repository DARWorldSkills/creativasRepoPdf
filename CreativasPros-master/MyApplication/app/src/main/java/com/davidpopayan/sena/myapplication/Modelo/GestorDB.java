package com.davidpopayan.sena.myapplication.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class GestorDB extends SQLiteOpenHelper{
    public GestorDB(Context context) {
        super(context, Constantes.DATABASE, null, Constantes.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.scriptCreateTablesUser);
        db.execSQL(Constantes.scriptCreateTablesInstructor);
        db.execSQL(Constantes.scriptCreateTablesHorario);
        db.execSQL(Constantes.scriptCreaeTablesPrograma);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void guardarDatosI(Instructor instructor){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try{
            values.put("NOMBRE",instructor.getName());
            values.put("APELLIDO",instructor.getLast_name());
            values.put("CORREO",instructor.getEmail());
            values.put("TELEFONO",instructor.getPhone());
            values.put("LIDER",instructor.getLider());
            db.insert("INSTRUCTOR",null,values);

        }catch (Exception e){

        }
    }

    public void guardarDatosH(Horario horario){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values =  new ContentValues();
        try {

            values.put("DIA",horario.getDia());
            values.put("HORA",horario.getHora());
            values.put("FICHA",horario.getFicha());
            values.put("INSTRUCTOR",horario.getInstructor());
            values.put("PROGRAMA", horario.getPrograma());
            db.insert("HORARIO",null,values);

        }catch (Exception e){

        }
    }

    public void guardarDatosP(Programa programa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {

            values.put("NOMBRE",programa.getName());
            values.put("DESCRIPCION",programa.getDescription());
            values.put("VIDEO",programa.getVideo());
            values.put("ICONO",programa.getIcono());
            db.insert("PROGRAMA",null,values);


        }catch (Exception e){

        }
    }



}
