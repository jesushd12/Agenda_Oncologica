package com.example.jesus.agendaoncologica.dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.fragmentos.FragmentoTratamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jesus on 12/17/2015.
 */
public class EleccionTratamiento extends DialogFragment{

    final CharSequence[] tratamientos = {"Radioterapia","Quimioterapia"};
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    String selection = null;
    DatabaseManager db;
    Calendar dateAndTime;
    TimePickerDialog.OnTimeSetListener t;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
    DatePickerDialog datePickerDialog;
    FragmentManager fm;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String fechaSeleccionada = getArguments().getString("fecha");
        db = new DatabaseManager(getActivity());
        dateAndTime=Calendar.getInstance();

        t = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay,
                                  int minute) {
                dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndTime.set(Calendar.MINUTE, minute);
                db.actualizarHora(fechaSeleccionada,dateFormat.format(dateAndTime.getTime()));

            }
        };



        //Si existe un tratamiento para la fecha
        if(db.consultarFechaTratamiento(fechaSeleccionada)){

            builder.setTitle("Su tratamiento para el dia");
            builder.setSingleChoiceItems(tratamientos, db.consultarTipoTratamiento(fechaSeleccionada), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:
                            selection = (String)tratamientos[which];
                            break;
                        case 1:
                            selection = (String)tratamientos[which];
                            break;
                    }

                }
            }).setPositiveButton("Postergar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(formato.parse(fechaSeleccionada));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    fm = getFragmentManager();
                   datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar nuevaFecha = Calendar.getInstance();
                            nuevaFecha.set(year,monthOfYear,dayOfMonth);
                            db.porstergarFechaTratamiento(fechaSeleccionada,formato.format(nuevaFecha.getTime()));

                            fm.beginTransaction().replace(R.id.contain_frame,new FragmentoTratamiento()).commit();

                        }

                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.setTitle("Seleccione la nueva fecha");

                    datePickerDialog.show();

                }
            }).setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.eliminarFechaTratamiento(fechaSeleccionada);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.contain_frame,new FragmentoTratamiento()).commit();
                }
            });

        }else {
            builder.setTitle("Seleccione un tratamiento: ");
            builder.setSingleChoiceItems(tratamientos, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0:
                            selection = (String)tratamientos[which];
                            break;
                        case 1:
                            selection = (String)tratamientos[which];
                            break;
                    }

                }
            }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(selection == null){
                        Toast.makeText(getActivity(),"Por favor selecciona un tratamiento", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    if(!db.consultarFechaTratamiento(selection)){
                        db.insertarTratamiento(fechaSeleccionada,selection);

                        new TimePickerDialog(getContext(),
                                t,
                                dateAndTime.get(Calendar.HOUR_OF_DAY),
                                dateAndTime.get(Calendar.MINUTE),
                                true).show();

                        FragmentManager fm = getFragmentManager();
                        fm.beginTransaction().replace(R.id.contain_frame,new FragmentoTratamiento()).commit();
                    }else{
                        Toast.makeText(getActivity(),"Ya existe un tratamiento para este dia", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dismiss();
                }
            });
        }


        return builder.create();
    }


}
