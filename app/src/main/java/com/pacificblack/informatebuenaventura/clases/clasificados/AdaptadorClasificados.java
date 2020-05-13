package com.pacificblack.informatebuenaventura.clases.clasificados;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorClasificados extends RecyclerView.Adapter<AdaptadorClasificados.ClasificadosHolder>implements View.OnClickListener, Filterable {

   List<Clasificados> listaClasificados;
   List<Clasificados> listaClasificadosFull;

   private View.OnClickListener listener;

    public AdaptadorClasificados(List<Clasificados> listaClasificados) {
        this.listaClasificados = listaClasificados;
        listaClasificadosFull = new ArrayList<>(listaClasificados);
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){

            listener.onClick(v);
        }


    }

    @NonNull
    @Override
    public AdaptadorClasificados.ClasificadosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_clasificados,null,false);
        view.setOnClickListener(this);
        return new ClasificadosHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorClasificados.ClasificadosHolder holder, int position) {


        if (listaClasificados.get(position).getImagen1_clasificados() != null){

            Picasso.get().load(listaClasificados.get(position).getImagen1_clasificados())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagenclasificados);


        }else{
            holder.imagenclasificados.setImageResource(R.drawable.ib);

        }
        holder.tituloclasificados.setText(listaClasificados.get(position).getTitulo_row_clasificados());
        holder.descripcioncortaclasificados.setText(listaClasificados.get(position).getDescripcion_row_clasificados());
        holder.fechapublicacionclasificados.setText(listaClasificados.get(position).getFechapublicacion_row_clasificados());
        holder.vistaclasificados.setText(String.valueOf(listaClasificados.get(position).getVistas_bienes())+" Visitas");

    }

    @Override
    public int getItemCount() {
        return listaClasificados.size();
    }

    @Override
    public Filter getFilter() {
        return listaClasificadosFiltro;
    }


    private Filter listaClasificadosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Clasificados> filtroListaClasificados = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaClasificados.addAll(listaClasificadosFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Clasificados itemEvento : listaClasificadosFull){
                    if(itemEvento.getTitulo_row_clasificados().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_clasificados().toLowerCase().contains(filtroparametro)){
                        filtroListaClasificados.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaClasificados;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaClasificados.clear();
            listaClasificados.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ClasificadosHolder extends RecyclerView.ViewHolder {

        TextView tituloclasificados,descripcioncortaclasificados,fechapublicacionclasificados,vistaclasificados;
        ImageView imagenclasificados;

        public ClasificadosHolder(@NonNull View itemView) {
            super(itemView);

            tituloclasificados = itemView.findViewById(R.id.titulo_row_clasificados);
            descripcioncortaclasificados = itemView.findViewById(R.id.descripcion_row_clasificados);
            fechapublicacionclasificados = itemView.findViewById(R.id.fecha_row_clasificados);
            vistaclasificados = itemView.findViewById(R.id.vista_row_clasificados);
            imagenclasificados = itemView.findViewById(R.id.imagen_row_clasificados);

        }
    }
}
