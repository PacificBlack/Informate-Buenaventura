package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;

public class DetalleDonaciones extends AppCompatActivity {

    TextView titulodonaciones,descripcion1donaciones,metadonaciones;
    ImageView imagen1donaciones,imagen2donaciones;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_donaciones);

        titulodonaciones = findViewById(R.id.titulo_detalle_donaciones);
        descripcion1donaciones = findViewById(R.id.descricion1_detalle_donaciones);
        imagen1donaciones = findViewById(R.id.imagen1_detalle_donaciones);
        imagen2donaciones = findViewById(R.id.imagen2_detalle_donaciones);
        metadonaciones = findViewById(R.id.meta_detalle_donaciones);


        Bundle objetoDonaciones = getIntent().getExtras();
        Donaciones dona = null;

        if (objetoDonaciones != null){

            dona = (Donaciones) objetoDonaciones.getSerializable("objeto6");

            titulodonaciones.setText(dona.getTitulo_row_donaciones());
            descripcion1donaciones.setText(dona.getDescripcion1_donaciones());
            imagen1donaciones.setImageResource(dona.getImagen1_donaciones());
            imagen2donaciones.setImageResource(dona.getImagen2_donaciones());
            metadonaciones.setText(String.valueOf(dona.getMeta_row_donaciones()));
        }



    }
}
