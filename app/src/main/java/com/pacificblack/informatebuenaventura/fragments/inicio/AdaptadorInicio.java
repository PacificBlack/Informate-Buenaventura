package com.pacificblack.informatebuenaventura.fragments.inicio;

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

public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.InicioHolder> {

    ArrayList<Inicio> listaInicio;

    public AdaptadorInicio(ArrayList<Inicio> listaInicio) {
        this.listaInicio = listaInicio;
    }

    @NonNull
    @Override
    public AdaptadorInicio.InicioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid,null,false);

        return new InicioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorInicio.InicioHolder holder, int position) {

        holder.texto_inicio.setText(listaInicio.get(position).getDescripcion_corta());

        if (listaInicio.get(position).getImagen_inicio() != null){

            Picasso.get().load(listaInicio.get(position).getImagen_inicio())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(holder.imagen_inicio);


        }else{
            holder.imagen_inicio.setImageResource(R.drawable.imagennodisponible);

        }
    }

    @Override
    public int getItemCount() {
        return listaInicio.size();
    }

    public class InicioHolder extends RecyclerView.ViewHolder {
        TextView texto_inicio;
        ImageView imagen_inicio;

        public InicioHolder(@NonNull View itemView) {
            super(itemView);
            texto_inicio = itemView.findViewById(R.id.texto_inicio);
            imagen_inicio = itemView.findViewById(R.id.imagen_inicio);
        }
    }
}
