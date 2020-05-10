package com.pacificblack.informatebuenaventura.fragments.inicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.InicioHolder> {

    ArrayList<Inicio> listaInicio;
    Context context;


    public AdaptadorInicio(ArrayList<Inicio> listaInicio) {
        this.listaInicio = listaInicio;
    }

    @NonNull
    @Override
    public AdaptadorInicio.InicioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid,null,false);
        context = view.getContext();

        return new InicioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorInicio.InicioHolder holder, int position) {

        holder.texto_inicio.setText(listaInicio.get(position).getDescripcion_corta());

        if (listaInicio.get(position).getImagen_inicio() != null){

            Picasso.get().load(listaInicio.get(position).getImagen_inicio())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagen_inicio);

            final String imagen1_link = listaInicio.get(position).getImagen_inicio();
            holder.imagen_inicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentultima = new Intent(context, FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentultima.putExtras(envioimg);
                    context.startActivity(intentultima);
                }
            });


        }else{
            holder.imagen_inicio.setImageResource(R.drawable.ib);

        }
    }

    @Override
    public int getItemCount() {
        return listaInicio.size();
    }

    public class InicioHolder extends RecyclerView.ViewHolder {
        TextView texto_inicio;
        ImageView imagen_inicio;

        public InicioHolder(@NonNull View itemView) {
            super(itemView);
            texto_inicio = itemView.findViewById(R.id.texto_inicio);
            imagen_inicio = itemView.findViewById(R.id.imagen_inicio);
        }
    }
}
