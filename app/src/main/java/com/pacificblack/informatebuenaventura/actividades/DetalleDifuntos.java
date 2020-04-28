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
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleDifuntos extends AppCompatActivity {

    TextView titulo_funebres,descripcion1_funebres,descripcion2_funebres;
    ImageView imagen1_funebres,imagen2_funebres,imagen3_funebres;
    StringRequest stringRequest_funebres_actualizar;
    int id_actualizar;
    private AdView baner1,baner2;


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

            id_actualizar = funebres.getId_funebres();
            titulo_funebres.setText(funebres.getTitulo_row_funebres());
            descripcion1_funebres.setText(funebres.getDescripcion1_funebres());
            descripcion2_funebres.setText(funebres.getDescripcion2_funebres());

            Picasso.get().load(funebres.getImagen1_funebres())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_funebres);

            Picasso.get().load(funebres.getImagen2_funebres())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_funebres);


            Picasso.get().load(funebres.getImagen3_funebres())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_funebres);


            final String imagen1_link = funebres.getImagen1_funebres();
            imagen1_funebres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentfunebres = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentfunebres.putExtras(envioimg);
                    startActivity(intentfunebres);
                }
            });
            final String imagen2_link = funebres.getImagen2_funebres();
            imagen2_funebres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentfunebres = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentfunebres.putExtras(envioimg);
                    startActivity(intentfunebres);
                }
            });
            final String imagen3_link = funebres.getImagen3_funebres();
            imagen3_funebres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentfunebres = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentfunebres.putExtras(envioimg);
                    startActivity(intentfunebres);
                }
            });


            String url_funebres = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_funebres_actualizar= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
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

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    String idinput = String.valueOf(id_actualizar);

                    Map<String,String> parametros = new HashMap<>();
                    parametros.put("id_funebres",idinput);
                    parametros.put("publicacion","Funebres");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_funebres_eliminar = Volley.newRequestQueue(this);
            request_funebres_eliminar.add(stringRequest_funebres_actualizar);

        }

        baner1 = findViewById(R.id.baner_difuntos1);
        baner2 = findViewById(R.id.baner_difuntos2);

        AdRequest adRequest = new AdRequest.Builder().build();

        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);
    }
}
