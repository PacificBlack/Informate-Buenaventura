package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;
import com.squareup.picasso.Picasso;

public class DetalleBienes extends AppCompatActivity {

    TextView titulo_bienes,descripcion1_bienes,descripcion2_bienes,precio_bienes;
    ImageView imagen1_bienes,imagen2_bienes,imagen3_bienes,imagen4_bienes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_bienes);


        titulo_bienes = findViewById(R.id.titulo_detalle_bienes);
        descripcion1_bienes = findViewById(R.id.descricion1_detalle_bienes);
        descripcion2_bienes = findViewById(R.id.descripcion2_detalle_bienes);
        precio_bienes = findViewById(R.id.precio_detalle_bienes);
        imagen1_bienes = findViewById(R.id.imagen1_detalle_bienes);
        imagen2_bienes = findViewById(R.id.imagen2_detalle_bienes);
        imagen3_bienes = findViewById(R.id.imagen3_detalle_bienes);
        imagen4_bienes = findViewById(R.id.imagen4_detalle_bienes);

        Bundle objetoBienes = getIntent().getExtras();

        Bienes bienes = null;

        if (objetoBienes != null){

            bienes = (Bienes) objetoBienes.getSerializable("objeto2");

            titulo_bienes.setText(bienes.getTitulo_row_bienes());
            descripcion1_bienes.setText(bienes.getDescripcion1_bienes());
            descripcion2_bienes.setText(bienes.getDescripcion2_bienes());
            precio_bienes.setText(String.valueOf(bienes.getPrecio_row_bienes()));
            Picasso.get().load(bienes.getImagen1_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_bienes);


            Picasso.get().load(bienes.getImagen1_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_bienes);

            Picasso.get().load(bienes.getImagen1_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_bienes);

            Picasso.get().load(bienes.getImagen1_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_bienes);

        }



        }


    }

