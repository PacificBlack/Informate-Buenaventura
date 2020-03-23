package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;
import com.squareup.picasso.Picasso;

public class DetalleComprayVenta extends AppCompatActivity {

    TextView titulo_comprayventa,descripcion1_comprayventa,descripcion2_comprayventa,precio_comprayventa,contacto_comprayventa,ubicacion_comprayventa,cantidad_comprayventa;
    ImageView imagen1_comprayventa,imagen2_comprayventa,imagen3_comprayventa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_compray_venta);


        titulo_comprayventa = findViewById(R.id.titulo_detalle_comprayventa);
        descripcion1_comprayventa = findViewById(R.id.descricion1_detalle_comprayventa);
        descripcion2_comprayventa = findViewById(R.id.descripcion2_detalle_comprayventa);
        precio_comprayventa = findViewById(R.id.precio_detalle_comprayventa);
        contacto_comprayventa = findViewById(R.id.contacto_detalle_comprayventa);
        ubicacion_comprayventa =findViewById(R.id.ubicacion_detalle_comprayventa);
        cantidad_comprayventa = findViewById(R.id.cantidad_detalle_comprayventa);
        imagen1_comprayventa = findViewById(R.id.imagen1_detalle_comprayventa);
        imagen2_comprayventa = findViewById(R.id.imagen2_detalle_comprayventa);
        imagen3_comprayventa = findViewById(R.id.imagen3_detalle_comprayventa);


        Bundle objetoComprayVenta = getIntent().getExtras();

        ComprayVenta comprayventa = null;

        if (objetoComprayVenta!=null){

            comprayventa = (ComprayVenta) objetoComprayVenta.getSerializable("objeto4");


            titulo_comprayventa.setText(comprayventa.getTitulo_row_comprayventa());
            descripcion1_comprayventa.setText(comprayventa.getDescripcion_comprayventa());
            descripcion2_comprayventa.setText(comprayventa.getDescripcionextra_comprayventa());
            precio_comprayventa.setText(comprayventa.getPrecio_row_comprayventa());
            contacto_comprayventa.setText(comprayventa.getContacto_comprayventa());
            ubicacion_comprayventa.setText(comprayventa.getUbicacion_comprayventa());
            cantidad_comprayventa.setText(String.valueOf(comprayventa.getCantidad_comprayventa()));

            Picasso.get().load(comprayventa.getImagen1_comprayventa())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_comprayventa);

            Picasso.get().load(comprayventa.getImagen2_comprayventa())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_comprayventa);

            Picasso.get().load(comprayventa.getImagen3̣̣_comprayventa())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_comprayventa);

        }


    }
}
