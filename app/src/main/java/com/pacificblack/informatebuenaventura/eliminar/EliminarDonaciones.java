package com.pacificblack.informatebuenaventura.eliminar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;
import com.pacificblack.informatebuenaventura.extras.Cargando;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;
//TODO: Esta full pero hay que verificar el tamaño de las imagenes


public class EliminarDonaciones extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    private InterstitialAd anuncioEliminarDonaciones;
    TextView titulo_eliminar_donaciones, descripcioncorta_eliminar_donaciones, descripcion1_eliminar_donaciones, meta_eliminar_donaciones;
    TextInputLayout buscar_eliminar_donaciones;
    ImageButton eliminar_donaciones,eliminar_buscar_donaciones;

    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    StringRequest stringRequest_donaciones;
    ImageView imagen1_eliminar_donaciones,imagen2_eliminar_donaciones;

    Cargando cargando = new Cargando(EliminarDonaciones.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_donaciones);

        titulo_eliminar_donaciones = findViewById(R.id.eliminar_titulo_donaciones);
        descripcioncorta_eliminar_donaciones = findViewById(R.id.eliminar_descripcioncorta_donaciones);
        descripcion1_eliminar_donaciones = findViewById(R.id.eliminar_descripcion1_donaciones);
        meta_eliminar_donaciones = findViewById(R.id.eliminar_meta_donaciones);
        eliminar_buscar_donaciones = findViewById(R.id.eliminar_buscar_donaciones);
        imagen1_eliminar_donaciones = findViewById(R.id.imagen1_eliminar_donaciones);
        imagen2_eliminar_donaciones = findViewById(R.id.imagen2_eliminar_donaciones);
        eliminar_donaciones = findViewById(R.id.eliminar_donaciones);
        buscar_eliminar_donaciones= findViewById(R.id.eliminar_id_donaciones);

        eliminar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarDonaciones.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_donaciones();
                        cargando.iniciarprogress();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_donaciones();
                cargando.iniciarprogress();

            }
        });

        anuncioEliminarDonaciones = new InterstitialAd(this);
        anuncioEliminarDonaciones.setAdUnitId(AnuncioEliminar);
        anuncioEliminarDonaciones.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = buscar_eliminar_donaciones.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_donaciones.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_eliminar_donaciones.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_donaciones.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_donaciones() {

        String url_buscar_donaciones = DireccionServidor+"wsnJSONBuscarDonaciones.php?id_donaciones="+buscar_eliminar_donaciones.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_donaciones,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargando.cancelarprogress();

    }

    @Override
    public void onResponse(JSONObject response) {

        Donaciones donacion = new Donaciones();

        JSONArray json = response.optJSONArray("donaciones");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            donacion.setId_donaciones(jsonObject.optInt("id_donaciones"));
            donacion.setTitulo_row_donaciones(jsonObject.optString("titulo_donaciones"));
            donacion.setDescripcion_row_donaciones(jsonObject.optString("descripcionrow_donaciones"));
            donacion.setFechapublicacion_row_donaciones(jsonObject.optString("fechapublicacion_donaciones"));
            donacion.setImagen1_donaciones(jsonObject.optString("imagen1_donaciones"));
            donacion.setImagen2_donaciones(jsonObject.getString("imagen2_donaciones"));
            donacion.setVistas_donaciones(jsonObject.optInt("vistas_donaciones"));
            donacion.setMeta_row_donaciones(jsonObject.optInt("meta_donaciones"));
            donacion.setDescripcion1_donaciones(jsonObject.optString("descripcion1_donaciones"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_donaciones.setText(donacion.getTitulo_row_donaciones());
        descripcioncorta_eliminar_donaciones.setText(donacion.getDescripcion_row_donaciones());
        descripcion1_eliminar_donaciones.setText(donacion.getDescripcion1_donaciones());
        meta_eliminar_donaciones.setText(String.valueOf(donacion.getMeta_row_donaciones()));

        Picasso.get().load(donacion.getImagen1_donaciones())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_donaciones);

        Picasso.get().load(donacion.getImagen2_donaciones())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_donaciones);

        cargando.cancelarprogress();


    }
    private void cargarEliminar_donaciones() {

        String url_donaciones = DireccionServidor+"wsnJSONEliminar.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    cargando.cancelarprogress();


                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarDonaciones.this);
                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioEliminarDonaciones.isLoaded()) {
                                        anuncioEliminarDonaciones.show();
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
                    cargando.cancelarprogress();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargando.cancelarprogress();

                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_eliminar_donaciones.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_donaciones",idinput);
                parametros.put("publicacion","Donaciones");
                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_funebres_actualizar = Volley.newRequestQueue(this);
        request_funebres_actualizar.add(stringRequest_donaciones);

    }

}
