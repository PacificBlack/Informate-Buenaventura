package com.pacificblack.informatebuenaventura.fragments.clasificados;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleClasificadosFragment extends Fragment {

    TextView titulo_clasificados,descripcion1_clasificados,descripcion2_clasificados;
    ImageView imagen1_clasificados,imagen2_clasificados,imagen3_clasificados,imagen4_clasificados;



    public DetalleClasificadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View vista = inflater.inflate(R.layout.fragment_detalle_clasificados, container, false);


        titulo_clasificados = vista.findViewById(R.id.titulo_detalle_clasificados);
        descripcion1_clasificados = vista.findViewById(R.id.descricion1_detalle_clasificados);
        descripcion2_clasificados = vista.findViewById(R.id.descripcion2_detalle_clasificados);
        imagen1_clasificados = vista.findViewById(R.id.imagen1_detalle_clasificados);
        imagen2_clasificados = vista.findViewById(R.id.imagen2_detalle_clasificados);
        imagen3_clasificados = vista.findViewById(R.id.imagen3_detalle_clasificados);
        imagen4_clasificados = vista.findViewById(R.id.imagen4_detalle_clasificados);

        Bundle objetoClasificados = getArguments();

        Clasificados clasificados = null;

        if (objetoClasificados != null){

            clasificados = (Clasificados) objetoClasificados.getSerializable("objeto3");

            titulo_clasificados.setText(clasificados.getTitulo_row_clasificados());
            descripcion1_clasificados.setText(clasificados.getDescripcion1_clasificados());
            descripcion2_clasificados.setText(clasificados.getDescripcion2_clasificados());
            imagen1_clasificados.setImageResource(clasificados.getImagen1_clasificados());
            imagen2_clasificados.setImageResource(clasificados.getImagen2_clasificados());
            imagen3_clasificados.setImageResource(clasificados.getImagen3_clasificados());
            imagen4_clasificados.setImageResource(clasificados.getImagen4_clasificados());

        }

        return vista;
    }

}
