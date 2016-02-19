package com.example.jesus.agendaoncologica.fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.database.DatabaseManager;

/**
 * Created by Jesus on 12/18/2015.
 */
public class FragmentoWebInformativa extends Fragment {
    ListView listView_titulos;
    DatabaseManager DB;
    String[] links = {"http://www.ninoscontraelcancer.org/","https://www.aecc.es/","http://www.amlcc.org/"," http://www.senosayuda.org.ve"};
    Bundle bundle = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_notas,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Links informativos");

        listView_titulos = (ListView)v.findViewById(R.id.listView_notas);
        if(links!=null) {
            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, links) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);

                    text1.setText(links[position]);
                    return view;
                }
            };
            listView_titulos.setAdapter(adapter);
            listView_titulos.setItemsCanFocus(true);
            listView_titulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle args = new Bundle();
                    args.putString("url", links[position]);
                    FragmentManager fm = getFragmentManager();
                    FragmentoWebView nota = new FragmentoWebView();
                    nota.setArguments(args);
                    fm.beginTransaction().replace(R.id.contain_frame, nota).addToBackStack("tag").commit();

                }
            });

        }

        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_settings);
        inflater.inflate(R.menu.treatment_main, menu);
        menu.removeItem(R.id.action_add);
    }


}

