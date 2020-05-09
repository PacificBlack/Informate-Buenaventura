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
import android.widget.ProgressBar;
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
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;
//Todo: Clase completamente lista.

public class ActualizarAdopcion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextInputLayout titulo_actualizar_adopcion, descripcioncorta_actualizar_adopcion, descripcion1_actualizar_adopcion, descripcion2_actualizar_adopcion, buscar_actualizar_adopcion;
    Button actualizarimagenes,actualizar_editar_adopcion;
    ImageButton actualizar_buscar_adopcion;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_adopcion_actualizar;
    ImageView imagen1_actualizar_adopcion,imagen2_actualizar_adopcion,imagen3_actualizar_adopcion,imagen4_actualizar_adopcion;
    GridView actualizar_gvImagenes_adopcion;
    Uri imagenesadopcionUri;
    List<Uri> listaimagenes_adopcion_actualizar =  new ArrayList<>();
    List<String> listaBase64_adopcion_actualizar = new ArrayList<>();
    GridViewAdapter baseAdapter_actualizar;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_adopcion_actualizar;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    private InterstitialAd anuncioAdopcion_actualizar;
    Toolbar barra_adopcion;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(ActualizarAdopcion.this);

    ProgressBar cargandopublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_adopcion);

        whatsapp = findViewById(R.id.whatsapp_actualizar_adopcion);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarAdopcion.this,Whatsapp);
            }
        });
        barra_adopcion = findViewById(R.id.toolbar_actualizar_adopcion);
        cargandopublicar = findViewById(R.id.CargandoActualizar_adopcion);
        barra_adopcion.setTitle("Publicar Adopción");
        cargandopublicar.setVisibility(View.GONE);
        titulo_actualizar_adopcion = findViewById(R.id.actualizar_titulo_adopcion);
        descripcioncorta_actualizar_adopcion = findViewById(R.id.actualizar_descripcioncorta_adopcion);
        descripcion1_actualizar_adopcion = findViewById(R.id.actualizar_descripcion1_adopcion);
        descripcion2_actualizar_adopcion = findViewById(R.id.actualizar_descripcion2_adopcion);
        imagenes_adopcion_actualizar = findViewById(R.id.imagenes_actualizar_adopcion);
        imagen1_actualizar_adopcion = findViewById(R.id.imagen1_actualizar_adopcion);
        imagen2_actualizar_adopcion = findViewById(R.id.imagen2_actualizar_adopcion);
        imagen3_actualizar_adopcion = findViewById(R.id.imagen3_actualizar_adopcion);
        imagen4_actualizar_adopcion = findViewById(R.id.imagen4_actualizar_adopcion);
        buscar_actualizar_adopcion = findViewById(R.id.actualizar_id_adopcion);
        actualizar_editar_adopcion = findViewById(R.id.actualizar_editar_adopcion);
        actualizar_buscar_adopcion = findViewById(R.id.actualizar_buscar_adopcion);

        actualizar_editar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()| !validardescripcioncorta()| ! validardescripcion1()| ! validarid()){return;}

                if (!validarfotoupdate()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarAdopcion.this);
                    mensaje.setMessage(aviso_actualizar).setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imagenes_adopcion_actualizar.setVisibility(View.GONE);
                                Subirimagen_adopcion_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_adopcion();
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
        actualizar_buscar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_adopcion();
                cargandoDialog.Mostrar();
            }
        });

        anuncioAdopcion_actualizar = new InterstitialAd(this);
        anuncioAdopcion_actualizar.setAdUnitId(AnuncioActualizar);
        anuncioAdopcion_actualizar.loadAd(new AdRequest.Builder().build());

        actualizar_gvImagenes_adopcion = findViewById(R.id.actualizar_grid_adopcion);
        actualizarimagenes = findViewById(R.id.actualizar_imagenes_adopcion);
        actualizarimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisos,PERMISSON_CODE);
                    }else { seleccionarimagen(); }
                }else{ seleccionarimagen(); }
            }
        });
   }
    private boolean validarid(){
        String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_adopcion.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            buscar_actualizar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_actualizar_adopcion.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_adopcion.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){ descripcion1_actualizar_adopcion.setError(descripcio1_vacio); return false; }
        else if(descripcion1input.length()>850){ descripcion1_actualizar_adopcion.setError(texto_superado); return false; }
        else { descripcion1_actualizar_adopcion.setError(null); return true; }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_adopcion_actualizar.size() == 0){ Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show(); return false; }
        else { return true; }
    }
    public void Subirimagen_adopcion_update(){
        listaBase64_adopcion_actualizar.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_adopcion_actualizar.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_adopcion_actualizar.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_adopcion"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){ cargarActualizarConImagen_adopcion_uno(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 2){ cargarActualizarConImagen_adopcion_dos(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 3){ cargarActualizarConImagen_adopcion_tres(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 4){ cargarActualizarConImagen_adopcion(); cargandoDialog.Mostrar(); }
        if (nombre.size()>4){ Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show(); }
    }

    private void cargarBusqueda_adopcion() {
        String url_buscar_adopcion = DireccionServidor+"wsnJSONBuscarAdopcion.php?id_adopcion="+buscar_actualizar_adopcion.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        cargandoDialog.Ocultar();
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

        titulo_actualizar_adopcion.getEditText().setText(adopcion.getTitulo_row_adopcion());
        descripcioncorta_actualizar_adopcion.getEditText().setText(adopcion.getDescripcion_row_adopcion());
        descripcion1_actualizar_adopcion.getEditText().setText(adopcion.getDescripcion1_adopcion());
        descripcion2_actualizar_adopcion.getEditText().setText(adopcion.getDescripcion2_adopcion());

        imagenes_adopcion_actualizar.setVisibility(View.VISIBLE);

        Picasso.get().load(adopcion.getImagen1_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_adopcion);

        Picasso.get().load(adopcion.getImagen2_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_adopcion);

        Picasso.get().load(adopcion.getImagen3_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_adopcion);

        Picasso.get().load(adopcion.getImagen4_adopcion())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_actualizar_adopcion);

        cargandoDialog.Ocultar();

    }


    private void cargarActualizarSinImagen_adopcion() {

        String url_adopcion = DireccionServidor+"wsnJSONActualizarSinImagenAdopcion.php?";
        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarAdopcion.this);
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
                            if (anuncioAdopcion_actualizar.isLoaded()) {
                                anuncioAdopcion_actualizar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        stringRequest_adopcion_actualizar.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);
    }
    private void cargarActualizarConImagen_adopcion_uno() {

        String url_adopcion = DireccionServidor+"wsnJSONActualizarConImagenAdopcion.php?";

        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarAdopcion.this);
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
                            if (anuncioAdopcion_actualizar.isLoaded()) {
                                anuncioAdopcion_actualizar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("imagen_adopcion0",cadena.get(0));
                parametros.put("imagen_adopcion1","Vacio");
                parametros.put("imagen_adopcion2","Vacio");
                parametros.put("imagen_adopcion3","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                Log.i("Parametros", String.valueOf(parametros));
                return parametros;
            }
        };
        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        stringRequest_adopcion_actualizar.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);
    }
    private void cargarActualizarConImagen_adopcion_dos() {

        String url_adopcion = DireccionServidor+"wsnJSONActualizarConImagenAdopcion.php?";

        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarAdopcion.this);
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
                            if (anuncioAdopcion_actualizar.isLoaded()) {
                                anuncioAdopcion_actualizar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("imagen_adopcion0",cadena.get(0));
                parametros.put("imagen_adopcion1",cadena.get(1));
                parametros.put("imagen_adopcion2","Vacio");
                parametros.put("imagen_adopcion3","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        stringRequest_adopcion_actualizar.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);
    }
    private void cargarActualizarConImagen_adopcion_tres() {

        String url_adopcion = DireccionServidor+"wsnJSONActualizarConImagenAdopcion.php?";

        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarAdopcion.this);
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
                            if (anuncioAdopcion_actualizar.isLoaded()) {
                                anuncioAdopcion_actualizar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("imagen_adopcion0",cadena.get(0));
                parametros.put("imagen_adopcion1",cadena.get(1));
                parametros.put("imagen_adopcion2",cadena.get(2));
                parametros.put("imagen_adopcion3","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                Log.i("Parametros", String.valueOf(parametros));
                return parametros;
            }
        };
        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        stringRequest_adopcion_actualizar.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);
    }
    private void cargarActualizarConImagen_adopcion() {

        String url_adopcion = DireccionServidor+"wsnJSONActualizarConImagenAdopcion.php?";

        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarAdopcion.this);
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
                            if (anuncioAdopcion_actualizar.isLoaded()) {
                                anuncioAdopcion_actualizar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("imagen_adopcion0",cadena.get(0));
                parametros.put("imagen_adopcion1",cadena.get(1));
                parametros.put("imagen_adopcion2",cadena.get(2));
                parametros.put("imagen_adopcion3",cadena.get(3));
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        stringRequest_adopcion_actualizar.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);
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
                    Toast.makeText(ActualizarAdopcion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesadopcionUri = data.getData();
                listaimagenes_adopcion_actualizar.add(imagenesadopcionUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_adopcion_actualizar.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter_actualizar = new GridViewAdapter(ActualizarAdopcion.this,listaimagenes_adopcion_actualizar);
        actualizar_gvImagenes_adopcion.setAdapter(baseAdapter_actualizar);
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
