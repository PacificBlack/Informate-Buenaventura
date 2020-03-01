package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
import com.squareup.picasso.Picasso;

public class DetalleDesaparecidos extends AppCompatActivity {

    TextView titulo_desaparecidos,descripcion1_desaparecidos,ultimolugar_desaparecidos,fechadesaparicion_desaparecidos,recompensa_desaparecidos,estado_desaparecidos,descripcion2_desaparecidos;
    ImageView imagen1_desaparecidos,imagen2_desaparecidos,imagen3_desaparecidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_desaparecidos);

        titulo_desaparecidos = findViewById(R.id.titulo_detalle_desaparecidos);
        descripcion1_desaparecidos = findViewById(R.id.descricion1_detalle_desaparecidos);
        descripcion2_desaparecidos = findViewById(R.id.descripcion2_detalle_desaparecidos);
        ultimolugar_desaparecidos = findViewById(R.id.ultimolugar_detalle_desaparecidos);
        fechadesaparicion_desaparecidos = findViewById(R.id.fechadesaparicion_detalle_desaparecidos);
        recompensa_desaparecidos = findViewById(R.id.recompensa_detalle_desaparecidos);
        estado_desaparecidos = findViewById(R.id.estado_detalle_desaparecidos);
        imagen1_desaparecidos = findViewById(R.id.imagen1_detalle_desaparecidos);
        imagen2_desaparecidos = findViewById(R.id.imagen2_detalle_desaparecidos);
        imagen3_desaparecidos = findViewById(R.id.imagen3_detalle_desaparecidos);



        Bundle objetoDesaparecidos = getIntent().getExtras();

        Desaparecidos desaparecidos = null;

        if (objetoDesaparecidos !=null){

            desaparecidos = (Desaparecidos) objetoDesaparecidos.getSerializable("objeto5");

            titulo_desaparecidos.setText(desaparecidos.getTitulo_row_desaparecidos());
            descripcion1_desaparecidos.setText(desaparecidos.getDescripcion1_desaparecidos());
            descripcion2_desaparecidos.setText(desaparecidos.getDescripcion2_desaparecidos());
            ultimolugar_desaparecidos.setText(desaparecidos.getUltimolugar_desaparecidos());
            fechadesaparicion_desaparecidos.setText(desaparecidos.getFechadesaparecido_desaparecidos());
            recompensa_desaparecidos.setText(desaparecidos.getRecompensa_row_desaparecidos());
            estado_desaparecidos.setText(desaparecidos.getEstado_desaparecidos());

            Picasso.get().load(desaparecidos.getImagen1_desaparecidos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_desaparecidos);


            Picasso.get().load(desaparecidos.getImagen2_desaparecidos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_desaparecidos);

            Picasso.get().load(desaparecidos.getImagen3̣̣_desaparecidos())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_desaparecidos);


        }




    }
}
