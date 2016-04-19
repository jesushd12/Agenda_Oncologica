package com.example.jesus.agendaoncologica.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jesus.agendaoncologica.R;

/**
 * Created by Jesus on 4/19/2016.
 */
public class DialogoAyuda extends DialogFragment {

    Bundle bundle;
    Button botonOk;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;
        bundle = getArguments();
        switch(bundle.getInt("tipo")){
            case 1:
                v = inflater.inflate(R.layout.dialogo_ayudatratamiento,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 2:
                v = inflater.inflate(R.layout.dialogo_ayudainfotratamiento,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 3:
                v = inflater.inflate(R.layout.dialogo_ayudaexamen,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 4:
                v = inflater.inflate(R.layout.dialogo_ayudamodificarexamen,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 5:
                v = inflater.inflate(R.layout.dialogo_ayudaresultadoexamen,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 6:
                v = inflater.inflate(R.layout.dialogo_ayudahistoriapaciente,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 7:
                v = inflater.inflate(R.layout.dialogo_ayudaalergias,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 8:
                v = inflater.inflate(R.layout.dialogo_ayudainformacion,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 9:
                v = inflater.inflate(R.layout.dialogo_ayudanota,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 10:
                v = inflater.inflate(R.layout.dialogo_ayudanotapagina,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 11:
                v = inflater.inflate(R.layout.dialogo_ayudainformacionpaciente,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 12:
                v = inflater.inflate(R.layout.dialogo_ayudamedico,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 13:
                v = inflater.inflate(R.layout.dialogo_ayudacontactoemergencia,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 14:
                v = inflater.inflate(R.layout.dialogo_ayudaprincipal,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
            case 15:
                v = inflater.inflate(R.layout.dialogo_ayudacontacto,container);
                botonOk = (Button)v.findViewById(R.id.btn_OkAyudaTratamiento);
                break;
        }
        botonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoAyuda.this.getDialog().dismiss();
            }
        });

        return v;
    }
}
