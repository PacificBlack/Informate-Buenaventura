package com.pacificblack.informatebuenaventura.clases.funebres;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorFunebres extends RecyclerView.Adapter<AdaptadorFunebres.FunebresHolder>implements View.OnClickListener {

    ArrayList<Funebres> listaFunebres;
    private View.OnClickListener listener;

    public AdaptadorFunebres(ArrayList<Funebres> listaFunebres) {
        this.listaFunebres = listaFunebres;
    }

    @Override
    public void onClick(View v) {

        if (listener!=null){

            listener.onClick(v);
        }

    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }


    @NonNull
    @Override
    public AdaptadorFunebres.FunebresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_funebres,null,false);
        view.setOnClickListener(this);

        return new FunebresHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFunebres.FunebresHolder holder, int position) {

        holder.titulofunebres.setText(listaFunebres.get(position).getTitulo_row_funebres());
        holder.descripcionfunebres.setText(listaFunebres.get(position).getDescripcion_row_funebres());
        holder.fechafunebres.setText(listaFunebres.get(position).getFechapublicacion_row_funebres());
        holder.vistafunebres.setText(String.valueOf(listaFunebres.get(position).getVistas_funebres()));
        holder.imagenfunebres.setImageResource(listaFunebres.get(position).getImagen1_funebres());

    }

    @Override
    public int getItemCount() {
        return listaFunebres.size();
    }

    public class FunebresHolder extends RecyclerView.ViewHolder {

        TextView titulofunebres,descripcionfunebres,fechafunebres,vistafunebres;
        ImageView imagenfunebres;

        public FunebresHolder(@NonNull View itemView) {
            super(itemView);

            titulofunebres = itemView.findViewById(R.id.titulo_row_funebres);
            descripcionfunebres = itemView.findViewById(R.id.descripcion_row_funebres);
            fechafunebres = itemView.findViewById(R.id.fecha_row_funebres);
            vistafunebres = itemView.findViewById(R.id.vista_row_funebres);
            imagenfunebres = itemView.findViewById(R.id.imagen_row_funebres);

        }
    }
}
