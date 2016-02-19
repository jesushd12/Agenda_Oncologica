package com.example.jesus.agendaoncologica.fragmentos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.Alergia;
import com.example.jesus.agendaoncologica.HistoriaPaciente;
import com.example.jesus.agendaoncologica.Paciente;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jesus on 1/16/2016.
 */
public class FragmentoHistoriaPaciente extends Fragment{

    TextView nombrePaciente;
    TextView fechaNacimiento;
    EditText condicion;
    EditText tipoDeSangre;
    EditText estatura;
    EditText peso;
    ArrayList<Alergia> alergias;
    DatabaseManager DB;
    boolean flag;
    Button misAlergias;


    SimpleDateFormat formato2 = new SimpleDateFormat("MMMM dd, yyyy",new Locale("es","ES"));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.historia_paciente, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Historia Medica");
        setHasOptionsMenu(true);
        flag = false;
        DB = new DatabaseManager(getContext());
        alergias = DB.consultarAlergias();
        nombrePaciente = (TextView) v.findViewById(R.id.edt_nombrePacienteH);
        fechaNacimiento = (TextView) v.findViewById(R.id.edt_fechaNacimientoH);
        condicion = (EditText) v.findViewById(R.id.edt_condicionMedica);
        tipoDeSangre = (EditText) v.findViewById(R.id.edt_tipoDeSangre);
        estatura = (EditText) v.findViewById(R.id.edt_estatura);
        peso = (EditText) v.findViewById(R.id.edt_peso);


        Paciente paciente = DB.consultarDatosPaciente();
        if (paciente != null) {
            nombrePaciente.setText(paciente.getNombre());
            fechaNacimiento.setText(formato2.format(paciente.getFechaNacimiento()) + " (" + paciente.obtenerEdad() + ")");
            condicion.setText(paciente.obtenerCondicion());
            tipoDeSangre.setText(paciente.obtenerTipoSangre());
            estatura.setText(paciente.obtenerEstatura());
            peso.setText(paciente.obtenerPeso());
        }

        desahabilitarCampos();

        misAlergias = (Button)v.findViewById(R.id.boton_alergico);
        misAlergias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentoAlergia alergy = new FragmentoAlergia();
                fm.beginTransaction().replace(R.id.contain_frame,alergy).addToBackStack( "tag" ).commit();
            }
        });


        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if(!flag){
                Toast.makeText(getContext(),"No ha hecho ningun cambio",Toast.LENGTH_SHORT).show();
                return false;
            }
            HistoriaPaciente historia;
            historia = new HistoriaPaciente();
            if(condicion.getText().toString().equalsIgnoreCase("")){
                return true;
            }
            historia.setCondicion(condicion.getText().toString());
            historia.setTipoSangre(tipoDeSangre.getText().toString());
            historia.setEstatura(estatura.getText().toString());
            historia.setPeso(peso.getText().toString());
            if (DB.consultarDatosPaciente() != null) {
                DB.actualizarHistoriaPaciente(historia);
                Toast.makeText(getContext(),"Su informacion ha sido guardada exitosamente",Toast.LENGTH_SHORT).show();
                desahabilitarCampos();
                return true;
            }
            Toast.makeText(getContext(),"Por favor registre sus datos personales",Toast.LENGTH_SHORT).show();




        }
        if (id == R.id.action_edit) {
            flag = true;
            habilitarCampos();
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

    private void desahabilitarCampos(){
        condicion.setEnabled(false);
        tipoDeSangre.setEnabled(false);
        estatura.setEnabled(false);
        peso.setEnabled(false);
    }

    private void habilitarCampos(){
        condicion.setEnabled(true);
        tipoDeSangre.setEnabled(true);
        estatura.setEnabled(true);
        peso.setEnabled(true);
    }


}
