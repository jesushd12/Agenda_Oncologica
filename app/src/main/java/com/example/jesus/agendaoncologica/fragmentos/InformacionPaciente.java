package com.example.jesus.agendaoncologica.fragmentos;

import android.support.v4.app.Fragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.example.jesus.agendaoncologica.*;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

/**
 * Created by Jesus on 1/31/2016.
 */
public class InformacionPaciente extends Fragment {
    private AutoCompleteTextView nombreUsuario;
    Paciente paciente;
    Paciente pacienteNuevo;
    EditText nombre;
    EditText apellidoUsuario;
    EditText cedulaUsuario;
    EditText fechaNacimientoUsuario;
    EditText lugarNacimientoUsuario;
    EditText tlfContactoUsuario;
    EditText tlfContactoUsuario2;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    DatePickerDialog fechaExamen;
    Calendar c ;
    DatabaseManager DB;
    ViewGroup layout;
    Spinner sexo;
    boolean flag ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.informacion_paciente,container,false);
        setHasOptionsMenu(true);

        DB = new DatabaseManager(getActivity());
        paciente = DB.consultarDatosPaciente();

        flag = false;

        // UI
        nombre = (EditText)v.findViewById(R.id.edt_nombre);
        apellidoUsuario = (EditText)v.findViewById(R.id.edt_apellido);
        cedulaUsuario = (EditText)v.findViewById(R.id.edt_cedula);
        sexo = (Spinner)v.findViewById(R.id.spin_sexo);
        fechaNacimientoUsuario = (EditText)v.findViewById(R.id.edt_fechaNacimiento);
        lugarNacimientoUsuario = (EditText)v.findViewById(R.id.edt_lugarNacimiento);
        tlfContactoUsuario = (EditText)v.findViewById(R.id.edt_tlfContacto);
        tlfContactoUsuario2 = (EditText)v.findViewById(R.id.edt_tlfContacto2);
        Button mEmailSignInButton = (Button) v.findViewById(R.id.email_sign_in_button);
        nombreUsuario = (AutoCompleteTextView) v.findViewById(R.id.emailUsuario);

        // adapter para el Spinner de seleccionar Sexo
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(adapter);
        //Mostrar calendario para seleccion de fecha de nacimiento
        fechaNacimientoUsuario.setInputType(InputType.TYPE_NULL);
        fechaNacimientoUsuario.requestFocus();
        fechaExamen = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                fechaNacimientoUsuario.setText(formatter.format(c.getTime()));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        fechaNacimientoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaExamen.show();
            }
        });


        //Si ya existen datos del paciente
        if(paciente != null) {
            nombre.setText(paciente.getNombre());
            apellidoUsuario.setText(paciente.getApellido());
            cedulaUsuario.setText(paciente.getCedula());
            fechaNacimientoUsuario.setText(formatter.format(paciente.getFechaNacimiento()));
            lugarNacimientoUsuario.setText(paciente.getLugarNacimiento());
            tlfContactoUsuario.setText(paciente.getTelefonocontacto1());
            tlfContactoUsuario2.setText(paciente.getTelefonocontacto2());
            nombreUsuario.setText(paciente.getEmail());
            if (paciente.getSexo().equalsIgnoreCase("femenino") || paciente.getSexo().equalsIgnoreCase("f"))
                sexo.setSelection(0);
            if (paciente.getSexo().equalsIgnoreCase("masculino") || paciente.getSexo().equalsIgnoreCase("m"))
                sexo.setSelection(1);
        }


        layout = (ViewGroup)mEmailSignInButton.getParent();
        layout.removeView(mEmailSignInButton);

        //Deshabilitar botones
        nombre.setEnabled(false);
        apellidoUsuario.setEnabled(false);
        cedulaUsuario.setEnabled(false);
        sexo.setEnabled(false);
        fechaNacimientoUsuario.setEnabled(false);
        lugarNacimientoUsuario.setEnabled(false);
        tlfContactoUsuario.setEnabled(false);
        tlfContactoUsuario2.setEnabled(false);
        nombreUsuario.setEnabled(false);

        return v;
    }

    public boolean validarCampos(){
        if(nombre.getText().toString().equals("") ||apellidoUsuario.getText().equals(""))
            return false;
        if(cedulaUsuario.getText().toString().equals(""))
            return false;
        if(fechaNacimientoUsuario.getText().toString().equals("") || lugarNacimientoUsuario.getText().equals(""))
            return false;
        if(tlfContactoUsuario.getText().toString().equals("") || tlfContactoUsuario2.getText().equals(""))
            return false;
        if(nombreUsuario.getText().toString().equals("") )
            return false;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if(!flag){
                Toast.makeText(getContext(),"No ha hecho ningun cambio",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(!validarCampos()) {
                Toast.makeText(getContext(),"Por favor completa todos los datos",Toast.LENGTH_SHORT).show();
                return false;
            }
            try {
                pacienteNuevo = new Paciente();
                pacienteNuevo.setNombre(nombre.getText().toString());
                pacienteNuevo.setApellido(apellidoUsuario.getText().toString());
                pacienteNuevo.setCedula(cedulaUsuario.getText().toString());
                pacienteNuevo.setFechaNacimiento(formatter.parse(fechaNacimientoUsuario.getText().toString()));
                pacienteNuevo.setLugarNacimiento(lugarNacimientoUsuario.getText().toString());
                pacienteNuevo.setTelefonocontacto1(tlfContactoUsuario.getText().toString());
                pacienteNuevo.setTelefonocontacto2(tlfContactoUsuario2.getText().toString());
                pacienteNuevo.setEmail(nombreUsuario.getText().toString());
                pacienteNuevo.setSexo(sexo.getSelectedItem().toString());


            } catch (ParseException e) {
                e.printStackTrace();
            }
            DB.insertarUsuario(pacienteNuevo);
            getActivity().onBackPressed();
        }
        if (id == R.id.action_edit) {
            flag = true;
            nombre.setEnabled(true);
            apellidoUsuario.setEnabled(true);
            cedulaUsuario.setEnabled(true);
            sexo.setEnabled(true);
            fechaNacimientoUsuario.setEnabled(true);
            lugarNacimientoUsuario.setEnabled(true);
            tlfContactoUsuario.setEnabled(true);
            tlfContactoUsuario2.setEnabled(true);
            nombreUsuario.setEnabled(true);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.examen_main, menu);
        menu.removeItem(R.id.action_delete);
        menu.removeItem(R.id.action_share);

    }
}
