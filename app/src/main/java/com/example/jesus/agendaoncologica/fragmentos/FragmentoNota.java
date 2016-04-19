package com.example.jesus.agendaoncologica.fragmentos;

/**
 * Created by Jesus on 2/1/2016.
 */

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

import com.example.jesus.agendaoncologica.Nota;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;

import java.util.ArrayList;

/**
 * Created by Jesus on 12/18/2015.
 */
public class FragmentoNota extends Fragment {
    ListView listView_titulos;
    DatabaseManager DB;
    ArrayList<Nota> todasLasNotas;
    Bundle bundle = null;
    FloatingActionButton fab;
    int idNota;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactos_emergencia,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notas");

        DB = new DatabaseManager(getActivity());
        todasLasNotas =  DB.pedirTodasLasNotas();
        int idTratamiento;

        bundle = getArguments();
        if(bundle!=null){
            idTratamiento = bundle.getInt("idTratamiento");
            todasLasNotas=DB.pedirTodasLasNotasPorTratamiento(idTratamiento);
            if(todasLasNotas==null){
                Toast.makeText(getContext(),"No tiene ninguna nota asociada a este ciclo: "+idTratamiento,Toast.LENGTH_LONG).show();
            }

        }

        listView_titulos = (ListView)v.findViewById(R.id.listacontactos);
        if(todasLasNotas!=null) {
            final ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, todasLasNotas) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(todasLasNotas.get(position).getTitulo());
                    text2.setText(todasLasNotas.get(position).obtenerFechaFormateada());
                    return view;
                }
            };
            listView_titulos.setAdapter(adapter);

            listView_titulos.setItemsCanFocus(true);

            listView_titulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle args = new Bundle();
                    args.putInt("id", todasLasNotas.get(position).getId());
                    FragmentManager fm = getFragmentManager();
                    FragmentoNotaPagina nota = new FragmentoNotaPagina();
                    nota.setArguments(args);
                    fm.beginTransaction().replace(R.id.contain_frame, nota).addToBackStack("tag").commit();

                }
            });
            listView_titulos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    String tituloNota = todasLasNotas.get(position).getTitulo();
                    idNota = todasLasNotas.get(position).getId();
                    builder.setTitle(tituloNota);
                    builder.setMessage("Desea eliminar esta nota?");
                    // Add the buttons
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            DB.eliminarNotas(idNota);
                            todasLasNotas.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            dialog.dismiss();
                        }
                    });
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
        }

        setHasOptionsMenu(true);

        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("id",-2);
                if(bundle!=null){
                    args.putInt("idTratamiento",bundle.getInt("idTratamiento"));
                }
                FragmentManager fm = getFragmentManager();
                FragmentoNotaPagina nota = new FragmentoNotaPagina();
                nota.setArguments(args);
                fm.beginTransaction().replace(R.id.contain_frame,nota).addToBackStack( "tag" ).commit();
            }
        });
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_help){
            DialogoAyuda dialogoAyuda = new DialogoAyuda();
            Bundle bundle = new Bundle();
            bundle.putInt("tipo",9);
            dialogoAyuda.setArguments(bundle);
            dialogoAyuda.show(getFragmentManager(),"my_dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.treatment_main, menu);
        menu.removeItem(R.id.action_add);
    }


}
