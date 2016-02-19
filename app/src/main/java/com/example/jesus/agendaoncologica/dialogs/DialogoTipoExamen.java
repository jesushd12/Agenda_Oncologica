package com.example.jesus.agendaoncologica.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.TipoExamen;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

/**
 * Created by Jesus on 1/18/2016.
 */

public class DialogoTipoExamen extends DialogFragment {

    EditText entradaUsuario;
    TextView tituloDialogo;
    public Button agregar;
    Button cancelar;
    String nuevoTipo;
    DatabaseManager DB;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogo_alergia, container);
        tituloDialogo =(TextView)v.findViewById(R.id.txt_titleDialog);
        tituloDialogo.setText("Nuevo Tipo de examen");
        entradaUsuario = (EditText) v.findViewById(R.id.edt_Dinput_usuario);
        entradaUsuario.setHint("Ingrese el nombre del nuevo tipo");
        agregar = (Button)v.findViewById(R.id.boton_agregar);
        cancelar= (Button)v.findViewById(R.id.boton_cancelar);
        DB = new DatabaseManager(getContext());

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entradaUsuario.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "Por favor introduzca su tipo de examen", Toast.LENGTH_SHORT);
                    return;
                }
                TipoExamen tipoExamen;
                tipoExamen = new TipoExamen();
                tipoExamen.setNombre(entradaUsuario.getText().toString());
                if (DB.existeTipoExamen(tipoExamen)){
                    Toast.makeText(getContext(), "Ya existe este tipo de examen", Toast.LENGTH_SHORT).show();
                    return;
                }
                nuevoTipo = tipoExamen.getNombre();
                DB.insertarTipoExamen(tipoExamen);
                Bundle args = new Bundle();
                args.putString("tipoexamen",tipoExamen.getNombre());
                args.putBoolean("esNuevo",true);
                DialogFragment dialogoConfirmarGuardarTest = new DialogoTipoAnalisis();
                dialogoConfirmarGuardarTest.setArguments(args);
                dialogoConfirmarGuardarTest.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FondoTransparente);
                dialogoConfirmarGuardarTest.show(getFragmentManager(),"DialogoPersonalizado");
                DialogoTipoExamen.this.getDialog().dismiss();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoTipoExamen.this.getDialog().cancel();
            }
        });
        getDialog().setCanceledOnTouchOutside(true);
        return v;
    }

    public String getNuevoTipo() {
        return nuevoTipo;
    }
}
