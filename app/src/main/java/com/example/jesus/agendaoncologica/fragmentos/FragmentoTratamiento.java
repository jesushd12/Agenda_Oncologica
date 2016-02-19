package com.example.jesus.agendaoncologica.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jesus.agendaoncologica.Adapters.TratamientoAdapter;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.Tratamiento;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.EleccionTratamiento;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Jesus on 12/17/2015.
 */
public class FragmentoTratamiento extends Fragment {
    private CaldroidFragment caldroidFragment;
    private DatabaseManager DB;
    ArrayList<Tratamiento> tratamientos;
    ListView lista_tratamiento;
    SimpleDateFormat formatoAno = new SimpleDateFormat("yyyy");
    SimpleDateFormat formatoMes = new SimpleDateFormat("MM");
    CaldroidListener listener;
    TratamientoAdapter treatmentAdapter;
    final SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");


    public void setCustomResourceForDate(){
        ArrayList<String> fechasTratamientos = DB.pedirFechaTratamiento();
        if (caldroidFragment!=null){
            int i;
            for(i = 0;i<fechasTratamientos.size();i++){
                try {
                    if(DB.consultarTipoTratamiento(fechasTratamientos.get(i))==0)
                        caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_holo_blue_light,formato.parse(fechasTratamientos.get(i)));
                    else
                        caldroidFragment.setBackgroundResourceForDate(R.color.md_green_500_50,formato.parse(fechasTratamientos.get(i)));
                    caldroidFragment.setTextColorForDate(R.color.caldroid_white,formato.parse(fechasTratamientos.get(i)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tratamiento,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tratamientos");
        setHasOptionsMenu(true);
        DB = new DatabaseManager(getActivity());
        lista_tratamiento = (ListView)v.findViewById(R.id.listview_tratamientos);

        tratamientos = DB.pedirTratamientosCompleto();
        Collections.sort(tratamientos,new CustomComparator());
        treatmentAdapter = new TratamientoAdapter(getActivity(),tratamientos);
        lista_tratamiento.setAdapter(treatmentAdapter);
        lista_tratamiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putInt("id",tratamientos.get(position).getId());
                FragmentManager fm = getFragmentManager();
                InformacionTratamiento treatmentInformation = new InformacionTratamiento();
                treatmentInformation.setArguments(args);
                fm.beginTransaction().replace(R.id.contain_frame,treatmentInformation).addToBackStack( "tag" ).commit();
            }
        });
        caldroidFragment = new CaldroidFragment();
        if(savedInstanceState !=null){
            caldroidFragment.restoreStatesFromKey(savedInstanceState,"CALDROID_SAVED_STATE");

        }else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);
            caldroidFragment.setArguments(args);
        }
        setCustomResourceForDate();

        FragmentTransaction t = this.getFragmentManager().beginTransaction();
        t.replace(R.id.caldroide,caldroidFragment);
        t.commit();

        listener = new CaldroidListener() {
            // Cada vez que el usuario cambie de mes, este visualizara en la lista solo los tratamientos correspondientes
            // a dicho mes.
            @Override
            public void onChangeMonth(int month, int year) {
                super.onChangeMonth(month, year);
                System.out.println("mes: "+month+" year: "+year);
                tratamientos = DB.pedirTratamientosCompleto();
                ArrayList<Tratamiento> tratamientoPorMes = new ArrayList<>();
                for(int i = 0; i<tratamientos.size();i++){
                    if(( (month==Integer.parseInt(formatoMes.format(tratamientos.get(i).getDate()))) && year == Integer.parseInt(formatoAno.format(tratamientos.get(i).getDate())))  ) {
                        tratamientoPorMes.add(tratamientos.get(i));
                    }
                }
                Collections.sort(tratamientoPorMes,new CustomComparator());
                treatmentAdapter = new TratamientoAdapter(getActivity(),tratamientoPorMes);
                lista_tratamiento.setAdapter(treatmentAdapter);
            }


            @Override
            public void onSelectDate(Date date, View view) {
                EleccionTratamiento my_dialog = new EleccionTratamiento();
                Bundle args = new Bundle();
                args.putString("fecha",formato.format(date));
                my_dialog.setArguments(args);
                my_dialog.show(getFragmentManager(),"my_dialog");
            }


            @Override
            public void onLongClickDate(Date date, View view) {
                super.onLongClickDate(date, view);
                EleccionTratamiento my_dialog = new EleccionTratamiento();
                Bundle args = new Bundle();
                args.putString("fecha",formato.format(date));
                my_dialog.setArguments(args);
                my_dialog.show(getFragmentManager(),"my_dialog");
            }
        };
        caldroidFragment.setCaldroidListener(listener);

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
        return super.onOptionsItemSelected(item);
    }


    public class CustomComparator implements Comparator<Tratamiento> {
        @Override
        public int compare(Tratamiento o1, Tratamiento o2) {
            return o1.getFecha().compareTo(o2.getFecha());
        }
    }
}
