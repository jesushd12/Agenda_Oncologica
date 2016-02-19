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

import com.example.jesus.agendaoncologica.Adapters.ResultadoExamenAdapter;
import com.example.jesus.agendaoncologica.AnalisisExamen;
import com.example.jesus.agendaoncologica.Examen;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.ResultadoExamen;
import com.example.jesus.agendaoncologica.TipoExamen;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.fragmentos.FragmentoResultadoExamen;

/**
 * Created by Jesus on 1/18/2016.
 */

public class DialogoTipoAnalisis extends DialogFragment {

    TextView tituloDialogo;
    EditText entradaUsuario;
    public Button agregar;
    Button cancelar;
    DatabaseManager DB;
    Bundle bundle;
    TipoExamen tipoexamen;
    AnalisisExamen analisisExamen;
    ResultadoExamen resultadoExamen;
    Examen examen;
    Boolean esNuevo = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialogo_alergia, container);
        bundle = getArguments();


        tipoexamen = new TipoExamen(bundle.getString("tipoexamen"));
        examen = new Examen();
        examen.setId(bundle.getInt("idexamen"));
        if(bundle.containsKey("esNuevo")){
            esNuevo = true;
        }
        analisisExamen = new AnalisisExamen();
        analisisExamen.setTipoExamen(tipoexamen);
        resultadoExamen = new ResultadoExamen();
        resultadoExamen.setExamen(examen);



        // UI
        tituloDialogo =(TextView)v.findViewById(R.id.txt_titleDialog);
        tituloDialogo.setText("Nuevo analisis");
        entradaUsuario = (EditText) v.findViewById(R.id.edt_Dinput_usuario);
        entradaUsuario.setHint("Ingrese el nombre del nuevo analisis");
        agregar = (Button)v.findViewById(R.id.boton_agregar);
        cancelar= (Button)v.findViewById(R.id.boton_cancelar);

        DB = new DatabaseManager(getContext());

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entradaUsuario.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "Por favor introduzca su tipo de analisis", Toast.LENGTH_SHORT);
                    return;
                }
                analisisExamen.setAnalisis(entradaUsuario.getText().toString());
                if (DB.existeAnalisis(analisisExamen)){
                    Toast.makeText(getContext(), "Ya existe este tipo de analisis", Toast.LENGTH_SHORT).show();
                    return;
                }
                resultadoExamen.setAnalisisExamen(analisisExamen);
                if(esNuevo){
                    DB.insertarAnalisis(analisisExamen);
                }else {
                    DB.insertarAnalisis(analisisExamen);
                    DB.insertarNuevoResultado(resultadoExamen);
                    FragmentoResultadoExamen.analisis = DB.consultarResultadoExamenes(examen.getId(),tipoexamen);
                    ResultadoExamenAdapter resultadoExamenAdapter = new ResultadoExamenAdapter(getActivity(),FragmentoResultadoExamen.analisis);
                    FragmentoResultadoExamen.listaResultados.setAdapter(resultadoExamenAdapter);
                }
                DialogoTipoAnalisis.this.getDialog().dismiss();
                //Actualizo la lista


            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoTipoAnalisis.this.getDialog().dismiss();
            }
        });
        getDialog().setCanceledOnTouchOutside(true);
        return v;
    }
}
