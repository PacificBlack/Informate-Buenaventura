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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
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
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.video_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarClasificados extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    CargandoDialog cargandoDialog = new CargandoDialog(ActualizarClasificados.this);
    GridView gvImagenes_clasificados;
    Uri imagenesclasificadosUri;
    List<Uri> listaimagenesclasificados =  new ArrayList<>();
    List<String> listaBase64clasificados = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequestclasificados;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    ImageButton actualizar_buscar_clasificados;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_clasificados,imagen2_actualizar_clasificados, imagen3_actualizar_clasificados,imagen4_actualizar_clasificados;
    TextInputLayout titulo_actualizar_clasificados, descripcioncorta_actualizar_clasificados,video_clasificados,descripcion1_actualizar_clasificados, descripcion2_actualizar_clasificados, buscar_actualizar_clasificados;
    Button actualizar_editar_clasificados,actualizarimagenes;
    private InterstitialAd anuncioClasificados_actualizar;
    Toolbar barra_clasificados;
    ImageView whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_clasificados);

        whatsapp = findViewById(R.id.whatsapp_actualizar_clasificados);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarClasificados.this,Whatsapp);
            }
        });
        barra_clasificados = findViewById(R.id.toolbar_actualizar_clasificados);
        barra_clasificados.setTitle("Actualizar Clasificados");
        barra_clasificados.setTitleTextColor(Color.WHITE);
        titulo_actualizar_clasificados = findViewById(R.id.actualizar_titulo_clasificados);
        descripcioncorta_actualizar_clasificados = findViewById(R.id.actualizar_descripcioncorta_clasificados);
        video_clasificados = findViewById(R.id.actualizar_video_clasificados);
        descripcion1_actualizar_clasificados = findViewById(R.id.actualizar_descripcion1_clasificados);
        descripcion2_actualizar_clasificados = findViewById(R.id.actualizar_descripcion2_clasificados);
        gvImagenes_clasificados = findViewById(R.id.grid_clasificados);
        actualizarimagenes = findViewById(R.id.actualizar_imagenes_clasificados);
        imagen1_actualizar_clasificados = findViewById(R.id.imagen1_actualizar_clasificados);
        imagen2_actualizar_clasificados = findViewById(R.id.imagen2_actualizar_clasificados);
        imagen3_actualizar_clasificados = findViewById(R.id.imagen3_actualizar_clasificados);
        imagen4_actualizar_clasificados = findViewById(R.id.imagen4_actualizar_clasificados);
        buscar_actualizar_clasificados = findViewById(R.id.actualizar_id_clasificados);
        actualizar_editar_clasificados = findViewById(R.id.actualizar_editar_clasificados);
        actualizar_buscar_clasificados = findViewById(R.id.actualizar_buscar_clasificados);

        actualizarimagenes.setOnClickListener(new View.OnClickListener() {
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

        actualizar_editar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo()| !validardescripcioncorta()| ! validardescripcion1()| ! validarid()| ! validarvideo()){return;}
                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarClasificados.this);
                    mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_clasificados_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_clasificados();
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
        actualizar_buscar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_clasificados();
                cargandoDialog.Mostrar();
            }
        });

        anuncioClasificados_actualizar = new InterstitialAd(this);
        anuncioClasificados_actualizar.setAdUnitId("ca-app-pub-7236340326570289/8305385026");
        anuncioClasificados_actualizar.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_clasificados.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_clasificados.setError(texto_superado);
            return false;
        }
        else {
            buscar_actualizar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_actualizar_clasificados.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_clasificados.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_clasificados.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_clasificados.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_clasificados.setError(null);
            return true;
        }

    }
    private boolean validarvideo(){
        String videoinput = video_clasificados.getEditText().getText().toString().trim();
        if (videoinput.isEmpty()){
            video_clasificados.setError(video_vacio);
            return false;
        }
        else if(videoinput.length()>150){
            video_clasificados.setError(texto_superado);
            return false;
        }
        else {
            video_clasificados.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){
            descripcion1_actualizar_clasificados.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){
            descripcion1_actualizar_clasificados.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_actualizar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenesclasificados.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }
    private void cargarBusqueda_clasificados() {
        String url_buscar_adopcion = DireccionServidor+"wsnJSONBuscarClasificados.php?id_clasificados="+buscar_actualizar_clasificados.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);
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

        titulo_actualizar_clasificados.getEditText().setText(clasificados.getTitulo_row_clasificados());
        descripcioncorta_actualizar_clasificados.getEditText().setText(clasificados.getDescripcion_row_clasificados());
        video_clasificados.getEditText().setText(clasificados.getVideo_clasificados());
        descripcion1_actualizar_clasificados.getEditText().setText(clasificados.getDescripcion1_clasificados());
        descripcion2_actualizar_clasificados.getEditText().setText(clasificados.getDescripcion2_clasificados());

        Picasso.get().load(clasificados.getImagen1_clasificados()).placeholder(R.drawable.ib).error(R.drawable.ib).into(imagen1_actualizar_clasificados);
        Picasso.get().load(clasificados.getImagen2_clasificados()).placeholder(R.drawable.ib).error(R.drawable.ib).into(imagen2_actualizar_clasificados);
        Picasso.get().load(clasificados.getImagen3_clasificados()).placeholder(R.drawable.ib).error(R.drawable.ib).into(imagen3_actualizar_clasificados);
        Picasso.get().load(clasificados.getImagen4_clasificados()).placeholder(R.drawable.ib).error(R.drawable.ib).into(imagen4_actualizar_clasificados);

        cargandoDialog.Ocultar();

    }
    public void Subirimagen_clasificados_update(){
        listaBase64clasificados.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenesclasificados.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenesclasificados.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_clasificados"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }

        if (nombre.size() == 1){
            cargarActualizarConImagen_clasificados_uno();
            cargandoDialog.Mostrar();
        }
        if (nombre.size() == 2){
            cargarActualizarConImagen_clasificados_dos();
            cargandoDialog.Mostrar();
        }
        if (nombre.size() == 3){
            cargarActualizarConImagen_clasificados_tres();
            cargandoDialog.Mostrar();
        }
        if (nombre.size() == 4){
            cargarActualizarConImagen_clasificados();
            cargandoDialog.Mostrar();
        }
        if (nombre.size()>4){
            Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show();
        }
    }

    private void cargarActualizarSinImagen_clasificados() {

        String url_clasificados = DireccionServidor+"wsnJSONActualizarSinImagenClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarClasificados.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);
                    dialogimagen.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioClasificados_actualizar.isLoaded()) { anuncioClasificados_actualizar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
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

                String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarActualizarConImagen_clasificados_uno() {

        String url_clasificados = DireccionServidor+"wsnJSONActualizarConImagenClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarClasificados.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);
                    dialogimagen.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioClasificados_actualizar.isLoaded()) { anuncioClasificados_actualizar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
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

                String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_clasificados1","vacio");
                parametros.put("imagen_clasificados2","vacio");
                parametros.put("imagen_clasificados3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarActualizarConImagen_clasificados_dos() {

        String url_clasificados = DireccionServidor+"wsnJSONActualizarConImagenClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarClasificados.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);
                    dialogimagen.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioClasificados_actualizar.isLoaded()) { anuncioClasificados_actualizar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
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

                String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_clasificados2","vacio");
                parametros.put("imagen_clasificados3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarActualizarConImagen_clasificados_tres() {

        String url_clasificados = DireccionServidor+"wsnJSONActualizarConImagenClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarClasificados.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);
                    dialogimagen.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioClasificados_actualizar.isLoaded()) { anuncioClasificados_actualizar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
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

                String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put("imagen_clasificados3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarActualizarConImagen_clasificados() {

        String url_clasificados = DireccionServidor+"wsnJSONActualizarConImagenClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarClasificados.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);
                    dialogimagen.setImageDrawable(getResources().getDrawable(R.drawable.heart_on));
                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioClasificados_actualizar.isLoaded()) { anuncioClasificados_actualizar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
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

                String idinput = buscar_actualizar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put(nombre.get(3),cadena.get(3));

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);
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
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 4 imagenes"),IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(ActualizarClasificados.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesclasificadosUri = data.getData();
                listaimagenesclasificados.add(imagenesclasificadosUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenesclasificados.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarClasificados.this,listaimagenesclasificados);
        gvImagenes_clasificados.setAdapter(baseAdapter);
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