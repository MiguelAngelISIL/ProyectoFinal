package com.example.alumno_j.pfinal;


import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements MainAdapter.MainAdapterListener{

    public static final String TAG = "MainActivity";
    private FragmentLeft mFragmentLeft;
    private BlankFragment blankFragment;
    // private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentLeft= (FragmentLeft) Fragment.instantiate(MainActivity.this, FragmentLeft.class.getName());
        getFragmentManager().beginTransaction().replace(R.id.frameleft,mFragmentLeft).commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //OBTENIENDO LOS DATOS DE LA TABLA STUDENT
        //LIMPIANDO LA LISTA DEL RECYCLER ASIGNANDOLE NUEVA DATA Y REFRESCANDO LA VISTA
       // mMainAdapter.clearAllAndAddAll(mPersonaDAO.getAll());
    }


    @Override
    public void onSelectPersona(Persona persona) {

    }

    @Override
    public void onRemovePersona(Persona persona) {

    }
}
