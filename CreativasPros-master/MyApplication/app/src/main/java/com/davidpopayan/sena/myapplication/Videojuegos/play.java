package com.davidpopayan.sena.myapplication.Videojuegos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidpopayan.sena.myapplication.Controlador.MainActivity;
import com.davidpopayan.sena.myapplication.Modelo.GestorDB;
import com.davidpopayan.sena.myapplication.R;

public class play extends AppCompatActivity {
    TextView info, txtprograma,txtinstructor,txtficha;
    TextView txtcampo1lunes,txtcampo2lunes;
    TextView txtcampo1martes,txtcampo2martes;
    TextView txtcampo1miercoles,txtcampo2miercoles;
    TextView txtcampo1jueves,txtcampo2jueves;
    TextView txtcampo1viernes,txtcampo2viernes;
    TextView txtcampo1sabado,txtcampo2sabado;
    String fichaM="";
    ImageView iconoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtprograma= findViewById(R.id.txtProgramaM);
        txtinstructor= findViewById(R.id.txtInstructorM);
        txtficha= findViewById(R.id.txtNumFichaM);
        info = findViewById(R.id.envio);
        iconoA = findViewById(R.id.imgplayI);

        inicializarTabla();
        listartabla();
        inputFichaD();
        inputImage();


    }


    public void inputFichaD(){
        fichaM= MainActivity.fichaM;
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        if (!fichaM.equals("")) {
            Cursor cursor = db.rawQuery("SELECT * FROM HORARIO WHERE FICHA = '" + fichaM + "' ;", null);
            if (cursor.moveToFirst()){
                Cursor cursorI;
                do {
                    txtficha.setText(cursor.getString(4));
                    int tmpI = cursor.getInt(3);
                    txtprograma.setText(cursor.getString(5));
                    cursorI = db.rawQuery("SELECT * FROM INSTRUCTOR WHERE IDINSTRUCTOR ="+tmpI+" AND LIDER='SI';",null);

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


    public void inicializarTabla(){
        txtcampo1lunes= findViewById(R.id.campo1lunes);
        txtcampo2lunes= findViewById(R.id.campo2lunes);

        txtcampo1martes= findViewById(R.id.campo1martes);
        txtcampo2martes= findViewById(R.id.campo2martes);

        txtcampo1miercoles= findViewById(R.id.campo1miercoles);
        txtcampo2miercoles= findViewById(R.id.campo2miercoles);

        txtcampo1jueves= findViewById(R.id.campo1jueves);
        txtcampo2jueves= findViewById(R.id.campo2jueves);

        txtcampo1viernes= findViewById(R.id.campo1viernes);
        txtcampo2viernes= findViewById(R.id.campo2viernes);

        txtcampo1sabado= findViewById(R.id.campo1sabado);
        txtcampo2sabado= findViewById(R.id.campo2sabado);


    }

    public void enviar(View view) {
        Intent intent = new Intent(play.this, infoplay.class);
        startActivity(intent);

    }

    public void listartabla() {
        GestorDB gestorBD = new GestorDB(this);
        SQLiteDatabase db = gestorBD.getWritableDatabase();

        String[] dias = {getString(R.string.lunes), getString(R.string.martes), getString(R.string.miercoles), getString(R.string.jueves), getString(R.string.viernes), getString(R.string.sabado)};
        String[] horas = {getString(R.string.sietediez),getString(R.string.dieztrece),getString(R.string.sietetrece)};
        TextView[] campos = {txtcampo1lunes, txtcampo2lunes,
                txtcampo1martes, txtcampo2martes, txtcampo1miercoles, txtcampo2miercoles,
                txtcampo1jueves, txtcampo2jueves, txtcampo1viernes, txtcampo2viernes,  txtcampo1sabado, txtcampo2sabado};
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
                        }

                        cursortmp.close();
                    }catch (Exception e){

                    }



                    pa=pa+1;
                }

            }

        pa=0;
        Cursor cursortmp1;
        for (int i = 0 ; i < dias.length; i++){

            try {
                cursortmp1 = db.rawQuery("SELECT INSTRUCTOR FROM HORARIO WHERE DIA = '" + dias[i] + "' AND HORA = '" + horas[2] + "' ;", null);

                if (cursortmp1.moveToFirst()){
                    do {
                        tmp = cursortmp1.getInt(0);
                        cursorcampos = db.rawQuery("SELECT NOMBRE FROM INSTRUCTOR WHERE IDINSTRUCTOR = "+tmp+";",null);
                        if (cursorcampos.moveToFirst()){
                            do {

                                campos[pa].setText(cursorcampos.getString(0));
                                campos[pa+1].setText(cursorcampos.getString(0));

                            }while (cursorcampos.moveToNext());

                        }

                    }while (cursortmp1.moveToNext());
                }

            }catch (Exception e){

            }
            pa=pa+2;
        }




        db.close();




    }


    public void inputImage(){
        switch (MainActivity.staIconoM){
            case 1:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.audifonitosverdes));
                break;
            case 2:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.psverdeblanco));
                break;

            case 3:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.verdecontroll));
                break;
            case 4:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.cubitover));
                break;
            case 5:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.peopleverdeeee));
                break;
            case 6:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.especiverdeee));
                break;
            case 7:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.lapiceroverde));
                break;
            case 8:
                iconoA.setImageDrawable(getResources().getDrawable(R.drawable.verdevideo));
                break;


        }
    }


    public void back(View view) {
        finish();
    }
}
