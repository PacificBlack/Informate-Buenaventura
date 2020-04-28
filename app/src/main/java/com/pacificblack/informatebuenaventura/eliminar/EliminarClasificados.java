package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes

public class EliminarClasificados extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    StringRequest stringRequestclasificados;
    ImageButton eliminar_clasificados, eliminar_buscar_clasificados;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_clasificados, imagen2_eliminar_clasificados, imagen3_eliminar_clasificados, imagen4_eliminar_clasificados;
    TextView titulo_eliminar_clasificados, descripcioncorta_eliminar_clasificados, video_eliminar_clasificados, descripcion1_eliminar_clasificados, descripcion2_eliminar_clasificados;
    TextInputLayout buscar_eliminar_clasificados;
    private InterstitialAd anuncioClasificados_eliminar;
    private ProgressDialog clasificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_clasificados);

        titulo_eliminar_clasificados = findViewById(R.id.eliminar_titulo_clasificados);
        descripcioncorta_eliminar_clasificados = findViewById(R.id.eliminar_descripcioncorta_clasificados);
        video_eliminar_clasificados = findViewById(R.id.eliminar_video_clasificados);
        descripcion1_eliminar_clasificados = findViewById(R.id.eliminar_descripcion1_clasificados);
        descripcion2_eliminar_clasificados = findViewById(R.id.eliminar_descripcion2_clasificados);
        imagen1_eliminar_clasificados = findViewById(R.id.imagen1_eliminar_clasificados);
        imagen2_eliminar_clasificados = findViewById(R.id.imagen2_eliminar_clasificados);
        imagen3_eliminar_clasificados = findViewById(R.id.imagen3_eliminar_clasificados);
        imagen4_eliminar_clasificados = findViewById(R.id.imagen4_eliminar_clasificados);
        buscar_eliminar_clasificados = findViewById(R.id.eliminar_id_clasificados);
        eliminar_clasificados = findViewById(R.id.eliminar_clasificados);
        eliminar_buscar_clasificados = findViewById(R.id.eliminar_buscar_clasificados);

        eliminar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarClasificados.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_clasificados();
                        CargandoSubida("Ver");

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Eliminar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_clasificados();
                CargandoSubida("Ver");

            }
        });

        anuncioClasificados_eliminar = new InterstitialAd(this);
        anuncioClasificados_eliminar.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioClasificados_eliminar.loadAd(new AdRequest.Builder().build());

    }

    private boolean validarid(){
        String idinput = buscar_eliminar_clasificados.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_clasificados.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_eliminar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_clasificados.setError(null);
            return true;
        }
    }
    private void cargarBusqueda_clasificados() {

        String url_buscar_adopcion = DireccionServidor+"wsnJSONBuscarClasificados.php?id_clasificados="+buscar_eliminar_clasificados.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        CargandoSubida("Ocultar");


    }

    @Override
    public void onResponse(JSONObject response) {

        Clasificados clasificados = new Clasificados();

        JSONArray json = response.optJSONArray("clasificados");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);


            clasificados.setId_clasificados(jsonObject.getInt("id_clasificados"));
            clasificados.setTitulo_row_clasificados(jsonObject.getString("titulo_clasificados"));
            clasificados.setDescripcion_row_clasificados(jsonObject.getString("descripcionrow_clasificados"));
            clasificados.setFechapublicacion_row_clasificados(jsonObject.getString("fechapublicacion_clasificados"));
            clasificados.setImagen1_clasificados(jsonObject.getString("imagen1_clasificados"));
            clasificados.setImagen2_clasificados(jsonObject.getString("imagen2_clasificados"));
            clasificados.setImagen3_clasificados(jsonObject.getString("imagen3_clasificados"));
            clasificados.setImagen4_clasificados(jsonObject.getString("imagen4_clasificados"));
            clasificados.setVideo_clasificados(jsonObject.getString("video_clasificados"));
            clasificados.setVistas_bienes(jsonObject.getInt("vistas_clasificados"));
            clasificados.setDescripcion1_clasificados(jsonObject.getString("descripcion1_clasificados"));
            clasificados.setDescripcion2_clasificados(jsonObject.getString("descripcion2_clasificados"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_clasificados.setText(clasificados.getTitulo_row_clasificados());
        descripcioncorta_eliminar_clasificados.setText(clasificados.getDescripcion_row_clasificados());
        video_eliminar_clasificados.setText(clasificados.getVideo_clasificados());
        descripcion1_eliminar_clasificados.setText(clasificados.getDescripcion1_clasificados());
        descripcion2_eliminar_clasificados.setText(clasificados.getDescripcion2_clasificados());

        Picasso.get().load(clasificados.getImagen1_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen2_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen3_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen4_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_eliminar_clasificados);

        CargandoSubida("Ocultar");

    }

    private void cargarEliminar_clasificados() {

        String url_clasificados = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    CargandoSubida("Ocultar");


                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarClasificados.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioClasificados_eliminar.isLoaded()) {
                                        anuncioClasificados_eliminar.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }

                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Eliminada exitosamente");
                    titulo.show();

                    Log.i("Funciona : ",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    CargandoSubida("Ocultar");

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");


                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_eliminar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("publicacion","Clasificados");
                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void CargandoSubida(String Mostrar){
        clasificados=new ProgressDialog(this);
        clasificados.setMessage("Subiendo su Empleos");
        clasificados.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        clasificados.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            clasificados.show();
        }if(Mostrar.equals("Ocultar")){
            clasificados.hide();
        }
    }


}