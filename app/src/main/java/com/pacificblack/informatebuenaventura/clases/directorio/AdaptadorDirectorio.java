package com.pacificblack.informatebuenaventura.clases.directorio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorDirectorio extends RecyclerView.Adapter<AdaptadorDirectorio.DirectorioHolder> {

    ArrayList<Directorio> listaDirectorio;

    public AdaptadorDirectorio(ArrayList<Directorio> listaDirectorio) {
        this.listaDirectorio = listaDirectorio;
    }

    @NonNull
    @Override
    public DirectorioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_directorios,null,false);

        return new DirectorioHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectorioHolder holder, int position) {

        holder.titulodirectorio.setText(listaDirectorio.get(position).getTitulo_row_directorio());
        holder.descripcioncortadirectorio.setText(listaDirectorio.get(position).getDescripcion_row_directorio());
        holder.contactosdirectorio.setText(listaDirectorio.get(position).getContactos_row_directorio());
        holder.fechapublicaciondirectorio.setText(listaDirectorio.get(position).getFechapublicacion_row_directorio());
        holder.vistadirectorio.setText(String.valueOf(listaDirectorio.get(position).getVistas_row_directorio()));
        holder.imagendirectorio.setImageResource(listaDirectorio.get(position).getImagen1_directorio());
    }

    @Override
    public int getItemCount() {
        return listaDirectorio.size();
    }

    public class DirectorioHolder extends RecyclerView.ViewHolder {

        TextView titulodirectorio,descripcioncortadirectorio,contactosdirectorio,fechapublicaciondirectorio,vistadirectorio;
        ImageView imagendirectorio;


        public DirectorioHolder(@NonNull View itemView) {
            super(itemView);
            titulodirectorio = itemView.findViewById(R.id.titulo_row_directorios);
            descripcioncortadirectorio = itemView.findViewById(R.id.descripcion_row_directorios);
            contactosdirectorio = itemView.findViewById(R.id.contactos_row_directorios);
            fechapublicaciondirectorio = itemView.findViewById(R.id.fecha_row_directorios);
            vistadirectorio = itemView.findViewById(R.id.vista_row_directorio);
            imagendirectorio = itemView.findViewById(R.id.imagen_row_directorios);
        }
    }
}
