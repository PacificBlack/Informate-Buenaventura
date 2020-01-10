package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;

import java.net.URL;

public class DetalleNoticias extends AppCompatActivity {

    TextView titulo_noticias,descripcion1_noticias,
            descripcion2_noticias,descripcion3_noticias,yutu;

    ImageView imagen1_noticias,imagen2_noticias,
            imagen3_noticias,imagen4_noticias;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticias);

        titulo_noticias = findViewById(R.id.titulo_detalle_noticias);
        descripcion1_noticias = findViewById(R.id.descricion1_detalle_noticias);
        descripcion2_noticias = findViewById(R.id.descripcion2_detalle_noticias);
        descripcion3_noticias = findViewById(R.id.descripcion3_detalle_noticias);
        imagen1_noticias = findViewById(R.id.imagen1_detalle_noticias);
        imagen2_noticias = findViewById(R.id.imagen2_detalle_noticias);
        imagen3_noticias = findViewById(R.id.imagen3_detalle_noticias);
        imagen4_noticias = findViewById(R.id.imagen4_detalle_noticias);
        yutu = findViewById(R.id.link_detalle_noticias);
        yutu.setMovementMethod(LinkMovementMethod.getInstance());
        descripcion3_noticias.setMovementMethod(LinkMovementMethod.getInstance());


        Bundle objetoNoticias = getIntent().getExtras();

        Noticias noticias = null;


        if (objetoNoticias != null){

            noticias = (Noticias) objetoNoticias.getSerializable("objeto10");

            titulo_noticias.setText(noticias.getTitulo_row_noticias());
            descripcion1_noticias.setText(noticias.getDescripcion1_noticias());
            descripcion2_noticias.setText(noticias.getDescripcion2_noticias());
            descripcion3_noticias.setText(noticias.getDescripcion3_noticias());
            imagen1_noticias.setImageResource(noticias.getImagen1_noticias());
            imagen2_noticias.setImageResource(noticias.getImagen2_noticias());
            imagen3_noticias.setImageResource(noticias.getImagen3_noticias());
            imagen4_noticias.setImageResource(noticias.getImagen4_noticias());
            yutu.setText(noticias.getVideo());

        }




    }
}
