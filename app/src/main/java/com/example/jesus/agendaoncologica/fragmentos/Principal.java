package com.example.jesus.agendaoncologica.fragmentos;

import android.support.v4.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

/**
 * Created by Jesus on 2/1/2016.
 */
public class Principal extends Fragment {
    ImageButton botonTratamiento ;
    ImageButton botonNotas;
    ImageButton botonExamen;
    ImageButton botonInformacion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pantalla_principal,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Principal");

        setHasOptionsMenu(true);


        botonTratamiento = (ImageButton)v.findViewById(R.id.treatment_button);
        botonTratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.contain_frame,new FragmentoTratamiento()).addToBackStack( "tag" ).commit();
            }
        });
        botonNotas = (ImageButton)v.findViewById(R.id.boton_notas);
        botonNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.contain_frame,new FragmentoNota()).addToBackStack( "tag" ).commit();
            }
        });
        botonExamen = (ImageButton) v.findViewById(R.id.btn_examen);
        botonExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.contain_frame,new FragmentoExamen()).addToBackStack( "tag" ).commit();

            }
        });

        botonInformacion = (ImageButton)v.findViewById(R.id.btn_informacion);
        botonInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.contain_frame,new FragmentoWebInformativa()).addToBackStack( "tag" ).commit();
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
    }
}
