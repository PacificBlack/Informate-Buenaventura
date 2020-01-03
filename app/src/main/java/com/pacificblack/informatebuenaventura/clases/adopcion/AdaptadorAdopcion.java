package com.pacificblack.informatebuenaventura.clases.adopcion;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorAdopcion extends RecyclerView.Adapter<AdaptadorAdopcion.AdopcionHolder> {

    ArrayList<Adopcion> listaAdopcion;

    public AdaptadorAdopcion(ArrayList<Adopcion> listaAdopcion) {
        this.listaAdopcion = listaAdopcion;
    }

    @NonNull
    @Override
    public AdopcionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adopcion,null,false);
        return new AdopcionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdopcionHolder holder, int position) {


        holder.imagenadopcion.setImageResource(listaAdopcion.get(position).getImagen1_adopcion());
        holder.tituloadopcion.setText(listaAdopcion.get(position).getTitulo_row_adopcion());
        holder.descripcioncortaadopcion.setText(listaAdopcion.get(position).getDescripcion_row_adopcion());
        holder.fechapublicacionadopcion.setText(listaAdopcion.get(position).getFechapublicacion_row_desaparecidos());
        holder.vistaadopcion.setText(String.valueOf(listaAdopcion.get(position).getVistas_adopcion()));

    }

    @Override
    public int getItemCount() {
        return listaAdopcion.size();
    }


    public class AdopcionHolder extends RecyclerView.ViewHolder {

        TextView tituloadopcion,descripcioncortaadopcion,fechapublicacionadopcion,vistaadopcion;
        ImageView imagenadopcion;

        public AdopcionHolder(@NonNull View itemView) {
            super(itemView);

            tituloadopcion = itemView.findViewById(R.id.titulo_row_adopcion);
            descripcioncortaadopcion = itemView.findViewById(R.id.descripcion_row_adopcion);
            fechapublicacionadopcion = itemView.findViewById(R.id.fecha_row_adopcion);
            vistaadopcion = itemView.findViewById(R.id.vista_row_adopcion);
            imagenadopcion = itemView.findViewById(R.id.imagen_row_adopcion);


        }
    }
}
