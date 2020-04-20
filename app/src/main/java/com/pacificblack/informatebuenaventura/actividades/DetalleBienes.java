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
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleBienes extends AppCompatActivity {

    TextView titulo_bienes,descripcion1_bienes,descripcion2_bienes,precio_bienes;
    ImageView imagen1_bienes,imagen2_bienes,imagen3_bienes,imagen4_bienes;

    StringRequest stringRequest_bienes_actualizar;
    int id_actualizar;
    private AdView baner1,baner2;


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

            id_actualizar = bienes.getId_bienes();
            titulo_bienes.setText(bienes.getTitulo_row_bienes());
            descripcion1_bienes.setText(bienes.getDescripcion1_bienes());
            descripcion2_bienes.setText(bienes.getDescripcion2_bienes());
            precio_bienes.setText(String.valueOf(bienes.getPrecio_row_bienes()));

            Picasso.get().load(bienes.getImagen1_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_bienes);


            Picasso.get().load(bienes.getImagen2_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_bienes);

            Picasso.get().load(bienes.getImagen3_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_bienes);

            Picasso.get().load(bienes.getImagen4_bienes())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_bienes);

            final String imagen1_link = bienes.getImagen1_bienes();
            imagen1_bienes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentbienes = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentbienes.putExtras(envioimg);
                    startActivity(intentbienes);
                }
            });
            final String imagen2_link = bienes.getImagen2_bienes();
            imagen2_bienes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentbienes = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentbienes.putExtras(envioimg);
                    startActivity(intentbienes);
                }
            });
            final String imagen3_link = bienes.getImagen3_bienes();
            imagen3_bienes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentbienes = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentbienes.putExtras(envioimg);
                    startActivity(intentbienes);
                }
            });
            final String imagen4_link = bienes.getImagen4_bienes();
            imagen4_bienes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentbienes = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen4_link);
                    intentbienes.putExtras(envioimg);
                    startActivity(intentbienes);
                }
            });






            String url_bienes = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_bienes_actualizar= new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i("Si actualizo",response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("No actualizo",error.toString());
                        }
                    }){
                @SuppressLint("LongLogTag")
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    String idinput = String.valueOf(id_actualizar);

                    Map<String,String> parametros = new HashMap<>();
                    parametros.put("id_bienes",idinput);
                    parametros.put("publicacion","Bienes");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_bienes_eliminar = Volley.newRequestQueue(this);
            request_bienes_eliminar.add(stringRequest_bienes_actualizar);

        }


        baner1 = findViewById(R.id.baner_bienes1);
        baner2 = findViewById(R.id.baner_bienes2);


        AdRequest adRequest = new AdRequest.Builder().build();

        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);




    }


    }

