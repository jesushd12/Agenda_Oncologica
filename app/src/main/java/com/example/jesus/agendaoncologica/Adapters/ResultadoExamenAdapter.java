package com.example.jesus.agendaoncologica.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jesus.agendaoncologica.AnalisisExamen;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.ResultadoExamen;
import com.example.jesus.agendaoncologica.Tratamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Jesus on 12/19/2015.
 */
public class ResultadoExamenAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<ResultadoExamen> analisis;
    EditText edt_valor;
    public static HashMap<String, String> textValues = new HashMap<String, String>();


    public ResultadoExamenAdapter(Activity activity, ArrayList<ResultadoExamen> analisis) {
        this.activity = activity;
        this.analisis = analisis;
    }

    @Override
    public int getCount() {
        return analisis.size();
    }

    @Override
    public Object getItem(int position) {
        return analisis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return analisis.get(position).getExamen().getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MyHolder holder;
        boolean convertViewWasNull = false;
        if(convertView==null){
            holder = new MyHolder();
            LayoutInflater inf = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.fila_resultado_examen,null);

            holder.nombreAnalisis = (TextView)v.findViewById(R.id.txt_nombreAnalisis) ;
            holder.valoranalisis = (EditText) v.findViewById(R.id.edt_valorexamen) ;
            v.setTag(holder);
            convertViewWasNull = true;
        }else{
            holder =(MyHolder)v.getTag();
        }

         final ResultadoExamen analisisExamen = analisis.get(position);
        // TextView txt_nombre_analisis = (TextView)v.findViewById(R.id.txt_nombreAnalisis);
        // edt_valor = (EditText) v.findViewById(R.id.edt_valorexamen);

        //txt_nombre_analisis.setText(analisisExamen.getAnalisisExamen().getAnalisis());
        holder.nombreAnalisis.setText(analisisExamen.getAnalisisExamen().getAnalisis());
        holder.valoranalisis.setText(analisisExamen.getValor());
        holder.valoranalisis.setId(position);
        holder.valoranalisis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    final int position = v.getId();
                    final EditText valor = (EditText) v;
                    analisis.get(position).setValor(valor.getText().toString());
                }
            }
        });
        //edt_valor.setTag("valor:"+position);
        //edt_valor.setText(analisisExamen.getValor());
        if(convertViewWasNull ){
            //edt_valor.addTextChangedListener(new GenericTextWatcher(edt_valor,position));
           // holder.valoranalisis.addTextChangedListener(new GenericTextWatcher(edt_valor,position));
        }
        //edt_valor.setTag("valor:"+position);
        return v;
    }

    private class GenericTextWatcher implements TextWatcher{

        private View view;
        private int position;
        private GenericTextWatcher(View view,int position) {
            this.view = view;
            this.position = position;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            //save the value for the given tag :
            analisis.get(position).setValor(text);
           // ResultadoExamenAdapter.this.textValues.put((String)view.getTag(),editable.toString());
        }
    }



}
class MyHolder{
    TextView nombreAnalisis;
    EditText valoranalisis;

}
