package com.example.jesus.agendaoncologica.database;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Jesus on 1/31/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "agenda_database.sqlite"; // Nombre del archivo de la Base de datos
    private static final int DB_SCHEME_NAME = 1; // Version del esquema de la Base de datos
    public static final String[] tipoexamen = {"Hematologia","Orina","Heces","Coagulacion","Quimica Sanguinea","Hormonas","Marcadores tumorales","Inmunologia","Bacteriologia"};
    public static final String[] analisisOrina = {"Calcio","Fosforo","Acido Furico","Depuracion de creatinina","Proteinuria","Sodio","Potasio","Recuento Minutado","Relacion Cal/Creatina","Relacion Calcio urico/Creatinina"};
    public static final String[] analisisHeces = {"Azucares Reductores","Sangre Oculta","Leucocitos","PH"};
    public static final String[] analisisCoagulacion = {"Tiempo de protombina","Tiempo Parcial de trombo plastina","Tiempo de trombina","Fibrinogeno"};
    public static final String[] analisisQuimicaSanguiena = {"Glicemia","Urea","Creatinina","Acido Urico","Colesterol Total","HDL Colesterol","LDL Colesterol","VLDL Colesterol","Trigliceridos","Glicemia POST Pandrial","Curva de tolerancia Glucosa","Hemoglobina glicosilada","Calcio","Fosforo","Sodio","Potasio","Litio","Cloro","Magnesio","Proteinas totales","Proteinas Fraccionadas","Transaminasa O","Transaminasa P","Fosfatasas Alcalina","Fosfatasas Acidas","GGT","CPK-Total","CPK-MB","Amilasa","Hierro","Gases Arteriales"};
    public static final String[] analisisHormonas = {"T4L","T3L","TSH","FSH","LH","Estradiol","Prolactina","Progesterona","Testosterona","Cortisol","Insulina","Peptido - C","HCG Cuantificada","Hormonas de crecimiento"};
    public static final String[] analisisMarcadoresTumorales = {"Antigeno prostatico total","Antigeno prostatico libre","Antigeno carcinoembrionario","Alfa fetoproteinas","CA 19-9","CA 15-3","CA 125","Tiroglobulina","Calcitonina"};
    public static final String[] analisisInmunologia = {"HIV","VDLR","RA-Test","PCR","Antigenos febriles","Antiestreptolisina O","Virus Epsteinbarr lg6","Tosoplasma lgC","Tosoplasma lgM","Helicobacter pilory","Citomegalovirus lgM","Chlamydia Trachomatis","Dengue","Crioglobulinas","Complemento C3","Complemento C4","Inmunoglobulina A","Inmunoglobulina M","Inmunoglobulina G","Inmunoglobulina E","Anti-DSNA","Ana","Complemento Hemolitico Total","Antigeno de superficie","Anti-Core","Hepatitis A lgM","Hepatitis C","Chagas","Mycoplasma PneumoniaE","Leptospira","Herpes I","Herpes II","HIV Confirmatorio"};
    public static final String[] analisisBacteriologia = {"GRAM","BIK","KCH","Rotavirus","Phadebatc","Hemocultivo","Urocultivo","Coprocultivo"};


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseManager.TABLA_PACIENTE);
        db.execSQL(DatabaseManager.TABLA_MEDICO);
        db.execSQL(DatabaseManager.TABLA_CONTACTOS);
        db.execSQL(DatabaseManager.TABLA_TRATAMIENTO);
        db.execSQL(DatabaseManager.TABLA_NOTAS);
        db.execSQL(DatabaseManager.TABLA_ALERGIA);
        db.execSQL(DatabaseManager.TABLA_TIPO_EXAMEN);
        db.execSQL(DatabaseManager.TABLA_ANALISIS_EXAMEN2);
        db.execSQL(DatabaseManager.TABLA_EXAMEN);
        db.execSQL(DatabaseManager.TABLA_RESULTADOS_EXAMEN2);



    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

