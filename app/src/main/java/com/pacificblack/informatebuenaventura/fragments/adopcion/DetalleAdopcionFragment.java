package com.pacificblack.informatebuenaventura.fragments.adopcion;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleAdopcionFragment extends Fragment {

TextView titulo_adopcion,descripcion1_adopcion,descripcion2_adopcion;
ImageView imagen1_adopcion,imagen2_adopcion,imagen3_adopcion,imagen4_adopcion;

    public DetalleAdopcionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_detalle_adopcion, container, false);

        titulo_adopcion = vista.findViewById(R.id.titulo_detalle_adopcion);
        descripcion1_adopcion = vista.findViewById(R.id.descricion1_detalle_adopcion);
        descripcion2_adopcion = vista.findViewById(R.id.descripcion2_detalle_adopcion);
        imagen1_adopcion = vista.findViewById(R.id.imagen1_detalle_adopcion);
        imagen2_adopcion = vista.findViewById(R.id.imagen2_detalle_adopcion);
        imagen3_adopcion = vista.findViewById(R.id.imagen3_detalle_adopcion);
        imagen4_adopcion = vista.findViewById(R.id.imagen4_detalle_adopcion);


        Bundle objetoAdopcion = getArguments();

        Adopcion adopcion = null;


        if (objetoAdopcion != null){

            adopcion = (Adopcion) objetoAdopcion.getSerializable("objeto");

            titulo_adopcion.setText(adopcion.getTitulo_row_adopcion());
            descripcion1_adopcion.setText(adopcion.getDescripcion1_adopcion());
            descripcion2_adopcion.setText(adopcion.getDescripcion2_adopcion());
            imagen1_adopcion.setImageResource(adopcion.getImagen1_adopcion());
            imagen2_adopcion.setImageResource(adopcion.getImagen2_adopcion());
            imagen3_adopcion.setImageResource(adopcion.getImagen3_adopcion());
            imagen4_adopcion.setImageResource(adopcion.getImagen4_adopcion());

        }

        return vista;
    }

}
