package com.example.jesus.agendaoncologica.fragmentos;

/**
 * Created by Jesus on 2/1/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.Nota;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;

import java.util.Calendar;

/**
 * Created by Jesus on 12/19/2015.
 */
public class FragmentoNotaPagina extends Fragment {

    DatabaseManager DB;

    EditText edt_titulo;
    EditText edt_cuerpoNota;
    private Integer idNota =-1;


    public FragmentoNotaPagina() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pagina_nota,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notas");
        setHasOptionsMenu(true);
        Nota nota;
        DB = new DatabaseManager(getActivity());
        idNota = getArguments().getInt("id");


        if(idNota==-2)
            idNota++;
        edt_titulo = (EditText) v.findViewById( R.id.edt_asunto);
        edt_cuerpoNota=(EditText) v.findViewById(R.id.edt_cuerpoNota);
        if(idNota != -1){

            nota = DB.pedirNotas(idNota);
            edt_titulo.setText(nota.getTitulo());
            edt_cuerpoNota.setText(nota.getNota());
        }

        return v;
    }

    public boolean validarCampos(){
        if(edt_titulo.getText().toString().equals("") || edt_cuerpoNota.getText().toString().equals(""))
            return false;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Calendar c = Calendar.getInstance();
            if(!validarCampos()) {
                Toast.makeText(getContext(),"Por favor completa todos los datos",Toast.LENGTH_SHORT).show();
                return false;
            }
            Nota nota;
            nota = new Nota();
            nota.setFecha(c.getTime());
            nota.setTitulo(edt_titulo.getText().toString());
            nota.setNota(edt_cuerpoNota.getText().toString());
            Bundle bundle = getArguments();
            if(bundle!=null){
                if(bundle.containsKey("idTratamiento")){
                    nota.setIdExamenAsociado(bundle.getInt("idTratamiento"));
                }
            }

            if(idNota != -1){
                nota.setId(idNota);
                DB.actualizarNota(nota);
            }else{
                DB.insertarNota(nota);
            }
            getActivity().onBackPressed();
            return true;
        }
        if (id == R.id.action_share){
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{" "});
            i.putExtra(Intent.EXTRA_SUBJECT, edt_titulo.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT   , edt_cuerpoNota.getText().toString());
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }

        if(id == R.id.action_help2){
            DialogoAyuda dialogoAyuda = new DialogoAyuda();
            Bundle bundle = new Bundle();
            bundle.putInt("tipo",10);
            dialogoAyuda.setArguments(bundle);
            dialogoAyuda.show(getFragmentManager(),"my_dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.examen_main, menu);
        menu.removeItem(R.id.action_edit);
        menu.removeItem(R.id.action_delete);

    }
}