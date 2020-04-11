package com.pacificblack.informatebuenaventura.clases.donaciones;

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

public class AdaptadorDonaciones extends RecyclerView.Adapter<AdaptadorDonaciones.DonacionesHolder> implements View.OnClickListener {

    ArrayList<Donaciones> listaDonaciones;
    private View.OnClickListener listener;

    public AdaptadorDonaciones(ArrayList<Donaciones> listaDonaciones) {
        this.listaDonaciones = listaDonaciones;
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
        holder.metadonaciones.setText(String.valueOf(listaDonaciones.get(position).getMeta_row_donaciones()));
        holder.vistadonaciones.setText(String.valueOf(listaDonaciones.get(position).getVistas_donaciones()));

        //TODO: Aqui verifico si trae la imagen o no


        if (listaDonaciones.get(position).getImagen1_donaciones() != null){

            Picasso.get().load(listaDonaciones.get(position).getImagen1_donaciones())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagendonaciones);


        }else{
            holder.imagendonaciones.setImageResource(R.drawable.imagennodisponible);

        }



        //TODO: Aqui verifico si trae la imagen o no

    }

    @Override
    public int getItemCount() {
        return listaDonaciones.size();
    }

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
