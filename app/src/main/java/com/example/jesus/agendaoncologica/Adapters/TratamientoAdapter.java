package com.example.jesus.agendaoncologica.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.Tratamiento;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jesus on 12/19/2015.
 */
public class TratamientoAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Tratamiento> tratamientos;
    private Date fecha;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formato2 = new SimpleDateFormat("MMMM dd, yyyy",new Locale("es","ES"));


    public TratamientoAdapter(Activity activity, ArrayList<Tratamiento> tratamientos) {
        this.activity = activity;
        this.tratamientos = tratamientos;
    }

    @Override
    public int getCount() {
        return tratamientos.size();
    }

    @Override
    public Object getItem(int position) {
        return tratamientos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tratamientos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView==null){
            LayoutInflater inf = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.single_row_tratamiento,null);

        }

        Tratamiento tratamiento = tratamientos.get(position);
        TextView txt_legend_color = (TextView)v.findViewById(R.id.txt_legend_color);


        TextView edt_fecha = (TextView) v.findViewById(R.id.edt_treatment_date);
        try {
            fecha = formato.parse(tratamiento.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        edt_fecha.setText(formato2.format(fecha));


        TextView edt_tipo = (TextView)v.findViewById(R.id.edt_treatment_type);
        if(tratamiento.getTipo().equalsIgnoreCase("Radioterapia"))

            txt_legend_color.setBackgroundResource(R.color.caldroid_holo_blue_light);
        else
            txt_legend_color.setBackgroundResource(R.color.md_green_500_50);
        edt_tipo.setText(tratamiento.getTipo());
        return v;
    }
}
