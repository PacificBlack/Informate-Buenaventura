package com.pacificblack.informatebuenaventura.extras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;
import com.squareup.picasso.Picasso;

public class FullImagen extends AppCompatActivity {

    ImageView imagengrande;
    PhotoViewAttacher mAtacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_imagen);

        imagengrande = findViewById(R.id.imagengrande);


        Bundle recibido = getIntent().getExtras();


        if (recibido != null){
            String valor = getIntent().getExtras().getString("imagen");
            Picasso.get().load(valor)
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(imagengrande);

            mAtacher = new PhotoViewAttacher(imagengrande);
        }
    }
}
