package com.example.alumno_j.pfinal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikip on 21/06/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {
    private List<Persona> mLstPersona = new ArrayList<>();
    private MainAdapterListener mMainAdapterListener;


    public MainAdapter(MainAdapterListener mainAdapterListener) {
        this.mMainAdapterListener =mainAdapterListener;
    }

    public void clearAllAndAddAll(List<Persona> lstPersona){
        mLstPersona.clear();
        mLstPersona.addAll(lstPersona);
        notifyDataSetChanged();
    }

    public void removeItemAtPosition(int position){
        if(mMainAdapterListener!=null)
            mMainAdapterListener.onRemovePersona(mLstPersona.get(position));
        mLstPersona.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MainAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MainAdapterViewHolder holder, int position) {
        Persona persona = mLstPersona.get(position);
        holder.itemView.setTag(persona);

        holder.itemView.setOnLongClickListener(itemViewOnLongClickListener);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
        holder.tvNombreCompleto.setText(persona.getNombre()==null?"":persona.getNombre()+", "+persona.getApellido()==null?"":persona.getApellido());
        holder.tvDireccion.setText(persona.getDireccion()==null?"":persona.getDireccion());
     //   holder.tvMainItemDocumentNumber.setText(student.getDocumentNumber()==null?"":student.getDocumentNumber());
       // holder.tvMainItemPhone.setText(student.getPhone()==null?"":student.getPhone());
       // int age = student.getAge()==null?0:Integer.parseInt(student.getAge());
       // holder.tvMainItemAge.setText(holder.itemView.getContext().getResources().getQuantityString(R.plurals.pl_anios,age,age));

    }
    private View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Persona persona = (Persona) view.getTag();
            if(mMainAdapterListener!=null)
                mMainAdapterListener.onSelectPersona(persona);
        }
    };

    //CUANDO PRESIONO LA FILA CON UN LONG CLICK
    private View.OnLongClickListener itemViewOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            Persona persona = (Persona) view.getTag();
            if(mMainAdapterListener!=null)
                mMainAdapterListener.onRemovePersona(persona);
            for(int i=0;i<mLstPersona.size();i++)
                if(persona.getId() == mLstPersona.get(i).getId()){
                    mLstPersona.remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            return false;
        }
    };
    @Override
    public int getItemCount() {
        return mLstPersona.size();
    }

    static class MainAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreCompleto,tvDireccion;

        public MainAdapterViewHolder(View itemView) {
            super(itemView);
            tvNombreCompleto= (TextView) itemView.findViewById(R.id.tvNombreCompleto);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);

        }
    }
    

    interface MainAdapterListener{
        void onSelectPersona(Persona persona);
        void onRemovePersona(Persona persona);
    }
}
