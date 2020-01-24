package com.pacificblack.informatebuenaventura.clases.noticias;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorUltimaHora extends RecyclerView.Adapter<AdaptadorUltimaHora.UltimaHolder> {

    ArrayList<UltimaHora> listaUltimaHora;

    public AdaptadorUltimaHora(ArrayList<UltimaHora> listaUltimaHora) {
        this.listaUltimaHora = listaUltimaHora;
    }

    @NonNull
    @Override
    public UltimaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ultimahora,null,false);

        return new UltimaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UltimaHolder holder, int position) {

        holder.tituloultima.setText(listaUltimaHora.get(position).getTitulo_row_ultimahora());
        holder.descripcionultima.setText(listaUltimaHora.get(position).getDescripcion_row_ultimahora());
        holder.fechaultimo.setText(listaUltimaHora.get(position).getFechapublicacion_row_ultimahora());
        holder.imagenultima.setImageResource(listaUltimaHora.get(position).getImagen_row_ultimahora());
        holder.imagen2ultima.setImageResource(listaUltimaHora.get(position).getImagen2_row_ultimahora());
        holder.imagen3ultima.setImageResource(listaUltimaHora.get(position).getImagen3_row_ultimahora());


    }

    @Override
    public int getItemCount() {
        return listaUltimaHora.size();
    }

    public class UltimaHolder extends RecyclerView.ViewHolder {

        TextView tituloultima,descripcionultima,fechaultimo;
        ImageView imagenultima,imagen2ultima,imagen3ultima;

        public UltimaHolder(@NonNull View itemView) {
            super(itemView);

            tituloultima = itemView.findViewById(R.id.titulo_row_ultimahora);
            descripcionultima = itemView.findViewById(R.id.descripcion_row_ultimahora);
            fechaultimo = itemView.findViewById(R.id.fecha_row_ultimahora);
            imagenultima = itemView.findViewById(R.id.imagen_row_ultimahora);
            imagen2ultima = itemView.findViewById(R.id.imagen2_row_ultimahora);
            imagen3ultima = itemView.findViewById(R.id.imagen3_row_ultimahora);

        }
    }
}
