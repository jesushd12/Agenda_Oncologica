package com.example.jesus.agendaoncologica.fragmentos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.Adapters.ResultadoExamenAdapter;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.ResultadoExamen;
import com.example.jesus.agendaoncologica.TipoExamen;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoTipoAnalisis;

import java.util.ArrayList;

/**
 * Created by Jesus on 2/4/2016.
 */
public class FragmentoResultadoExamen extends Fragment {
    public static ListView listaResultados;
    public static ArrayList<ResultadoExamen>analisis;
    DatabaseManager db;
    TipoExamen tipoexamen;
    Bundle bundle;
    FloatingActionButton fab;
    String tipoexamenNombre;
    int idExamen;
    ResultadoExamenAdapter resultadoExamenAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactos_emergencia,container,false);

        setHasOptionsMenu(true);
        db = new DatabaseManager(getContext());
        bundle = getArguments();

        tipoexamenNombre = bundle.getString("tipoexamen");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(tipoexamenNombre);
        idExamen = bundle.getInt("idexamen");

        tipoexamen = new TipoExamen();
        tipoexamen.setNombre(tipoexamenNombre);
        analisis = db.consultarResultadoExamenes(idExamen,tipoexamen);
        listaResultados = (ListView)v.findViewById(R.id.listacontactos);

        if(analisis!=null) {
            resultadoExamenAdapter = new ResultadoExamenAdapter(getActivity(), analisis);
            listaResultados.setAdapter(resultadoExamenAdapter);
            listaResultados.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    dialogoTipoExamen(analisis.get(position),position);
                    return true;
                }
            });
        }


        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("tipoexamen",tipoexamenNombre);
                args.putInt("idexamen",idExamen);

                DialogFragment dialogoConfirmarGuardarTest = new DialogoTipoAnalisis();
                dialogoConfirmarGuardarTest.setArguments(args);
                dialogoConfirmarGuardarTest.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FondoTransparente);
                dialogoConfirmarGuardarTest.show(getFragmentManager(),"DialogoPersonalizado");

            }
        });


        return v;
    }

    public void dialogoTipoExamen(final ResultadoExamen resultadoExamen, final int position){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Desea eliminar este examen")
                .setPositiveButton("Eliminar de este examen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarResultado(resultadoExamen);
                        analisis.remove(position);
                        resultadoExamenAdapter.notifyDataSetChanged();

                    }
                }).setNeutralButton("Eliminar para siempre ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarAnalisisExamen(resultadoExamen.getAnalisisExamen());
                        analisis.remove(position);
                        resultadoExamenAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            listaResultados.clearFocus();
            for(int aux = 0; aux<listaResultados.getAdapter().getCount();aux++){
                db.actualizarResultado((ResultadoExamen)listaResultados.getAdapter().getItem(aux));
            }
            Toast.makeText(getContext(),"Sus datos han sido guardado satisfactoriamente",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.examen_main, menu);
        menu.removeItem(R.id.action_share);
        menu.removeItem(R.id.action_delete);
        menu.removeItem(R.id.action_edit);
    }
}
