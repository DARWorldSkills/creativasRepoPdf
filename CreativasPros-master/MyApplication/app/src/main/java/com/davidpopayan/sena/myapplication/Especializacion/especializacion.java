package com.davidpopayan.sena.myapplication.Especializacion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidpopayan.sena.myapplication.Controlador.MainActivity;
import com.davidpopayan.sena.myapplication.Modelo.GestorDB;
import com.davidpopayan.sena.myapplication.R;

public class especializacion extends AppCompatActivity {
    TextView txtprograma,txtinstructor,txtficha;
    TextView txtcampo16lunes,txtcampo17lunes;
    TextView txtcampo16martes,txtcampo17martes;
    TextView txtcampo16miercoles,txtcampo17miercoles;
    TextView txtcampo16jueves,txtcampo17jueves;
    TextView txtcampo16viernes,txtcampo17viernes;
    TextView txtcampo16sabado,txtcampo17sabado;
    String fichaN="";
    ImageView iconoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especializacion);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtprograma= findViewById(R.id.txtProgramaN);
        txtinstructor= findViewById(R.id.txtInstructorN);
        txtficha= findViewById(R.id.txtNumFichaN);
        iconoA = findViewById(R.id.imgespeI);
        inicializartabla();
        listartabla();
        inputFichaD();
        inputImage();
    }

    public void inputFichaD(){
        fichaN= MainActivity.fichaN;
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        txtprograma.setText(MainActivity.staprogramaN);
        if (!fichaN.equals("")) {
            Cursor cursor = db.rawQuery("SELECT * FROM HORARIO WHERE FICHA = '" + fichaN + "' ;", null);
            if (cursor.moveToFirst()){
                Cursor cursorI;
                do {
                    txtficha.setText(cursor.getString(4));
                    int tmpI = cursor.getInt(3);
                    txtprograma.setText(cursor.getString(5));
                    cursorI = db.rawQuery("SELECT * FROM INSTRUCTOR WHERE LIDER='SI' AND IDINSTRUCTOR ="+tmpI+" ;",null);

                    if (cursorI.moveToFirst()){
                        do {

                            txtinstructor.setText(cursorI.getString(1)+" "+cursorI.getString(2));

                        }while (cursorI.moveToNext());
                    }
                    cursorI.close();




                }while (cursor.moveToNext());
            }
            cursor.close();

        }
    }


    public void enviar3(View view) {
        Intent intent = new Intent(especializacion.this, infoespeci.class);
        startActivity(intent);

    }

    public void inicializartabla(){
        txtcampo16lunes= findViewById(R.id.campo16lunes);
        txtcampo17lunes= findViewById(R.id.campo17lunes);

        txtcampo16martes= findViewById(R.id.campo16martes);
        txtcampo17martes= findViewById(R.id.campo17martes);

        txtcampo16miercoles= findViewById(R.id.campo16miercoles);
        txtcampo17miercoles= findViewById(R.id.campo17miercoles);

        txtcampo16jueves= findViewById(R.id.campo16jueves);
        txtcampo17jueves= findViewById(R.id.campo17jueves);

        txtcampo16viernes= findViewById(R.id.campo16viernes);
        txtcampo17viernes= findViewById(R.id.campo17viernes);

        txtcampo16sabado= findViewById(R.id.campo16sabado);
        txtcampo17sabado= findViewById(R.id.campo17sabado);

    }

    public void listartabla(){
        GestorDB gestorBD = new GestorDB(this);
        SQLiteDatabase db = gestorBD.getWritableDatabase();

        String[] dias = {getString(R.string.lunes), getString(R.string.martes), getString(R.string.miercoles), getString(R.string.jueves), getString(R.string.viernes), getString(R.string.sabado)};
        String[] horas = {getString(R.string.diecinueveveniuno),getString(R.string.ventiunocero),getString(R.string.diecinuevecero)};
        TextView[] campos = {txtcampo16lunes, txtcampo17lunes, txtcampo16martes, txtcampo17martes, txtcampo16miercoles, txtcampo17miercoles,
                txtcampo16jueves, txtcampo17jueves, txtcampo16viernes, txtcampo17viernes,
                txtcampo16sabado, txtcampo17sabado};
        int tmp = 0;
        int pa=0;
        Cursor cursortmp;
        Cursor cursorcampos;
        for (int i = 0; i < dias.length; i++) {
            for (int j = 0; j < horas.length-1; j++) {
                try {
                    cursortmp = (db.rawQuery("SELECT INSTRUCTOR FROM HORARIO WHERE DIA = '" + dias[i] + "' AND HORA = '" + horas[j] + "' ;", null));
                    if (cursortmp.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros
                        do {
                            tmp = (cursortmp.getInt(0));
                            cursorcampos = (db.rawQuery("SELECT NOMBRE FROM INSTRUCTOR WHERE IDINSTRUCTOR = " + tmp + ";", null));
                            if (cursorcampos.moveToFirst()) {
                                //Recorremos el cursor hasta que no haya más registros
                                do {
                                    campos[pa].setText(cursorcampos.getString(0));
                                } while (cursorcampos.moveToNext());

                                cursorcampos.close();
                            }
                        } while (cursortmp.moveToNext());
                        cursortmp.close();
                    }
                }catch (Exception e){
                }

                pa=pa+1;
            }

        }


        pa=0;
        for (int i = 0 ; i < dias.length; i++){
            try {
                cursortmp = db.rawQuery("SELECT INSTRUCTOR FROM HORARIO WHERE DIA = '" + dias[i] + "' AND HORA = '" + horas[2] + "' ;", null);
                if (cursortmp.moveToFirst()){
                    do {
                        tmp = cursortmp.getInt(0);
                        cursorcampos = db.rawQuery("SELECT NOMBRE FROM INSTRUCTOR WHERE IDINSTRUCTOR = "+tmp+";",null);
                        if (cursorcampos.moveToFirst()){
                            do {
                                campos[pa].setText(cursorcampos.getString(0));
                                campos[pa+1].setText(cursorcampos.getString(0));
                            }while (cursorcampos.moveToNext());

                        }

                    }while (cursortmp.moveToNext());
                }

            }catch (Exception e){

            }
            pa=pa+2;
        }


        db.close();

    }

    public void inputImage(){
        switch (MainActivity.staIconoN){
            case 1:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.audifonoteazul));
                break;
            case 2:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.psazulblan));
                break;

            case 3:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.controloteazul));
                break;
            case 4:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.cubitoazul));
                break;
            case 5:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.personotaazul));
                break;
            case 6:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.esceeepazul));
                break;
            case 7:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.lapiceroteazul));
                break;
            case 8:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.camarotaazul));
                break;


        }
    }




    public void back(View view) {
        finish();
    }
}
