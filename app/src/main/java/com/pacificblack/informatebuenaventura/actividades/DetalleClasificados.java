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
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleClasificados extends AppCompatActivity {

    TextView titulo_clasificados,descripcion1_clasificados,descripcion2_clasificados;
    ImageView imagen1_clasificados,imagen2_clasificados,imagen3_clasificados,imagen4_clasificados;
    StringRequest stringRequest_clasificados_actualizar;
    int id_actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_clasificados);

        titulo_clasificados = findViewById(R.id.titulo_detalle_clasificados);
        descripcion1_clasificados = findViewById(R.id.descricion1_detalle_clasificados);
        descripcion2_clasificados = findViewById(R.id.descripcion2_detalle_clasificados);
        imagen1_clasificados = findViewById(R.id.imagen1_detalle_clasificados);
        imagen2_clasificados = findViewById(R.id.imagen2_detalle_clasificados);
        imagen3_clasificados = findViewById(R.id.imagen3_detalle_clasificados);
        imagen4_clasificados = findViewById(R.id.imagen4_detalle_clasificados);

        Bundle objetoClasificados = getIntent().getExtras();

        Clasificados clasificados = null;

        if (objetoClasificados != null){

            clasificados = (Clasificados) objetoClasificados.getSerializable("objeto3");

            id_actualizar= clasificados.getId_clasificados();
            titulo_clasificados.setText(clasificados.getTitulo_row_clasificados());
            descripcion1_clasificados.setText(clasificados.getDescripcion1_clasificados());
            descripcion2_clasificados.setText(clasificados.getDescripcion2_clasificados());


            Picasso.get().load(clasificados.getImagen1_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_clasificados);


            Picasso.get().load(clasificados.getImagen2_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_clasificados);

            Picasso.get().load(clasificados.getImagen3_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_clasificados);

            Picasso.get().load(clasificados.getImagen4_clasificados())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_clasificados);


            final String imagen1_link = clasificados.getImagen1_clasificados();
            imagen1_clasificados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentclasificados = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentclasificados.putExtras(envioimg);
                    startActivity(intentclasificados);
                }
            });
            final String imagen2_link = clasificados.getImagen2_clasificados();
            imagen2_clasificados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentclasificados = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentclasificados.putExtras(envioimg);
                    startActivity(intentclasificados);
                }
            });
            final String imagen3_link = clasificados.getImagen3_clasificados();
            imagen3_clasificados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentclasificados = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentclasificados.putExtras(envioimg);
                    startActivity(intentclasificados);
                }
            });
            final String imagen4_link = clasificados.getImagen4_clasificados();
            imagen4_clasificados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentclasificados = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen4_link);
                    intentclasificados.putExtras(envioimg);
                    startActivity(intentclasificados);
                }
            });


            String url_clasificados = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_clasificados_actualizar= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
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
                    parametros.put("id_clasificados",idinput);
                    parametros.put("publicacion","Clasificados");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_clasificados_eliminar = Volley.newRequestQueue(this);
            request_clasificados_eliminar.add(stringRequest_clasificados_actualizar);

        }
    }
}
