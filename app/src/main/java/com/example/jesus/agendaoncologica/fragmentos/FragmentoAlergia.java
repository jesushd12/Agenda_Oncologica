package com.example.jesus.agendaoncologica.fragmentos;

/**
 * Created by Jesus on 2/1/2016.
 */
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import com.example.jesus.agendaoncologica.Alergia;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoAlergia;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;

import java.util.ArrayList;

/**
 * Created by Jesus on 1/18/2016.
 */
public class FragmentoAlergia extends Fragment {
    public static ListView listaAlergia;
    public static ArrayList<Alergia> misAlergias;
    DatabaseManager DB;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactos_emergencia,container,false);
        DB = new DatabaseManager(getContext());
        setHasOptionsMenu(true);
        listaAlergia = (ListView)v.findViewById(R.id.listacontactos);
        misAlergias = DB.consultarAlergias();
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, misAlergias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(misAlergias.get(position).getNombrealergia());
                return view;
            }
        };
        listaAlergia.setAdapter(adapter);
        listaAlergia.setItemsCanFocus(true);
        listaAlergia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String nombreAlergia = misAlergias.get(position).getNombrealergia();
                builder.setTitle(misAlergias.get(position).getNombrealergia());
                builder.setMessage("Desea eliminar esta alergia?");
                // Add the buttons
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        DB.eliminarAlergia(nombreAlergia);
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
            }
        });


        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dialogoConfirmarGuardarTest = new DialogoAlergia();
                dialogoConfirmarGuardarTest.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FondoTransparente);
                dialogoConfirmarGuardarTest.show(getFragmentManager(),"DialogoPersonalizado");
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
            bundle.putInt("tipo",7);
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
