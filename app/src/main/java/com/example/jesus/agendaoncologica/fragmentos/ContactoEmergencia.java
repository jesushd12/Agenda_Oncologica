package com.example.jesus.agendaoncologica.fragmentos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jesus.agendaoncologica.Contactos;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Jesus on 2/1/2016.
 */
public class ContactoEmergencia extends Fragment{
    ListView listacontactos;
    FloatingActionButton fab;
    DatabaseManager db;
    ArrayList<Contactos> misContactos;
    ArrayAdapter adapter=null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactos_emergencia,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contactos de emergencia");
        setHasOptionsMenu(true);
        db = new DatabaseManager(getContext());

        listacontactos = (ListView)v.findViewById(R.id.listacontactos);
        misContactos = db.consultarContacto();

        if(misContactos!=null) {
            adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, misContactos) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(misContactos.get(position).getNombre());
                    text2.setText(misContactos.get(position).getNumero());
                    return view;
                }
            };
            listacontactos.setAdapter(adapter);
            listacontactos.setItemsCanFocus(true);

            listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    LayoutInflater li = LayoutInflater.from(getContext());
                    View promptsView = li.inflate(R.layout.dialogo_contacto, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.edt_nombrecontacto);
                    userInput.setText(misContactos.get(position).getNombre());
                    final EditText userNumber = (EditText) promptsView
                            .findViewById(R.id.edt_numerocontacto);
                    userNumber.setText(misContactos.get(position).getNumero());


                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            Contactos contacto = new Contactos(userInput.getText().toString(),userNumber.getText().toString());
                                           contacto.setId(misContactos.get(position).getId());
                                            db.actualizarcontacto(contacto);
                                            misContactos.set(position,contacto);
                                            //misContactos = db.consultarContacto();
                                            adapter.notifyDataSetChanged();

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                }
            });
            listacontactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final String tituloNota = misContactos.get(position).getNombre();
                    final int idNota = position;
                    builder.setTitle(tituloNota);
                    builder.setMessage("Desea eliminar este contacto?");
                    // Add the buttons
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            db.eliminarContacto(misContactos.get(position).getId());
                            misContactos.remove(position);
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


        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.dialogo_contacto, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.edt_nombrecontacto);
                final EditText userNumber = (EditText) promptsView
                        .findViewById(R.id.edt_numerocontacto);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Contactos contacto = new Contactos(userInput.getText().toString(),userNumber.getText().toString());
                                        db.insertarContacto(contacto);
                                        misContactos.add(contacto);
                                        adapter.notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
    }
}
