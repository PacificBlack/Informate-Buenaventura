package com.pacificblack.informatebuenaventura.clases.comprayventa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorComprayVenta extends RecyclerView.Adapter<AdaptadorComprayVenta.ComprayVentaHolder>implements View.OnClickListener {

    ArrayList<ComprayVenta> listaComprayVenta;
    private View.OnClickListener listener;

    public AdaptadorComprayVenta(ArrayList<ComprayVenta> listaComprayVenta) {
        this.listaComprayVenta = listaComprayVenta;
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


        //TODO: Aqui verifico si trae la imagen o no


        if (listaComprayVenta.get(position).getImagen1_comprayventa() != null){

            Picasso.get().load(listaComprayVenta.get(position).getImagen1_comprayventa())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagencomprayventa);


        }else{
            holder.imagencomprayventa.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no

        holder.titulocomprayventa.setText(listaComprayVenta.get(position).getTitulo_row_comprayventa());
        holder.descripcioncortacomprayventa.setText(listaComprayVenta.get(position).getDescripcion_row_comprayventa());
        holder.fechapublicacioncomprayventa.setText(listaComprayVenta.get(position).getFechapublicacion_row_comprayventa());
        holder.preciocomprayventa.setText(listaComprayVenta.get(position).getPrecio_row_comprayventa());
        holder.vistacomprayventa.setText(String.valueOf(listaComprayVenta.get(position).getVista_comprayventa()));


    }

    @Override
    public int getItemCount() {
        return listaComprayVenta.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

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
