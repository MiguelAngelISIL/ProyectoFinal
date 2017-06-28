package com.example.alumno_j.pfinal;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikip on 21/06/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder> {
    private List<Persona> mLstPersona = new ArrayList<>();
    private MainAdapterListener mMainAdapterListener;


    public MainAdapter(Activity mainAdapterListener) {
        this.mMainAdapterListener = (MainAdapterListener) mainAdapterListener;
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
        holder.tvNombre.setText(persona.getNombre()==null?"":persona.getNombre());
        holder.tvApellido.setText(persona.getApellido()==null?"":persona.getApellido());
        holder.tvDireccion.setText(persona.getDireccion()==null?"":persona.getDireccion());

       holder.tvDocumento.setText(persona.getDocumento()==null?"":persona.getDocumento());
        holder.tvTip_doc.setText(persona.getTip_doc()==null?"":persona.getTip_doc());
       holder.tvFecha_nac.setText(persona.getFec_nac()==null?"":persona.getFec_nac());
       int age = persona.getEdad()==null?0:Integer.parseInt(persona.getEdad());
        holder.tvEdad.setText(holder.itemView.getContext().getResources().getQuantityString(R.plurals.pl_anios,age,age));

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
        TextView tvNombre,tvApellido,tvDireccion, tvEdad,tvDocumento,tvTip_doc,tvFecha_nac;


        public MainAdapterViewHolder(View itemView) {
            super(itemView);
            tvNombre= (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellido=(TextView)itemView.findViewById(R.id.tvApellido);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
           tvEdad=(TextView)itemView.findViewById(R.id.tvEdad);
            tvDocumento=(TextView)itemView.findViewById(R.id.tvDocumento);
            tvTip_doc=(TextView)itemView.findViewById(R.id.tvTip_doc);
            tvFecha_nac=(TextView)itemView.findViewById(R.id.tvFecha_nac);

        }
    }
    

    interface MainAdapterListener{
        void onSelectPersona(Persona persona);
        void onRemovePersona(Persona persona);
    }
}
