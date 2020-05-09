package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

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
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleAdopcion extends AppCompatActivity {

    TextView titulo_adopcion, descripcion1_adopcion, descripcion2_adopcion;
    ImageView imagen1_adopcion, imagen2_adopcion, imagen3_adopcion, imagen4_adopcion;
    StringRequest stringRequest_adopcion_actualizar;
    int id_actualizar;
    private AdView baner1, baner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_detalle);

        titulo_adopcion = findViewById(R.id.titulo_detalle_adopcion);
        descripcion1_adopcion = findViewById(R.id.descricion1_detalle_adopcion);
        descripcion2_adopcion = findViewById(R.id.descripcion2_detalle_adopcion);
        imagen1_adopcion = findViewById(R.id.imagen1_detalle_adopcion);
        imagen2_adopcion = findViewById(R.id.imagen2_detalle_adopcion);
        imagen3_adopcion = findViewById(R.id.imagen3_detalle_adopcion);
        imagen4_adopcion = findViewById(R.id.imagen4_detalle_adopcion);

        Bundle objetoAdopcion = getIntent().getExtras();
        Adopcion adopcion = null;

        if (objetoAdopcion != null) {

            adopcion = (Adopcion) objetoAdopcion.getSerializable("objeto1");

            id_actualizar = adopcion.getId_adopcion();
            titulo_adopcion.setText(adopcion.getTitulo_row_adopcion());
            descripcion1_adopcion.setText(adopcion.getDescripcion1_adopcion());
            descripcion2_adopcion.setText(adopcion.getDescripcion2_adopcion());

            Picasso.get().load(adopcion.getImagen1_adopcion())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_adopcion);

            Picasso.get().load(adopcion.getImagen2_adopcion())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_adopcion);

            Picasso.get().load(adopcion.getImagen3_adopcion())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_adopcion);

            Picasso.get().load(adopcion.getImagen4_adopcion())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_adopcion);

            final String imagen1_link = adopcion.getImagen1_adopcion();
            imagen1_adopcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentadopcion = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentadopcion.putExtras(envioimg);
                    startActivity(intentadopcion);
                }
            });
            final String imagen2_link = adopcion.getImagen2_adopcion();
            imagen2_adopcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentadopcion = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentadopcion.putExtras(envioimg);
                    startActivity(intentadopcion);
                }
            });
            final String imagen3_link = adopcion.getImagen3_adopcion();
            imagen3_adopcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentadopcion = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentadopcion.putExtras(envioimg);
                    startActivity(intentadopcion);
                }
            });
            final String imagen4_link = adopcion.getImagen4_adopcion();
            imagen4_adopcion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentadopcion = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen4_link);
                    intentadopcion.putExtras(envioimg);
                    startActivity(intentadopcion);
                }
            });

            String url_adopcion = DireccionServidor + "wsnJSONActualizarVista.php?";

            stringRequest_adopcion_actualizar = new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Si actualizo", "Vista Positiva");
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("No actualizo", "Vista Negativa");
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String idinput = String.valueOf(id_actualizar);
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("id_adopcion", idinput);
                    parametros.put("publicacion", "Adopcion");
                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };
            RequestQueue request_adopcion_eliminar = Volley.newRequestQueue(this);
            request_adopcion_eliminar.add(stringRequest_adopcion_actualizar);
        }
        baner1 = findViewById(R.id.baner_adopcion1);
        baner2 = findViewById(R.id.baner_adopcion2);
        AdRequest adRequest = new AdRequest.Builder().build();
        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);
    }
}
