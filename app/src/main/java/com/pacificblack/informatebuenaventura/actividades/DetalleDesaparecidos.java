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
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleDesaparecidos extends AppCompatActivity {

    TextView titulo_desaparecidos,descripcion1_desaparecidos,ultimolugar_desaparecidos,fechadesaparicion_desaparecidos,recompensa_desaparecidos,estado_desaparecidos,descripcion2_desaparecidos;
    ImageView imagen1_desaparecidos,imagen2_desaparecidos,imagen3_desaparecidos;
    StringRequest stringRequest_desaparecidos_actualizar;
    int id_actualizar;

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

            id_actualizar = desaparecidos.getId_desaparecidos();
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


            final String imagen1_link = desaparecidos.getImagen1_desaparecidos();
            imagen1_desaparecidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentdesaparecidos = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentdesaparecidos.putExtras(envioimg);
                    startActivity(intentdesaparecidos);
                }
            });
            final String imagen2_link = desaparecidos.getImagen2_desaparecidos();
            imagen2_desaparecidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentdesaparecidos = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentdesaparecidos.putExtras(envioimg);
                    startActivity(intentdesaparecidos);
                }
            });
            final String imagen3_link = desaparecidos.getImagen3̣̣_desaparecidos();
            imagen3_desaparecidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentdesaparecidos = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentdesaparecidos.putExtras(envioimg);
                    startActivity(intentdesaparecidos);
                }
            });


            String url_desaparecidos = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_desaparecidos_actualizar= new StringRequest(Request.Method.POST, url_desaparecidos, new Response.Listener<String>() {
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
                    parametros.put("id_desaparicion",idinput);
                    parametros.put("publicacion","Desaparicion");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_desaparecidos_eliminar = Volley.newRequestQueue(this);
            request_desaparecidos_eliminar.add(stringRequest_desaparecidos_actualizar);

        }




    }
}
