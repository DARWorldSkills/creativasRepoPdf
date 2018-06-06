package com.davidpopayan.sena.myapplication.Controlador;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davidpopayan.sena.myapplication.Animacion.Caja;
import com.davidpopayan.sena.myapplication.Especializacion.especializacion;
import com.davidpopayan.sena.myapplication.Login.LoginActivity;
import com.davidpopayan.sena.myapplication.Modelo.GestorDB;
import com.davidpopayan.sena.myapplication.Modelo.User;
import com.davidpopayan.sena.myapplication.R;
import com.davidpopayan.sena.myapplication.Videojuegos.play;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1,btn2,btn3,btnlogin,btnregistro;
    Thread reloj;
    TextView textViewReloj,textViewDate,txtjornadaM,txtjornadaT,txtjornadaN;
    int respuesta=0;
    public static String fichaM="";
    public static String fichaT="";
    public static String fichaN="";
    public static String staprogramaM;
    public static int staIconoM;
    public static String staprogramaT;
    public static int staIconoT;
    public static String staprogramaN;
    public static int staIconoN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        createSU();

        btn1 = findViewById(R.id.hola);
        btn2 = findViewById(R.id.hola2);
        btn3 = findViewById(R.id.hola3);
        btnlogin = findViewById(R.id.btnloginA);
        btnregistro = findViewById(R.id.btnregistroA);
        textViewReloj = findViewById(R.id.txtHora);
        textViewDate = findViewById(R.id.txtDate);
        txtjornadaM = findViewById(R.id.txtjornadaM);
        txtjornadaT = findViewById(R.id.txtjornadaTarde);
        txtjornadaN = findViewById(R.id.txtjornadaNoche);
        btnregistro.setVisibility(View.INVISIBLE);
        btnlogin.setOnClickListener(this);
        btnregistro.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        inputJornadas();
        inputIconosM();
        inputIconosT();
        inputIconosN();


        reloj = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Date date= new Date();

                            SimpleDateFormat dateForma = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                            textViewReloj.setText(dateFormat.format(date));
                            textViewDate.setText(dateForma.format(date));
                        }
                    });


                }
            }
        });
        reloj.start();


    }



    private void createSU() {
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        try {
            User newuser = new User();
            newuser.setName("root1");
            newuser.setPassword("root1234");

            contentValues.put("USERNAME",newuser.getName());
            contentValues.put("CLAVE",newuser.getPassword());
            db.insert("USER",null,contentValues);
        }catch (Exception e){
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

        }

        db.close();

    }


    @Override
    protected void onStart() {
        super.onStart();
        respuesta=LoginActivity.respuesta;
        if (respuesta==1){
            btnlogin.setText(getString(R.string.logout));
            btnregistro.setVisibility(View.VISIBLE);
            LoginActivity.respuesta=0;


        }
    }

    public void inputJornadas(){
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        try{

            Cursor cursorM= db.rawQuery("SELECT * FROM HORARIO WHERE HORA='7:00-10:00' OR HORA= '7:00-13:00' OR HORA = '10:00-13:00' ;",null);
            if (cursorM.moveToFirst()){
                do {
                    txtjornadaM.setText(cursorM.getString(5));
                    staprogramaM= cursorM.getString(5);
                    fichaM=cursorM.getString(4);

                }while (cursorM.moveToNext());
            }
            cursorM.close();

        }catch (Exception e){

        }

        try{
            Cursor cursorT= db.rawQuery("SELECT * FROM HORARIO WHERE HORA='13:00-19:00' OR HORA= '13:00-16:00' OR HORA = '16:00-19:00' ;",null);
            if (cursorT.moveToFirst()){
                do {
                    txtjornadaT.setText(cursorT.getString(5));
                    staprogramaT= cursorT.getString(5);
                    fichaT=cursorT.getString(4);

                }while (cursorT.moveToNext());
            }
            cursorT.close();

        }catch (Exception e){

        }

        try{
            Cursor cursorN= db.rawQuery("SELECT * FROM HORARIO WHERE HORA='19:00-0:00' OR HORA= '19:00-21:00' OR HORA = '21:00-0:00' ;",null);
            if (cursorN.moveToFirst()){
                do {
                    txtjornadaN.setText(cursorN.getString(5));
                    staprogramaN= cursorN.getString(5);
                    fichaN=cursorN.getString(4);
                }while (cursorN.moveToNext());
            }
            cursorN.close();

        }catch (Exception e){

        }



        db.close();

    }

    public void inputIconosT(){
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        try {
            Cursor cursorN = db.rawQuery("SELECT PROGRAMA FROM HORARIO WHERE HORA='13:00-16:00' OR HORA= '16:00-19:00' OR HORA = '13:00-19:00' ;", null);
            if (cursorN.moveToFirst()) {
                do {
                    Cursor cursor1 = db.rawQuery("SELECT ICONO FROM PROGRAMA WHERE NOMBRE = '" + cursorN.getString(0) + "' ;", null);
                    if (cursor1.moveToFirst()) {
                        do {

                            switch (cursor1.getString(0)) {

                                case "audifonos":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.audifonosnaranja));
                                    staIconoT=1;

                                    break;

                                case "computador":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.pcnaranja));
                                    staIconoT=2;

                                    break;

                                case "control":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.controlnaranja));
                                    staIconoT=3;

                                    break;

                                case "cubo":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.cubonaranja));
                                    staIconoT=4;

                                    break;

                                case "especializacion":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.especinaranaja));
                                    staIconoT=5;

                                    break;

                                case "muneco":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.personanaranja));
                                    staIconoT=6;

                                    break;

                                case "pincel":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.pencilnaranja));
                                    staIconoT=7;

                                    break;

                                case "video":
                                    btn2.setBackground(getResources().getDrawable(R.drawable.videonaranaj));
                                    staIconoT=8;

                                    break;
                            }


                        } while (cursor1.moveToNext());
                    }
                    cursor1.close();
                } while (cursorN.moveToNext());
            }
            cursorN.close();
        }catch (Exception e){

        }


    }

    public void inputIconosN(){
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        try{
            Cursor cursorN= db.rawQuery("SELECT PROGRAMA FROM HORARIO WHERE HORA='19:00-0:00' OR HORA= '19:00-21:00' OR HORA = '21:00-0:00' ;",null);
            if (cursorN.moveToFirst()){
                do {

                    Cursor cursor1 = db.rawQuery("SELECT ICONO FROM PROGRAMA WHERE NOMBRE = '"+cursorN.getString(0)+"' ;",null);
                    if (cursor1.moveToFirst()){
                        do {

                            switch (cursor1.getString(0)) {

                                case "audifonos":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.audifono));
                                    staIconoN=1;

                                    break;

                                case "computador":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.computador));
                                    staIconoN=2;

                                    break;

                                case "control":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.control));
                                    staIconoN=3;

                                    break;

                                case "cubo":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.cubo));
                                    staIconoN=4;

                                    break;

                                case "especializacion":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.especi));
                                    staIconoN=5;

                                    break;

                                case "muneco":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.muneco));
                                    staIconoN=6;

                                    break;

                                case "pincel":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.pencil));
                                    staIconoN=7;

                                    break;

                                case "video":
                                    btn3.setBackground(getResources().getDrawable(R.drawable.video));
                                    staIconoN=8;

                                    break;
                            }


                        }while (cursor1.moveToNext());
                    }
                    cursor1.close();



                }while (cursorN.moveToNext());
            }
            cursorN.close();

        }catch (Exception e){

        }
    }

    public void inputIconosM(){
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getReadableDatabase();
        try{
            Cursor cursorN= db.rawQuery("SELECT PROGRAMA FROM HORARIO WHERE HORA='7:00-10:00' OR HORA= '7:00-13:00' OR HORA = '10:00-13:00' ;",null);
            if (cursorN.moveToFirst()){
                do {
                    Cursor cursor1 = db.rawQuery("SELECT ICONO FROM PROGRAMA WHERE NOMBRE = '"+cursorN.getString(0)+"' ;",null);
                    if (cursor1.moveToFirst()){
                        do {

                            switch (cursor1.getString(0)) {

                                case "audifonos":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.audifonosverdes));
                                    staIconoM=1;

                                    break;

                                case "computador":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.computadorverce));
                                    staIconoM=2;

                                    break;

                                case "control":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.controlverde));
                                    staIconoM=3;

                                    break;

                                case "cubo":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.cuboverde));
                                    staIconoM=4;

                                    break;

                                case "especializacion":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.especiverde));
                                    staIconoM=5;

                                    break;

                                case "muneco":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.personaverfde));
                                    staIconoM=6;

                                    break;

                                case "pincel":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.pencilverde));
                                    staIconoM=7;

                                    break;

                                case "video":
                                    btn1.setBackground(getResources().getDrawable(R.drawable.videoverde));
                                    staIconoM=8;

                                    break;
                            }


                        }while (cursor1.moveToNext());
                    }
                    cursor1.close();
                }while (cursorN.moveToNext());
            }
            cursorN.close();

        }catch (Exception e){

        }
    }


    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.hola:
                intent = new Intent(MainActivity.this, play.class);
                startActivity(intent);
                break;
            case R.id.hola2:
                intent = new Intent(MainActivity.this, Caja.class);
                startActivity(intent);
                break;
            case R.id.hola3:
                intent = new Intent(MainActivity.this, especializacion.class);
                startActivity(intent);
                break;
            case R.id.btnloginA:
                if (btnlogin.getText().equals(getString(R.string.login))){
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                if (btnlogin.getText().equals(getString(R.string.logout))){
                    btnlogin.setText(getString(R.string.login));
                    btnregistro.setVisibility(View.INVISIBLE);
                    Snackbar.make(v,"Ha cerrado sesión",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnregistroA:
                intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
                break;




        }



    }




}
