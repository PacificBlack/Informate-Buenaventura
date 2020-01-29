package com.pacificblack.informatebuenaventura.clases.eventos;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageRequest;
import com.pacificblack.informatebuenaventura.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorEventos extends RecyclerView.Adapter<AdaptadorEventos.EventosHolder> {


    ArrayList<Eventos> listaEventos;

    public AdaptadorEventos(ArrayList<Eventos> listaEventos) {
        this.listaEventos = listaEventos;
    }

    @NonNull
    @Override
    public EventosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_eventos,null,false);

        return new EventosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosHolder holder, int position) {

        holder.tituloeventos.setText(listaEventos.get(position).getTitulo_row_eventos());
        holder.descripcioneventos.setText(listaEventos.get(position).getDescripcion_row_eventos());
        holder.fechapublicacioneventos.setText(listaEventos.get(position).getFechapublicacion_row_eventos());
        holder.lugareventos.setText(listaEventos.get(position).getLugar_row_eventos());
        holder.vistaeventos.setText(String.valueOf(listaEventos.get(position).getVistas_eventos()));

       //TODO: Aqui verifico si trae la imagen o no


        if (listaEventos.get(position).getImagen1_eventos() != null){

            Picasso.get().load(listaEventos.get(position).getImagen1_eventos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imageneventos);


        }else{
            holder.imageneventos.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no


    }



    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

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
