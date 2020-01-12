package com.pacificblack.informatebuenaventura.publicar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.R;

public class PublicarArticulo extends AppCompatActivity {

    ImageView imagen1_publicar_comprayventa,
            imagen2_publicar_comprayventa,
            imagen3_publicar_comprayventa;

    TextInputLayout titulo_publicar_comprayventa,
            descripcioncorta_publicar_comprayventa,
            descripcion_publicar_comprayventa,
            descripcionextra_publicar_comprayventa,
            precio_publicar_comprayventa,
            ubicacion_publicar_comprayventa,
            cantidad_publicar_comprayventa,
            contacto_publicar_comprayventa;

    Button publicarfinal_comprayventa;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int IMAGE_PICK_CODE2 = 1002;
    private static final int IMAGE_PICK_CODE3 = 1003;
    private static final int IMAGE_PICK_CODE4 = 1004;

    private static final int PERMISSON_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_articulo);

        titulo_publicar_comprayventa = findViewById(R.id.publicar_titulo_comprayventa);
        descripcioncorta_publicar_comprayventa = findViewById(R.id.publicar_descripcioncorta_comprayventa);
        descripcion_publicar_comprayventa = findViewById(R.id.publicar_descripcion_comprayventa);
        descripcionextra_publicar_comprayventa = findViewById(R.id.publicar_descripcionextra_comprayventa);
        precio_publicar_comprayventa = findViewById(R.id.publicar_precio_comprayventa);
        ubicacion_publicar_comprayventa = findViewById(R.id.publicar_ubicacion_comprayventa);
        cantidad_publicar_comprayventa = findViewById(R.id.publicar_cantidad_comprayventa);
        contacto_publicar_comprayventa = findViewById(R.id.publicar_contacto_comprayventa);
        publicarfinal_comprayventa = findViewById(R.id.publicar_final_comprayventa);

        imagen1_publicar_comprayventa = findViewById(R.id.publicar_imagen1_comprayventa);
        imagen2_publicar_comprayventa = findViewById(R.id.publicar_imagen2_comprayventa);
        imagen3_publicar_comprayventa = findViewById(R.id.publicar_imagen3_comprayventa);


    }
}
