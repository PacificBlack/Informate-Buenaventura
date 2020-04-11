package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;

public class DetalleAdopcion extends AppCompatActivity {

    TextView titulo_adopcion,descripcion1_adopcion,descripcion2_adopcion;
    ImageView imagen1_adopcion,imagen2_adopcion,imagen3_adopcion,imagen4_adopcion;
    StringRequest stringRequest_adopcion_actualizar;
    int id_actualizar;

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


        if (objetoAdopcion != null){

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

        //TODO://////////////////////////////////////////////////////////////



            String url_adopcion = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
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
                    parametros.put("id_adopcion",idinput);
                    parametros.put("publicacion","Adopcion");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_adopcion_eliminar = Volley.newRequestQueue(this);
            request_adopcion_eliminar.add(stringRequest_adopcion_actualizar);

        }

    }

    }
