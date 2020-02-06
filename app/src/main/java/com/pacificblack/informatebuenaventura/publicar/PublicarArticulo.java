package com.pacificblack.informatebuenaventura.publicar;

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

public class PublicarArticulo extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {



    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
    GridView gvImagenes_comprayventa;
    Uri imagenescomprayventaUri;
    List<Uri> listaimagenes_comprayventa =  new ArrayList<>();
    List<String> listaBase64_comprayventa = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_comprayventa;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    //TODO: Aqui finaliza

    TextInputLayout titulo_publicar_comprayventa,
            descripcioncorta_publicar_comprayventa,
            descripcion_publicar_comprayventa,
            descripcionextra_publicar_comprayventa,
            precio_publicar_comprayventa,
            ubicacion_publicar_comprayventa,
            cantidad_publicar_comprayventa,
            contacto_publicar_comprayventa,
            buscar_publicar_comprayventa;

    Button publicarfinal_comprayventa,subirimagenes;


    //TODO: Modificar y Eliminar
    ImageButton publicar_editar_comprayventa,
            publicar_eliminar_comprayventa,
            publicar_buscar_comprayventa;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_comprayventa;
    ImageView imagen1_actualizar_comprayventa,imagen2_actualizar_comprayventa,
            imagen3_actualizar_comprayventa;

    private InterstitialAd anuncioAdopcion;


    //TODO: Modificar y Eliminar



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_articulo);

        titulo_publicar_comprayventa = findViewById(R.id.publicar_titulo_comprayventa);
        descripcioncorta_publicar_comprayventa = findViewById(R.id.publicar_descripcioncorta_comprayventa);
        descripcion_publicar_comprayventa = findViewById(R.id.publicar_descripcion_comprayventa);
        descripcionextra_publicar_comprayventa = findViewById(R.id.publicar_descripcionextra_comprayventa);
        precio_publicar_comprayventa = findViewById(R.id.publicar_precio_comprayventa);
        ubicacion_publicar_comprayventa = findViewById(R.id.publicar_ubicacion_comprayventa);
        cantidad_publicar_comprayventa = findViewById(R.id.publicar_cantidad_comprayventa);
        contacto_publicar_comprayventa = findViewById(R.id.publicar_contacto_comprayventa);
        publicarfinal_comprayventa = findViewById(R.id.publicar_final_comprayventa);


        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_comprayventa = findViewById(R.id.grid_comprayventa);
        subirimagenes = findViewById(R.id.subir_imagenes_comprayventa);
        subirimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        //permiso denegado
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Mostrar emergente del menu
                        requestPermissions(permisos,PERMISSON_CODE);
                    }else {
                        //permiso ya obtenido
                        seleccionarimagen();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen();
                }
            }
        });

        //TODO: Aqui va todo lo del grid para mostrar en la pantalla


        publicarfinal_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        !validartitulo() | !validardescripcioncorta()| !validardescripcion()| !validardescripcionextra()|
                                !validarprecio()| !validarubicacion()| !validarcantidad()| ! validarcontacto() | !validarfoto()){

                    return;
                }

                Subirimagen_comprayventa();

            }
        });


        //TODO: Modificar y Eliminar


        imagenes_comprayventa = findViewById(R.id.imagenes_actualizar_comprayventa);
        imagen1_actualizar_comprayventa = findViewById(R.id.imagen1_actualizar_comprayventa);
        imagen2_actualizar_comprayventa = findViewById(R.id.imagen2_actualizar_comprayventa);
        imagen3_actualizar_comprayventa = findViewById(R.id.imagen3_actualizar_comprayventa);


        buscar_publicar_comprayventa = findViewById(R.id.publicar_id_comprayventa);
        publicar_editar_comprayventa = findViewById(R.id.publicar_editar_comprayventa);
        publicar_editar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()|
                !validartitulo()|
                !validardescripcioncorta()|
                !validardescripcion()|
                !validardescripcionextra()|
                !validarprecio()|
                !validarubicacion()|
                !validarcantidad()|
                !validarcontacto()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);
                    mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            imagenes_comprayventa.setVisibility(View.GONE);

                            if (listaimagenes_comprayventa.size() == 3){
                                Subirimagen_comprayventa_update();
                            }

                        }
                    }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarActualizarSinImagen_comprayventa();

                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();


                    return; }

            }
        });
        publicar_eliminar_comprayventa = findViewById(R.id.publicar_eliminar_comprayventa);
        publicar_eliminar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cargarEliminar_comprayventa();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });
        publicar_buscar_comprayventa = findViewById(R.id.publicar_buscar_comprayventa);


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        publicar_buscar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subirimagenes.setText("Actualizar Imagenes");

                if (!validarid()){return;}

                cargarBusqueda_comprayventa();
                publicarfinal_comprayventa.setEnabled(false);
            }
        });



        //TODO: Modificar y Eliminar

        //TODO: Anuncios

        anuncioAdopcion = new InterstitialAd(this);
        anuncioAdopcion.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioAdopcion.loadAd(new AdRequest.Builder().build());
        //TODO: Anuncios







    }


    private boolean validarid(){
        String idinput = buscar_publicar_comprayventa.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_publicar_comprayventa.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_publicar_comprayventa.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_comprayventa.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_comprayventa.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcion(){
        String descripcioninput = descripcion_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcioninput.isEmpty()){
            descripcion_publicar_comprayventa.setError(""+R.string.error_descripcion1);
            return false;
        }
        else if(descripcioninput.length()>740){

            descripcion_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcionextra(){
        String descripcionextrainput = descripcionextra_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcionextrainput.isEmpty()){
            descripcionextra_publicar_comprayventa.setError(""+R.string.error_descripcion2);
            return false;
        }
        else if(descripcionextrainput.length()>740){

            descripcionextra_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcionextra_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarprecio(){
        String precioinput = precio_publicar_comprayventa.getEditText().getText().toString().trim();

        if (precioinput.isEmpty()){
            precio_publicar_comprayventa.setError(""+R.string.error_precio);
            return false;
        }
        else if(precioinput.length()>400){

            precio_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            precio_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarubicacion(){

        String ubicacioninput = ubicacion_publicar_comprayventa.getEditText().getText().toString().trim();

        if (ubicacioninput.isEmpty()){
            ubicacion_publicar_comprayventa.setError(""+R.string.error_ubicacion);
            return false;
        }
        else if(ubicacioninput.length()>150){

            ubicacion_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            ubicacion_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcantidad(){

        String cantidadinput = cantidad_publicar_comprayventa.getEditText().getText().toString().trim();

        if (cantidadinput.isEmpty()){
            cantidad_publicar_comprayventa.setError(""+R.string.error_cantidad);
            return false;
        }
        else if(cantidadinput.length()>150){

            cantidad_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            cantidad_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcontacto(){
        String contactoinput = contacto_publicar_comprayventa.getEditText().getText().toString().trim();

        if (contactoinput.isEmpty()){
            contacto_publicar_comprayventa.setError(""+R.string.error_contacto);
            return false;
        }
        else if(contactoinput.length()>150){

            contacto_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            contacto_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){

        if (listaimagenes_comprayventa.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 3 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_comprayventa.size() > 3){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 3 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_comprayventa.size() < 3){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_comprayventa.size()+"imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

    }
    private boolean validarfotoupdate(){

        if (listaimagenes_comprayventa.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_comprayventa.size() > 3){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_comprayventa.size() < 3){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenes_comprayventa.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }

        else if(listaimagenes_comprayventa.size() == 3){
            return false;
        }

        else {
            return true;
        }
    }


    //TODO:-------------------------------------------------------------------------------------------------------------------------------------------------
    private void cargarBusqueda_comprayventa() {

        String url_buscar_comprayventa = "http://192.168.0.18/InformateDB/wsnJSONBuscarComprayVenta.php?id_comprayventa="+buscar_publicar_comprayventa.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_comprayventa,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());

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


        titulo_publicar_comprayventa.getEditText().setText(comprayVenta.getTitulo_row_comprayventa());
        descripcioncorta_publicar_comprayventa.getEditText().setText(comprayVenta.getDescripcion_row_comprayventa());
        descripcion_publicar_comprayventa.getEditText().setText(comprayVenta.getDescripcion_comprayventa());
        descripcionextra_publicar_comprayventa.getEditText().setText(comprayVenta.getDescripcionextra_comprayventa());
        precio_publicar_comprayventa.getEditText().setText(comprayVenta.getPrecio_row_comprayventa());
        ubicacion_publicar_comprayventa.getEditText().setText(comprayVenta.getUbicacion_comprayventa());
        cantidad_publicar_comprayventa.getEditText().setText(String.valueOf(comprayVenta.getCantidad_comprayventa()));
        contacto_publicar_comprayventa.getEditText().setText(comprayVenta.getContacto_comprayventa());


        imagenes_comprayventa.setVisibility(View.VISIBLE);

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

    }


    private void cargarActualizarSinImagen_comprayventa() {

        String url_comprayventa = "http://192.168.0.18/InformateDB/wsnJSONActualizarSinImagenArticulo.php?";


        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion.isLoaded()) {
                                        anuncioAdopcion.show();
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
                    Toast.makeText(getApplicationContext(),"Lo siento papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Error",response);


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Tenemos un error"+error,Toast.LENGTH_LONG).show();

                        Log.i("EL ERROR ES : ",error.toString());


                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_publicar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcionextrainput = descripcionextra_publicar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_publicar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_publicar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_publicar_comprayventa.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa",descripcionextrainput);
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
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    private void cargarActualizarConImagen_comprayventa() {

        String url_comprayventa = "http://192.168.0.18/InformateDB/wsnJSONActualizarConImagenArticulo.php?";


        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion.isLoaded()) {
                                        anuncioAdopcion.show();
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
                    Toast.makeText(getApplicationContext(),"Lo siento papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Error",response);


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

                        Log.i("ERROR",error.toString());


                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_publicar_comprayventa.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcionextrainput = descripcionextra_publicar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_publicar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_publicar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_publicar_comprayventa.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_comprayventa",idinput);
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa",descripcionextrainput);
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","ComprayVenta");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    private void cargarEliminar_comprayventa() {

        String url_comprayventa = "http://192.168.0.18/InformateDB/wsnJSONEliminar.php?";


        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion.isLoaded()) {
                                        anuncioAdopcion.show();
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
                    Toast.makeText(getApplicationContext(),"Lo siento papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Error",response);


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

                        Log.i("ERROR",error.toString());


                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_publicar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_comprayventa",idinput);
                parametros.put("publicacion","ComprayVenta");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_comprayventa_actualizar = Volley.newRequestQueue(this);
        request_comprayventa_actualizar.add(stringRequest_comprayventa);

    }
    public void Subirimagen_comprayventa_update(){


        listaBase64_comprayventa.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_comprayventa.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_comprayventa.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_comprayventa"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }

        cargarActualizarConImagen_comprayventa();

    }




    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos





    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_comprayventa() {

        String url_comprayventa = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(getApplicationContext(),"Registro papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Funciona : ",response);

                }else {
                    Toast.makeText(getApplicationContext(),"Lo siento papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Error",response);


                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

                        Log.i("ERROR",error.toString());


                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcioninput = descripcion_publicar_comprayventa.getEditText().getText().toString().trim();
                String descripcionextrainput = descripcionextra_publicar_comprayventa.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_comprayventa.getEditText().getText().toString().trim();
                String ubicacioninput = ubicacion_publicar_comprayventa.getEditText().getText().toString().trim();
                String cantidadinput = cantidad_publicar_comprayventa.getEditText().getText().toString().trim();
                String contactoinput = contacto_publicar_comprayventa.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_comprayventa",tituloinput);
                parametros.put("descripcionrow_comprayventa",descripcioncortainput);
                parametros.put("descripcion1_comprayventa",descripcioninput);
                parametros.put("descripcion2_comprayventa",descripcionextrainput);
                parametros.put("precio_comprayventa",precioinput);
                parametros.put("vistas_comprayventa","0");
                parametros.put("ubicacion_comprayventa",ubicacioninput);
                parametros.put("cantidad_comprayventa",cantidadinput);
                parametros.put("contacto_comprayventa",contactoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Compra y Venta");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }


        Log.i("Lo que entra",parametros.toString());

                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        request_desaparicion.add(stringRequest_comprayventa);

    }
    public void Subirimagen_comprayventa(){


        listaBase64_comprayventa.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_comprayventa.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_comprayventa.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_comprayventa"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_comprayventa();

    }
    public String convertirUriEnBase64(Bitmap bmp){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,array);

        byte[] imagenByte = array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }
    public void seleccionarimagen() {

        //intent para seleccionar imagen
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
                    //Permiso autorizado
                    seleccionarimagen();

                }
                else{
                    //Permiso denegado
                    Toast.makeText(PublicarArticulo.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        ClipData clipData = data.getClipData();

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){


            if (clipData == null){
                imagenescomprayventaUri = data.getData();
                listaimagenes_comprayventa.add(imagenescomprayventaUri);
            }else {
                for (int i = 0; i< 3; i++){
                    listaimagenes_comprayventa.add(clipData.getItemAt(i).getUri());
                }
            }




        }

        baseAdapter = new GridViewAdapter(PublicarArticulo.this,listaimagenes_comprayventa);
        gvImagenes_comprayventa.setAdapter(baseAdapter);



    }


}

