package com.example.alumno_j.pfinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by mikip on 21/06/2017.
 */

public class FragmentLeft extends android.app.Fragment  {


    private static final String TAG = "FragmentLeft";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private MainAdapter mMainAdapter;
    private PersonaDao mPersonaDAO;
    private Persona persona;
    private RecyclerView mRecyclerView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);

        //INICIALIZANDO EL ADAPTER DE RECYCLER VIEW
        mMainAdapter = new MainAdapter(getActivity());

        //INFLANDO EL RECYCLER VIEW
        RecyclerView rvMainPersonas = (RecyclerView) view.findViewById(R.id.rvMainPersonas);
        //ASIGNANDOLE EL MANAGER DE LA VISTA DEL RECYCLER EN MODO VERTICAL LINEAR (LISTA)
        rvMainPersonas.setLayoutManager(new LinearLayoutManager(getActivity()));
        //ASIGNANDO EL ADAPTADOR AL RECYCLER VIEW
        rvMainPersonas.setAdapter(mMainAdapter);

        //SWIPE TO ACTION
      /*  ItemTouchHelper itemTouchHelper = new ItemTouchHelper(rvMainItemTouchHelperSimpleCallback);
        itemTouchHelper.attachToRecyclerView(rvMainStudents);
*/
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        try {
            //CREANDO LA BASE DE DATOS SI NO EXISTE
            databaseHelper.createDataBase();
//            databaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


}
