package com.pacificblack.informatebuenaventura.clases.ofertas;

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

public class AdaptadorEmpleos extends RecyclerView.Adapter<AdaptadorEmpleos.EmpleosHolder> implements Filterable {

    List<OfertaEmpleos> listaEmpleos;
    List<OfertaEmpleos> listaEmpleosFull;


    public AdaptadorEmpleos(List<OfertaEmpleos> listaEmpleos) {
        this.listaEmpleos = listaEmpleos;
        listaEmpleosFull = new ArrayList<>(listaEmpleos);
    }


    @NonNull
    @Override
    public AdaptadorEmpleos.EmpleosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ofertaempleos,null,false);

        return new EmpleosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEmpleos.EmpleosHolder holder, int position) {

        holder.tituloempleos.setText(listaEmpleos.get(position).getTitulo_row_ofertasempleos());
        holder.descripcionempleos.setText(listaEmpleos.get(position).getDescripcion_row_ofertasempleos());
        holder.fechaempleos.setText(listaEmpleos.get(position).getFechapublicacion_row_ofertasempleos());
        holder.necesidadempleos.setText(listaEmpleos.get(position).getNecesidad_row_ofertasempleos());
        holder.vistaempleos.setText(String.valueOf(listaEmpleos.get(position).getVistas_ofertasempleos()));

        if (listaEmpleos.get(position).getImagen1_ofertasempleos() != null){

            Picasso.get().load(listaEmpleos.get(position).getImagen1_ofertasempleos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagenempleos);


        }else{
            holder.imagenempleos.setImageResource(R.drawable.imagennodisponible);

        }

    }



    @Override
    public int getItemCount() {
        return listaEmpleos.size();
    }

    @Override
    public Filter getFilter() {
        return listaEmpleosFiltro;
    }

    private Filter listaEmpleosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<OfertaEmpleos> filtroListaEmpleos = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaEmpleos.addAll(listaEmpleosFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (OfertaEmpleos itemEvento : listaEmpleosFull){
                    if(itemEvento.getTitulo_row_ofertasempleos().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_ofertasempleos().toLowerCase().contains(filtroparametro)){
                        filtroListaEmpleos.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaEmpleos;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaEmpleos.clear();
            listaEmpleos.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class EmpleosHolder extends RecyclerView.ViewHolder {

            TextView tituloempleos,descripcionempleos,fechaempleos,necesidadempleos,vistaempleos;
            ImageView imagenempleos;

        public EmpleosHolder(@NonNull View itemView) {
            super(itemView);

            tituloempleos = itemView.findViewById(R.id.titulo_row_ofertaempleos);
            descripcionempleos = itemView.findViewById(R.id.descripcion_row_ofertaempleos);
            fechaempleos = itemView.findViewById(R.id.fecha_row_ofertaempleos);
            necesidadempleos = itemView.findViewById(R.id.nota_row_ofertaempleos);
            vistaempleos = itemView.findViewById(R.id.vista_row_ofertaempleos);
            imagenempleos = itemView.findViewById(R.id.imagen_row_ofertaempleos);

        }
    }
}
