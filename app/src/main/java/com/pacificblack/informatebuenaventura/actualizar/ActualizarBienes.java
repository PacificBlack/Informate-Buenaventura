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
import static com.pacificblack.informatebuenaventura.texto.Avisos.precio_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarBienes extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextInputLayout titulo_actualizar_bienes, descripcioncorta_actualizar_bienes, descripcion1_actualizar_bienes, descripcion2_actualizar_bienes, precio_actualizar_bienes, buscar_actualizar_bienes;
    Button actualizar_editar_bienes,actualizarimagenes;
    ImageButton actualizar_buscar_bienes;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_bienes,imagen2_actualizar_bienes,imagen3_actualizar_bienes,imagen4_actualizar_bienes;
    private InterstitialAd anunciobienes;
    GridView gvImagenes_bienes;
    Uri imagenesbienesUri;
    List<Uri> listaimagenes_bienes =  new ArrayList<>();
    List<String> listaBase64_bienes = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_bienes;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    Toolbar barra_bienes;
    ImageView whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_bienes);

        whatsapp = findViewById(R.id.whatsapp_actualizar_bienes);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarBienes.this,Whatsapp);
            }
        });
        barra_bienes = findViewById(R.id.toolbar_actualizar_bienes);
        barra_bienes.setTitle("Actualizar Bienes");
        titulo_actualizar_bienes = findViewById(R.id.actualizar_titulo_bienes);
        descripcioncorta_actualizar_bienes = findViewById(R.id.actualizar_descripcioncorta_bienes);
        descripcion1_actualizar_bienes = findViewById(R.id.actualizar_descripcion1_bienes);
        descripcion2_actualizar_bienes = findViewById(R.id.actualizar_descripcion2_bienes);
        precio_actualizar_bienes = findViewById(R.id.actualizar_precio_bienes);
        imagen1_actualizar_bienes = findViewById(R.id.imagen1_actualizar_bienes);
        imagen2_actualizar_bienes = findViewById(R.id.imagen2_actualizar_bienes);
        imagen3_actualizar_bienes = findViewById(R.id.imagen3_actualizar_bienes);
        imagen4_actualizar_bienes = findViewById(R.id.imagen4_actualizar_bienes);
        buscar_actualizar_bienes = findViewById(R.id.actualizar_id_bienes);
        actualizar_editar_bienes = findViewById(R.id.actualizar_editar_bienes);
        actualizar_buscar_bienes = findViewById(R.id.actualizar_buscar_bienes);
        gvImagenes_bienes = findViewById(R.id.actualizar_grid_bienes);
        actualizarimagenes = findViewById(R.id.actualizar_imagenes_bienes);

        actualizar_editar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo()| !validardescripcioncorta()| ! validardescripcion1()| ! validarprecio()| ! validarid()){return;}
                if (!validarfotoupdate()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarBienes.this);
                    mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_bienes_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_bienes();
                            CargandoSubida("Ver");
                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();
                    return;
                }
            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_bienes();
                CargandoSubida("Ver");

            }
        });

        anunciobienes = new InterstitialAd(this);
        anunciobienes.setAdUnitId(AnuncioActualizar);
        anunciobienes.loadAd(new AdRequest.Builder().build());

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
    }
    private boolean validarid(){
        String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_bienes.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_bienes.setError(texto_superado);
            return false;
        }
        else {
            buscar_actualizar_bienes.setError(null);
            return true;
        }
    }
    private boolean validartitulo() {
        String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()) {
            titulo_actualizar_bienes.setError(titulo_vacio);
            return false;
        } else if (tituloinput.length() > 120) {
            titulo_actualizar_bienes.setError(texto_superado);
            return false;
        } else {
            titulo_actualizar_bienes.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta() {
        String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()) {
            descripcioncorta_actualizar_bienes.setError(descripcion_vacio);
            return false;
        } else if (descripcioncortainput.length() > 150) {
            descripcioncorta_actualizar_bienes.setError(texto_superado);
            return false;
        } else {
            descripcioncorta_actualizar_bienes.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1() {
        String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()) {
            descripcion1_actualizar_bienes.setError(descripcio1_vacio);
            return false;
        } else if (descripcion1input.length() > 850) {
            descripcion1_actualizar_bienes.setError(texto_superado);
            return false;
        } else {
            descripcion1_actualizar_bienes.setError(null);
            return true;
        }
    }
    private boolean validarprecio() {
        String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();
        if (precioinput.isEmpty()) {
            precio_actualizar_bienes.setError(precio_vacio);
            return false;
        } else if (precioinput.length() > 20) {
            precio_actualizar_bienes.setError(texto_superado);
            return false;
        } else {
            precio_actualizar_bienes.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_bienes.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void cargarBusqueda_bienes() {
        String url_buscar_bienes = DireccionServidor+"wsnJSONBuscarBienes.php?id_bienes="+buscar_actualizar_bienes.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);
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

        titulo_actualizar_bienes.getEditText().setText(bienes.getTitulo_row_bienes());
        descripcioncorta_actualizar_bienes.getEditText().setText(bienes.getDescripcion_row_bienes());
        descripcion1_actualizar_bienes.getEditText().setText(bienes.getDescripcion1_bienes());
        descripcion2_actualizar_bienes.getEditText().setText(bienes.getDescripcion2_bienes());
        precio_actualizar_bienes.getEditText().setText(String.valueOf(bienes.getPrecio_row_bienes()));


        Picasso.get().load(bienes.getImagen1_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_bienes);

        Picasso.get().load(bienes.getImagen2_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_bienes);

        Picasso.get().load(bienes.getImagen3_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_bienes);

        Picasso.get().load(bienes.getImagen4_bienes())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_actualizar_bienes);

        CargandoSubida("Ocultar");

    }
    public void Subirimagen_bienes_update(){
        listaBase64_bienes.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_bienes.size(); i++){

            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_bienes.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add("imagen_bienes"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){
            cargarActualizarConImagen_bienes_1();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 2){
            cargarActualizarConImagen_bienes_2();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 3){
            cargarActualizarConImagen_bienes_3();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 4){
            cargarActualizarConImagen_bienes();
            CargandoSubida("Ver");
        }
        if (nombre.size()>4){ Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show();        }

    }
    private void cargarActualizarSinImagen_bienes() {

        String url_bienes = DireccionServidor+"wsnJSONActualizarSinImagenBienes.php?";


        stringRequest_bienes= new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
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
                            if (anunciobienes.isLoaded()) { anunciobienes.show(); }
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

                String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes","Vacio");
                parametros.put("precio_bienes",precioinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");


                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_bienes.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarActualizarConImagen_bienes_1() {

        String url_bienes = DireccionServidor+"wsnJSONActualizarConImagenBienes.php?";

        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
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
                            if (anunciobienes.isLoaded()) { anunciobienes.show(); }
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


                String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes","Vacio");
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_bienes1","vacio");
                parametros.put("imagen_bienes2","vacio");
                parametros.put("imagen_bienes3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_bienes.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarActualizarConImagen_bienes_2() {

        String url_bienes = DireccionServidor+"wsnJSONActualizarConImagenBienes.php?";

        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
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
                            if (anunciobienes.isLoaded()) { anunciobienes.show(); }
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


                String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes","Vacio");
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_bienes2","vacio");
                parametros.put("imagen_bienes3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_bienes.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarActualizarConImagen_bienes_3() {

        String url_bienes = DireccionServidor+"wsnJSONActualizarConImagenBienes.php?";

        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
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
                            if (anunciobienes.isLoaded()) { anunciobienes.show(); }
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


                String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes","Vacio");
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put("imagen_bienes3","vacio");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_bienes.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarActualizarConImagen_bienes() {

        String url_bienes = DireccionServidor+"wsnJSONActualizarConImagenBienes.php?";

        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
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
                            if (anunciobienes.isLoaded()) { anunciobienes.show(); }
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


                String idinput = buscar_actualizar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes","Vacio");
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put(nombre.get(3),cadena.get(3));

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_bienes.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_bienes);
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
                    Toast.makeText(ActualizarBienes.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesbienesUri = data.getData();
                listaimagenes_bienes.add(imagenesbienesUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_bienes.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarBienes.this,listaimagenes_bienes);
        gvImagenes_bienes.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarBienes.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cargando,null);
        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        switch (Mostrar){
            case "Ver":
                dialog.show();
                Log.i("Mostrar se ve", Mostrar);
                break;

            case "Ocultar":
                dialog.dismiss();
                Log.i("Mostrar se oculta", Mostrar);
                break;
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
