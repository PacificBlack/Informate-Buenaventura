package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;

public class DetalleDifuntos extends AppCompatActivity {

    TextView titulo_funebres,descripcion1_funebres,descripcion2_funebres;
    ImageView imagen1_funebres,imagen2_funebres,imagen3_funebres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_difuntos);



        titulo_funebres = findViewById(R.id.titulo_detalle_funebres);
        descripcion1_funebres = findViewById(R.id.descricion1_detalle_funebres);
        descripcion2_funebres = findViewById(R.id.descripcion2_detalle_funebres);
        imagen1_funebres = findViewById(R.id.imagen1_detalle_funebres);
        imagen2_funebres = findViewById(R.id.imagen3_detalle_funebres);
        imagen3_funebres = findViewById(R.id.imagen4_detalle_funebres);


        Bundle objetoFunebres = getIntent().getExtras();

        Funebres funebres = null;

        if (objetoFunebres != null){

            funebres = (Funebres) objetoFunebres.getSerializable("objeto9");

            titulo_funebres.setText(funebres.getTitulo_row_funebres());
            descripcion1_funebres.setText(funebres.getDescripcion1_funebres());
            descripcion2_funebres.setText(funebres.getDescripcion2_funebres());
            imagen1_funebres.setImageResource(funebres.getImagen1_funebres());
            imagen2_funebres.setImageResource(funebres.getImagen2_funebres());
            imagen3_funebres.setImageResource(funebres.getImagen3_funebres());


        }
    }
}
