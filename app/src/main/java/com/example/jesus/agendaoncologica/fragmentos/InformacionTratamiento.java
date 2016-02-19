package com.example.jesus.agendaoncologica.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.Tratamiento;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

/**
 * Created by Jesus on 1/19/2016.
 */
public class InformacionTratamiento extends Fragment {
    EditText nombreTratamiento;
    EditText fechaTratamiento;
    EditText horaTratamiento;
    DatabaseManager DB;
    int idTratamiento;
    Button botonNotas;
    Button botonExamen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.informacion_tratamiento,container,false);
        DB = new DatabaseManager(getContext());
        setHasOptionsMenu(true);
        nombreTratamiento = (EditText)v.findViewById(R.id.edt_tratamientoEspecifico);
        nombreTratamiento.setEnabled(false);
        fechaTratamiento = (EditText)v.findViewById(R.id.edt_fechaTratamientoEspecifico);
        fechaTratamiento.setEnabled(false);
        horaTratamiento = (EditText)v.findViewById(R.id.edt_horaTratamientoEspecifico);
        horaTratamiento.setEnabled(false);

        idTratamiento = getArguments().getInt("id");
        Tratamiento tratamiento = DB.consultarTratamiento(idTratamiento);

        nombreTratamiento.setText(tratamiento.getTipo());
        fechaTratamiento.setText(tratamiento.getFecha());
        horaTratamiento.setText(tratamiento.getHora());

        botonNotas = (Button)v.findViewById(R.id.btn_notasTratamientoEspecifico);
        botonExamen = (Button)v.findViewById(R.id.btn_examenestratamientoespecifico);


        botonNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("idTratamiento",idTratamiento);
                FragmentManager fm = getFragmentManager();
                FragmentoNota nota = new FragmentoNota();
                nota.setArguments(args);
                fm.beginTransaction().replace(R.id.contain_frame,nota).addToBackStack( "tag" ).commit();

            }
        });

        botonExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putInt("idTratamiento",idTratamiento);
                FragmentManager fm = getFragmentManager();
                FragmentoExamen fragmentoExamen = new FragmentoExamen();
                fragmentoExamen.setArguments(args);
                fm.beginTransaction().replace(R.id.contain_frame,fragmentoExamen).addToBackStack( "tag" ).commit();


            }
        });

        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
    }
}