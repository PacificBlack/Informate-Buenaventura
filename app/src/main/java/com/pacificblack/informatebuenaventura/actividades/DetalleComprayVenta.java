package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleComprayVenta extends AppCompatActivity {

    TextView titulo_comprayventa,descripcion1_comprayventa,descripcion2_comprayventa,precio_comprayventa,contacto_comprayventa,ubicacion_comprayventa,cantidad_comprayventa;
    ImageView imagen1_comprayventa,imagen2_comprayventa,imagen3_comprayventa;
    StringRequest stringRequest_comprayventa_actualizar;
    int id_actualizar;
    private AdView baner1,baner2;


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

            id_actualizar = comprayventa.getId_comprayventa();
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



            final String imagen1_link = comprayventa.getImagen1_comprayventa();
            imagen1_comprayventa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentcomprayventa = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentcomprayventa.putExtras(envioimg);
                    startActivity(intentcomprayventa);
                }
            });
            final String imagen2_link = comprayventa.getImagen2_comprayventa();
            imagen2_comprayventa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentcomprayventa = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentcomprayventa.putExtras(envioimg);
                    startActivity(intentcomprayventa);
                }
            });
            final String imagen3_link = comprayventa.getImagen3̣̣_comprayventa();
            imagen3_comprayventa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentcomprayventa = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentcomprayventa.putExtras(envioimg);
                    startActivity(intentcomprayventa);
                }
            });



            String url_comprayventa = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_comprayventa_actualizar= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i("Si actualizo","Vista Positiva");

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("No actualizo","Vista Negativa");
                        }
                    }){
                @SuppressLint("LongLogTag")
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    String idinput = String.valueOf(id_actualizar);

                    Map<String,String> parametros = new HashMap<>();
                    parametros.put("id_comprayventa",idinput);
                    parametros.put("publicacion","ComprayVenta");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_comprayventa_eliminar = Volley.newRequestQueue(this);
            request_comprayventa_eliminar.add(stringRequest_comprayventa_actualizar);

        }

        baner1 = findViewById(R.id.baner_comprayventa1);
        baner2 = findViewById(R.id.baner_comprayventa2);


        AdRequest adRequest = new AdRequest.Builder().build();

        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);



    }
}
