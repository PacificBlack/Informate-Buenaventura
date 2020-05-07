package com.pacificblack.informatebuenaventura.actualizar;
//Todo: Clase completamente lista.

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
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;
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
import static com.pacificblack.informatebuenaventura.texto.Avisos.cantidad_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.contacto_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.precio_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.ubicacion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;


public class ActualizarArticulo extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener  {

    GridView gvImagenes_comprayventa_actualizar;
    Uri imagenescomprayventaUri;
    List<Uri> listaimagenes_comprayventa =  new ArrayList<>();
    List<String> listaBase64_comprayventa = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_comprayventa;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    TextInputLayout titulo_actualizar_comprayventa, descripcioncorta_actualizar_comprayventa, descripcion_actualizar_comprayventa, descripcionextra_actualizar_comprayventa, precio_actualizar_comprayventa, ubicacion_actualizar_comprayventa, cantidad_actualizar_comprayventa, contacto_actualizar_comprayventa, buscar_actualizar_comprayventa;
    Button actualizar_editar_comprayventa,subirimagenes;
    ImageButton  actualizar_buscar_comprayventa;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_comprayventa,imagen2_actualizar_comprayventa, imagen3_actualizar_comprayventa;
    private InterstitialAd anuncioAdopcion_actualizar;
    Toolbar barra_articulo;
    ImageView whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_articulo);

        whatsapp = findViewById(R.id.whatsapp_actualizar_articulo);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarArticulo.this,Whatsapp);
            }
        });
        barra_articulo = findViewById(R.id.toolbar_actualizar_articulo);
        barra_articulo.setTitle("Actualizar Articulo");
        titulo_actualizar_comprayventa = findViewById(R.id.actualizar_titulo_comprayventa);
        descripcioncorta_actualizar_comprayventa = findViewById(R.id.actualizar_descripcioncorta_comprayventa);
        descripcion_actualizar_comprayventa = findViewById(R.id.actualizar_descripcion_comprayventa);
        descripcionextra_actualizar_comprayventa = findViewById(R.id.actualizar_descripcionextra_comprayventa);
        precio_actualizar_comprayventa = findViewById(R.id.actualizar_precio_comprayventa);
        ubicacion_actualizar_comprayventa = findViewById(R.id.actualizar_ubicacion_comprayventa);
        cantidad_actualizar_comprayventa = findViewById(R.id.actualizar_cantidad_comprayventa);
        contacto_actualizar_comprayventa = findViewById(R.id.actualizar_contacto_comprayventa);
        gvImagenes_comprayventa_actualizar = findViewById(R.id.grid_comprayventa_actualizar);
        subirimagenes = findViewById(R.id.actualizar_imagenes_comprayventa);
        imagen1_actualizar_comprayventa = findViewById(R.id.imagen1_actualizar_comprayventa);
        imagen2_actualizar_comprayventa = findViewById(R.id.imagen2_actualizar_comprayventa);
        imagen3_actualizar_comprayventa = findViewById(R.id.imagen3_actualizar_comprayventa);
        buscar_actualizar_comprayventa = findViewById(R.id.actualizar_id_comprayventa);
        actualizar_editar_comprayventa = findViewById(R.id.actualizar_editar_comprayventa);
        actualizar_buscar_comprayventa = findViewById(R.id.actualizar_buscar_comprayventa);


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

        actualizar_editar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()| !validartitulo()| !validardescripcioncorta()| !validardescripcion()| !validarprecio()| !validarubicacion()| !validarcantidad()| !validarcontacto()){return;}
                if (!validarfotoupdate()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarArticulo.this);
                    mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_comprayventa_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_comprayventa();
                            CargandoSubida("Ver");
                        }
                    });
                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();

                    return; }

            }
        });
        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_comprayventa();
                CargandoSubida("Ver");
            }
        });
        anuncioAdopcion_actualizar = new InterstitialAd(this);
        anuncioAdopcion_actualizar.setAdUnitId(AnuncioActualizar);
        anuncioAdopcion_actualizar.loadAd(new AdRequest.Builder().build());

    }
    private boolean validarid(){
        String idinput = buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_comprayventa.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            buscar_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_actualizar_comprayventa.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_comprayventa.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcion(){
        String descripcioninput = descripcion_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (descripcioninput.isEmpty()){
            descripcion_actualizar_comprayventa.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcioninput.length()>850){
            descripcion_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            descripcion_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarprecio(){
        String precioinput = precio_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (precioinput.isEmpty()){
            precio_actualizar_comprayventa.setError(precio_vacio);
            return false;
        }
        else if(precioinput.length()>400){
            precio_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            precio_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarubicacion(){
        String ubicacioninput = ubicacion_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (ubicacioninput.isEmpty()){
            ubicacion_actualizar_comprayventa.setError(ubicacion_vacio);
            return false;
        }
        else if(ubicacioninput.length()>150){
            ubicacion_actualizar_comprayventa.setError(ubicacion_vacio);
            return false;
        }
        else {
            ubicacion_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcantidad(){
        String cantidadinput = cantidad_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (cantidadinput.isEmpty()){
            cantidad_actualizar_comprayventa.setError(cantidad_vacio);
            return false;
        }
        else if(cantidadinput.length()>10){
            cantidad_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            cantidad_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcontacto(){
        String contactoinput = contacto_actualizar_comprayventa.getEditText().getText().toString().trim();
        if (contactoinput.isEmpty()){
            contacto_actualizar_comprayventa.setError(contacto_vacio);
            return false;
        }
        else if(contactoinput.length()>150){
            contacto_actualizar_comprayventa.setError(texto_superado);
            return false;
        }
        else {
            contacto_actualizar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_comprayventa.size() == 0){ Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show(); return false; }
        else { return true; }
    }
    private void cargarBusqueda_comprayventa() {
        String url_buscar_comprayventa = DireccionServidor+"wsnJSONBuscarComprayVenta.php?id_comprayventa="+buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_comprayventa,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        CargandoSubida("Ocultar");
    }
    @Override
    public void onResponse(JSONObject response) {

        ComprayVenta comprayVenta = new ComprayVenta();

        JSONArray json = response.optJSONArray("comprayventa");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            comprayVenta.setId_comprayventa(jsonObject.getInt("id_comprayventa"));
            comprayVenta.setImagen1_comprayventa(jsonObject.getString("imagen1_comprayventa"));
            comprayVenta.setImagen2_comprayventa(jsonObject.getString("imagen2_comprayventa"));
            comprayVenta.setImagen3̣̣_comprayventa(jsonObject.getString("imagen3_comprayventa"));
            comprayVenta.setTitulo_row_comprayventa(jsonObject.getString("titulo_comprayventa"));
            comprayVenta.setDescripcion_row_comprayventa(jsonObject.getString("descripcionrow_comprayventa"));
            comprayVenta.setDescripcion_comprayventa(jsonObject.getString("descripcion_comprayventa"));
            comprayVenta.setFechapublicacion_row_comprayventa(jsonObject.getString("fechapublicacion_comprayventa"));
            comprayVenta.setPrecio_row_comprayventa(jsonObject.getString("precio_comprayventa"));
            comprayVenta.setContacto_comprayventa(jsonObject.getString("contacto_comprayventa"));
            comprayVenta.setUbicacion_comprayventa(jsonObject.getString("ubicacion_comprayventa"));
            comprayVenta.setDescripcionextra_comprayventa(jsonObject.getString("descripcionextra_comprayventa"));
            comprayVenta.setCantidad_comprayventa(jsonObject.getInt("cantidad_comprayventa"));
            comprayVenta.setVista_comprayventa(jsonObject.getInt("vistas_comprayventa"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_actualizar_comprayventa.getEditText().setText(comprayVenta.getTitulo_row_comprayventa());
        descripcioncorta_actualizar_comprayventa.getEditText().setText(comprayVenta.getDescripcion_row_comprayventa());
        descripcion_actualizar_comprayventa.getEditText().setText(comprayVenta.getDescripcion_comprayventa());
        descripcionextra_actualizar_comprayventa.getEditText().setText(comprayVenta.getDescripcionextra_comprayventa());
        precio_actualizar_comprayventa.getEditText().setText(comprayVenta.getPrecio_row_comprayventa());
        ubicacion_actualizar_comprayventa.getEditText().setText(comprayVenta.getUbicacion_comprayventa());
        cantidad_actualizar_comprayventa.getEditText().setText(String.valueOf(comprayVenta.getCantidad_comprayventa()));
        contacto_actualizar_comprayventa.getEditText().setText(comprayVenta.getContacto_comprayventa());

        Picasso.get().load(comprayVenta.getImagen1_comprayventa())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen2_comprayventa())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen3̣̣_comprayventa())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_comprayventa);

        CargandoSubida("Ocultar");

    }
    private void cargarActualizarSinImagen_comprayventa() {

        String url_comprayventa = DireccionServidor+"wsnJSONActualizarSinImagenArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarArticulo.this);
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
                    CargandoSubida("Ocultar");


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        CargandoSubida("Ocultar");

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_actualizar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_actualizar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa","Vacio");
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("vistas_comprayventa","0");
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","ComprayVenta");

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    private void cargarActualizarConImagen_comprayventa_uno() {

        String url_comprayventa = DireccionServidor+"wsnJSONActualizarConImagenArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarArticulo.this);
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
                    CargandoSubida("Ocultar");

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        CargandoSubida("Ocultar");

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_actualizar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_actualizar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa","Vacio");
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","ComprayVenta");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_comprayventa1","vacio");
                parametros.put("imagen_comprayventa2","vacio");

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    private void cargarActualizarConImagen_comprayventa_dos() {

        String url_comprayventa = DireccionServidor+"wsnJSONActualizarConImagenArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarArticulo.this);
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
                    CargandoSubida("Ocultar");

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        CargandoSubida("Ocultar");

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_actualizar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_actualizar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa","Vacio");
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","ComprayVenta");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_comprayventa2","vacio");

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    private void cargarActualizarConImagen_comprayventa() {

        String url_comprayventa = DireccionServidor+"wsnJSONActualizarConImagenArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarArticulo.this);
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
                    CargandoSubida("Ocultar");

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        CargandoSubida("Ocultar");

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_actualizar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_actualizar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_actualizar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_actualizar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_actualizar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa","Vacio");
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","ComprayVenta");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    public void Subirimagen_comprayventa_update(){

        listaBase64_comprayventa.clear();
        nombre.clear();
        cadena.clear();

        for (int i = 0; i < listaimagenes_comprayventa.size(); i++){

            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_comprayventa.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_comprayventa"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();

            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){
            cargarActualizarConImagen_comprayventa_uno();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 2){
            cargarActualizarConImagen_comprayventa_dos();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 3){
            cargarActualizarConImagen_comprayventa();
            CargandoSubida("Ver");
        }
        if (nombre.size()>3){ Toast.makeText(getApplicationContext(),imagen_maxima +"3",Toast.LENGTH_LONG).show();        }

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
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(ActualizarArticulo.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenescomprayventaUri = data.getData();
                listaimagenes_comprayventa.add(imagenescomprayventaUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_comprayventa.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarArticulo.this,listaimagenes_comprayventa);
        gvImagenes_comprayventa_actualizar.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarArticulo.this);
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
