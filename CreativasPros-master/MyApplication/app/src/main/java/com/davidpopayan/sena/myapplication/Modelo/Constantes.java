package com.davidpopayan.sena.myapplication.Modelo;

import java.util.ArrayList;
import java.util.List;

public class Constantes {
    public static final String DATABASE="CreativasT.db";
    public static final int VERSION=1;
    public static final String scriptCreateTablesUser="CREATE TABLE USER (USERNAME TEXT PRIMARY KEY, CLAVE TEXT);";
    public static final String scriptCreateTablesInstructor="CREATE TABLE INSTRUCTOR (IDINSTRUCTOR INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, APELLIDO TEXT, TELEFONO TEXT, CORREO TEXT, LIDER TEXT)";
    public static final String scriptCreaeTablesPrograma="CREATE TABLE PROGRAMA (NOMBRE TEXT PRIMARY KEY, DESCRIPCION TEXT, VIDEO TEXT, ICONO TEXT)";
    public static final String scriptCreateTablesHorario="CREATE TABLE HORARIO (IDHORARIO INTEGER PRIMARY KEY AUTOINCREMENT,DIA TEXT, HORA TEXT,INSTRUCTOR INTEGER, FICHA TEXT, PROGRAMA TEXT, \n" +
            "FOREIGN KEY (INSTRUCTOR) REFERENCES INSTRUCTOR (IDINSTRUCTOR)," +
            "FOREIGN KEY (PROGRAMA) REFERENCES PROGRAMA (NOMBRE));";

}
