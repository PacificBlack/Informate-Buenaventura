package com.pacificblack.informatebuenaventura.clases.directorio;

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

public class AdaptadorDirectorio extends RecyclerView.Adapter<AdaptadorDirectorio.DirectorioHolder> implements Filterable {

    List<Directorio> listaDirectorio;
    List<Directorio> listaDirectorioFull;


    public AdaptadorDirectorio(List<Directorio> listaDirectorio) {
        this.listaDirectorio = listaDirectorio;
        listaDirectorioFull = new ArrayList<>(listaDirectorio);
    }

    @NonNull
    @Override
    public DirectorioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_directorios,null,false);

        return new DirectorioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorioHolder holder, int position) {

        holder.titulodirectorio.setText(listaDirectorio.get(position).getTitulo_row_directorio());
        holder.descripcioncortadirectorio.setText(listaDirectorio.get(position).getDescripcion_row_directorio());
        holder.contactosdirectorio.setText(listaDirectorio.get(position).getContactos_row_directorio());
        holder.fechapublicaciondirectorio.setText(listaDirectorio.get(position).getFechapublicacion_row_directorio());
        holder.vistadirectorio.setText(String.valueOf(listaDirectorio.get(position).getVistas_row_directorio()));

        if (listaDirectorio.get(position).getImagen1_directorio() != null){

            Picasso.get().load(listaDirectorio.get(position).getImagen1_directorio())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagendirectorio);


        }else{
            holder.imagendirectorio.setImageResource(R.drawable.imagennodisponible);

        }    }

    @Override
    public int getItemCount() {
        return listaDirectorio.size();
    }

    @Override
    public Filter getFilter() {
        return listaDirectorioFiltro;
    }

    private Filter listaDirectorioFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Directorio> filtroListaDirectorio = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaDirectorio.addAll(listaDirectorioFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Directorio itemEvento : listaDirectorioFull){
                    if(itemEvento.getTitulo_row_directorio().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_directorio().toLowerCase().contains(filtroparametro)){
                        filtroListaDirectorio.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaDirectorio;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaDirectorio.clear();
            listaDirectorio.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class DirectorioHolder extends RecyclerView.ViewHolder {

        TextView titulodirectorio,descripcioncortadirectorio,contactosdirectorio,fechapublicaciondirectorio,vistadirectorio;
        ImageView imagendirectorio;


        public DirectorioHolder(@NonNull View itemView) {
            super(itemView);
            titulodirectorio = itemView.findViewById(R.id.titulo_row_directorios);
            descripcioncortadirectorio = itemView.findViewById(R.id.descripcion_row_directorios);
            contactosdirectorio = itemView.findViewById(R.id.contactos_row_directorios);
            fechapublicaciondirectorio = itemView.findViewById(R.id.fecha_row_directorios);
            vistadirectorio = itemView.findViewById(R.id.vista_row_directorio);
            imagendirectorio = itemView.findViewById(R.id.imagen_row_directorios);
        }
    }
}
