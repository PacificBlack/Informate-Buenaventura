package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;

public class DetalleAdopcion extends AppCompatActivity {

    TextView titulo_adopcion,descripcion1_adopcion,descripcion2_adopcion;
    ImageView imagen1_adopcion,imagen2_adopcion,imagen3_adopcion,imagen4_adopcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_detalle);


        titulo_adopcion = findViewById(R.id.titulo_detalle_adopcion);
        descripcion1_adopcion = findViewById(R.id.descricion1_detalle_adopcion);
        descripcion2_adopcion = findViewById(R.id.descripcion2_detalle_adopcion);
        imagen1_adopcion = findViewById(R.id.imagen1_detalle_adopcion);
        imagen2_adopcion = findViewById(R.id.imagen2_detalle_adopcion);
        imagen3_adopcion = findViewById(R.id.imagen3_detalle_adopcion);
        imagen4_adopcion = findViewById(R.id.imagen4_detalle_adopcion);


        Bundle objetoAdopcion = getIntent().getExtras();

        Adopcion adopcion = null;


        if (objetoAdopcion != null){

            adopcion = (Adopcion) objetoAdopcion.getSerializable("objeto1");

            titulo_adopcion.setText(adopcion.getTitulo_row_adopcion());
            descripcion1_adopcion.setText(adopcion.getDescripcion1_adopcion());
            descripcion2_adopcion.setText(adopcion.getDescripcion2_adopcion());
            imagen1_adopcion.setImageResource(adopcion.getImagen1_adopcion());
            imagen2_adopcion.setImageResource(adopcion.getImagen2_adopcion());
            imagen3_adopcion.setImageResource(adopcion.getImagen3_adopcion());
            imagen4_adopcion.setImageResource(adopcion.getImagen4_adopcion());

        }



    }



}
