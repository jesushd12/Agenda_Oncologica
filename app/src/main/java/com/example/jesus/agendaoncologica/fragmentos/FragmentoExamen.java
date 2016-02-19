package com.example.jesus.agendaoncologica.fragmentos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.Examen;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoTipoExamen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Jesus on 2/2/2016.
 */
public class FragmentoExamen extends Fragment {
    ListView listaExamenes;
    ArrayList<Examen> examenesRealizados;
    DatabaseManager db;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    Bundle bundle;
    ArrayAdapter adapter;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactos_emergencia,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Examenes");
        setHasOptionsMenu(true);
        db = new DatabaseManager(getContext());

        bundle = getArguments();

        if(bundle!=null){
            if(bundle.containsKey("idTratamiento")){
                examenesRealizados = db.consultasExamenesPorTratamiento(bundle.getInt("idTratamiento"));
            }
        }else{
            examenesRealizados = db.consultasExamenes();
        }

        // UI
        listaExamenes = (ListView)v.findViewById(R.id.listacontactos);
        if(examenesRealizados!=null){
             adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, examenesRealizados) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    String titulo = "Examen "+formato.format(examenesRealizados.get(position).getFecha());
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    text1.setText(titulo);
                    return view;
                }
            };
            listaExamenes.setAdapter(adapter);
            listaExamenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle =  new Bundle();
                    bundle.putInt("idExamen",examenesRealizados.get(position).getId());
                    FragmentManager fm = getFragmentManager();
                    FragmetoCrearExamen examenTipos = new FragmetoCrearExamen();
                    examenTipos.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.contain_frame,examenTipos).addToBackStack( "tag" ).commit();
                }
            });
            listaExamenes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getContext(), "Desea eliminar", Toast.LENGTH_SHORT).show();
                    Examen examen = new Examen();
                    examen.setId(examenesRealizados.get(position).getId());
                    dialogoExamen(examen,position);
                    return true;
                }
            });
        }else{
            System.out.println("TA NULLITOOOOOOOOOOOOOOOOO");
        }

        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                FragmentManager fm = getFragmentManager();
                if(bundle!=null){
                    if(bundle.containsKey("idTratamiento"))
                        args.putInt("idTratamiento",bundle.getInt("idTratamiento"));
                }
                FragmetoCrearExamen examenTipos = new FragmetoCrearExamen();
                examenTipos.setArguments(args);
                fm.beginTransaction().replace(R.id.contain_frame,examenTipos).addToBackStack( "tag" ).commit();
            }
        });
        return v;
    }


    public void dialogoExamen(final Examen examen,final int position){
         AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("¿Desea eliminar este examen?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarExamen(examen);
                        examenesRealizados.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        dialog.create();
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.treatment_main, menu);
        menu.removeItem(R.id.action_add);
    }

}
