package com.pacificblack.informatebuenaventura.clases.noticias;

import android.app.Activity;
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

public class AdaptadorUltimaHora extends RecyclerView.Adapter<AdaptadorUltimaHora.UltimaHolder> implements Filterable {

    List<UltimaHora> listaUltimaHora;
    List<UltimaHora> listaUltimaHoraFull;
    Context context;


    public AdaptadorUltimaHora(List<UltimaHora> listaUltimaHora) {
        this.listaUltimaHora = listaUltimaHora;
        listaUltimaHoraFull = new ArrayList<>(listaUltimaHora);
    }

    @NonNull
    @Override
    public UltimaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ultimahora,null,false);

        context = view.getContext();

        return new UltimaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UltimaHolder holder, int position) {

        holder.tituloultima.setText(listaUltimaHora.get(position).getTitulo_row_ultimahora());
        holder.descripcionultima.setText(listaUltimaHora.get(position).getDescripcion_row_ultimahora());
        holder.fechaultimo.setText(listaUltimaHora.get(position).getFechapublicacion_row_ultimahora());
        if (listaUltimaHora.get(position).getImagen_row_ultimahora() != null){

            Picasso.get().load(listaUltimaHora.get(position).getImagen_row_ultimahora())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagenultima);

            final String imagen1_link = listaUltimaHora.get(position).getImagen_row_ultimahora();
            holder.imagenultima.setOnClickListener(new View.OnClickListener() {
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
            holder.imagenultima.setImageResource(R.drawable.ib);

        }

    }

    @Override
    public int getItemCount() {
        return listaUltimaHora.size();
    }

    @Override
    public Filter getFilter() {
        return listaUltimaHoraFiltro;
    }

    private Filter listaUltimaHoraFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UltimaHora> filtroListaUltimaHora = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaUltimaHora.addAll(listaUltimaHoraFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (UltimaHora itemEvento : listaUltimaHoraFull){
                    if(itemEvento.getTitulo_row_ultimahora().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_ultimahora().toLowerCase().contains(filtroparametro)){
                        filtroListaUltimaHora.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaUltimaHora;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaUltimaHora.clear();
            listaUltimaHora.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class UltimaHolder extends RecyclerView.ViewHolder {

        TextView tituloultima,descripcionultima,fechaultimo;
        ImageView imagenultima;

        public UltimaHolder(@NonNull View itemView) {
            super(itemView);

            tituloultima = itemView.findViewById(R.id.titulo_row_ultimahora);
            descripcionultima = itemView.findViewById(R.id.descripcion_row_ultimahora);
            fechaultimo = itemView.findViewById(R.id.fecha_row_ultimahora);
            imagenultima = itemView.findViewById(R.id.imagen_row_ultimahora);

        }
    }
}
