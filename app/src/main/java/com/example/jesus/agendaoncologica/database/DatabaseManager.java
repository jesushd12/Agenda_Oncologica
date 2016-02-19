package com.example.jesus.agendaoncologica.database;

/**
 * Created by Jesus on 1/31/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jesus.agendaoncologica.Alergia;
import com.example.jesus.agendaoncologica.AnalisisExamen;
import com.example.jesus.agendaoncologica.Contactos;
import com.example.jesus.agendaoncologica.Examen;
import com.example.jesus.agendaoncologica.HistoriaPaciente;
import com.example.jesus.agendaoncologica.Medico;
import com.example.jesus.agendaoncologica.Nota;
import com.example.jesus.agendaoncologica.Paciente;
import com.example.jesus.agendaoncologica.ResultadoExamen;
import com.example.jesus.agendaoncologica.TipoExamen;
import com.example.jesus.agendaoncologica.Tratamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Jesus on 12/3/2015.
 */
public class DatabaseManager {
    final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public static final String TABLA_PACIENTE = " create table paciente ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "nombre " + " varchar (25) not null, "
            + "apellido " + " varchar (25) not null, "
            + "cedula " + " varchar (25) not null, "
            + "sexo " + " varchar (25) not null, "
            + "fechaNacimiento " + " date not null, "
            + "lugarNacimiento " + " varchar (25) not null, "
            + "email " + " varchar (25) not null, "
            + "telefonoContacto1 " + " varchar (25) not null, "
            + "telefonoContacto2 " + " varchar (25), "
            + "numerohistoria " + " varchar (25), "
            + "condicion " + " varchar (25), "
            + "tipodesangre " + " varchar (15), "
            + "peso " + " varchar (25), "
            + "estatura " + " varchar (25) );";


    public static final String TABLA_MEDICO = " create table medico ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "prefijo " + " varchar (2) not null, "
            + "nombre " + " varchar (25) not null, "
            + "apellido " + " varchar (25) not null, "
            + "email " + " varchar (25) not null, "
            + "ubicacion " + " varchar (25) not null, "
            + "telefonoContacto1 " + " varchar (25) not null, "
            + "telefonoContacto2 " + " varchar (25)  );";

    public static final String TABLA_CONTACTOS = " create table contacto ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "nombre " + " varchar (25) not null, "
            + "numero " + " varchar (25) not null );";


    public static final String TABLA_TRATAMIENTO = " create table tratamiento ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "fecha " + " date not null, "
            + "hora " + " varchar (25) , "
            + "tipoTratamiento " + " varchar (25)  );";


    public static final String TABLA_NOTAS = " create table nota ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "fecha " + " date not null, "
            + "titulo " + " varchar (25) not null, "
            + "examenAsociado " + " integer REFERENCES tratamiento(_id) on delete cascade on update cascade, "
            + "nota " + " text  );";


    public static final String TABLA_ALERGIA = " create table alergia ( "
            + "_id" + "  integer primary key autoincrement,  "
            + "alergia" + " varchar (25) not null  );";

    public static final String TABLA_TIPO_EXAMEN = " create table tipoexamen ( "
            + "_id" + "  integer,  "
            + "tipoexamen" + " varchar (25)  primary key  );";


    public static final String TABLA_ANALISIS_EXAMEN = " create table analisisexamen ( "
            + "analisis" + "  varchar (25) ,  "
            + "tipoexamen" + " varchar (25) not null REFERENCES tipoexamen(tipoexamen) on delete cascade on update cascade," +
            "primary key(analisis)  );";

    public static final String TABLA_ANALISIS_EXAMEN2 = " create table analisisexamen ( "
            + "analisis" + "  varchar (25) ,  "
            + "tipoexamen" + " varchar (25) not null REFERENCES tipoexamen(tipoexamen) on delete cascade on update cascade," +
            "foreign key(tipoexamen) references tipoexamen(tipoexamen),"
            +"primary key(analisis,tipoexamen)  );";

    public static final String TABLA_EXAMEN = " create table examen ( "
            + "_id" + "   integer primary key autoincrement,  "
            + "fecha " + " date not null, "
            + "examenAsociado " + " integer REFERENCES tratamiento(_id) on delete cascade on update cascade );";


    public static final String TABLA_RESULTADOS_EXAMEN = " create table resultado ( "
            + "_id" + "   integer  not null REFERENCES examen(_id) on delete cascade on update cascade,"
            + "tipoexamen"  + " varchar (25) not null REFERENCES tipoexamen(tipoexamen) on delete cascade on update cascade,"
            + "analisis"  + " varchar (25) not null REFERENCES analisisexamen(analisis) on delete cascade on update cascade,"
            + "valor" + "  integer ,  "
            +"primary key(_id,tipoexamen,analisis)  );";

    public static final String TABLA_RESULTADOS_EXAMEN2 = " create table resultado ( "
            + "_id" + "   integer  ,"
            + "tipoexamen"  + " varchar (25),"
            + "analisis"  + " varchar (25),"
            + "valor" + "  integer ,  "
            +"foreign key(_id) references examen(_id) on delete cascade on update cascade,"
            +"foreign key(analisis,tipoexamen) references analisisexamen(analisis,tipoexamen) on delete cascade on update cascade,"
            +"primary key(_id,tipoexamen,analisis)  );";



    /*
    public static final  String TABLA_MEDICAMENTO = " create table alergias ( "
            + "_id " + "  integer primary key autoincrement,  "
            + "medicamento " + " varchar (25) not null  );" ;


*/


    private DataBaseHelper helper;
    private SQLiteDatabase db;

    /* Si la base de datos no existe se crea y se devuelve en modo escritura, y si
     * existe se devuelve en modo lectura */
    public DatabaseManager(Context context) {
        helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    /*- - - - - - - - - - - - - - - - - -PACIENTE - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    //Insertar datos paciente
    public void insertarUsuario(Paciente paciente) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", paciente.getNombre());
        valores.put("apellido", paciente.getApellido());
        valores.put("cedula", paciente.getCedula());
        valores.put("sexo", paciente.getSexo());
        valores.put("fechaNacimiento", formatter.format(paciente.getFechaNacimiento()));
        valores.put("lugarNacimiento", paciente.getLugarNacimiento());
        valores.put("email", paciente.getEmail());
        valores.put("telefonoContacto1", paciente.getTelefonocontacto1());
        valores.put("telefonoContacto2", paciente.getTelefonocontacto2());
        db.insert("paciente", null, valores);

    }

    //Consultar datos paciente

    public Paciente consultarDatosPaciente() {
        Paciente paciente;
        HistoriaPaciente historia;
        String[] campos = {"nombre", "apellido", "cedula", "fechaNacimiento", "lugarNacimiento", "sexo", "email", "telefonoContacto1", "telefonoContacto2", "numerohistoria", "condicion", "peso", "estatura", "tipodesangre"};
        Cursor c = db.query("paciente", campos, "_id=1", null, null, null, null);
        Calendar calendar = Calendar.getInstance();
        if (c.moveToFirst()) {
            do {
                paciente = new Paciente();
                paciente.setNombre(c.getString(0));
                paciente.setApellido(c.getString(1));
                paciente.setCedula(c.getString(2));
                try {
                    paciente.setFechaNacimiento(formatter.parse(c.getString(3)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                paciente.setLugarNacimiento(c.getString(4));
                paciente.setSexo(c.getString(5));
                paciente.setEmail(c.getString(6));
                paciente.setTelefonocontacto1(c.getString(7));
                paciente.setTelefonocontacto2(c.getString(8));
                historia = new HistoriaPaciente();
                historia.setNumerohistoria(c.getString(9));
                historia.setCondicion(c.getString(10));
                historia.setPeso(c.getString(11));
                historia.setEstatura(c.getString(12));
                historia.setTipoSangre(c.getString(13));
                paciente.setHistoria(historia);
            } while (c.moveToNext());
            return paciente;
        }
        return null;
    }

    /*Modificar datos paciente*/
    public void actualizarPaciente(Paciente paciente) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", paciente.getNombre());
        valores.put("apellido", paciente.getApellido());
        valores.put("cedula", paciente.getCedula());
        valores.put("sexo", paciente.getSexo());
        valores.put("fechaNacimiento", formatter.format(paciente.getFechaNacimiento()));
        valores.put("lugarNacimiento", paciente.getLugarNacimiento());
        valores.put("email", paciente.getEmail());
        valores.put("telefonoContacto1", paciente.getTelefonocontacto1());
        valores.put("telefonoContacto2", paciente.getTelefonocontacto2());
        String[] campo = {"1"};
        db.update("paciente", valores, "_id=?", campo);
    }

    /*Modificar historia paciente*/
    public void actualizarHistoriaPaciente(HistoriaPaciente historiaPaciente) {
        ContentValues valores = new ContentValues();
        valores.put("numerohistoria", historiaPaciente.getNumerohistoria());
        valores.put("peso", historiaPaciente.getPeso());
        valores.put("estatura", historiaPaciente.getEstatura());
        valores.put("tipodesagre", historiaPaciente.getTipoSangre());
        valores.put("condicion", historiaPaciente.getCondicion());
        String[] campo = {"1"};
        db.update("paciente", valores, "_id=?", campo);
    }


    /*- - - - - - - - - - - - - - - - - - FIN PACIENTE - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */


    /*- - - - - - - - - - - - - - - - - - MEDICO - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
     /*Insertar medico en la BD utilizando los metodos de android*/
    public void insertarMedico(Medico medico) {
        ContentValues valores = new ContentValues();
        valores.put("prefijo", medico.getPrefijo());
        valores.put("nombre", medico.getNombre());
        valores.put("apellido", medico.getApellido());
        valores.put("email", medico.getEmail());
        valores.put("ubicacion", medico.getUbicacion());
        valores.put("telefonoContacto1", medico.getNumeroTelefonoOpcion1());
        valores.put("telefonoContacto2", medico.getNumeroTelefonoOpcion2());
        db.insert("medico", null, valores);

    }

    /*Modificar datos Medico*/
    public void actualizarMedico(Medico medico) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", medico.getNombre());
        valores.put("apellido", medico.getApellido());
        valores.put("email", medico.getEmail());
        valores.put("telefonoContacto1", medico.getNumeroTelefonoOpcion1());
        valores.put("telefonoContacto2", medico.getNumeroTelefonoOpcion2());
        String[] campo = {"1"};
        System.out.println(medico.toString());
        db.update("medico", valores, "_id=?", campo);

    }

    /*Consultar datos medico*/
    public Medico consultarDatosMedico() {
        Medico medico;
        String[] campos = {"prefijo", "nombre", "apellido", "email", "ubicacion", "telefonoContacto1", "telefonoContacto2"};
        Cursor c = db.query("medico", campos, "_id=1", null, null, null, null);
        if (c.moveToFirst()) {

            do {

                medico = new Medico(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(5), c.getString(6), c.getString(4));
            } while (c.moveToNext());
            return medico;
        }

        return null;

    }


    /*- - - - - - - - - - - - - - - - - - FIN MEDICO - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */


    /*- - - - - - - - - - - - - - - - - - Contactos Emergencia - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
       /*Insertar medico en la BD utilizando los metodos de android*/
    public void insertarContacto(Contactos contacto) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", contacto.getNombre());
        valores.put("numero", contacto.getNumero());
        db.insert("contacto", null, valores);

    }

    /*Modificar datos Medico*/
    public void actualizarcontacto(Contactos contacto) {
        ContentValues valores = new ContentValues();
        valores.put("nombre", contacto.getNombre());
        valores.put("numero", contacto.getNumero());
        String[] campo = {Integer.toString(contacto.getId())};
        db.update("contacto", valores, "_id=?", campo);

    }

    /*Consultar tratamiento completo */
    public ArrayList<Contactos> consultarContacto() {
        String[] campos = {"_id", "nombre", "numero"};
        Cursor c = db.query("contacto", campos, null, null, null, null, null);
        ArrayList<Contactos> contactos = new ArrayList<>();
        Contactos contactoInd;
        if (c.moveToFirst()) {

            do {
                contactoInd = new Contactos(c.getString(1), c.getString(2));
                contactoInd.setId(c.getInt(0));
                contactos.add(contactoInd);
            } while (c.moveToNext());
            return contactos;
        }
        return null;

    }

    /*Eliminar Contacto*/
    public void eliminarContacto(int id) {
        String[] campos = {Integer.toString(id)};
        db.delete("contacto", "_id=?", campos);
    }


     /*- - - - - - - - - - - - - - - - - -TRATAMIENTOS - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    /*Insertar trtamientoen la BD utilizando los metodos de android*/
    public void insertarTratamiento(String fecha, String tratamiento) {
        ContentValues valores = new ContentValues();
        valores.put("fecha", fecha);
        valores.put("tipoTratamiento", tratamiento);
        db.insert("tratamiento", null, valores);

    }

    /*Consultar fecha tratamiento*/
    public boolean consultarFechaTratamiento(String fecha) {
        String[] campos = {"_id", "fecha"};
        String[] args = {fecha};
        Cursor c = db.query("tratamiento", campos, "fecha=?", args, null, null, null);
        if (c.moveToFirst()) {
            return true;
        }
        return false;
    }

    /*Consultar tipo tratamiento devuelve 0 si es radioterapia y  1 si es quimioterapia */
    public int consultarTipoTratamiento(String fecha) {
        String[] campos = {"_id", "fecha", "tipoTratamiento"};
        String[] args = {fecha};
        Cursor c = db.query("tratamiento", campos, "fecha=?", args, null, null, null);
        if (c.moveToFirst()) {

            if (c.getString(2).equalsIgnoreCase("Radioterapia")) {
                return 0;
            }

        }
        return 1;
    }


    /*Consultar tipo tratamiento devuelve 0 si es radioterapia y  1 si es quimioterapia */
    public Tratamiento consultarTratamiento(int id) {
        Tratamiento tratamiento = null;
        String[] campos = {"_id", "fecha", "tipoTratamiento", "hora"};
        String[] args = {Integer.toString(id)};
        Cursor c = db.query("tratamiento", campos, "_id=?", args, null, null, null);
        if (c.moveToFirst()) {

            do {
                tratamiento = new Tratamiento(c.getInt(0), c.getString(1), c.getString(2));
                tratamiento.setHora(c.getString(3));
            } while (c.moveToNext());

        }
        return tratamiento;
    }

    /*Consultar tratamiento completo */
    public ArrayList<Tratamiento> pedirTratamientosCompleto() {
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String[] campos = {"_id", "fecha", "tipoTratamiento"};
        Cursor c = db.query("tratamiento", campos, null, null, null, null, null);
        ArrayList<Tratamiento> tratamientos = new ArrayList<Tratamiento>();

        Tratamiento tratamientoInd = new Tratamiento();
        if (c.moveToFirst()) {

            do {
                tratamientoInd = new Tratamiento(c.getInt(0), c.getString(1), c.getString(2));
                tratamientos.add(tratamientoInd);
            } while (c.moveToNext());

        }

        return tratamientos;
    }

    /*Consultar fecha tratamiento*/
    public ArrayList<String> pedirFechaTratamiento() {
        ArrayList<String> fechas_tratamientos = new ArrayList<String>();
        String[] campos = {"_id", "fecha"};
        Cursor c = db.query("tratamiento", campos, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
                fechas_tratamientos.add(c.getString(1));
            } while (c.moveToNext());
        }
        return fechas_tratamientos;
    }

    /*Eliminar fecha de tratamiento*/
    public void eliminarFechaTratamiento(String fecha) {
        String[] fechaAborrar = {fecha};
        db.delete("tratamiento", "fecha=?", fechaAborrar);
    }

    /*Postergar fecha tratamiento*/
    public void porstergarFechaTratamiento(String fechaVieja, String fechaNueva) {
        ContentValues args = new ContentValues();
        args.put("fecha", fechaNueva);
        String[] campo = {fechaVieja};
        db.update("tratamiento", args, "fecha=?", campo);

    }

    /*actualizarHora tratamiento*/
    public void actualizarHora(String fecha, String hora) {
        ContentValues args = new ContentValues();
        args.put("hora", hora);
        String[] campo = {fecha};
        db.update("tratamiento", args, "fecha=?", campo);

    }


    /*- - - - - - - - - - - - - - - - - -FIN TRATAMIENTO - - - - - - - - - - - - - - - - - - - - - - - - - */

/*- - - - - - - - - - - - - - - - - -NOTAS - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    /*Insertar nota*/

    public void insertarNota(Nota nota) {
        ContentValues valores = new ContentValues();
        valores.put("fecha", nota.obtenerFechaFormateada());
        valores.put("titulo", nota.getTitulo());
        valores.put("nota", nota.getNota());
        if (nota.getIdExamenAsociado() != -1) {
            valores.put("examenAsociado", nota.getIdExamenAsociado());
        }
        db.insert("nota", null, valores);
    }


    /*Obtener todas las notas*/
    public ArrayList<Nota> pedirTodasLasNotas() {
        ArrayList<Nota> todasLasNotas = new ArrayList<>();
        Nota nota;
        HashMap<Integer, String> misNotas = new HashMap<Integer, String>();
        ArrayList<String> tituloTratamiento = new ArrayList<String>();
        String[] campos = {"_id", "titulo", "fecha"};
        Cursor c = db.query("nota", campos, null, null, null, null, null);
        if (c.moveToFirst()) {

            do {
                nota = new Nota();
                nota.setId(c.getInt(0));
                nota.setTitulo(c.getString(1));
                nota.setFechaFormateada(c.getString(2));
                todasLasNotas.add(nota);
            } while (c.moveToNext());
            return todasLasNotas;
        }

        return todasLasNotas;

    }


    /*Obtener todas las notas*/
    public ArrayList<Nota> pedirTodasLasNotasPorTratamiento(int id) {
        ArrayList<Nota> todasLasNotas = new ArrayList<>();
        Nota nota;
        HashMap<Integer, String> misNotas = new HashMap<Integer, String>();
        ArrayList<String> tituloTratamiento = new ArrayList<String>();
        String[] campos = {"_id", "titulo", "fecha"};
        String[] argumentos = {Integer.toString(id)};
        Cursor c = db.query("nota", campos, "examenAsociado=?", argumentos, null, null, null);
        if (c.moveToFirst()) {
            do {
                nota = new Nota();
                nota.setId(c.getInt(0));
                nota.setTitulo(c.getString(1));
                nota.setFechaFormateada(c.getString(2));
                todasLasNotas.add(nota);
            } while (c.moveToNext());
            return todasLasNotas;
        }
        return null;
    }

    /*Consultar notas individual*/
    public Nota pedirNotas(int id) {
        Nota nota = new Nota();
        String[] campos = {"_id", "titulo", "nota"};
        String[] campoAbuscar = {Integer.toString(id)};
        Cursor c = db.query("nota", campos, "_id=?", campoAbuscar, null, null, null);
        if (c.moveToFirst()) {
            do {
                nota.setId(c.getInt(0));
                nota.setTitulo(c.getString(1));
                nota.setNota(c.getString(2));
            } while (c.moveToNext());
            return nota;
        }
        nota = null;
        return nota;
    }


    /*Actualizar nota*/

    public void actualizarNota(Nota nota) {
        ContentValues args = new ContentValues();
        args.put("fecha", nota.obtenerFechaFormateada());
        args.put("titulo", nota.getTitulo());
        args.put("nota", nota.getNota());
        String[] campo = {Integer.toString(nota.getId())};
        db.update("nota", args, "_id=?", campo);
    }


    /*Eliminar fecha de tratamiento*/
    public void eliminarNotas(int id) {
        String[] campos = {Integer.toString(id)};
        db.delete("nota", "_id=?", campos);
    }
    /*- - - - - - - - - - - - - - - - - -FIN NOTAS - - - - - - - - - - - - - - - - - - - - - - - - - */

     /*- - - - - - - - - - - - - - - - - -ALERGIA - - - - - - - - - - - - - - - - - - - - - - - - - */
    /*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

    /*Insertar Alergia*/
    public void insertarAlergia(Alergia alergia) {
        ContentValues valores = new ContentValues();
        valores.put("alergia", alergia.getNombrealergia());
        db.insert("alergia", null, valores);
    }

    public ArrayList<Alergia> consultarAlergias() {
        ArrayList<Alergia> alergias = new ArrayList<>();

        String[] campos = {"_id", "alergia"};
        Cursor c = db.query("alergia", campos, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Alergia alergia;
                alergia = new Alergia(c.getString(1), c.getInt(0));
                alergias.add(alergia);

            } while (c.moveToNext());
            return alergias;
        }
        return alergias;
    }

    public boolean consultarAlergiaEspecifica(String alergia) {

        String[] campos = {alergia};
        Cursor c = db.query("alergia", null, "alergia=?", campos, null, null, null);
        if (c.moveToFirst()) {
            do {
                return true;

            } while (c.moveToNext());
        }
        return false;
    }

    /*Eliminar alergia*/
    public void eliminarAlergia(String alergia) {
        String[] alergiaBorrar = {alergia};
        db.delete("alergia", "alergia=?", alergiaBorrar);
    }



/*- - - - - - - - - - - - - - - - - -FIN ALERGIA - - - - - - - - - - - - - - - - - - - - - - - - - */


    /*- - - - - - - - - - - - - - - - - -TIPO EXAMEN - - - - - - - - - - - - - - - - - - - - - - - - - */
    // Insertar nuevo tipo examen
    public void insertarTipoExamen(TipoExamen tipoExamen) {
        ContentValues valores = new ContentValues();
        valores.put("tipoexamen", tipoExamen.getNombre());
        db.insert("tipoexamen", null, valores);
    }


    // Verificar que el tipo de examen no existe
    public boolean existeTipoExamen(TipoExamen tipoExamen) {

        String[] campos = {tipoExamen.getNombre()};
        Cursor c = db.query("tipoexamen", null, "tipoexamen=?", campos, null, null, null);
        if (c.moveToFirst()) {
            do {
                return true;

            } while (c.moveToNext());
        }
        return false;
    }

    // Consultar tipo de examenes
    public ArrayList<TipoExamen> consultarTipoExamen() {
        ArrayList<TipoExamen> tipoExamen = null;

        String[] campos = {"tipoexamen"};
        Cursor c = db.query("tipoexamen", campos, null, null, null, null, null);
        if (c.moveToFirst()) {
            tipoExamen = new ArrayList<>();
            do {
                TipoExamen tipoExamenInd;
                tipoExamenInd = new TipoExamen(c.getString(0));
                tipoExamen.add(tipoExamenInd);

            } while (c.moveToNext());
            return tipoExamen;
        }
        return tipoExamen;
    }

    /*Eliminar tipo de examen*/
    public void eliminarTipoExamen(TipoExamen tipoExamen) {
        String[] tipoexamenAborrar = {tipoExamen.getNombre()};
        db.delete("tipoexamen", "tipoexamen=?", tipoexamenAborrar);
    }


    /*- - - - - - - - - - - - - - - - - -Analisis examen - - - - - - - - - - - - - - - - - - - - - - - - - */
    // Insertar nuevo tipo examen
    public void insertarAnalisis(AnalisisExamen analisisExamen) {
        ContentValues valores = new ContentValues();
        valores.put("analisis", analisisExamen.getAnalisis());
        valores.put("tipoexamen", analisisExamen.getTipoExamen().getNombre());
        db.insert("analisisexamen", null, valores);
    }

    // Verificar que el Analisis no existe
    public boolean existeAnalisis(AnalisisExamen analisisExamen) {

        String[] campos = {analisisExamen.getAnalisis()};
        Cursor c = db.query("analisisexamen", null, "analisis=?", campos, null, null, null);
        if (c.moveToFirst()) {
            do {
                return true;
            } while (c.moveToNext());
        }
        return false;
    }

    // Consultar analisis de examenes
    public ArrayList<AnalisisExamen> consultarAnalisisTodos() {
        ArrayList<AnalisisExamen> analisisExamenes = null;

        String[] campos = {"tipoexamen", "analisis"};
        Cursor c = db.query("analisisexamen", campos, null, null, null, null, null);
        if (c.moveToFirst()) {
            analisisExamenes = new ArrayList<>();
            do {
                AnalisisExamen analisisExamenInd;
                TipoExamen tipoExamen = new TipoExamen(c.getString(0));
                analisisExamenInd = new AnalisisExamen(c.getString(1), tipoExamen);
                analisisExamenes.add(analisisExamenInd);

            } while (c.moveToNext());
            return analisisExamenes;
        }
        return null;
    }


    // Consultar analisis de examenes
    public ArrayList<AnalisisExamen> consultarAnaliisPortipo(TipoExamen tipoExamen) {
        ArrayList<AnalisisExamen> analisisExamenes = null;

        String[] campos = {"tipoexamen", "analisis"};
        String[] arg = {tipoExamen.getNombre()};
        Cursor c = db.query("analisisexamen", campos, "tipoexamen=?", arg, null, null, null);
        if (c.moveToFirst()) {
            analisisExamenes = new ArrayList<>();
            do {
                AnalisisExamen analisisExamenInd;
                analisisExamenInd = new AnalisisExamen(c.getString(1), tipoExamen);
                analisisExamenes.add(analisisExamenInd);
            } while (c.moveToNext());
            return analisisExamenes;
        }
        return null;
    }
    /*Eliminar analisis de examen*/
    public void eliminarAnalisisExamen(AnalisisExamen analisisExamen) {
        String[] analisisAborrar = {analisisExamen.getAnalisis(),analisisExamen.getTipoExamen().getNombre()};
        db.delete("analisisexamen", "analisis=? and tipoexamen=?", analisisAborrar);
    }


    /*- - - - - - - - - - - - - - - - - -examen - - - - - - - - - - - - - - - - - - - - - - - - - */
    // Insertar nuevo tipo examen
    public long insertarNuevoExamen(Examen examen) {
        ContentValues valores = new ContentValues();
        valores.put("fecha", formatter.format(examen.getFecha()));
        long id =  db.insert("examen", null, valores);
        return id;
    }

    /*Asociar tratamiento a resultado*/
    public void asociarTratamientoAExamen(Examen examen) {
        ContentValues valores = new ContentValues();
        valores.put("examenAsociado", examen.getIdTratamientoAsociado());
        String[] campo = {Integer.toString(examen.getId())};
        db.update("examen", valores, "_id=?", campo);
    }

    /*Asociar tratamiento a resultado*/
    public void actualizarExamen(Examen examen) {
        ContentValues valores = new ContentValues();
        valores.put("fecha", formatter.format(examen.getFecha()));
        String[] campo = {Integer.toString(examen.getId())};
        db.update("examen", valores, "_id=?", campo);
    }

    // Consultar examenes
    public ArrayList<Examen> consultasExamenes() {
        ArrayList<Examen> examenes = null;

        String[] campos = {"_id", "fecha"};
        Cursor c = db.query("examen", campos, null, null, null, null, null);
        if (c.moveToFirst()) {
            examenes = new ArrayList<>();
            do {
                Examen examenInd = new Examen();
                examenInd.setId(c.getInt(0));
                try {
                    examenInd.setFecha(formatter.parse(c.getString(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                examenes.add(examenInd);

            } while (c.moveToNext());
            return examenes;
        }
        return null;
    }

    // Consultar examenes
    public ArrayList<Examen> consultasExamenesPorTratamiento(int idTratamiento) {
        ArrayList<Examen> examenes = null;

        String[] campos = {"_id", "fecha"};
        String[] args = {Integer.toString(idTratamiento)};
        Cursor c = db.query("examen", campos, "examenAsociado=?", args, null, null, null);
        if (c.moveToFirst()) {
            examenes = new ArrayList<>();
            do {
                Examen examenInd = new Examen();
                examenInd.setId(c.getInt(0));
                try {
                    examenInd.setFecha(formatter.parse(c.getString(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                examenes.add(examenInd);

            } while (c.moveToNext());
            return examenes;
        }
        return null;
    }

    // Consultar examenes
    public Examen consultarExamenEspecifico(int id) {
        ArrayList<Examen> examenes = null;
        Examen examen;

        String[] campos = {"_id", "fecha"};
        String[] args = {Integer.toString(id)};
        Cursor c = db.query("examen", campos, "_id=?", args, null, null, null);
        if (c.moveToFirst()) {
            examenes = new ArrayList<>();
            do {
                examen = new Examen();
                examen.setId(c.getInt(0));
                try {
                    examen.setFecha(formatter.parse(c.getString(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());
            return examen;
        }
        return null;
    }

    /*Eliminar analisis de examen*/
    public void eliminarExamen(Examen examen) {
        String[] examenAborrar = {Integer.toString(examen.getId())};
        db.delete("examen", "_id=?", examenAborrar);
    }



    /*- - - - - - - - - - - - - - - - - -Resultado examen - - - - - - - - - - - - - - - - - - - - - - - - - */
    // Insertar nuevo tipo examen
    public void insertarNuevoResultado(ResultadoExamen examen) {
        ContentValues valores = new ContentValues();
        valores.put("_id",examen.getExamen().getId());
        valores.put("analisis",examen.getAnalisisExamen().getAnalisis());
        valores.put("tipoexamen",examen.getAnalisisExamen().getTipoExamen().getNombre());
         db.insert("resultado", null, valores);

    }

    //eliminar examen
    public void eliminarTipoExamenResultados(TipoExamen tipoExamen, int id) {
        String[] args = {Integer.toString(id),tipoExamen.getNombre()};
        db.delete("resultado", "_id=? and tipoexamen=?", args);
    }

    /*Actualizar resultado*/
    public void actualizarResultado(ResultadoExamen examen) {
        ContentValues valores = new ContentValues();
        valores.put("valor", examen.getValor());
        String[] campo = {Integer.toString(examen.getExamen().getId()),examen.getAnalisisExamen().getAnalisis()};
        db.update("resultado", valores, "_id=? and analisis=?", campo);

    }

    /*Actualizar resultado*/
    public void eliminarResultado(ResultadoExamen examen) {
        String[] campo = {Integer.toString(examen.getExamen().getId()),examen.getAnalisisExamen().getTipoExamen().getNombre(),examen.getAnalisisExamen().getAnalisis()};
        db.delete("resultado","_id=? and tipoexamen=? and analisis=?", campo);

    }


    // Consultar examenes
    public ArrayList<ResultadoExamen> consultarResultadoExamenes(int id,TipoExamen tipoExamen) {
        ArrayList<ResultadoExamen> resultadoExamenArrayList = null;
        ResultadoExamen resultadoExamen;
        AnalisisExamen analisisExamen;
        Examen examen;


        String[] campos = {"_id", "analisis","valor"};
        String[] args = {Integer.toString(id),tipoExamen.getNombre()};
        Cursor c = db.query("resultado", campos, "_id=? AND tipoexamen=?", args, null, null, null);
        if (c.moveToFirst()) {
            resultadoExamenArrayList = new ArrayList<>();
            do {
                analisisExamen = new AnalisisExamen();
                analisisExamen.setTipoExamen(tipoExamen);
                analisisExamen.setAnalisis(c.getString(1));
                examen = new Examen();
                examen.setId(c.getInt(0));
                resultadoExamen = new ResultadoExamen();
                resultadoExamen.setExamen(examen);
                resultadoExamen.setAnalisisExamen(analisisExamen);
                resultadoExamen.setValor(c.getString(2));
                resultadoExamenArrayList.add(resultadoExamen);

            } while (c.moveToNext());
            return resultadoExamenArrayList;
        }
        return null;
    }



    // Consultar tipos de examenes
    public ArrayList<TipoExamen> consultarResultadoExamenes(int id) {
        ArrayList<TipoExamen> tipoExamenArrayList = null;
        TipoExamen tipoExamen;
        AnalisisExamen analisisExamen;
        Examen examen;


        String[] campos = {"_id", "tipoexamen"};
        String[] args = {Integer.toString(id)};
        Cursor c = db.query("resultado", campos, "_id=?", args, "tipoexamen", null, null);
        if (c.moveToFirst()) {
            tipoExamenArrayList = new ArrayList<>();
            do {
                tipoExamen = new TipoExamen();
                tipoExamen.setNombre(c.getString(1));
                System.out.println("TIPO EXAMEN: "+c.getString(1));
                tipoExamenArrayList.add(tipoExamen);
            } while (c.moveToNext());
            return tipoExamenArrayList;
        }
        return null;
    }



}

