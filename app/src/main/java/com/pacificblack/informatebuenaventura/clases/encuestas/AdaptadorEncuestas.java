package com.pacificblack.informatebuenaventura.clases.encuestas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorEncuestas extends RecyclerView.Adapter<AdaptadorEncuestas.EncuestasHolder>implements View.OnClickListener {

    ArrayList<Encuestas> listaEncuestas;
    private View.OnClickListener listener;

    public AdaptadorEncuestas(ArrayList<Encuestas> listaEncuestas) {
        this.listaEncuestas = listaEncuestas;
    }



    @Override
    public void onClick(View v) {

        if (listener!=null){

            listener.onClick(v);
        }

    }

    @NonNull
    @Override
    public AdaptadorEncuestas.EncuestasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_encuestas,null,false);
        view.setOnClickListener(this);

        return new EncuestasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEncuestas.EncuestasHolder holder, int position) {

        holder.tituloencuestas.setText(listaEncuestas.get(position).getTitulo_row_encuestas());
        holder.descripcioncortaencuestas.setText(listaEncuestas.get(position).getDescripcion_row_encuestas());
        holder.fechapublicacionencuestas.setText(listaEncuestas.get(position).getFechapublicacion_row_encuestas());
        holder.vistaencuestas.setText(String.valueOf(listaEncuestas.get(position).getVistas_encuestas()));
        holder.imagenencuestas.setImageResource(listaEncuestas.get(position).getImagen1_encuestas());

    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }


    @Override
    public int getItemCount() {
        return listaEncuestas.size();
    }

    public class EncuestasHolder extends RecyclerView.ViewHolder {

        TextView tituloencuestas,descripcioncortaencuestas,fechapublicacionencuestas,vistaencuestas;
        ImageView imagenencuestas;


        public EncuestasHolder(@NonNull View itemView) {
            super(itemView);

            tituloencuestas = itemView.findViewById(R.id.titulo_row_encuestas);
            descripcioncortaencuestas = itemView.findViewById(R.id.descripcion_row_encuestas);
            fechapublicacionencuestas = itemView.findViewById(R.id.fecha_row_encuestas);
            vistaencuestas = itemView.findViewById(R.id.vista_row_encuestas);
            imagenencuestas = itemView.findViewById(R.id.imagen_row_encuestas);
        }
    }
}
