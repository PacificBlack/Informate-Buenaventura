package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;
import com.pacificblack.informatebuenaventura.extras.CargandoDialog;
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

import okhttp3.internal.Util;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Avisos.Whatsapp;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_imagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_noimagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.meta_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.video_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarDonaciones extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    GridView gvImagenes_donaciones;
    Uri imagenesdonacionesUri;
    List<Uri> listaimagenes_donaciones =  new ArrayList<>();
    List<String> listaBase64_donaciones = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_donaciones;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    private InterstitialAd anuncioActualizarDonaciones;

    TextInputLayout titulo_actualizar_donaciones,video_donaciones, descripcioncorta_actualizar_donaciones, descripcion1_actualizar_donaciones, meta_actualizar_donaciones, buscar_actualizar_donaciones;
    Button subirimagenes;
    ImageButton actualizar_donaciones,actualizar_buscar_donaciones;

    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_donaciones;
    ImageView imagen1_actualizar_donaciones,imagen2_actualizar_donaciones;
    Toolbar barra_donaciones;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(ActualizarDonaciones.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_donaciones);

        whatsapp = findViewById(R.id.whatsapp_actualizar_donaciones);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarDonaciones.this,Whatsapp);
            }
        });
        barra_donaciones = findViewById(R.id.toolbar_actualizar_donaciones);
        barra_donaciones.setTitle("Actualizar Donaciones");

        titulo_actualizar_donaciones = findViewById(R.id.actualizar_titulo_donaciones);
        descripcioncorta_actualizar_donaciones = findViewById(R.id.actualizar_descripcioncorta_donaciones);
        descripcion1_actualizar_donaciones = findViewById(R.id.actualizar_descripcion1_donaciones);
        meta_actualizar_donaciones = findViewById(R.id.actualizar_meta_donaciones);
        video_donaciones = findViewById(R.id.actualizar_video_donaciones);
        imagenes_donaciones = findViewById(R.id.imagenes_actualizar_donaciones);
        imagen1_actualizar_donaciones = findViewById(R.id.imagen1_actualizar_donaciones);
        imagen2_actualizar_donaciones = findViewById(R.id.imagen2_actualizar_donaciones);
        buscar_actualizar_donaciones = findViewById(R.id.actualizar_id_donaciones);
        actualizar_donaciones = findViewById(R.id.actualizar_donaciones);
        actualizar_buscar_donaciones = findViewById(R.id.actualizar_buscar_donaciones);
        gvImagenes_donaciones = findViewById(R.id.grid_actualizar_donaciones);
        subirimagenes = findViewById(R.id.subir_imagenes_actualizar_donaciones);

        actualizar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()| !validardescripcioncorta()| ! validardescripcion1()| ! validarmeta()| ! validarid() | !validarvideo()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDonaciones.this);
                    mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_donaciones_update();

                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_donaciones();
                            cargandoDialog.Mostrar();
                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();

                    return; }

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_donaciones();
                cargandoDialog.Mostrar();

            }
        });

        anuncioActualizarDonaciones = new InterstitialAd(this);
        anuncioActualizarDonaciones.setAdUnitId("ca-app-pub-7236340326570289/8464973602");
        anuncioActualizarDonaciones.loadAd(new AdRequest.Builder().build());
        subirimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisos,PERMISSON_CODE);
                    }else {
                        seleccionarimagen();
                    }

                }else{
                    seleccionarimagen();
                }
            }
        });
    }

    private boolean validarid(){
        String idinput = buscar_actualizar_donaciones.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_donaciones.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            buscar_actualizar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_donaciones.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_actualizar_donaciones.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_donaciones.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_donaciones.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_donaciones.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_actualizar_donaciones.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){
            descripcion1_actualizar_donaciones.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){
            descripcion1_actualizar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_actualizar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validarmeta(){
        String metainput = meta_actualizar_donaciones.getEditText().getText().toString().trim();
        if (metainput.isEmpty()){
            meta_actualizar_donaciones.setError(meta_vacio);
            return false;
        }
        else if(metainput.length()>15){
            meta_actualizar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            meta_actualizar_donaciones.setError(null);
            return true;
        }

    }
    private boolean validarvideo(){
        String videoinput = video_donaciones.getEditText().getText().toString().trim();
        if (videoinput.isEmpty()){
            video_donaciones.setError(video_vacio);
            return false;
        }
        else if(videoinput.length()>150){
            video_donaciones.setError(texto_superado);
            return false;
        }
        else {
            video_donaciones.setError(null);
            return true;
        }

    }
    private boolean validarfotoupdate(){
        if (listaimagenes_donaciones.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void cargarBusqueda_donaciones() {
        String url_buscar_donaciones = DireccionServidor + "wsnJSONBuscarDonaciones.php?id_donaciones=" + buscar_actualizar_donaciones.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET, url_buscar_donaciones, null, this, this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargandoDialog.Ocultar();

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
            donacion.setVideo_donaciones(jsonObject.optString("video_donaciones"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_actualizar_donaciones.getEditText().setText(donacion.getTitulo_row_donaciones());
        descripcioncorta_actualizar_donaciones.getEditText().setText(donacion.getDescripcion_row_donaciones());
        descripcion1_actualizar_donaciones.getEditText().setText(donacion.getDescripcion1_donaciones());
        meta_actualizar_donaciones.getEditText().setText(String.valueOf(donacion.getMeta_row_donaciones()));
        video_donaciones.getEditText().setText(String.valueOf(donacion.getVideo_donaciones()));

        Picasso.get().load(donacion.getImagen1_donaciones())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_actualizar_donaciones);

        Picasso.get().load(donacion.getImagen2_donaciones())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen2_actualizar_donaciones);

        cargandoDialog.Ocultar();
    }

    public void Subirimagen_donaciones_update(){
        listaBase64_donaciones.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_donaciones.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_donaciones.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_donaciones"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }

        }
        if (nombre.size() == 1){
            cargarActualizarConImagen_donaciones_uno();
            cargandoDialog.Mostrar();
        }
        if (nombre.size() == 2){
            cargarActualizarConImagen_donaciones();
            cargandoDialog.Mostrar();
        }
        if (nombre.size()>2){
            Toast.makeText(getApplicationContext(),imagen_maxima+" 2",Toast.LENGTH_LONG).show();
        }
    }
    private void cargarActualizarSinImagen_donaciones() {
        String url_donaciones = DireccionServidor+"wsnJSONActualizarSinImageneDonaciones.php?";

        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    cargandoDialog.Ocultar();

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDonaciones.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioActualizarDonaciones.isLoaded()) {
                                        anuncioActualizarDonaciones.show();
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
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_donaciones.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_donaciones.getEditText().getText().toString().trim();
                String metainput = meta_actualizar_donaciones.getEditText().getText().toString().trim();
                String videoinput = video_donaciones.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_donaciones",idinput);
                parametros.put("titulo_donaciones",tituloinput);
                parametros.put("descripcionrow_donaciones",descripcioncortainput);
                parametros.put("descripcion1_donaciones",descripcion1input);
                parametros.put("meta_donaciones",metainput);
                parametros.put("video_donaciones",videoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Donaciones");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_donaciones_actualizar = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_donaciones_actualizar.add(stringRequest_donaciones);

    }
    private void cargarActualizarConImagen_donaciones_uno() {

        String url_donaciones = DireccionServidor+"wsnJSONActualizarConImagenDonaciones.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    cargandoDialog.Ocultar();

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDonaciones.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioActualizarDonaciones.isLoaded()) {
                                        anuncioActualizarDonaciones.show();
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
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_donaciones.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_donaciones.getEditText().getText().toString().trim();
                String metainput = meta_actualizar_donaciones.getEditText().getText().toString().trim();
                String videoinput = video_donaciones.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_donaciones",idinput);
                parametros.put("titulo_donaciones",tituloinput);
                parametros.put("descripcionrow_donaciones",descripcioncortainput);
                parametros.put("vistas_donaciones","0");
                parametros.put("descripcion1_donaciones",descripcion1input);
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_donaciones1","vacio");
                parametros.put("meta_donaciones",metainput);
                parametros.put("video_donaciones",videoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Donaciones");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_donaciones_actualizar = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_donaciones_actualizar.add(stringRequest_donaciones);
    }
    private void cargarActualizarConImagen_donaciones() {

        String url_donaciones = DireccionServidor+"wsnJSONActualizarConImagenDonaciones.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    cargandoDialog.Ocultar();

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDonaciones.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioActualizarDonaciones.isLoaded()) {
                                        anuncioActualizarDonaciones.show();
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
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_donaciones.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_donaciones.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_donaciones.getEditText().getText().toString().trim();
                String metainput = meta_actualizar_donaciones.getEditText().getText().toString().trim();
                String videoinput = video_donaciones.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_donaciones",idinput);
                parametros.put("titulo_donaciones",tituloinput);
                parametros.put("descripcionrow_donaciones",descripcioncortainput);
                parametros.put("vistas_donaciones","0");
                parametros.put("descripcion1_donaciones",descripcion1input);
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("meta_donaciones",metainput);
                parametros.put("video_donaciones",videoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Donaciones");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_donaciones_actualizar = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_donaciones_actualizar.add(stringRequest_donaciones);
    }

    public String convertirUriEnBase64(Bitmap bmp){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }
    public void seleccionarimagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 2 imagenes"),IMAGE_PICK_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(ActualizarDonaciones.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesdonacionesUri = data.getData();
                listaimagenes_donaciones.add(imagenesdonacionesUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_donaciones.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarDonaciones.this,listaimagenes_donaciones);
        gvImagenes_donaciones.setAdapter(baseAdapter);
    }
    @SuppressLint("NewApi")
    private void whatsapp(Activity activity, String phone) {
        String formattedNumber = Util.format(phone);
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "");
            sendIntent.putExtra("jid", formattedNumber + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(activity, "Instale whatsapp en su dispositivo o cambie a la version oficial que esta disponible en PlayStore", Toast.LENGTH_SHORT).show();
        }
    }

}