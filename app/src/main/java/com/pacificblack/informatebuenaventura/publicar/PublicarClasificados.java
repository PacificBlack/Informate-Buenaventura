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
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
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

public class PublicarClasificados extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{


    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
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

    //TODO: Aqui finaliza


    //TODO: Modificar y Eliminar
    ImageButton publicar_editar_clasificados,
            publicar_eliminar_clasificados,
            publicar_buscar_clasificados;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_clasificados;
    ImageView imagen1_actualizar_clasificados,imagen2_actualizar_clasificados,
            imagen3_actualizar_clasificados,imagen4_actualizar_clasificados;


    //TODO: Modificar y Eliminar


    TextInputLayout titulo_publicar_clasificados,
            descripcioncorta_publicar_clasificados,video_clasificados,
            descripcion1_publicar_clasificados,
            descripcion2_publicar_clasificados,
            buscar_publicar_clasificados;

    Button publicarfinal_clasificados,subirimagenes;

    private InterstitialAd anuncioClasificados;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_clasificados);



        titulo_publicar_clasificados = findViewById(R.id.publicar_titulo_clasificados);
        descripcioncorta_publicar_clasificados = findViewById(R.id.publicar_descripcioncorta_clasificados);
        video_clasificados = findViewById(R.id.publicar_video_clasificados);
        descripcion1_publicar_clasificados = findViewById(R.id.publicar_descripcion1_clasificados);
        descripcion2_publicar_clasificados = findViewById(R.id.publicar_descripcion2_clasificados);

        publicarfinal_clasificados = findViewById(R.id.publicar_final_clasificados);

        publicarfinal_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validardescripcion2() | !validarfoto() | !validarvideo()){
                    return;
                }

                Subirimagen_clasificados();
            }
        });


        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_clasificados = findViewById(R.id.grid_clasificados);
        subirimagenes = findViewById(R.id.subir_imagenes_clasificados);
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




        //TODO: Modificar y Eliminar


        imagenes_clasificados = findViewById(R.id.imagenes_actualizar_clasificados);
        imagen1_actualizar_clasificados = findViewById(R.id.imagen1_actualizar_clasificados);
        imagen2_actualizar_clasificados = findViewById(R.id.imagen2_actualizar_clasificados);
        imagen3_actualizar_clasificados = findViewById(R.id.imagen3_actualizar_clasificados);
        imagen4_actualizar_clasificados = findViewById(R.id.imagen4_actualizar_clasificados);



        buscar_publicar_clasificados = findViewById(R.id.publicar_id_clasificados);
        publicar_editar_clasificados = findViewById(R.id.publicar_editar_clasificados);
        publicar_editar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()|
                        !validardescripcioncorta()|
                        ! validardescripcion1()|
                        ! validardescripcion2()|
                        ! validarid()|
                        ! validarvideo()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarClasificados.this);
                    mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            imagenes_clasificados.setVisibility(View.GONE);

                            if (listaimagenesclasificados.size() == 4){
                                Subirimagen_clasificados_update();
                            }

                        }
                    }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarActualizarSinImagen_clasificados();

                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();


                    return; }

            }
        });
        publicar_eliminar_clasificados = findViewById(R.id.publicar_eliminar_clasificados);
        publicar_eliminar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarClasificados.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cargarEliminar_clasificados();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });
        publicar_buscar_clasificados = findViewById(R.id.publicar_buscar_clasificados);


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        publicar_buscar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subirimagenes.setText("Actualizar Imagenes");

                if (!validarid()){return;}

                cargarBusqueda_clasificados();
                publicarfinal_clasificados.setEnabled(false);
            }
        });



        //TODO: Modificar y Eliminar

        //TODO: Anuncios

        anuncioClasificados = new InterstitialAd(this);
        anuncioClasificados.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioClasificados.loadAd(new AdRequest.Builder().build());
        //TODO: Anuncios


    }

    private boolean validarid(){
        String idinput = buscar_publicar_clasificados.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_publicar_clasificados.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_publicar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_publicar_clasificados.setError(null);
            return true;
        }
    }


    private boolean validartitulo(){
        String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_clasificados.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){

        String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_clasificados.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_clasificados.setError(null);
            return true;
        }

    }
    private boolean validarvideo(){

        String videoinput = video_clasificados.getEditText().getText().toString().trim();

        if (videoinput.isEmpty()){
            video_clasificados.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(videoinput.length()>150){

            video_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            video_clasificados.setError(null);
            return true;
        }

    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_publicar_clasificados.setError(""+R.string.error_descripcion1);
            return false;
        }
        else if(descripcion1input.length()>150){

            descripcion1_publicar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_publicar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){

        String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_publicar_clasificados.setError(""+R.string.error_descripcion2);
            return false;
        }
        else if(descripcion2input.length()>150){

            descripcion2_publicar_clasificados.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_publicar_clasificados.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){

        if (listaimagenesclasificados.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 4 imagenes para la publicacion (Puede subir la misma 4 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenesclasificados.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 4 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenesclasificados.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenesclasificados.size()+"imagenes, pero deben ser 4",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

    }
    private boolean validarfotoupdate(){

        if (listaimagenesclasificados.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenesclasificados.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenesclasificados.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenesclasificados.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }

        else if(listaimagenesclasificados.size() == 4){
            return false;
        }

        else {
            return true;
        }
    }


    //TODO:-------------------------------------------------------------------------------------------------------------------------------------------------
    private void cargarBusqueda_clasificados() {

        String url_buscar_adopcion = "http://192.168.0.18/InformateDB/wsnJSONBuscarClasificados.php?id_clasificados="+buscar_publicar_clasificados.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());

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

        titulo_publicar_clasificados.getEditText().setText(clasificados.getTitulo_row_clasificados());
        descripcioncorta_publicar_clasificados.getEditText().setText(clasificados.getDescripcion_row_clasificados());
        video_clasificados.getEditText().setText(clasificados.getVideo_clasificados());
        descripcion1_publicar_clasificados.getEditText().setText(clasificados.getDescripcion1_clasificados());
        descripcion2_publicar_clasificados.getEditText().setText(clasificados.getDescripcion2_clasificados());


        imagenes_clasificados.setVisibility(View.VISIBLE);

        Picasso.get().load(clasificados.getImagen1_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_clasificados);

        Picasso.get().load(clasificados.getImagen2_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_clasificados);

        Picasso.get().load(clasificados.getImagen3_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_clasificados);

        Picasso.get().load(clasificados.getImagen4_clasificados())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen4_actualizar_clasificados);
    }


    private void cargarActualizarSinImagen_clasificados() {

        String url_clasificados = "http://192.168.0.18/InformateDB/wsnJSONActualizarSinImagenClasificados.php?";


        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarClasificados.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioClasificados.isLoaded()) {
                                        anuncioClasificados.show();
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

                String idinput = buscar_publicar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");



                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarActualizarConImagen_clasificados() {

        String url_clasificados = "http://192.168.0.18/InformateDB/wsnJSONActualizarConImagenClasificados.php?";


        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarClasificados.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioClasificados.isLoaded()) {
                                        anuncioClasificados.show();
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

                String idinput = buscar_publicar_clasificados.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }


                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    private void cargarEliminar_clasificados() {

        String url_clasificados = "http://192.168.0.18/InformateDB/wsnJSONEliminar.php?";


        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarClasificados.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioClasificados.isLoaded()) {
                                        anuncioClasificados.show();
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

                String idinput = buscar_publicar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_clasificados",idinput);
                parametros.put("publicacion","Clasificados");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        request_clasificados_actualizar.add(stringRequestclasificados);

    }
    public void Subirimagen_clasificados_update(){


        listaBase64clasificados.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenesclasificados.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenesclasificados.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                //TODO: aqui se debe modificar para que la imagen guarde good

                nombre.add( "imagen_clasificados"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarActualizarConImagen_clasificados();

    }




    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_clasificados() {

        String url_clasificados = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
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

                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }

                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("vistas_clasificados","0");
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_clasificados = Volley.newRequestQueue(this);
        request_clasificados.add(stringRequestclasificados);

    }
    public void Subirimagen_clasificados(){


        listaBase64clasificados.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenesclasificados.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenesclasificados.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_clasificados"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_clasificados();

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
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 4 imagenes"),IMAGE_PICK_CODE);

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
                    Toast.makeText(PublicarClasificados.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesclasificadosUri = data.getData();
                listaimagenesclasificados.add(imagenesclasificadosUri);
            }else {
                for (int i = 0; i< 4; i++){
                    listaimagenesclasificados.add(clipData.getItemAt(i).getUri());
                }
            }




        }

        baseAdapter = new GridViewAdapter(PublicarClasificados.this,listaimagenesclasificados);
        gvImagenes_clasificados.setAdapter(baseAdapter);



    }


}
