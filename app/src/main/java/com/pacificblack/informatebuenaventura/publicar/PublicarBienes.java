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

public class PublicarBienes extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {


    TextInputLayout titulo_publicar_bienes,
            descripcioncorta_publicar_bienes,
            descripcion1_publicar_bienes,
            descripcion2_publicar_bienes,
            precio_publicar_bienes,
            buscar_publicar_bienes;


    Button publicarfinal_bienes,subirimagenes;


    //TODO: Modificar y Eliminar
    ImageButton publicar_editar_bienes,
            publicar_eliminar_bienes,
            publicar_buscar_bienes;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_bienes;
    ImageView imagen1_actualizar_bienes,imagen2_actualizar_bienes,
            imagen3_actualizar_bienes,imagen4_actualizar_bienes;


    //TODO: Modificar y Eliminar

    private InterstitialAd anunciobienes;



    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
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

    //TODO: Aqui finaliza



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_bienes);

        titulo_publicar_bienes = findViewById(R.id.publicar_titulo_bienes);
        descripcioncorta_publicar_bienes = findViewById(R.id.publicar_descripcioncorta_bienes);
        descripcion1_publicar_bienes = findViewById(R.id.publicar_descripcion1_bienes);
        descripcion2_publicar_bienes = findViewById(R.id.publicar_descripcion2_bienes);
        precio_publicar_bienes = findViewById(R.id.publicar_precio_bienes);


        publicarfinal_bienes = findViewById(R.id.publicar_final_bienes);

        publicarfinal_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validardescripcion2() | !validarprecio() | !validarfoto()){
                    return;
                }

                Subirimagen_bienes();


            }
        });




        //TODO: Modificar y Eliminar


        imagenes_bienes = findViewById(R.id.imagenes_actualizar_bienes);
        imagen1_actualizar_bienes = findViewById(R.id.imagen1_actualizar_bienes);
        imagen2_actualizar_bienes = findViewById(R.id.imagen2_actualizar_bienes);
        imagen3_actualizar_bienes = findViewById(R.id.imagen3_actualizar_bienes);
        imagen4_actualizar_bienes = findViewById(R.id.imagen4_actualizar_bienes);


        buscar_publicar_bienes = findViewById(R.id.publicar_id_bienes);
        publicar_editar_bienes = findViewById(R.id.publicar_editar_bienes);
        publicar_editar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()|
                        !validardescripcioncorta()|
                        ! validardescripcion1()|
                        ! validardescripcion2()|
                        ! validarid()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarBienes.this);
                    mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            imagenes_bienes.setVisibility(View.GONE);

                            if (listaimagenes_bienes.size() == 4){
                                Subirimagen_bienes_update();
                            }

                        }
                    }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarActualizarSinImagen_bienes();

                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();


                    return; }

            }
        });
        publicar_eliminar_bienes = findViewById(R.id.publicar_eliminar_bienes);
        publicar_eliminar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarBienes.this);
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
        publicar_buscar_bienes = findViewById(R.id.publicar_buscar_bienes);


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        publicar_buscar_bienes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subirimagenes.setText("Actualizar Imagenes");

                if (!validarid()){return;}

                cargarBusqueda_bienes();
                publicarfinal_bienes.setEnabled(false);
            }
        });



        //TODO: Modificar y Eliminar

        //TODO: Anuncios

        anunciobienes = new InterstitialAd(this);
        anunciobienes.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anunciobienes.loadAd(new AdRequest.Builder().build());
        //TODO: Anuncios




        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_bienes = findViewById(R.id.grid_bienes);
        subirimagenes = findViewById(R.id.subir_imagenes_bienes);
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


    }


    private boolean validarid(){
        String idinput = buscar_publicar_bienes.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_publicar_bienes.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_publicar_bienes.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_publicar_bienes.setError(null);
            return true;
        }
    }
    private boolean validartitulo() {
        String tituloinput = titulo_publicar_bienes.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()) {
            titulo_publicar_bienes.setError("" + R.string.error_titulo);
            return false;
        } else if (tituloinput.length() > 120) {

            titulo_publicar_bienes.setError("" + R.string.supera);
            return false;
        } else {
            titulo_publicar_bienes.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta() {

        String descripcioncortainput = descripcioncorta_publicar_bienes.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()) {
            descripcioncorta_publicar_bienes.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (descripcioncortainput.length() > 150) {

            descripcioncorta_publicar_bienes.setError("" + R.string.supera);
            return false;
        } else {
            descripcioncorta_publicar_bienes.setError(null);
            return true;
        }

    }
    private boolean validardescripcion1() {
        String descripcion1input = descripcion1_publicar_bienes.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()) {
            descripcion1_publicar_bienes.setError("" + R.string.error_descripcion1);
            return false;
        } else if (descripcion1input.length() > 150) {

            descripcion1_publicar_bienes.setError("" + R.string.supera);
            return false;
        } else {
            descripcion1_publicar_bienes.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2() {

        String descripcion2input = descripcion2_publicar_bienes.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()) {
            descripcion2_publicar_bienes.setError("" + R.string.error_descripcion2);
            return false;
        } else if (descripcion2input.length() > 150) {

            descripcion2_publicar_bienes.setError("" + R.string.supera);
            return false;
        } else {
            descripcion2_publicar_bienes.setError(null);
            return true;


        }
    }
    private boolean validarprecio() {

        String precioinput = precio_publicar_bienes.getEditText().getText().toString().trim();

        if (precioinput.isEmpty()) {
            precio_publicar_bienes.setError("" + R.string.error_descripcion2);
            return false;
        } else if (precioinput.length() > 20) {

            precio_publicar_bienes.setError("" + R.string.supera);
            return false;
        } else {
            precio_publicar_bienes.setError(null);
            return true;


        }
    }
    private boolean validarfoto(){

        if (listaimagenes_bienes.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 4 imagenes para la publicacion (Puede subir la misma 4 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_bienes.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 4 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_bienes.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_bienes.size()+"imagenes, pero deben ser 4",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

    }
    private boolean validarfotoupdate(){

        if (listaimagenes_bienes.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_bienes.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_bienes.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenes_bienes.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }

        else if(listaimagenes_bienes.size() == 4){
            return false;
        }

        else {
            return true;
        }
    }


    //TODO:-------------------------------------------------------------------------------------------------------------------------------------------------
    private void cargarBusqueda_bienes() {

        String url_buscar_bienes = "http://192.168.0.18/InformateDB/wsnJSONBuscarBienes.php?id_bienes="+buscar_publicar_bienes.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {


        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

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

        titulo_publicar_bienes.getEditText().setText(bienes.getTitulo_row_bienes());
        descripcioncorta_publicar_bienes.getEditText().setText(bienes.getDescripcion_row_bienes());
        descripcion1_publicar_bienes.getEditText().setText(bienes.getDescripcion1_bienes());
        descripcion2_publicar_bienes.getEditText().setText(bienes.getDescripcion2_bienes());
        precio_publicar_bienes.getEditText().setText(bienes.getPrecio_row_bienes());


        imagenes_bienes.setVisibility(View.VISIBLE);

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
    }


    private void cargarActualizarSinImagen_bienes() {

        String url_bienes = "http://192.168.0.18/InformateDB/wsnJSONActualizarSinImagenBienes.php?";


        stringRequest_bienes= new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarBienes.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciobienes.isLoaded()) {
                                        anunciobienes.show();
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

                String idinput = buscar_publicar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes",descripcion2input);
                parametros.put("precio_bienes",precioinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");


                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarActualizarConImagen_bienes() {

        String url_bienes = "http://192.168.0.18/InformateDB/wsnJSONActualizarConImagenBienes.php?";


        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarBienes.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciobienes.isLoaded()) {
                                        anunciobienes.show();
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


                String idinput = buscar_publicar_bienes.getEditText().getText().toString().trim();
                String tituloinput = titulo_publicar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_bienes.getEditText().getText().toString().trim();


                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_bienes",idinput);
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes",descripcion2input);
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");


                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }


                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        request_bienes_actualizar.add(stringRequest_bienes);

    }
    private void cargarEliminar_bienes() {

        String url_bienes = "http://192.168.0.18/InformateDB/wsnJSONEliminar.php?";


        stringRequest_bienes = new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarBienes.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciobienes.isLoaded()) {
                                        anunciobienes.show();
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

                String idinput = buscar_publicar_bienes.getEditText().getText().toString().trim();

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
    public void Subirimagen_bienes_update(){


        listaBase64_bienes.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_bienes.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_bienes.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                //TODO: aqui se debe modificar para que la imagen guarde good

                nombre.add( "imagen_bienes"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarActualizarConImagen_bienes();

    }






    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_bienes() {

        String url_bienes = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequest_bienes= new StringRequest(Request.Method.POST, url_bienes, new Response.Listener<String>() {
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

                String tituloinput = titulo_publicar_bienes.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_bienes.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_bienes.getEditText().getText().toString().trim();
                String precioinput = precio_publicar_bienes.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_bienes",tituloinput);
                parametros.put("descripcionrow_bienes",descripcioncortainput);
                parametros.put("descripcion1_bienes",descripcion1input);
                parametros.put("descripcion2_bienes",descripcion2input);
                parametros.put("precio_bienes",precioinput);
                parametros.put("vistas_bienes","0");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Bienes");



                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }




                return parametros;
            }
        };

        RequestQueue request_bienes = Volley.newRequestQueue(this);
        request_bienes.add(stringRequest_bienes);

    }
    public void Subirimagen_bienes(){


        listaBase64_bienes.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_bienes.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_bienes.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_bienes"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_bienes();

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
                    Toast.makeText(PublicarBienes.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesbienesUri = data.getData();
                listaimagenes_bienes.add(imagenesbienesUri);
            }else {
                for (int i = 0; i< 4; i++){
                    listaimagenes_bienes.add(clipData.getItemAt(i).getUri());
                }
            }




        }

        baseAdapter = new GridViewAdapter(PublicarBienes.this,listaimagenes_bienes);
        gvImagenes_bienes.setAdapter(baseAdapter);



    }



}