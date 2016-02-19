package com.example.jesus.agendaoncologica.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jesus.agendaoncologica.Alergia;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.fragmentos.FragmentoAlergia;

/**
 * Created by Jesus on 1/18/2016.
 */

public class DialogoAlergia extends DialogFragment {

    EditText entradaUsuario;
    public Button agregar;
    Button cancelar;
    DatabaseManager DB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogo_alergia, container);
        entradaUsuario = (EditText) v.findViewById(R.id.edt_Dinput_usuario);
        agregar = (Button)v.findViewById(R.id.boton_agregar);
        cancelar= (Button)v.findViewById(R.id.boton_cancelar);
        DB = new DatabaseManager(getContext());

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entradaUsuario.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "Por favor introduzca su alergia", Toast.LENGTH_SHORT);
                    return;
                }
                Alergia alergia;
                alergia = new Alergia();
                alergia.setNombrealergia(entradaUsuario.getText().toString());
                if (DB.consultarAlergiaEspecifica(alergia.getNombrealergia())) {
                    Toast.makeText(getContext(), "Ya esta registrada esta alergia", Toast.LENGTH_SHORT).show();
                    return;
                }
                DB.insertarAlergia(alergia);
                DialogoAlergia.this.getDialog().dismiss();
                //Actualizo la lista
                FragmentoAlergia.misAlergias = DB.consultarAlergias();
                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, FragmentoAlergia.misAlergias) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                        text1.setText(FragmentoAlergia.misAlergias.get(position).getNombrealergia());
                        return view;
                    }
                };
                FragmentoAlergia.listaAlergia.setAdapter(adapter);

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoAlergia.this.getDialog().dismiss();
            }
        });
        getDialog().setCanceledOnTouchOutside(true);
        return v;
    }
}
