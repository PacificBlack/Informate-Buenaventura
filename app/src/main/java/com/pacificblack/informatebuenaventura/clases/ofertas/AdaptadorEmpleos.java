package com.pacificblack.informatebuenaventura.clases.ofertas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorEmpleos extends RecyclerView.Adapter<AdaptadorEmpleos.EmpleosHolder> {

    ArrayList<OfertaEmpleos> listaEmpleos;

    public AdaptadorEmpleos(ArrayList<OfertaEmpleos> listaEmpleos) {
        this.listaEmpleos = listaEmpleos;
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
        holder.imagenempleos.setImageResource(listaEmpleos.get(position).getImagen1_ofertasempleos());

    }



    @Override
    public int getItemCount() {
        return listaEmpleos.size();
    }

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
