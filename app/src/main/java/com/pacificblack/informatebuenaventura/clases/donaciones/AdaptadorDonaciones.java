package com.pacificblack.informatebuenaventura.clases.donaciones;

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

public class AdaptadorDonaciones extends RecyclerView.Adapter<AdaptadorDonaciones.DonacionesHolder> implements View.OnClickListener, Filterable {

    List<Donaciones> listaDonaciones;
    List<Donaciones> listaDonacionesFull;

    private View.OnClickListener listener;

    public AdaptadorDonaciones(List<Donaciones> listaDonaciones) {
        this.listaDonaciones = listaDonaciones;
        listaDonacionesFull = new ArrayList<>(listaDonaciones);
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){

            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AdaptadorDonaciones.DonacionesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_donaciones,null,false);
        view.setOnClickListener(this);

        return new DonacionesHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDonaciones.DonacionesHolder holder, int position) {

        holder.titulodonaciones.setText(listaDonaciones.get(position).getTitulo_row_donaciones());
        holder.descripcioncortadonaciones.setText(listaDonaciones.get(position).getDescripcion_row_donaciones());
        holder.fechapublicaciondonaciones.setText(listaDonaciones.get(position).getFechapublicacion_row_donaciones());
        holder.metadonaciones.setText(String.valueOf("Meta: "+listaDonaciones.get(position).getMeta_row_donaciones()));
        holder.vistadonaciones.setText(String.valueOf(listaDonaciones.get(position).getVistas_donaciones())+" Visitas");

        //TODO: Aqui verifico si trae la imagen o no


        if (listaDonaciones.get(position).getImagen1_donaciones() != null){

            Picasso.get().load(listaDonaciones.get(position).getImagen1_donaciones())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagendonaciones);


        }else{
            holder.imagendonaciones.setImageResource(R.drawable.ib);

        }



        //TODO: Aqui verifico si trae la imagen o no

    }

    @Override
    public int getItemCount() {
        return listaDonaciones.size();
    }

    @Override
    public Filter getFilter() {
        return listaDonacionesFiltro;
    }

    private Filter listaDonacionesFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Donaciones> filtroListaDonaciones = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaDonaciones.addAll(listaDonacionesFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Donaciones itemEvento : listaDonacionesFull){
                    if(itemEvento.getTitulo_row_donaciones().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_donaciones().toLowerCase().contains(filtroparametro)){
                        filtroListaDonaciones.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaDonaciones;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaDonaciones.clear();
            listaDonaciones.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class DonacionesHolder extends RecyclerView.ViewHolder {

        TextView titulodonaciones,descripcioncortadonaciones,fechapublicaciondonaciones,metadonaciones,vistadonaciones;
        ImageView imagendonaciones;

        public DonacionesHolder(@NonNull View itemView) {
            super(itemView);

            titulodonaciones = itemView.findViewById(R.id.titulo_row_donaciones);
            descripcioncortadonaciones = itemView.findViewById(R.id.descripcion_row_donaciones);
            fechapublicaciondonaciones = itemView.findViewById(R.id.fecha_row_donaciones);
            metadonaciones = itemView.findViewById(R.id.meta_row_donaciones);
            vistadonaciones = itemView.findViewById(R.id.vista_row_donaciones);
            imagendonaciones = itemView.findViewById(R.id.imagen_row_donaciones);
        }
    }
}
