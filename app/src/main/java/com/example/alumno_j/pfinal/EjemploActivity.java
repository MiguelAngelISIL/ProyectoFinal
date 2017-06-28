package com.example.alumno_j.pfinal;

import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.ArrayList;

public class EjemploActivity extends AppCompatActivity implements MainAdapter.MainAdapterListener{

    private MainAdapter mMainAdapter;
    private PersonaDao mPersonaDAO;
    private BlankFragment blankFragment;
    private FragmentRight fragmentRight;
    private ImageView ivEstado;
     private Persona persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SE ASIGNA LA VISTA AL ACTIVIDAD
        setContentView(R.layout.activity_ejemplo);



        blankFragment = (BlankFragment) Fragment.instantiate(EjemploActivity.this, BlankFragment.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.contenedor,blankFragment).commit();


        //ASINGNAR EL EVENTO ONCLICK AL BOTON
       // findViewById(R.id.fabMainAdd).setOnClickListener(fabMainAddOnClickListener);


        //INICIALIZANDO EL OBJETO PARA MODIFICAR EL STUDENT
        mPersonaDAO = new PersonaDao(EjemploActivity.this);

        //INICIALIZANDO EL ADAPTER DE RECYCLER VIEW
        mMainAdapter = new MainAdapter(EjemploActivity.this);

        //INFLANDO EL RECYCLER VIEW
        RecyclerView rvEjemplo = (RecyclerView) findViewById(R.id.rvEjemplo);
        //ASIGNANDOLE EL MANAGER DE LA VISTA DEL RECYCLER EN MODO VERTICAL LINEAR (LISTA)
        rvEjemplo.setLayoutManager(new LinearLayoutManager(EjemploActivity.this));
        //ASIGNANDO EL ADAPTADOR AL RECYCLER VIEW
        rvEjemplo.setAdapter(mMainAdapter);

        //SWIPE TO ACTION
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(rvMainItemTouchHelperSimpleCallback);
      //  itemTouchHelper.attachToRecyclerView(rvMainStudents);

        DatabaseHelper databaseHelper = new DatabaseHelper(EjemploActivity.this);
        try {
            //CREANDO LA BASE DE DATOS SI NO EXISTE
            databaseHelper.createDataBase();
           databaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.agregar:

                fragmentRight = (FragmentRight) Fragment.instantiate(EjemploActivity.this, FragmentRight.class.getName());
                getFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentRight).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onStart() {
        super.onStart();
        //OBTENIENDO LOS DATOS DE LA TABLA STUDENT
        //LIMPIANDO LA LISTA DEL RECYCLER ASIGNANDOLE NUEVA DATA Y REFRESCANDO LA VISTA

        mMainAdapter.clearAllAndAddAll(mPersonaDAO.getAll());
    }



    @Override
    public void onSelectPersona(Persona persona) {

        fragmentRight = (FragmentRight) Fragment.instantiate(EjemploActivity.this, FragmentRight.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.contenedor,fragmentRight).commit();
        Bundle bundle = new Bundle();
        bundle.putParcelable(FragmentRight.ARG_PERSONA,persona); ;
        fragmentRight.setArguments(bundle);

    }

    @Override
    public void onRemovePersona(Persona persona) {

    }

}

