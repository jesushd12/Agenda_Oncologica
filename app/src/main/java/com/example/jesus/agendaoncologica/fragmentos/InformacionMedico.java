package com.example.jesus.agendaoncologica.fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.jesus.agendaoncologica.Medico;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;

/**
 * Created by Jesus on 2/1/2016.
 */
public class InformacionMedico extends Fragment {
    EditText nombre;
    EditText apellido;
    EditText email;
    EditText numeroTelefonoOpcion1;
    EditText numeroTelefonoOpcion2;
    EditText ubicacion;
    Spinner prefijoMedico;
    Button contactosemergencia;
    String prefijo;
    DatabaseManager db;
    boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.informacion_medico,container,false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Medico tratante");


        Medico medico;
        flag = false;
        prefijo = null;
        db = new DatabaseManager(getContext());

        // UI
        nombre = (EditText) v.findViewById(R.id.edt_nombreMedico);
        apellido = (EditText)v.findViewById(R.id.edt_apellidoMedico);
        email = (EditText) v.findViewById(R.id.edt_emailMedico);
        numeroTelefonoOpcion1 = (EditText)v.findViewById(R.id.edt_tlfContactoMedico);
        numeroTelefonoOpcion2 = (EditText)v.findViewById(R.id.edt_tlfContacto2Medico);
        ubicacion = (EditText)v.findViewById(R.id.edt_ubicacionMedico);
        prefijoMedico = (Spinner)v.findViewById(R.id.spin_prefijoMedico);
        contactosemergencia = (Button)v.findViewById(R.id.btn_contactos);
        contactosemergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.contain_frame,new ContactoEmergencia()).addToBackStack( "tag" ).commit();
            }
        });

        //Adapter para el prefijo del medico
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(),R.array.prefijoMedico,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefijoMedico.setAdapter(adapter);
        prefijoMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefijo = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                prefijo = " ";

            }
        });

        // Si existe un medico rellenar los campos con el medico guardado
        if((medico= db.consultarDatosMedico())!=null){
            nombre.setText(medico.getNombre());
            apellido.setText(medico.getApellido());
            email.setText(medico.getEmail());
            ubicacion.setText(medico.getUbicacion());
            numeroTelefonoOpcion1.setText(medico.getNumeroTelefonoOpcion1());
            numeroTelefonoOpcion2.setText(medico.getNumeroTelefonoOpcion2());

            if(medico.getPrefijo().equalsIgnoreCase("Dr."))
                prefijoMedico.setSelection(0);
            if(medico.getPrefijo().equalsIgnoreCase("Dra."))
                prefijoMedico.setSelection(1);
            if(medico.getPrefijo().equalsIgnoreCase("Enfermera"))
                prefijoMedico.setSelection(2);
        }

        //Deshabilitar los campos
        deshabilitarBotones();
        return v;
    }

    private void deshabilitarBotones(){
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        prefijoMedico.setEnabled(false);
        numeroTelefonoOpcion1.setEnabled(false);
        numeroTelefonoOpcion2.setEnabled(false);
        email.setEnabled(false);
        ubicacion.setEnabled(false);
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
            Medico medico  = new Medico();
            medico.setNombre(nombre.getText().toString());
            medico.setPrefijo(prefijoMedico.getSelectedItem().toString());
            medico.setApellido(apellido.getText().toString());
            medico.setEmail(email.getText().toString());
            medico.setNumeroTelefonoOpcion1(numeroTelefonoOpcion1.getText().toString());
            medico.setNumeroTelefonoOpcion2(numeroTelefonoOpcion2.getText().toString());
            medico.setUbicacion(ubicacion.getText().toString());
            if(db.consultarDatosMedico()!=null){
                db.actualizarMedico(medico);
            }else{
                db.insertarMedico(medico);
            }

            Toast.makeText(getContext(),"Sus datos han sido guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
            return true;

        }
        if (id == R.id.action_edit) {
            flag = true;
            nombre.setEnabled(true);
            apellido.setEnabled(true);
            prefijoMedico.setEnabled(true);
            numeroTelefonoOpcion1.setEnabled(true);
            numeroTelefonoOpcion2.setEnabled(true);
            email.setEnabled(true);
            ubicacion.setEnabled(true);
        }

        if(id == R.id.action_help2){
            DialogoAyuda dialogoAyuda = new DialogoAyuda();
            Bundle bundle = new Bundle();
            bundle.putInt("tipo",12);
            dialogoAyuda.setArguments(bundle);
            dialogoAyuda.show(getFragmentManager(),"my_dialog");
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
    public boolean validarCampos(){
        if(nombre.getText().toString().equals(""))
            return false;
        if(apellido.getText().toString().equals(""))
            return false;
        if(ubicacion.getText().toString().equals(""))
            return false;
        if(prefijo.equals(""))
            return false;
        if(numeroTelefonoOpcion2.getText().toString().equals(""))
            return false;
        if(numeroTelefonoOpcion1.getText().toString().equals(""))
            return false;
        if(email.getText().toString().equals(""))
            return false;

        return true;
    }
}
