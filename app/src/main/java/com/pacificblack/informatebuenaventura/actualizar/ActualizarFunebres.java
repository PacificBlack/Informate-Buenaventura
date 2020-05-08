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
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;
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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarFunebres extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    GridView gvImagenes_funebres;
    Uri imagenesfunebresUri;
    List<Uri> listaimagenes_funebres =  new ArrayList<>();
    List<String> listaBase64_funebres = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_funebres;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    TextInputLayout id_actualizar_funebres, titulo_actualizar_funebres, descripcioncorta_actualizar_funebres, descripcion1_actualizar_funebres, descripcion2_actualizar_funebres;
    Button actualizar_funebres,subirimagenes;
    ImageButton actualizar_buscar_funebres;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_funebres,imagen2_actualizar_funebres,imagen3_actualizar_funebres;
    private InterstitialAd anunciofunebres;
    Toolbar barra_funebres;
    ImageView whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_funebres);

        whatsapp = findViewById(R.id.whatsapp_actualizar_funebres);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarFunebres.this,Whatsapp);
            }
        });
        barra_funebres = findViewById(R.id.toolbar_actualizar_funebres);
        barra_funebres.setTitle("Actualizar Funebres");
        id_actualizar_funebres = findViewById(R.id.id_actualizar_funebres);
        titulo_actualizar_funebres = findViewById(R.id.actualizar_titulo_funebres);
        descripcioncorta_actualizar_funebres = findViewById(R.id.actualizar_descripcioncorta_funebres);
        descripcion1_actualizar_funebres = findViewById(R.id.actualizar_descripcion1_funebres);
        descripcion2_actualizar_funebres = findViewById(R.id.actualizar_descripcion2_funebres);
        imagen1_actualizar_funebres = findViewById(R.id.imagen1_actualizar_funebres);
        imagen2_actualizar_funebres = findViewById(R.id.imagen2_actualizar_funebres);
        imagen3_actualizar_funebres = findViewById(R.id.imagen3_actualizar_funebres);
        actualizar_funebres = findViewById(R.id.actualizar_final_funebres);
        actualizar_buscar_funebres = findViewById(R.id.actualizar_buscar_funebres);
        actualizar_funebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !validarid() | !validartitulo()|!validardescripcioncorta()| ! validardescripcion1()){return;}


                if ( !validarfotoupdate() ){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarFunebres.this);
                    mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_funebres_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_funebres();
                            CargandoSubida("Ver");
                        }
                    });
                    AlertDialog titulo2 = mensaje.create();
                    titulo2.setTitle("Modificar Publicación");
                    titulo2.show();
                    return;
                }

            }
        });
        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_funebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_funebres();
                CargandoSubida("Ver");
            }
        });

        anunciofunebres = new InterstitialAd(this);
        anunciofunebres.setAdUnitId(AnuncioActualizar);
        anunciofunebres.loadAd(new AdRequest.Builder().build());

        gvImagenes_funebres = findViewById(R.id.grid_actualizar_funebres);
        subirimagenes = findViewById(R.id.subir_actualizar_funebres);
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
        String idinput = id_actualizar_funebres.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            id_actualizar_funebres.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            id_actualizar_funebres.setError(texto_superado);
            return false;
        }
        else {
            id_actualizar_funebres.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_funebres.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_actualizar_funebres.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_funebres.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_funebres.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_funebres.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_funebres.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_funebres.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_funebres.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_actualizar_funebres.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){
            descripcion1_actualizar_funebres.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>800){
            descripcion1_actualizar_funebres.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_actualizar_funebres.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_funebres.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void cargarBusqueda_funebres() {
        String url_buscar_funebres = DireccionServidor+"wsnJSONBuscarFunebres.php?id_funebres="+id_actualizar_funebres.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_funebres,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        CargandoSubida("Ocultar");
    }
    @Override
    public void onResponse(JSONObject response) {
        Funebres funebres = new Funebres();

        JSONArray json = response.optJSONArray("funebres");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            funebres.setId_funebres(jsonObject.getInt("id_funebres"));
            funebres.setTitulo_row_funebres(jsonObject.getString("titulo_funebres"));
            funebres.setDescripcion_row_funebres(jsonObject.getString("descripcionrow_funebres"));
            funebres.setFechapublicacion_row_funebres(jsonObject.getString("fechapublicacion_funebres"));
            funebres.setImagen1_funebres(jsonObject.getString("imagen1_funebres"));
            funebres.setImagen2_funebres(jsonObject.getString("imagen2_funebres"));
            funebres.setImagen3_funebres(jsonObject.getString("imagen3_funebres"));
            funebres.setVistas_funebres(jsonObject.getInt("vistas_funebres"));
            funebres.setDescripcion1_funebres(jsonObject.getString("descripcion1_funebres"));
            funebres.setDescripcion2_funebres(jsonObject.getString("descripcion2_funebres"));


        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_actualizar_funebres.getEditText().setText(funebres.getTitulo_row_funebres());
        descripcioncorta_actualizar_funebres.getEditText().setText(funebres.getDescripcion_row_funebres());
        descripcion1_actualizar_funebres.getEditText().setText(funebres.getDescripcion1_funebres());
        descripcion2_actualizar_funebres.getEditText().setText(funebres.getDescripcion2_funebres());

        Picasso.get().load(funebres.getImagen1_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_funebres);

        Picasso.get().load(funebres.getImagen2_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_funebres);

        Picasso.get().load(funebres.getImagen3_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_funebres);

        CargandoSubida("Ocultar");


    }

    public void Subirimagen_funebres_update(){

        listaBase64_funebres.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_funebres.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_funebres.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_funebres"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){
            cargarActualizarConImagen_funebres_uno();
            CargandoSubida("Ver"); }
        if (nombre.size()== 2){
            cargarActualizarConImagen_funebres_dos();
            CargandoSubida("Ver");}
        if (nombre.size() == 3 ){
            cargarActualizarConImagen_funebres();
            CargandoSubida("Ver"); }
        if (nombre.size()>3){
            Toast.makeText(getApplicationContext(),imagen_maxima+" 3",Toast.LENGTH_LONG).show();
        }
    }
    private void cargarActualizarConImagen_funebres_uno() {

        String url_funebres = DireccionServidor+"wsnJSONActualizarConImagenFunebres.php?";

        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarFunebres.this);
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
                            if (anunciofunebres.isLoaded()) { anunciofunebres.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String idinput = id_actualizar_funebres.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_funebres.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_funebres",idinput);
                parametros.put("titulo_funebres",tituloinput);
                parametros.put("descripcionrow_funebres",descripcioncortainput);
                parametros.put("vistas_funebres","0");
                parametros.put("descripcion1_funebres",descripcion1input);
                parametros.put("descripcion2_funebres","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Funebres");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_funebres1","vacio");
                parametros.put("imagen_funebres2","vacio");
                return parametros;
            }
        };
        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_funebres.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_funebres);

    }
    private void cargarActualizarConImagen_funebres_dos() {

        String url_funebres = DireccionServidor+"wsnJSONActualizarConImagenFunebres.php?";

        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarFunebres.this);
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
                            if (anunciofunebres.isLoaded()) { anunciofunebres.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String idinput = id_actualizar_funebres.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_funebres.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_funebres",idinput);
                parametros.put("titulo_funebres",tituloinput);
                parametros.put("descripcionrow_funebres",descripcioncortainput);
                parametros.put("vistas_funebres","0");
                parametros.put("descripcion1_funebres",descripcion1input);
                parametros.put("descripcion2_funebres","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Funebres");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_funebres2","vacio");
                return parametros;
            }
        };
        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_funebres.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_funebres);

    }
    private void cargarActualizarConImagen_funebres() {

        String url_funebres = DireccionServidor+"wsnJSONActualizarConImagenFunebres.php?";

        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarFunebres.this);
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
                            if (anunciofunebres.isLoaded()) { anunciofunebres.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String idinput = id_actualizar_funebres.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_funebres.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_funebres",idinput);
                parametros.put("titulo_funebres",tituloinput);
                parametros.put("descripcionrow_funebres",descripcioncortainput);
                parametros.put("vistas_funebres","0");
                parametros.put("descripcion1_funebres",descripcion1input);
                parametros.put("descripcion2_funebres","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Funebres");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));

                return parametros;
            }
        };
        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_funebres.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_funebres);

    }
    private void cargarActualizarSinImagen_funebres() {

        String url_funebres = DireccionServidor+"wsnJSONActualizarSinImagenFunebres.php?";

        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarFunebres.this);
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
                            if (anunciofunebres.isLoaded()) { anunciofunebres.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String idinput = id_actualizar_funebres.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_funebres.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_funebres.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_funebres",idinput);
                parametros.put("titulo_funebres",tituloinput);
                parametros.put("descripcionrow_funebres",descripcioncortainput);
                parametros.put("vistas_funebres","0");
                parametros.put("descripcion1_funebres",descripcion1input);
                parametros.put("descripcion2_funebres","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Funebres");

                return parametros;
            }
        };
        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_funebres.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_funebres);

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
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 3 imagenes"),IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(ActualizarFunebres.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesfunebresUri = data.getData();
                listaimagenes_funebres.add(imagenesfunebresUri);
            }else {
                for (int i = 0; i< 3; i++){
                    listaimagenes_funebres.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarFunebres.this,listaimagenes_funebres);
        gvImagenes_funebres.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarFunebres.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cargando,null);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        if(Mostrar.equals("Ver")){
            dialog.show();
        }
        if(Mostrar.equals("Ocultar")){
            dialog.hide();
        }
    }
    @SuppressLint("NewApi")
    private void whatsapp(Activity activity, String phone) {
        String formattedNumber = Util.format(phone);
        try{
            Intent sendIntent =new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT,"");
            sendIntent.putExtra("jid", formattedNumber +"@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            activity.startActivity(sendIntent);
        }
        catch(Exception e)
        {
            Toast.makeText(activity,"Instale whatsapp en su dispositivo o cambie a la version oficial que esta disponible en PlayStore",Toast.LENGTH_SHORT).show();
        }
    }
}
