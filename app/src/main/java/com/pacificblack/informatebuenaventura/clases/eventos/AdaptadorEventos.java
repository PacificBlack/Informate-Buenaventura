package com.pacificblack.informatebuenaventura.clases.eventos;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.EventosHolder> implements Filterable {


    List<Eventos> listaEventos;
    List<Eventos> listaEventosFull;
    Context context;



    public AdaptadorEventos(List<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
        listaEventosFull = new ArrayList<>(listaEventos);
    }

    @NonNull
    @Override
    public EventosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventos,null,false);
        context = view.getContext();

        return new EventosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosHolder holder, int position) {

        holder.tituloeventos.setText(listaEventos.get(position).getTitulo_row_eventos());
        holder.descripcioneventos.setText(listaEventos.get(position).getDescripcion_row_eventos());
        holder.fechapublicacioneventos.setText(listaEventos.get(position).getFechapublicacion_row_eventos());
        holder.lugareventos.setText("Lugar: "+listaEventos.get(position).getLugar_row_eventos());
        holder.vistaeventos.setText(String.valueOf(listaEventos.get(position).getVistas_eventos())+" Visitas");

       //TODO: Aqui verifico si trae la imagen o no


        if (listaEventos.get(position).getImagen1_eventos() != null){

            Picasso.get().load(listaEventos.get(position).getImagen1_eventos())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imageneventos);

            final String imagen1_link = listaEventos.get(position).getImagen1_eventos();
            holder.imageneventos.setOnClickListener(new View.OnClickListener() {
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
            holder.imageneventos.setImageResource(R.drawable.ib);

        }

    }



    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    @Override
    public Filter getFilter() {
        return listaEventosFiltro;
    }

    private Filter listaEventosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Eventos> filtroListaEventos = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaEventos.addAll(listaEventosFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Eventos itemEvento : listaEventosFull){
                    if(itemEvento.getTitulo_row_eventos().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_eventos().toLowerCase().contains(filtroparametro)){
                        filtroListaEventos.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaEventos;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaEventos.clear();
            listaEventos.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };



    public class EventosHolder extends RecyclerView.ViewHolder {

        TextView tituloeventos,descripcioneventos,fechapublicacioneventos,lugareventos,vistaeventos;
        ImageView imageneventos;

        public EventosHolder(@NonNull View itemView) {
            super(itemView);

            tituloeventos = itemView.findViewById(R.id.titulo_row_eventos);
            descripcioneventos = itemView.findViewById(R.id.descripcion_row_eventos);
            fechapublicacioneventos = itemView.findViewById(R.id.fecha_row_eventos);
            lugareventos = itemView.findViewById(R.id.lugar_row_eventos);
            vistaeventos = itemView.findViewById(R.id.vista_row_eventos);
            imageneventos = itemView.findViewById(R.id.imagen_row_eventos);

        }
    }
}
