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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
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
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

//TODO: Testeando para comprobar si todo esta full

public class EliminarBienes extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextView titulo_eliminar_bienes, descripcioncorta_eliminar_bienes, descripcion1_eliminar_bienes, descripcion2_eliminar_bienes, precio_eliminar_bienes;
    TextInputLayout buscar_eliminar_bienes;
    ImageButton eliminar_bienes, eliminar_buscar_bienes;
    RequestQueue requestbuscar;
    StringRequest stringRequest_bienes;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_bienes,imagen2_eliminar_bienes,imagen3_eliminar_bienes,imagen4_eliminar_bienes;
    private InterstitialAd anunciobienes_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_bienes);


        titulo_eliminar_bienes = findViewById(R.id.eliminar_titulo_bienes);
        descripcioncorta_eliminar_bienes = findViewById(R.id.eliminar_descripcioncorta_bienes);
        descripcion1_eliminar_bienes = findViewById(R.id.eliminar_descripcion1_bienes);
        descripcion2_eliminar_bienes = findViewById(R.id.eliminar_descripcion2_bienes);
        precio_eliminar_bienes = findViewById(R.id.eliminar_precio_bienes);
        imagen1_eliminar_bienes = findViewById(R.id.imagen1_eliminar_bienes);
        imagen2_eliminar_bienes = findViewById(R.id.imagen2_eliminar_bienes);
        imagen3_eliminar_bienes = findViewById(R.id.imagen3_eliminar_bienes);
        imagen4_eliminar_bienes = findViewById(R.id.imagen4_eliminar_bienes);
        buscar_eliminar_bienes = findViewById(R.id.eliminar_id_bienes);
        eliminar_bienes = findViewById(R.id.eliminar_bienes);
        eliminar_buscar_bienes = findViewById(R.id.eliminar_buscar_bienes);

        eliminar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarBienes.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_bienes();
                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_bienes();
            }
        });

        anunciobienes_eliminar = new InterstitialAd(this);
        anunciobienes_eliminar.setAdUnitId(AnuncioEliminar);
        anunciobienes_eliminar.loadAd(new AdRequest.Builder().build());

    }

    private boolean validarid(){
        String idinput = buscar_eliminar_bienes.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_bienes.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_eliminar_bienes.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_bienes.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_bienes() {
        String url_buscar_bienes = DireccionServidor+"wsnJSONBuscarBienes.php?id_bienes="+buscar_eliminar_bienes.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        Bienes bienes = new Bienes();

        JSONArray json = response.optJSONArray("bienes");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);


            bienes.setId_bienes(jsonObject.getInt("id_bienes"));
            bienes.setTitulo_row_bienes(jsonObject.getString("titulo_bienes"));
            bienes.setDescripcion_row_bienes(jsonObject.getString("descripcionrow_bienes"));
            bienes.setFechapublicacion_row_bienes(jsonObject.getString("fechapublicacion_bienes"));
            bienes.setImagen1_bienes(jsonObject.getString("imagen1_bienes"));
            bienes.setImagen2_bienes(jsonObject.getString("imagen2_bienes"));
            bienes.setImagen3_bienes(jsonObject.getString("imagen3_bienes"));
            bienes.setImagen4_bienes(jsonObject.getString("imagen4_bienes"));
            bienes.setPrecio_row_bienes(jsonObject.getInt("precio_bienes"));
            bienes.setVistas_bienes(jsonObject.getInt("vistas_bienes"));
            bienes.setDescripcion1_bienes(jsonObject.getString("descripcion1_bienes"));
            bienes.setDescripcion2_bienes(jsonObject.getString("descripcion2_bienes"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_bienes.setText(bienes.getTitulo_row_bienes());
        descripcioncorta_eliminar_bienes.setText(bienes.getDescripcion_row_bienes());
        descripcion1_eliminar_bienes.setText(bienes.getDescripcion1_bienes());
        descripcion2_eliminar_bienes.setText(bienes.getDescripcion2_bienes());
        precio_eliminar_bienes.setText(String.valueOf(bienes.getPrecio_row_bienes()));

        Picasso.get().load(bienes.getImagen1_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_bienes);

        Picasso.get().load(bienes.getImagen2_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_bienes);

        Picasso.get().load(bienes.getImagen3_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_bienes);

        Picasso.get().load(bienes.getImagen4_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_eliminar_bienes);
    }

    private void cargarEliminar_bienes() {

        String url_bienes = DireccionServidor+"wsnJSONEliminar.php?";


        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarBienes.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciobienes_eliminar.isLoaded()) {
                                        anunciobienes_eliminar.show();
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

                String idinput = buscar_eliminar_bienes.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_bienes",idinput);
                parametros.put("publicacion","Bienes");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        request_bienes_actualizar.add(stringRequest_bienes);

    }
}