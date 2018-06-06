
package com.davidpopayan.sena.myapplication.Animacion;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidpopayan.sena.myapplication.Controlador.MainActivity;
import com.davidpopayan.sena.myapplication.Modelo.GestorDB;
import com.davidpopayan.sena.myapplication.R;

public class Caja extends AppCompatActivity {
    TextView txtprograma,txtinstructor,txtficha;
    TextView txtcampo9lunes,txtcampo10lunes;
    TextView txtcampo9martes,txtcampo10martes;
    TextView txtcampo9miercoles,txtcampo10miercoles;
    TextView txtcampo9jueves,txtcampo10jueves;
    TextView txtcampo9viernes,txtcampo10viernes;
    TextView txtcampo9sabado,txtcampo10sabado;
    String fichaT="";
    ImageView iconoA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtprograma= findViewById(R.id.txtProgramaT);
        txtinstructor= findViewById(R.id.txtInstructorT);
        txtficha= findViewById(R.id.txtNumFichaT);
        iconoA = findViewById(R.id.imgCajaI);
        inicializartabla();
        llenarTabla();
        inputFichaD();
        inputImage();

    }

    public void inputFichaD() {
        fichaT = MainActivity.fichaT;
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        txtprograma.setText(MainActivity.staprogramaT);
        if (!fichaT.equals("")) {
            Cursor cursor = db.rawQuery("SELECT * FROM HORARIO WHERE FICHA = '" + fichaT + "' ;", null);
            if (cursor.moveToFirst()) {
                Cursor cursorI;
                do {
                    txtficha.setText(cursor.getString(4));
                    int tmpI = cursor.getInt(3);
                    cursorI = db.rawQuery("SELECT * FROM INSTRUCTOR WHERE IDINSTRUCTOR =" + tmpI + " AND LIDER='SI';", null);

                    if (cursorI.moveToFirst()) {
                        do {

                            txtinstructor.setText(cursorI.getString(1)+" "+cursorI.getString(2));

                        } while (cursorI.moveToNext());
                    }
                    cursorI.close();


                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public void inicializartabla(){
        txtcampo9lunes= findViewById(R.id.campo9lunes);
        txtcampo10lunes= findViewById(R.id.campo10lunes);

        txtcampo9martes= findViewById(R.id.campo9martes);
        txtcampo10martes= findViewById(R.id.campo10martes);

        txtcampo9miercoles= findViewById(R.id.campo9miercoles);
        txtcampo10miercoles= findViewById(R.id.campo10miercoles);

        txtcampo9jueves= findViewById(R.id.campo9jueves);
        txtcampo10jueves= findViewById(R.id.campo10jueves);

        txtcampo9viernes= findViewById(R.id.campo9viernes);
        txtcampo10viernes= findViewById(R.id.campo10viernes);

        txtcampo9sabado= findViewById(R.id.campo9sabado);
        txtcampo10sabado= findViewById(R.id.campo10sabado);

    }

    public void llenarTabla(){
        GestorDB gestorBD = new GestorDB(this);
        SQLiteDatabase db = gestorBD.getWritableDatabase();

        String[] dias = {getString(R.string.lunes), getString(R.string.martes), getString(R.string.miercoles), getString(R.string.jueves), getString(R.string.viernes), getString(R.string.sabado)};
        String[] horas = {getString(R.string.trecedieciseis),getString(R.string.dieciseisdiecinueve),getString(R.string.trecediecinueve)};
        TextView[] campos = {txtcampo9lunes, txtcampo10lunes, txtcampo9martes, txtcampo10martes,
                txtcampo9miercoles, txtcampo10miercoles, txtcampo9jueves, txtcampo10jueves, txtcampo9viernes,
                txtcampo10viernes, txtcampo9sabado, txtcampo10sabado};
        int tmp = 0;
        int pa=0;
        Cursor cursortmp;
        Cursor cursorcampos;
        for (int i = 0; i < dias.length; i++) {
            for (int j = 0; j < horas.length-1; j++) {
                try {
                    cursortmp = (db.rawQuery("SELECT INSTRUCTOR FROM HORARIO AND DIA = '" + dias[i] + "' AND HORA = '" + horas[j] + "' ;", null));
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
                    }


                    cursortmp.close();
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
        switch (MainActivity.staIconoT){
            case 1:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.audinaranjita));
                break;
            case 2:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.psnaranjita));
                break;

            case 3:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.ctrnaranja));
                break;
            case 4:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.cubitonara));
                break;
            case 5:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.personitaaanara));
                break;
            case 6:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.especiverdeee));
                break;
            case 7:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.lapiceritonaranjita));
                break;
            case 8:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.camaraaaanaranja));
                break;


        }
    }




    public void enviar4(View view) {
        Intent intent = new Intent(Caja.this, Infocaja.class);
        startActivity(intent);

    }

    public void back(View view) {
        finish();
    }
}
