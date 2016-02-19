package com.example.jesus.agendaoncologica;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jesus.agendaoncologica.database.DataBaseHelper;
import com.example.jesus.agendaoncologica.database.DatabaseManager;
import com.example.jesus.agendaoncologica.fragmentos.ContactoFundahog;
import com.example.jesus.agendaoncologica.fragmentos.FragmentoHistoriaPaciente;
import com.example.jesus.agendaoncologica.fragmentos.InformacionMedico;
import com.example.jesus.agendaoncologica.fragmentos.InformacionPaciente;
import com.example.jesus.agendaoncologica.fragmentos.Principal;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = new DatabaseManager(this);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstFun", true);
        if (isFirstRun) {
            // Inicializar tipo de examen
            TipoExamen tipoExamen;
            for(int aux = 0 ; aux< DataBaseHelper.tipoexamen.length; aux++){
                tipoExamen = new TipoExamen(DataBaseHelper.tipoexamen[aux]);
                db.insertarTipoExamen(tipoExamen);
                // Inicializar analisis
                switch (DataBaseHelper.tipoexamen[aux]){
                    case "Orina":
                        inicializarAnalisis(DataBaseHelper.analisisOrina,tipoExamen);
                        break;
                    case "Heces":
                        inicializarAnalisis(DataBaseHelper.analisisHeces,tipoExamen);
                        break;
                    case "Coagulacion":
                        inicializarAnalisis(DataBaseHelper.analisisCoagulacion,tipoExamen);
                        break;
                    case "Quimica Sanguinea":
                        inicializarAnalisis(DataBaseHelper.analisisQuimicaSanguiena,tipoExamen);
                        break;
                    case "Hormonas":
                        inicializarAnalisis(DataBaseHelper.analisisHormonas,tipoExamen);
                        break;
                    case "Marcadores tumorales":
                        inicializarAnalisis(DataBaseHelper.analisisMarcadoresTumorales,tipoExamen);
                        break;
                    case "Inmunologia":
                        inicializarAnalisis(DataBaseHelper.analisisInmunologia,tipoExamen);
                        break;
                    case "Bacteriologia":
                        inicializarAnalisis(DataBaseHelper.analisisBacteriologia,tipoExamen);
                        break;
                }
            }


            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstFun", false).commit();
        }
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.contain_frame, new Principal()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            fm.beginTransaction().replace(R.id.contain_frame,new Principal()).addToBackStack( "tag" ).commit();
        } else if (id == R.id.nav_gallery) {
            fm.beginTransaction().replace(R.id.contain_frame,new InformacionPaciente()).addToBackStack( "tag" ).commit();
        } else if (id == R.id.nav_slideshow) {
            fm.beginTransaction().replace(R.id.contain_frame,new InformacionMedico()).addToBackStack( "tag" ).commit();
        } else if (id == R.id.nav_manage) {
            fm.beginTransaction().replace(R.id.contain_frame,new FragmentoHistoriaPaciente()).addToBackStack( "tag" ).commit();
        } else if (id == R.id.nav_share) {
            fm.beginTransaction().replace(R.id.contain_frame,new ContactoFundahog()).addToBackStack( "tag" ).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inicializarAnalisis(String[] analisis, TipoExamen tipoExamen){
        AnalisisExamen analisisExamen;
        for(int aux = 0 ; aux<analisis.length;aux++){
            analisisExamen = new AnalisisExamen(analisis[aux],tipoExamen);
            db.insertarAnalisis(analisisExamen);
        }
    }
}
