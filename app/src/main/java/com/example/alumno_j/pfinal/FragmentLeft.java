package com.example.alumno_j.pfinal;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by mikip on 21/06/2017.
 */

public class FragmentLeft extends Fragment implements MainAdapter.MainAdapterListener{

    private MainAdapter mMainAdapter;
    private PersonaDao mPersonaDAO;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onSelectPersona(Persona persona) {

    }

    @Override
    public void onRemovePersona(Persona persona) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView rvMainPersonas = (RecyclerView) getView().findViewById(R.id.rvMainPersonas);
        //ASIGNANDOLE EL MANAGER DE LA VISTA DEL RECYCLER EN MODO VERTICAL LINEAR (LISTA)
        rvMainPersonas.setLayoutManager(new LinearLayoutManager(getActivity()));
        //ASIGNANDO EL ADAPTADOR AL RECYCLER VIEW
        rvMainPersonas.setAdapter(mMainAdapter);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        try {
            //CREANDO LA BASE DE DATOS SI NO EXISTE
            databaseHelper.createDataBase();
//            databaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
