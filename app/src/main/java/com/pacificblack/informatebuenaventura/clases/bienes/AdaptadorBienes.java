package com.pacificblack.informatebuenaventura.clases.bienes;

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

public class AdaptadorBienes extends RecyclerView.Adapter<AdaptadorBienes.BienesHolder>implements View.OnClickListener, Filterable {

   List<Bienes> listaBienes;
   List<Bienes> listaBienesFull;

   private View.OnClickListener listener;

    public AdaptadorBienes(List<Bienes> listaBienes) {
        this.listaBienes = listaBienes;
        listaBienesFull = new ArrayList<>(listaBienes);
    }



    @Override
    public void onClick(View v) {

        if (listener!=null){
            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public AdaptadorBienes.BienesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bienes,null,false);
        view.setOnClickListener(this);
        return new BienesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBienes.BienesHolder holder, int position) {

        holder.titulobienes.setText(listaBienes.get(position).getTitulo_row_bienes());
        holder.descripcionesbienes.setText(listaBienes.get(position).getDescripcion_row_bienes());
        holder.fechapublicacionbienes.setText(listaBienes.get(position).getFechapublicacion_row_bienes());
        holder.vistabienes.setText(String.valueOf(listaBienes.get(position).getVistas_bienes()));
        holder.preciobienes.setText(String.valueOf(listaBienes.get(position).getPrecio_row_bienes()));

        //TODO: Aqui verifico si trae la imagen o no


        if (listaBienes.get(position).getImagen1_bienes() != null){

            Picasso.get().load(listaBienes.get(position).getImagen1_bienes())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagenbienes);


        }else{
            holder.imagenbienes.setImageResource(R.drawable.ib);

        }



        //TODO: Aqui verifico si trae la imagen o no


    }

    @Override
    public int getItemCount() {
        return listaBienes.size();
    }

    @Override
    public Filter getFilter() {
        return listaBienesFiltro;
    }


    private Filter listaBienesFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Bienes> filtroListaBienes = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaBienes.addAll(listaBienesFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (Bienes itemEvento : listaBienesFull){
                    if(itemEvento.getTitulo_row_bienes().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_bienes().toLowerCase().contains(filtroparametro)){
                        filtroListaBienes.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaBienes;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaBienes.clear();
            listaBienes.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class BienesHolder extends RecyclerView.ViewHolder {

        TextView titulobienes,descripcionesbienes,fechapublicacionbienes,vistabienes,preciobienes;
        ImageView imagenbienes;

        public BienesHolder(@NonNull View itemView) {
            super(itemView);

            titulobienes = itemView.findViewById(R.id.titulo_row_bienes);
            descripcionesbienes = itemView.findViewById(R.id.descripcion_row_bienes);
            fechapublicacionbienes = itemView.findViewById(R.id.fecha_row_bienes);
            vistabienes = itemView.findViewById(R.id.vista_row_bienes);
            preciobienes = itemView.findViewById(R.id.precio_row_bienes);
            imagenbienes = itemView.findViewById(R.id.imagen_row_bienes);

        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}
