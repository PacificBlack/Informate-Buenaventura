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
import android.widget.GridView;
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
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
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

//TODO: Esta full adopcion solo faltan retoques

public class EliminarAdopcion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    TextView titulo_eliminar_adopcion,descripcioncorta_eliminar_adopcion, descripcion1_eliminar_adopcion, descripcion2_eliminar_adopcion;
    TextInputLayout  buscar_eliminar_adopcion;

    //TODO: Modificar y Eliminar
    ImageButton publicar_eliminar_adopcion, publicar_buscar_adopcion;
    RequestQueue requestbuscar_eliminar;
    JsonObjectRequest jsonObjectRequestBuscar_eliminar;
    HorizontalScrollView eliminar_imagenes_adopcion;
    ImageView imagen1_eliminar_adopcion,imagen2_eliminar_adopcion,imagen3_eliminar_adopcion,imagen4_eliminar_adopcion;
    StringRequest stringRequest_adopcion_eliminar;

    private InterstitialAd anuncioAdopcion_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_adopcion);

        titulo_eliminar_adopcion = findViewById(R.id.eliminar_titulo_adopcion);
        descripcioncorta_eliminar_adopcion = findViewById(R.id.eliminar_descripcioncorta_adopcion);
        descripcion1_eliminar_adopcion = findViewById(R.id.eliminar_descripcion1_adopcion);
        descripcion2_eliminar_adopcion = findViewById(R.id.eliminar_descripcion2_adopcion);

        eliminar_imagenes_adopcion = findViewById(R.id.imagenes_eliminar_adopcion);
        imagen1_eliminar_adopcion = findViewById(R.id.imagen1_eliminar_adopcion);
        imagen2_eliminar_adopcion = findViewById(R.id.imagen2_eliminar_adopcion);
        imagen3_eliminar_adopcion = findViewById(R.id.imagen3_eliminar_adopcion);
        imagen4_eliminar_adopcion = findViewById(R.id.imagen4_eliminar_adopcion);
        buscar_eliminar_adopcion = findViewById(R.id.eliminar_id_adopcion);
        publicar_eliminar_adopcion = findViewById(R.id.eliminar_adopcion);
        publicar_buscar_adopcion = findViewById(R.id.eliminar_buscar_adopcion);


        publicar_eliminar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarAdopcion.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_adopcion();
                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();
            }
        });

        requestbuscar_eliminar = Volley.newRequestQueue(getApplicationContext());
        publicar_buscar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_adopcion();
            }
        });

        anuncioAdopcion_eliminar = new InterstitialAd(this);
        anuncioAdopcion_eliminar.setAdUnitId(AnuncioEliminar);
        anuncioAdopcion_eliminar.loadAd(new AdRequest.Builder().build());

    }

    //TODO: AQUI VA LO DE ACTUALIZAR Y ELIMINAR

    private boolean validarid(){
        String idinput = buscar_eliminar_adopcion.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_adopcion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_eliminar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_adopcion.setError(null);
            return true;
        }
    }

    //TODO:-------------------------------------------------------------------------------------------------------------------------------------------------
    private void cargarBusqueda_adopcion() {

        String url_buscar_adopcion = DireccionServidor+"wsnJSONBuscarAdopcion.php?id_adopcion="+buscar_eliminar_adopcion.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar_eliminar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);
        requestbuscar_eliminar.add(jsonObjectRequestBuscar_eliminar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Adopcion adopcion = new Adopcion();

        JSONArray json = response.optJSONArray("adopcion");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            adopcion.setId_adopcion(jsonObject.getInt("id_adopcion"));
            adopcion.setTitulo_row_adopcion(jsonObject.getString("titulo_adopcion"));
            adopcion.setDescripcion_row_adopcion(jsonObject.getString("descripcionrow_adopcion"));
            adopcion.setFechapublicacion_row_desaparecidos(jsonObject.getString("fechapublicacion_adopcion"));
            adopcion.setImagen1_adopcion(jsonObject.getString("imagen1_adopcion"));
            adopcion.setImagen2_adopcion(jsonObject.getString("imagen2_adopcion"));
            adopcion.setImagen3_adopcion(jsonObject.getString("imagen3_adopcion"));
            adopcion.setImagen4_adopcion(jsonObject.getString("imagen4_adopcion"));
            adopcion.setVistas_adopcion(jsonObject.getInt("vistas_adopcion"));
            adopcion.setDescripcion1_adopcion(jsonObject.getString("descripcion1_adopcion"));
            adopcion.setDescripcion2_adopcion(jsonObject.getString("descripcion2_adopcion"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_adopcion.setText(adopcion.getTitulo_row_adopcion());
        descripcioncorta_eliminar_adopcion.setText(adopcion.getDescripcion_row_adopcion());
        descripcion1_eliminar_adopcion.setText(adopcion.getDescripcion1_adopcion());
        descripcion2_eliminar_adopcion.setText(adopcion.getDescripcion2_adopcion());


        eliminar_imagenes_adopcion.setVisibility(View.VISIBLE);

        Picasso.get().load(adopcion.getImagen1_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_adopcion);

        Picasso.get().load(adopcion.getImagen2_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_adopcion);

        Picasso.get().load(adopcion.getImagen3_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_adopcion);

        Picasso.get().load(adopcion.getImagen4_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_eliminar_adopcion);
    }


    private void cargarEliminar_adopcion() {

        String url_adopcion = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_adopcion_eliminar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarAdopcion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion_eliminar.isLoaded()) {
                                        anuncioAdopcion_eliminar.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }

                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Eliminada exitosamente");
                    titulo.show();

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
                    }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_eliminar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_adopcion",idinput);
                parametros.put("publicacion","Adopcion");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_adopcion_eliminar = Volley.newRequestQueue(this);
        request_adopcion_eliminar.add(stringRequest_adopcion_eliminar);

    }

}
