package com.pacificblack.informatebuenaventura.clases.bienes;

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

public class AdaptadorBienes extends RecyclerView.Adapter<AdaptadorBienes.BienesHolder>implements View.OnClickListener {

   ArrayList<Bienes> listaBienes;
   private View.OnClickListener listener;

    public AdaptadorBienes(ArrayList<Bienes> listaBienes) {
        this.listaBienes = listaBienes;
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
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagenbienes);


        }else{
            holder.imagenbienes.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no


    }

    @Override
    public int getItemCount() {
        return listaBienes.size();
    }

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
