package com.pacificblack.informatebuenaventura.clases.adopcion;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorAdopcion extends RecyclerView.Adapter<AdaptadorAdopcion.AdopcionHolder> implements View.OnClickListener, Filterable {

    List<Adopcion> listaAdopcion;
    List<Adopcion> listaAdopcionFull;
    private View.OnClickListener listener;

    public AdaptadorAdopcion(List<Adopcion> listaAdopcion) {
        this.listaAdopcion = listaAdopcion;
        listaAdopcionFull = new ArrayList<>(listaAdopcion);
    }



    @NonNull
    @Override
    public AdopcionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adopcion,null,false);
        view.setOnClickListener(this);
        return new AdopcionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdopcionHolder holder, int position) {


        //TODO: Aqui verifico si trae la imagen o no


        if (listaAdopcion.get(position).getImagen1_adopcion() != null){

            Picasso.get().load(listaAdopcion.get(position).getImagen1_adopcion())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagenadopcion);


        }else{
            holder.imagenadopcion.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no


        holder.tituloadopcion.setText(listaAdopcion.get(position).getTitulo_row_adopcion());
        holder.descripcioncortaadopcion.setText(listaAdopcion.get(position).getDescripcion_row_adopcion());
        holder.fechapublicacionadopcion.setText(listaAdopcion.get(position).getFechapublicacion_row_desaparecidos());
        holder.vistaadopcion.setText(String.valueOf(listaAdopcion.get(position).getVistas_adopcion()));

    }

    @Override
    public int getItemCount() {
        return listaAdopcion.size();
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

    @Override
    public Filter getFilter() {
        return listaAdopcionFiltro;
    }

    private Filter listaAdopcionFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Adopcion> filtroListaAdopcion = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaAdopcion.addAll(listaAdopcionFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Adopcion itemEvento : listaAdopcionFull){
                    if(itemEvento.getTitulo_row_adopcion().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_adopcion().toLowerCase().contains(filtroparametro)){
                        filtroListaAdopcion.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaAdopcion;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaAdopcion.clear();
            listaAdopcion.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


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
