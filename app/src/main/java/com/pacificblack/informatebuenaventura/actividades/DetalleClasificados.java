package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
import com.squareup.picasso.Picasso;

public class DetalleClasificados extends AppCompatActivity {

    TextView titulo_clasificados,descripcion1_clasificados,descripcion2_clasificados;
    ImageView imagen1_clasificados,imagen2_clasificados,imagen3_clasificados,imagen4_clasificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_clasificados);

        titulo_clasificados = findViewById(R.id.titulo_detalle_clasificados);
        descripcion1_clasificados = findViewById(R.id.descricion1_detalle_clasificados);
        descripcion2_clasificados = findViewById(R.id.descripcion2_detalle_clasificados);
        imagen1_clasificados = findViewById(R.id.imagen1_detalle_clasificados);
        imagen2_clasificados = findViewById(R.id.imagen2_detalle_clasificados);
        imagen3_clasificados = findViewById(R.id.imagen3_detalle_clasificados);
        imagen4_clasificados = findViewById(R.id.imagen4_detalle_clasificados);

        Bundle objetoClasificados = getIntent().getExtras();

        Clasificados clasificados = null;

        if (objetoClasificados != null){

            clasificados = (Clasificados) objetoClasificados.getSerializable("objeto3");

            titulo_clasificados.setText(clasificados.getTitulo_row_clasificados());
            descripcion1_clasificados.setText(clasificados.getDescripcion1_clasificados());
            descripcion2_clasificados.setText(clasificados.getDescripcion2_clasificados());


            Picasso.get().load(clasificados.getImagen1_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_clasificados);


            Picasso.get().load(clasificados.getImagen2_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_clasificados);

            Picasso.get().load(clasificados.getImagen3_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_clasificados);

            Picasso.get().load(clasificados.getImagen4_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_clasificados);

        }
    }
}
