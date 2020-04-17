package com.pacificblack.informatebuenaventura.clases.noticias;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNoticias extends RecyclerView.Adapter<AdaptadorNoticias.NoticiasHolder>implements View.OnClickListener, Filterable {

    List<Noticias> listaNoticias;
    List<Noticias> listaNoticiasFull;
    private View.OnClickListener listener;

    public AdaptadorNoticias(List<Noticias> listaNoticias) {
        this.listaNoticias = listaNoticias;
        listaNoticiasFull = new ArrayList<>(listaNoticias);
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

    @NonNull
    @Override
    public AdaptadorNoticias.NoticiasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noticias,null,false);
        view.setOnClickListener(this);

        return new NoticiasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNoticias.NoticiasHolder holder, int position) {

        holder.titulonoticias.setText(listaNoticias.get(position).getTitulo_row_noticias());
        holder.descripcionnoticias.setText(listaNoticias.get(position).getDescripcion_row_noticias());
        holder.fechanoticias.setText(listaNoticias.get(position).getFechapublicacion_row_noticias());
        holder.vistanoticias.setText(String.valueOf(listaNoticias.get(position).getVistas_noticias()));
        holder.likenoticias.setText(String.valueOf(listaNoticias.get(position).getLikes_noticias()));
        holder.dislikenoticias.setText(String.valueOf(listaNoticias.get(position).getDislikes_noticias()));

        if (listaNoticias.get(position).getImagen1_noticias() != null){

            Picasso.get().load(listaNoticias.get(position).getImagen1_noticias())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagennoticias);
        }else{
            holder.imagennoticias.setImageResource(R.drawable.imagennodisponible);

        }


    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    @Override
    public Filter getFilter() {
        return listaNoticiasFiltro;
    }

    private Filter listaNoticiasFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Noticias> filtroListaNoticias = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaNoticias.addAll(listaNoticiasFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Noticias itemEvento : listaNoticiasFull){
                    if(itemEvento.getTitulo_row_noticias().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_noticias().toLowerCase().contains(filtroparametro)){
                        filtroListaNoticias.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaNoticias;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaNoticias.clear();
            listaNoticias.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class NoticiasHolder extends RecyclerView.ViewHolder {

        TextView titulonoticias,descripcionnoticias,fechanoticias,vistanoticias;
        ImageView imagennoticias;
        RadioButton likenoticias, dislikenoticias;


        public NoticiasHolder(@NonNull View itemView) {
            super(itemView);

            titulonoticias = itemView.findViewById(R.id.titulo_row_noticias);
            descripcionnoticias = itemView.findViewById(R.id.descripcion_row_noticias);
            fechanoticias = itemView.findViewById(R.id.fecha_row_noticias);
            vistanoticias = itemView.findViewById(R.id.vista_row_noticias);
            imagennoticias = itemView.findViewById(R.id.imagen_row_noticias);
            likenoticias = itemView.findViewById(R.id.gusta_row_noticias);
            dislikenoticias = itemView.findViewById(R.id.nogusta_row_noticias);
        }
    }
}
