package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.eventos.Eventos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class EliminarEventos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    TextView titulo_eliminar_eventos,descripcioncorta_eliminar_eventos,lugar_eliminar_eventos;
    TextInputLayout id_eliminar_eventos;

    ImageButton eliminar_eventos,eliminar_buscar_eventos;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_eventos;
    StringRequest stringRequest_eventos;
    private InterstitialAd anuncioeventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_eventos);

        titulo_eliminar_eventos = findViewById(R.id.eliminar_titulo_eventos);
        descripcioncorta_eliminar_eventos = findViewById(R.id.eliminar_descripcion_eventos);
        lugar_eliminar_eventos = findViewById(R.id.eliminar_lugar_eventos);
        id_eliminar_eventos = findViewById(R.id.eliminar_id_eventos);
        eliminar_eventos = findViewById(R.id.eliminar_eventos);
        eliminar_buscar_eventos = findViewById(R.id.eliminar_buscar_eventos);
        imagen1_eliminar_eventos = findViewById(R.id.imagen1_eliminar_eventos);

        eliminar_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarEventos.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_eventos();
                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_eventos();
            }
        });

        anuncioeventos = new InterstitialAd(this);
        anuncioeventos.setAdUnitId(AnuncioEliminar);
        anuncioeventos.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = id_eliminar_eventos.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_eliminar_eventos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            id_eliminar_eventos.setError(""+R.string.supera);
            return false;
        }
        else {
            id_eliminar_eventos.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_eventos() {

        String url_buscar_eventos = DireccionServidor+"wsnJSONBuscarEventos.php?id_eventos="+id_eliminar_eventos.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_eventos,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }
    @Override
    public void onResponse(JSONObject response) {
        Eventos eventos = new Eventos();

        JSONArray json = response.optJSONArray("eventos");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            eventos.setId_eventos(jsonObject.getInt("id_eventos"));
            eventos.setTitulo_row_eventos(jsonObject.getString("titulo_eventos"));
            eventos.setDescripcion_row_eventos(jsonObject.getString("descripcionrow_eventos"));
            eventos.setFechapublicacion_row_eventos(jsonObject.getString("fechapublicacion_eventos"));
            eventos.setLugar_row_eventos(jsonObject.getString("lugar_eventos"));
            eventos.setImagen1_eventos(jsonObject.getString("imagen1_eventos"));
            eventos.setVistas_eventos(jsonObject.getInt("vistas_eventos"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_eliminar_eventos.setText(eventos.getTitulo_row_eventos());
        descripcioncorta_eliminar_eventos.setText(eventos.getDescripcion_row_eventos());
        lugar_eliminar_eventos.setText(eventos.getLugar_row_eventos());
        Picasso.get().load(eventos.getImagen1_eventos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_eventos);
    }


    private void cargarEliminar_eventos() {

        String url_eventos = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_eventos= new StringRequest(Request.Method.POST, url_eventos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarEventos.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioeventos.isLoaded()) {
                                        anuncioeventos.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }
                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Recuerda");
                    titulo.show();

                    Log.i("Funciona : ",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_eliminar_eventos.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_eventos",idinput);
                parametros.put("publicacion","Eventos");

                return parametros;
            }
        };

        RequestQueue request_eventos = Volley.newRequestQueue(this);
        request_eventos.add(stringRequest_eventos);

    }


}
