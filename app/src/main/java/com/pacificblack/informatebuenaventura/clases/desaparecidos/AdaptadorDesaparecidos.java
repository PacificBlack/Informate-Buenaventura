package com.pacificblack.informatebuenaventura.clases.desaparecidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorDesaparecidos extends RecyclerView.Adapter<AdaptadorDesaparecidos.DesaparecidosHolder>implements View.OnClickListener {

    ArrayList<Desaparecidos> listaDesaparecidos;
    private View.OnClickListener listener;

    public AdaptadorDesaparecidos(ArrayList<Desaparecidos> listaDesaparecidos) {
        this.listaDesaparecidos = listaDesaparecidos;
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
        holder.imagendesaparecidos.setImageResource(listaDesaparecidos.get(position).getImagen1_desaparecidos());

    }

    @Override
    public int getItemCount() {
        return listaDesaparecidos.size();
    }

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
