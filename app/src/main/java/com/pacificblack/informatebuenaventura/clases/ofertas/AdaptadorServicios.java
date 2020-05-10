package com.pacificblack.informatebuenaventura.clases.ofertas;

import android.content.Context;
import android.content.Intent;
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

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorServicios extends RecyclerView.Adapter<AdaptadorServicios.ServiciosHolder> implements Filterable {

    List<OfertaServicios> listaServicios;
    List<OfertaServicios> listaServiciosFull;
    Context context;



    public AdaptadorServicios(List<OfertaServicios> listaServicios) {
        this.listaServicios = listaServicios;
        listaServiciosFull = new ArrayList<>(listaServicios);

    }

    @NonNull
    @Override
    public ServiciosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ofertaservicios,null,false);
        context = view.getContext();

        return new ServiciosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosHolder holder, int position) {

        holder.tituloservicios.setText(listaServicios.get(position).getTitulo_row_ofertaservicios());
        holder.descripcionservicios.setText(listaServicios.get(position).getDescripcion_row_ofertaservicios());
        holder.fechaservicios.setText(listaServicios.get(position).getFechapublicacion_row_ofertaservicios());
        holder.notaservicios.setText(listaServicios.get(position).getNecesidad_row_ofertaservicios());
        holder.vistaservicios.setText(String.valueOf(listaServicios.get(position).getVistas_ofertaservicios()));

        if (listaServicios.get(position).getImagen1_ofertaservicios() != null){

            Picasso.get().load(listaServicios.get(position).getImagen1_ofertaservicios())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagenservicios);

            final String imagen1_link = listaServicios.get(position).getImagen1_ofertaservicios();
            holder.imagenservicios.setOnClickListener(new View.OnClickListener() {
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
            holder.imagenservicios.setImageResource(R.drawable.ib);

        }
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    @Override
    public Filter getFilter() {
        return listaServiciosFiltro;
    }

    private Filter listaServiciosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OfertaServicios> filtroListaOfertaServicios = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaOfertaServicios.addAll(listaServiciosFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (OfertaServicios itemEvento : listaServiciosFull){
                    if(itemEvento.getTitulo_row_ofertaservicios().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_ofertaservicios().toLowerCase().contains(filtroparametro)){
                        filtroListaOfertaServicios.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaOfertaServicios;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaServicios.clear();
            listaServicios.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

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
