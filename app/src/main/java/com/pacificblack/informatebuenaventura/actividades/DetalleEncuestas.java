package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.encuestas.Encuestas;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleEncuestas extends AppCompatActivity {

    TextView tituloencuestas, descripcion;
    ImageView imagenencuesta;
    RadioButton opcion1encuesta,opcion2encuesta,opcion3encuesta,opcion4encuesta;
    Button votarencuesta;
    StringRequest stringRequest_encuestas_actualizar;
    int id_actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_encuestas);


        tituloencuestas = findViewById(R.id.titulo_detalle_encuesta);
        descripcion = findViewById(R.id.descricion1_detalle_encuesta);
        imagenencuesta = findViewById(R.id.imagen1_detalle_encuesta);
        opcion1encuesta = findViewById(R.id.opcion1);
        opcion2encuesta = findViewById(R.id.opcion2);
        opcion3encuesta = findViewById(R.id.opcion3);
        opcion4encuesta = findViewById(R.id.opcion4);
        votarencuesta   = findViewById(R.id.votar_encuesta);



        Bundle objetoEncuesta = getIntent().getExtras();

        Encuestas encuesta = null;

        if (objetoEncuesta!=null){

            encuesta = (Encuestas) objetoEncuesta.getSerializable("objeto7");

           // String opcion1;

          //  opcion1 = encuesta.getOpcion1();

            id_actualizar = encuesta.getId_encuestas();
            tituloencuestas.setText(encuesta.getTitulo_row_encuestas());
            descripcion.setText(encuesta.getDescripcion1_encuestas());

            Picasso.get().load(encuesta.getImagen1_encuestas())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagenencuesta);

            opcion1encuesta.setText(encuesta.getOpcion1()+" \t"+encuesta.getVoto1_encuestas()+" votos");
            opcion2encuesta.setText(encuesta.getOpcion2()+" \t"+encuesta.getVoto2_encuestas()+" votos");
            opcion3encuesta.setText(encuesta.getOpcion3()+" \t"+encuesta.getVoto3_encuestas()+" votos");
            opcion4encuesta.setText(encuesta.getOpcion4()+" \t"+encuesta.getVoto4_encuestas()+" votos");

            //TODO://////////////////////////////////////////////////////////////



            String url_encuestas = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_encuestas_actualizar= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
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
                    parametros.put("id_encuestas",idinput);
                    parametros.put("publicacion","Encuestas");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
            request_encuestas_eliminar.add(stringRequest_encuestas_actualizar);



        }



    }


    public void seleccionado(View view){

        if (opcion1encuesta.isChecked()){

            Toast.makeText(getApplicationContext(),"Buena seleccion mi perro  "+opcion1encuesta.getText(),Toast.LENGTH_LONG).show();
        }
        if (opcion2encuesta.isChecked()){

            Toast.makeText(getApplicationContext(),"Buena seleccion mi perro  "+opcion2encuesta.getText(),Toast.LENGTH_LONG).show();
        }
        if (opcion3encuesta.isChecked()){

            Toast.makeText(getApplicationContext(),"Buena seleccion mi perro  "+opcion3encuesta.getText(),Toast.LENGTH_LONG).show();
        }
        if (opcion4encuesta.isChecked()){

            Toast.makeText(getApplicationContext(),"Buena seleccion mi perro  "+opcion4encuesta.getText(),Toast.LENGTH_LONG).show();
        }




    }
}
