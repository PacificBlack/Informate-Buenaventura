package com.pacificblack.informatebuenaventura.clases.noticias;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;

public class AdaptadorNoticias extends RecyclerView.Adapter<AdaptadorNoticias.NoticiasHolder>implements View.OnClickListener {

    ArrayList<Noticias> listaNoticias;
    private View.OnClickListener listener;

    public AdaptadorNoticias(ArrayList<Noticias> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @Override
    public void onClick(View v) {

        if (listener!=null){

            listener.onClick(v);
        }

    }


    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;
    }

    @NonNull
    @Override
    public AdaptadorNoticias.NoticiasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_noticias,null,false);
        view.setOnClickListener(this);

        return new NoticiasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNoticias.NoticiasHolder holder, int position) {

        holder.titulonoticias.setText(listaNoticias.get(position).getTitulo_row_noticias());
        holder.descripcionnoticias.setText(listaNoticias.get(position).getDescripcion_row_noticias());
        holder.fechanoticias.setText(listaNoticias.get(position).getFechapublicacion_row_noticias());
        holder.vistanoticias.setText(String.valueOf(listaNoticias.get(position).getVistas_noticias()));
        holder.imagennoticias.setImageResource(listaNoticias.get(position).getImagen1_noticias());
        holder.likenoticias.setText(String.valueOf(listaNoticias.get(position).getLikes_noticias()));
        holder.dislikenoticias.setText(String.valueOf(listaNoticias.get(position).getDislikes_noticias()));
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public class NoticiasHolder extends RecyclerView.ViewHolder {

        TextView titulonoticias,descripcionnoticias,fechanoticias,vistanoticias;
        ImageView imagennoticias;
        RadioButton likenoticias, dislikenoticias;


        public NoticiasHolder(@NonNull View itemView) {
            super(itemView);

            titulonoticias = itemView.findViewById(R.id.titulo_row_noticias);
            descripcionnoticias = itemView.findViewById(R.id.descripcion_row_noticias);
            fechanoticias = itemView.findViewById(R.id.fecha_row_noticias);
            vistanoticias = itemView.findViewById(R.id.vista_row_noticias);
            imagennoticias = itemView.findViewById(R.id.imagen_row_noticias);
            likenoticias = itemView.findViewById(R.id.gusta_row_noticias);
            dislikenoticias = itemView.findViewById(R.id.nogusta_row_noticias);
        }
    }
}
