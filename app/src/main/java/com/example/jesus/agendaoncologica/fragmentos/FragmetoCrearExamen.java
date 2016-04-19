package com.example.jesus.agendaoncologica.fragmentos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jesus.agendaoncologica.AnalisisExamen;
import com.example.jesus.agendaoncologica.Examen;
import com.example.jesus.agendaoncologica.R;
import com.example.jesus.agendaoncologica.ResultadoExamen;
import com.example.jesus.agendaoncologica.TipoExamen;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.dialogs.DialogoAyuda;
import com.example.jesus.agendaoncologica.dialogs.DialogoTipoExamen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jesus on 2/4/2016.
 */
public class FragmetoCrearExamen extends Fragment {

    EditText fecha;
    DatePickerDialog fechaExamen;
    FloatingActionButton fab;
    ListView listaTiposDeExamen;
    ArrayList<TipoExamen> misTipos,tiposExamenEspecifico;
    ArrayList<String> misTiposDeExamenString,tiposExamenEspecificoString;
    DatabaseManager db;
    List<String> listaSeleccionada;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    Calendar c ;
    Bundle bundle;
    int idExamen = -1;
    Examen examenRealizado;
    boolean[] estados,nuevosEstados;
    DialogFragment dialogoNuevoTipo;
    CrearExamenAdapter adaptador;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.armar_examen,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Examen Nuevo");
        setHasOptionsMenu(true);

        db = new DatabaseManager(getActivity());
        listaSeleccionada = new ArrayList<>();

        inicializarEstados();

        // UI
        fecha = (EditText)v.findViewById(R.id.edt_fechaExamen);
        listaTiposDeExamen = (ListView)v.findViewById(R.id.listacontactos);

        // Manejo fecha de examen
        fecha.setInputType(InputType.TYPE_NULL);
        fecha.requestFocus();
        fechaExamen = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c = Calendar.getInstance();
                c.set(year,monthOfYear,dayOfMonth);
                fecha.setText(formato.format(c.getTime()));
            }
        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaExamen.show();
            }
        });

        // Si el usuario selecciono un examen, se muestran en lista los formatos guardados para dicho examen
        bundle = getArguments();
        if(!bundle.isEmpty()){
            examenRealizado = new Examen();
            if(bundle.containsKey("idExamen")) {
                idExamen = bundle.getInt("idExamen");
                examenRealizado = db.consultarExamenEspecifico(idExamen);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Examen "+formato.format(examenRealizado.getFecha()));
                fecha.setText(formato.format(examenRealizado.getFecha()));
                /*fechaExamen = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c = Calendar.getInstance();
                        c.set(year,monthOfYear,dayOfMonth);
                        fecha.setText(formato.format(c.getTime()));
                        examenRealizado.setFecha(c.getTime());
                        db.actualizarExamen(examenRealizado);
                    }
                },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                */
                setearEstados();
                adaptador = new CrearExamenAdapter(getActivity(), tiposExamenEspecificoString);
                adaptador.setIdExamen(idExamen);
                listaTiposDeExamen.setAdapter(adaptador);
            }
        }




        // Gestion de los formatos correspondientes al examen
        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inicializarEstados();
                if(!bundle.isEmpty()){
                    if(bundle.containsKey("idExamen"))
                        setearEstados();
                }
                CharSequence[] cs = misTiposDeExamenString.toArray(new CharSequence[misTiposDeExamenString.size()]);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Selecciona los formatos")
                        .setMultiChoiceItems(cs,estados, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    listaSeleccionada.add(misTiposDeExamenString.get(which));
                                }else if(listaSeleccionada.contains(misTiposDeExamenString.get(which))){
                                    listaSeleccionada.remove(misTiposDeExamenString.get(which));
                                }

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(listaSeleccionada.size()==0){
                                    Toast.makeText(getContext(), "Por favor seleccione un formato", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                long idnuevoexamen;
                                Examen examen = new Examen();
                                TipoExamen tipoExamen;
                                ArrayList<AnalisisExamen> analisisExamen;
                                ResultadoExamen resultadoExamen;

                                System.out.println("paso poraqui");

                                if (!bundle.isEmpty()) { //Si estamos en un examen seleccionado

                                    System.out.println("inserto paso poraqui");
                                    if (bundle.containsKey("idExamen")) {
                                        System.out.println("el mismo examen: ");
                                        examen.setId(idExamen);
                                        for (int aux = 0; aux < estados.length; aux++) {
                                            if (estados[aux] == nuevosEstados[aux]) {
                                            } else if (!estados[aux]) {
                                                db.eliminarTipoExamenResultados(misTipos.get(aux), examen.getId());
                                            } else if (estados[aux]) {
                                                analisisExamen = db.consultarAnaliisPortipo(misTipos.get(aux));
                                                for (int auxiliar2 = 0; auxiliar2 < analisisExamen.size(); auxiliar2++) {
                                                    resultadoExamen = new ResultadoExamen();
                                                    resultadoExamen.setExamen(examen);
                                                    resultadoExamen.setAnalisisExamen(analisisExamen.get(auxiliar2));
                                                    db.insertarNuevoResultado(resultadoExamen);
                                                }
                                            }
                                        }

                                } else { //Si estamos en un nuevo examen

                                    examen.setFecha(c.getTime());

                                    System.out.println("inserto");
                                    idnuevoexamen = db.insertarNuevoExamen(examen);

                                    System.out.println("inserto" + idnuevoexamen);
                                    examen.setId((int) idnuevoexamen);
                                    if (!bundle.isEmpty()) {
                                        if (bundle.containsKey("idTratamiento")) {

                                            System.out.println("asocio" + bundle.getInt("idTratamiento"));
                                            examen.setIdTratamientoAsociado(bundle.getInt("idTratamiento"));
                                            db.asociarTratamientoAExamen(examen);
                                        }
                                    }
                                    for (int auxiliar = 0; auxiliar < listaSeleccionada.size(); auxiliar++) {
                                        tipoExamen = new TipoExamen(listaSeleccionada.get(auxiliar));
                                        analisisExamen = db.consultarAnaliisPortipo(tipoExamen);
                                        if (analisisExamen != null) {
                                            for (int auxiliar2 = 0; auxiliar2 < analisisExamen.size(); auxiliar2++) {
                                                resultadoExamen = new ResultadoExamen();
                                                resultadoExamen.setExamen(examen);
                                                resultadoExamen.setAnalisisExamen(analisisExamen.get(auxiliar2));
                                                db.insertarNuevoResultado(resultadoExamen);
                                            }
                                        }
                                    }
                                }

                            }else{
                                    examen.setFecha(c.getTime());

                                    System.out.println("inserto");
                                    idnuevoexamen = db.insertarNuevoExamen(examen);

                                    System.out.println("inserto" + idnuevoexamen);
                                    examen.setId((int) idnuevoexamen);
                                    for (int auxiliar = 0; auxiliar < listaSeleccionada.size(); auxiliar++) {
                                        tipoExamen = new TipoExamen(listaSeleccionada.get(auxiliar));
                                        analisisExamen = db.consultarAnaliisPortipo(tipoExamen);
                                        if (analisisExamen != null) {
                                            for (int auxiliar2 = 0; auxiliar2 < analisisExamen.size(); auxiliar2++) {
                                                resultadoExamen = new ResultadoExamen();
                                                resultadoExamen.setExamen(examen);
                                                resultadoExamen.setAnalisisExamen(analisisExamen.get(auxiliar2));
                                                db.insertarNuevoResultado(resultadoExamen);
                                            }
                                        }
                                    }
                                }

                                // Actualizo la lista
                                adaptador = new CrearExamenAdapter(getActivity(),listaSeleccionada);
                                adaptador.setIdExamen(examen.getId());

                                System.out.println(""+examen.getId());
                                listaTiposDeExamen.setAdapter(adaptador);
                                listaTiposDeExamen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(getContext(), "Desea Eliminar", Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                });
                            }
                        }).setNeutralButton("Nuevo tipo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialogoNuevoTipo = new DialogoTipoExamen();
                                dialogoNuevoTipo.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FondoTransparente);
                                dialogoNuevoTipo.show(getFragmentManager(),"DialogoPersonalizado");
                            }
                        })
                        .setNegativeButton(getResources().getString(android.R.string.cancel), null);
                dialog.create();
                dialog.show();
            }
        });
        return v;
    }

    public void inicializarEstados(){
        misTipos = db.consultarTipoExamen();
        estados = new boolean[misTipos.size()];
        nuevosEstados = new boolean[misTipos.size()];
        misTiposDeExamenString = new ArrayList<>();
        for(int aux = 0 ; aux<misTipos.size();aux++){
            misTiposDeExamenString.add(misTipos.get(aux).getNombre());
            estados[aux] = false;
            nuevosEstados[aux]=false;
        }
    }
    private void setearEstados(){
        tiposExamenEspecifico = db.consultarResultadoExamenes(examenRealizado.getId());
        tiposExamenEspecificoString = new ArrayList<>();
        for(int aux = 0 ; aux<tiposExamenEspecifico.size();aux++){
            tiposExamenEspecificoString.add(tiposExamenEspecifico.get(aux).getNombre());
        }
        // Seteando estados
        for( int aux = 0 ; aux<misTiposDeExamenString.size();aux++){
            for(int aux2 = 0 ; aux2<tiposExamenEspecificoString.size();aux2++){
                if(misTiposDeExamenString.get(aux).equalsIgnoreCase(tiposExamenEspecificoString.get(aux2))){
                    estados[aux] = true;
                    nuevosEstados[aux]=true;
                    listaSeleccionada.add(misTiposDeExamenString.get(aux));
                }
            }
        }

    }

    public void dialogoTipoExamen(final TipoExamen tipoExamen, final int idExamen, final int position){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Desea eliminar este examen")
                .setPositiveButton("Eliminar de este examen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarTipoExamenResultados(tipoExamen,idExamen);
                        tiposExamenEspecificoString.remove(position);
                        adaptador.notifyDataSetChanged();
                    }
                }).setNeutralButton("Eliminar para siempre ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.eliminarTipoExamenResultados(tipoExamen,idExamen);
                        db.eliminarTipoExamen(tipoExamen);
                        tiposExamenEspecificoString.remove(position);
                        adaptador.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.create();
        dialog.show();
    }

    public class CrearExamenAdapter extends BaseAdapter {
        protected Activity activity;
        protected List<String> tipoExamens;
        int idExamen;

        public void setIdExamen(long id){
            this.idExamen = (int)id;
        }

        public CrearExamenAdapter(Activity activity, List<String> tipoExamens) {
            this.activity = activity;
            this.tipoExamens = tipoExamens;
        }

        @Override
        public int getCount() {
            return tipoExamens.size();
        }

        @Override
        public Object getItem(int position) {
            return tipoExamens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final int posicion = position;
            if(convertView==null){
                LayoutInflater inf = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(android.R.layout.simple_list_item_1,null);
            }
            TextView txt = (TextView) v.findViewById(android.R.id.text1);
            txt.setText(tipoExamens.get(position));
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("tipoexamen",tipoExamens.get(posicion));
                    bundle2.putInt("idexamen",idExamen);
                    FragmentManager fm = getFragmentManager();
                    FragmentoResultadoExamen resultadoExamen = new FragmentoResultadoExamen();
                    resultadoExamen.setArguments(bundle2);
                    fm.beginTransaction().replace(R.id.contain_frame,resultadoExamen).addToBackStack( "tag" ).commit();

                }
            });
            txt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getContext(), "Desea eliminar", Toast.LENGTH_SHORT).show();
                    TipoExamen tipoExamen = new TipoExamen(tipoExamens.get(posicion));
                    dialogoTipoExamen(tipoExamen,idExamen,position);
                    return true;
                }
            });

            return v;
        }
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
            bundle.putInt("tipo",4);
            dialogoAyuda.setArguments(bundle);
            dialogoAyuda.show(getFragmentManager(),"my_dialog");
        }
        return super.onOptionsItemSelected(item);
    }

}
