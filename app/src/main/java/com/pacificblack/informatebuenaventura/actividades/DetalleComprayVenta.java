package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;

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

        ComprayVenta compra = null;

        if (compra!=null){

            compra = (ComprayVenta) objetoComprayVenta.getSerializable("objeto4");


            titulo_comprayventa.setText(compra.getTitulo_row_comprayventa());
            descripcion1_comprayventa.setText(compra.getDescripcion_comprayventa());
            descripcion2_comprayventa.setText(compra.getDescripcionextra_comprayventa());
            precio_comprayventa.setText(compra.getPrecio_row_comprayventa());
            contacto_comprayventa.setText(compra.getContacto_comprayventa());
            ubicacion_comprayventa.setText(compra.getUbicacion_comprayventa());
            cantidad_comprayventa.setText(compra.getCantidad_comprayventa());
            imagen1_comprayventa.setImageResource(compra.getImagen1_comprayventa());
            imagen2_comprayventa.setImageResource(compra.getImagen2_comprayventa());
            imagen3_comprayventa.setImageResource(compra.getImagen3̣̣_comprayventa());
        }


    }
}
