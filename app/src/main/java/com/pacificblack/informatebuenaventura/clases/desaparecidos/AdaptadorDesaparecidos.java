package com.pacificblack.informatebuenaventura.clases.desaparecidos;

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

public class AdaptadorDesaparecidos extends RecyclerView.Adapter<AdaptadorDesaparecidos.DesaparecidosHolder>implements View.OnClickListener, Filterable {

    List<Desaparecidos> listaDesaparecidos;
    List<Desaparecidos> listaDesaparecidosFull;
    private View.OnClickListener listener;

    public AdaptadorDesaparecidos(List<Desaparecidos> listaDesaparecidos) {
        this.listaDesaparecidos = listaDesaparecidos;
        listaDesaparecidosFull = new ArrayList<>(listaDesaparecidos);
    }


    @Override
    public void onClick(View v) {
        if (listener!=null){

            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public AdaptadorDesaparecidos.DesaparecidosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_desaparecidos,null,false);
       view.setOnClickListener(this);

        return new DesaparecidosHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDesaparecidos.DesaparecidosHolder holder, int position) {

        holder.titulodesaparecidos.setText(listaDesaparecidos.get(position).getTitulo_row_desaparecidos());
        holder.descripcioncortadesaparecidos.setText(listaDesaparecidos.get(position).getDescripcion_row_desaparecidos());
        holder.fechapublicaciondesaparecidos.setText(listaDesaparecidos.get(position).getFechapublicacion_row_desaparecidos());
        holder.vistadesaparecidos.setText(String.valueOf(listaDesaparecidos.get(position).getVista_row_desaparecidos()));
        holder.recompensadesaparecidos.setText(listaDesaparecidos.get(position).getRecompensa_row_desaparecidos());


        //TODO: Aqui verifico si trae la imagen o no


        if (listaDesaparecidos.get(position).getImagen1_desaparecidos() != null){

            Picasso.get().load(listaDesaparecidos.get(position).getImagen1_desaparecidos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagendesaparecidos);


        }else{
            holder.imagendesaparecidos.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no


    }

    @Override
    public int getItemCount() {
        return listaDesaparecidos.size();
    }

    @Override
    public Filter getFilter() {
        return listaDesaparecidosFiltro;
    }

    private Filter listaDesaparecidosFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Desaparecidos> filtroListaDesaparecidos = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaDesaparecidos.addAll(listaDesaparecidosFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Desaparecidos itemEvento : listaDesaparecidosFull){
                    if(itemEvento.getTitulo_row_desaparecidos().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_desaparecidos().toLowerCase().contains(filtroparametro)){
                        filtroListaDesaparecidos.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaDesaparecidos;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaDesaparecidos.clear();
            listaDesaparecidos.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class DesaparecidosHolder extends RecyclerView.ViewHolder {

        TextView titulodesaparecidos,descripcioncortadesaparecidos,fechapublicaciondesaparecidos,vistadesaparecidos,recompensadesaparecidos;
        ImageView imagendesaparecidos;

        public DesaparecidosHolder(@NonNull View itemView) {
            super(itemView);

            titulodesaparecidos = itemView.findViewById(R.id.titulo_row_desaparecidos);
            descripcioncortadesaparecidos = itemView.findViewById(R.id.descripcion_row_desaparecidos);
            fechapublicaciondesaparecidos = itemView.findViewById(R.id.fecha_row_desaparecidos);
            vistadesaparecidos = itemView.findViewById(R.id.vista_row_desaparecidos);
            recompensadesaparecidos = itemView.findViewById(R.id.recompensa_row_desaparecidos);
            imagendesaparecidos = itemView.findViewById(R.id.imagen_row_desaparecidos);
        }
    }
}
