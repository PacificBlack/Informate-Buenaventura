package com.pacificblack.informatebuenaventura.clases.ofertas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorServicios extends RecyclerView.Adapter<AdaptadorServicios.ServiciosHolder> {

    ArrayList<OfertaServicios> listaServicios;

    public AdaptadorServicios(ArrayList<OfertaServicios> listaServicios) {
        this.listaServicios = listaServicios;
    }

    @NonNull
    @Override
    public ServiciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ofertaservicios,null,false);

        return new ServiciosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosHolder holder, int position) {

        holder.tituloservicios.setText(listaServicios.get(position).getTitulo_row_ofertaservicios());
        holder.descripcionservicios.setText(listaServicios.get(position).getDescripcion_row_ofertaservicios());
        holder.fechaservicios.setText(listaServicios.get(position).getFechapublicacion_row_ofertaservicios());
        holder.notaservicios.setText(listaServicios.get(position).getNecesidad_row_ofertaservicios());
        holder.vistaservicios.setText(String.valueOf(listaServicios.get(position).getVistas_ofertaservicios()));
        holder.imagenservicios.setImageResource(listaServicios.get(position).getImagen1_ofertaservicios());

    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    public class ServiciosHolder extends RecyclerView.ViewHolder {

        TextView tituloservicios,descripcionservicios,fechaservicios,notaservicios,vistaservicios;
        ImageView imagenservicios;

        public ServiciosHolder(@NonNull View itemView) {
            super(itemView);

            tituloservicios = itemView.findViewById(R.id.titulo_row_ofertaservicios);
            descripcionservicios = itemView.findViewById(R.id.descripcion_row_ofertaservicios);
            fechaservicios = itemView.findViewById(R.id.fecha_row_ofertaservicios);
            notaservicios = itemView.findViewById(R.id.nota_row_ofertaservicios);
            vistaservicios = itemView.findViewById(R.id.vista_row_ofertaservicios);
            imagenservicios = itemView.findViewById(R.id.imagen_row_ofertaservicios);


        }
    }
}
