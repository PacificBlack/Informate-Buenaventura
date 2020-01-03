package com.pacificblack.informatebuenaventura.clases.adopcion;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class Adaptador_adopcion extends RecyclerView.Adapter<Adaptador_adopcion.ViewHolderAdopcion> {

//Creamos los arreglos

    ArrayList<Adopcion> Lista_adopcion;


    //Creamos constructor para el arreglo


    public Adaptador_adopcion(ArrayList<Adopcion> lista_adopcion) {
        Lista_adopcion = lista_adopcion;
    }



    @NonNull
    @Override
    public Adaptador_adopcion.ViewHolderAdopcion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//Aqui llenamos el recycler con el row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_adopcion,null,false);

        return new ViewHolderAdopcion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_adopcion.ViewHolderAdopcion holder, int position) {

        //Aqui se llena el recycler con los datos del array

        holder.img_row_adopcion.setImageResource(Lista_adopcion.get(position).getImagen1_adopcion());
        holder.titulo_row_adopcion.setText(Lista_adopcion.get(position).getTitulo_row_adopcion());
        holder.descripcioncorta_row_adopcion.setText(Lista_adopcion.get(position).getDescripcion_row_adopcion());
        holder.fechapublicacion_row_adopcion.setText(Lista_adopcion.get(position).getFechapublicacion_row_desaparecidos());
        holder.vista_row_adopcion.setText(Lista_adopcion.get(position).getVistas_adopcion());

    }

    @Override
    public int getItemCount() {
        //Aqui va el tamaño de la lista
        return Lista_adopcion.size();
    }

    public class ViewHolderAdopcion extends RecyclerView.ViewHolder {

        //Aqui adentro declaramos las variables de las row, en este caso sera row adopcion

        ImageView img_row_adopcion;
        TextView titulo_row_adopcion,descripcioncorta_row_adopcion,fechapublicacion_row_adopcion;
        TextView vista_row_adopcion;

        public ViewHolderAdopcion(@NonNull View itemView) {

            //Aqui se referencian las variables declaradas anteriormente
            super(itemView);

            img_row_adopcion= itemView.findViewById(R.id.imagen_row_adopcion);
            titulo_row_adopcion = itemView.findViewById(R.id.titulo_row_adopcion);
            descripcioncorta_row_adopcion = itemView.findViewById(R.id.descripcion_row_adopcion);
            fechapublicacion_row_adopcion = itemView.findViewById(R.id.fecha_row_adopcion);
            vista_row_adopcion = itemView.findViewById(R.id.vista_row_adopcion);

        }
    }
}
