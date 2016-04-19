package com.example.jesus.agendaoncologica.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;

/**
 * Created by Jesus on 2/16/2016.
 */
public class ContactoFundahog extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contacto_fundahog,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Contacto");
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.treatment_main, menu);
        menu.removeItem(R.id.action_add);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_help){
            DialogoAyuda dialogoAyuda = new DialogoAyuda();
            Bundle bundle = new Bundle();
            bundle.putInt("tipo",15);
            dialogoAyuda.setArguments(bundle);
            dialogoAyuda.show(getFragmentManager(),"my_dialog");
        }
        return super.onOptionsItemSelected(item);
    }

}
