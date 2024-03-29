package com.pacificblack.informatebuenaventura.clases.comprayventa;

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

public class AdaptadorComprayVenta extends RecyclerView.Adapter<AdaptadorComprayVenta.ComprayVentaHolder>implements View.OnClickListener, Filterable {

    List<ComprayVenta> listaComprayVenta;
    List<ComprayVenta> listaComprayVentaFull;
    private View.OnClickListener listener;

    public AdaptadorComprayVenta(List<ComprayVenta> listaComprayVenta) {
        this.listaComprayVenta = listaComprayVenta;
        listaComprayVentaFull = new ArrayList<>(listaComprayVenta);
    }

    @Override
    public void onClick(View v) {

        if (listener!=null){

            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public AdaptadorComprayVenta.ComprayVentaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comprayventa,null,false);
        view.setOnClickListener(this);

        return new ComprayVentaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorComprayVenta.ComprayVentaHolder holder, int position) {

        if (listaComprayVenta.get(position).getImagen1_comprayventa() != null){

            Picasso.get().load(listaComprayVenta.get(position).getImagen1_comprayventa())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(holder.imagencomprayventa);


        }else{
            holder.imagencomprayventa.setImageResource(R.drawable.ib);

        }

        holder.titulocomprayventa.setText(listaComprayVenta.get(position).getTitulo_row_comprayventa());
        holder.descripcioncortacomprayventa.setText(listaComprayVenta.get(position).getDescripcion_row_comprayventa());
        holder.fechapublicacioncomprayventa.setText(listaComprayVenta.get(position).getFechapublicacion_row_comprayventa());
        holder.preciocomprayventa.setText("Precio: "+listaComprayVenta.get(position).getPrecio_row_comprayventa());
        holder.vistacomprayventa.setText(String.valueOf(listaComprayVenta.get(position).getVista_comprayventa())+" Visitas");


    }

    @Override
    public int getItemCount() {
        return listaComprayVenta.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

    @Override
    public Filter getFilter() {
        return listaComprayVentaFiltro;
    }


    private Filter listaComprayVentaFiltro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ComprayVenta> filtroListaComprayVenta = new ArrayList<>();
            if (constraint==null || constraint.length()==0){
                filtroListaComprayVenta.addAll(listaComprayVentaFull);

            }else {
                String filtroparametro = constraint.toString().toLowerCase().trim();
                for (ComprayVenta itemEvento : listaComprayVentaFull){
                    if(itemEvento.getTitulo_row_comprayventa().toLowerCase().contains(filtroparametro) || itemEvento.getDescripcion_row_comprayventa().toLowerCase().contains(filtroparametro)){
                        filtroListaComprayVenta.add(itemEvento);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtroListaComprayVenta;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            listaComprayVenta.clear();
            listaComprayVenta.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ComprayVentaHolder extends RecyclerView.ViewHolder {

        TextView titulocomprayventa,descripcioncortacomprayventa,fechapublicacioncomprayventa,preciocomprayventa,vistacomprayventa;
        ImageView imagencomprayventa;

        public ComprayVentaHolder(@NonNull View itemView) {
            super(itemView);

            titulocomprayventa = itemView.findViewById(R.id.titulo_row_compra);
            descripcioncortacomprayventa = itemView.findViewById(R.id.descripcion_row_compra);
            fechapublicacioncomprayventa = itemView.findViewById(R.id.fecha_row_compra);
            preciocomprayventa = itemView.findViewById(R.id.precio_row_compra);
            vistacomprayventa = itemView.findViewById(R.id.vista_row_compra);
            imagencomprayventa = itemView.findViewById(R.id.imagen_row_compra);


        }
    }
}
