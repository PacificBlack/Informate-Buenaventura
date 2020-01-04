package com.pacificblack.informatebuenaventura.fragments.bienesraizes;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleBienesFragment extends Fragment {

    TextView titulo_bienes,descripcion1_bienes,descripcion2_bienes,precio_bienes;
    ImageView imagen1_bienes,imagen2_bienes,imagen3_bienes,imagen4_bienes;


    public DetalleBienesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_detalle_bienes, container, false);

        titulo_bienes = vista.findViewById(R.id.titulo_detalle_bienes);
        descripcion1_bienes = vista.findViewById(R.id.descricion1_detalle_bienes);
        descripcion2_bienes = vista.findViewById(R.id.descripcion2_detalle_bienes);
        precio_bienes = vista.findViewById(R.id.precio_detalle_bienes);
        imagen1_bienes = vista.findViewById(R.id.imagen1_detalle_bienes);
        imagen2_bienes = vista.findViewById(R.id.imagen2_detalle_bienes);
        imagen3_bienes = vista.findViewById(R.id.imagen3_detalle_bienes);
        imagen4_bienes = vista.findViewById(R.id.imagen4_detalle_bienes);

        Bundle objetoBienes = getArguments();

        Bienes bienes = null;

        if (objetoBienes != null){

            bienes = (Bienes) objetoBienes.getSerializable("objeto2");

            titulo_bienes.setText(bienes.getTitulo_row_bienes());
            descripcion1_bienes.setText(bienes.getDescripcion1_bienes());
            descripcion2_bienes.setText(bienes.getDescripcion2_bienes());
            precio_bienes.setText(String.valueOf(bienes.getPrecio_row_bienes()));
            imagen1_bienes.setImageResource(bienes.getImagen1_bienes());
            imagen2_bienes.setImageResource(bienes.getImagen2_bienes());
            imagen3_bienes.setImageResource(bienes.getImagen3_bienes());
            imagen4_bienes.setImageResource(bienes.getImagen4_bienes());


        }


        return vista;
    }

}
