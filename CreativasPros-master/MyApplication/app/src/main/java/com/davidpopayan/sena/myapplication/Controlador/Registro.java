package com.davidpopayan.sena.myapplication.Controlador;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.davidpopayan.sena.myapplication.Modelo.GestorDB;
import com.davidpopayan.sena.myapplication.Modelo.Horario;
import com.davidpopayan.sena.myapplication.Modelo.Instructor;
import com.davidpopayan.sena.myapplication.Modelo.Programa;
import com.davidpopayan.sena.myapplication.Modelo.User;
import com.davidpopayan.sena.myapplication.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    Button btnusuario;
    Button btncaeusuario;
    Button btnCSV;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        inizialite();
        btnusuario.setOnClickListener(this);
        btncaeusuario.setOnClickListener(this);
        btnCSV.setOnClickListener(this);

    }

    public void inizialite(){
        btnusuario= findViewById(R.id.btnuserR);
        btncaeusuario= findViewById(R.id.btnuserR2);
        btnCSV = findViewById(R.id.btnCSV);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnuserR:
                intent= new Intent(Registro.this, IUser.class);
                startActivity(intent);
                break;


            case R.id.btnuserR2:
                intent= new Intent(Registro.this, CUser.class);
                startActivity(intent);
                break;
                
            case R.id.btnCSV:
                cargarCSV(v);
                intent= new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    private void cargarCSV(View v) {
        File storage = new File(Environment.getExternalStorageDirectory(),"/Download");
        file = new File(storage,"horario.csv");
        GestorDB gestorDB = new GestorDB(this);
        try{
            CSVReader csvReader = new CSVReader(new FileReader(file));
            String [] nextline;
            SQLiteDatabase db = gestorDB.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM PROGRAMA",null);
            if (cursor.moveToNext()){
                db.execSQL("DELETE FROM HORARIO");
                db.execSQL("DELETE FROM PROGRAMA");
                db.execSQL("DELETE FROM INSTRUCTOR");
                db.execSQL("DELETE FROM sqlite_sequence WHERE name='INSTRUCTOR'");


            }
            cursor.close();
            while ((nextline= csvReader.readNext())!=null){
                String[] array = nextline[0].split(";");
                if (!array[0].equals("")) {
                    Instructor instructor = new Instructor();
                    instructor.setName(array[0]);
                    instructor.setLast_name(array[1]);
                    instructor.setPhone(array[2]);
                    instructor.setEmail(array[3]);
                    instructor.setLider(array[4]);
                    gestorDB.guardarDatosI(instructor);
                    Snackbar.make(v, "Se ha guardado datos correctamente", Snackbar.LENGTH_SHORT).show();
                }
            }

            csvReader = new CSVReader(new FileReader(file));
            while ((nextline=csvReader.readNext())!=null){
                String [] array = nextline[0].split(";");
                if (!array[6].equals("")){
                    Programa programa= new Programa();
                    programa.setName(array[6]);
                    programa.setDescription(array[7]);
                    programa.setVideo(array[8]);
                    programa.setIcono(array[9]);
                    gestorDB.guardarDatosP(programa);
                    Snackbar.make(v, "Se ha guardado datos correctamente", Snackbar.LENGTH_SHORT).show();

                }
            }

            csvReader = new CSVReader(new FileReader(file));
            while ((nextline=csvReader.readNext())!=null){
                String [] array = nextline[0].split(";");
                if (!array[11].equals("")) {
                    Horario horario = new Horario();
                    horario.setDia(array[11]);
                    horario.setHora(array[12]);
                    horario.setInstructor(Integer.parseInt(array[13]));
                    horario.setFicha(array[14]);
                    horario.setPrograma(array[15]);
                    gestorDB.guardarDatosH(horario);
                    Snackbar.make(v, "Se ha guardado datos correctamente", Snackbar.LENGTH_SHORT).show();
                }

            }







        }catch (Exception e){
            Log.e("NOOOOOOOOOOO:",e.toString());
        }

    }

}
