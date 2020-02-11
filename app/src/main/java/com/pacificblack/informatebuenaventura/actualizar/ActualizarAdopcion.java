package com.pacificblack.informatebuenaventura.actualizar;

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

public class ActualizarAdopcion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextInputLayout titulo_actualizar_adopcion,
            descripcioncorta_actualizar_adopcion,
            descripcion1_actualizar_adopcion,
            descripcion2_actualizar_adopcion,
            buscar_actualizar_adopcion;

    Button actualizarimagenes;

    //TODO: Modificar y Eliminar

    ImageButton actualizar_editar_adopcion,actualizar_buscar_adopcion;

    RequestQueue requestbuscar;

    JsonObjectRequest jsonObjectRequestBuscar;

    HorizontalScrollView imagenes_adopcion_actualizar;

    ImageView imagen1_actualizar_adopcion,imagen2_actualizar_adopcion,
            imagen3_actualizar_adopcion,imagen4_actualizar_adopcion;

    //TODO: Modificar y Eliminar


    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
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

    //TODO: Aqui finaliza

    private InterstitialAd anuncioAdopcion_actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_adopcion);

        titulo_actualizar_adopcion = findViewById(R.id.actualizar_titulo_adopcion);
        descripcioncorta_actualizar_adopcion = findViewById(R.id.actualizar_descripcioncorta_adopcion);
        descripcion1_actualizar_adopcion = findViewById(R.id.actualizar_descripcion1_adopcion);
        descripcion2_actualizar_adopcion = findViewById(R.id.actualizar_descripcion2_adopcion);


        //TODO: Modificar y Eliminar


        imagenes_adopcion_actualizar = findViewById(R.id.imagenes_actualizar_adopcion);
        imagen1_actualizar_adopcion = findViewById(R.id.imagen1_actualizar_adopcion);
        imagen2_actualizar_adopcion = findViewById(R.id.imagen2_actualizar_adopcion);
        imagen3_actualizar_adopcion = findViewById(R.id.imagen3_actualizar_adopcion);
        imagen4_actualizar_adopcion = findViewById(R.id.imagen4_actualizar_adopcion);


        buscar_actualizar_adopcion = findViewById(R.id.actualizar_id_adopcion);
        actualizar_editar_adopcion = findViewById(R.id.actualizar_editar_adopcion);
        actualizar_editar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()|
                        !validardescripcioncorta()|
                        ! validardescripcion1()|
                        ! validardescripcion2()|
                        ! validarid()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarAdopcion.this);
                    mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            imagenes_adopcion_actualizar.setVisibility(View.GONE);

                            if (listaimagenes_adopcion_actualizar.size() == 4){
                                Subirimagen_adopcion_update();
                            }

                        }
                    }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarActualizarSinImagen_adopcion();

                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();


                    return; }

            }
        });
        actualizar_buscar_adopcion = findViewById(R.id.actualizar_buscar_adopcion);


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                actualizarimagenes.setText("Actualizar Imagenes");

                if (!validarid()){return;}

                cargarBusqueda_adopcion();
            }
        });



        //TODO: Modificar y Eliminar

        //TODO: Anuncios

        anuncioAdopcion_actualizar = new InterstitialAd(this);
        anuncioAdopcion_actualizar.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioAdopcion_actualizar.loadAd(new AdRequest.Builder().build());
        //TODO: Anuncios




        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        actualizar_gvImagenes_adopcion = findViewById(R.id.actualizar_grid_adopcion);
        actualizarimagenes = findViewById(R.id.actualizar_imagenes_adopcion);
        actualizarimagenes.setOnClickListener(new View.OnClickListener() {
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




//TODO: AQUI VA LO DE ACTUALIZAR Y ELIMINAR

    private boolean validarid(){
        String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_actualizar_adopcion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_actualizar_adopcion.setError(""+R.string.supera);
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
            titulo_actualizar_adopcion.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_adopcion.setError(""+R.string.supera);
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
            descripcioncorta_actualizar_adopcion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_actualizar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_actualizar_adopcion.setError(null);
            return true;
        }

    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_actualizar_adopcion.setError(""+R.string.error_descripcion1);
            return false;
        }
        else if(descripcion1input.length()>150){

            descripcion1_actualizar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_actualizar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){

        String descripcion2input = descripcion2_actualizar_adopcion.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_actualizar_adopcion.setError(""+R.string.error_descripcion2);
            return false;
        }
        else if(descripcion2input.length()>150){

            descripcion2_actualizar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_actualizar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){

        if (listaimagenes_adopcion_actualizar.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_adopcion_actualizar.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_adopcion_actualizar.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenes_adopcion_actualizar.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }

        else if(listaimagenes_adopcion_actualizar.size() == 4){
            return false;
        }

        else {
            return true;
        }
    }


    //TODO:-------------------------------------------------------------------------------------------------------------------------------------------------
    private void cargarBusqueda_adopcion() {

        String url_buscar_adopcion = "http://192.168.0.18/InformateDB/wsnJSONBuscarAdopcion.php?id_adopcion="+buscar_actualizar_adopcion.getEditText().getText().toString().trim();

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
    }


    private void cargarActualizarSinImagen_adopcion() {

        String url_adopcion = "http://192.168.0.18/InformateDB/wsnJSONActualizarSinImagenAdopcion.php?";


        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarAdopcion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion_actualizar.isLoaded()) {
                                        anuncioAdopcion_actualizar.show();
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

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_actualizar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");


                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_adopcion_actualizar = Volley.newRequestQueue(this);
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);

    }
    private void cargarActualizarConImagen_adopcion() {

        String url_adopcion = "http://192.168.0.18/InformateDB/wsnJSONActualizarConImagenAdopcion.php?";


        stringRequest_adopcion_actualizar= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarAdopcion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion_actualizar.isLoaded()) {
                                        anuncioAdopcion_actualizar.show();
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

                String idinput = buscar_actualizar_adopcion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_adopcion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_actualizar_adopcion.getEditText().getText().toString().trim();


                Log.i("Mostrar name------------------------------------------------------------------",nombre.get(0)+cadena.get(0));
                Log.i("Mostrar name------------------------------------------------------------------",nombre.get(1)+cadena.get(1));

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_adopcion",idinput);
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion",descripcion2input);
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
        request_adopcion_actualizar.add(stringRequest_adopcion_actualizar);

    }


    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    public void Subirimagen_adopcion_update(){

        listaBase64_adopcion_actualizar.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_adopcion_actualizar.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_adopcion_actualizar.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                //TODO: aqui se debe modificar para que la imagen guarde good

                nombre.add( "imagen_adopcion"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarActualizarConImagen_adopcion();

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
                    Toast.makeText(ActualizarAdopcion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesadopcionUri = data.getData();
                listaimagenes_adopcion_actualizar.add(imagenesadopcionUri);
            }else {
                for (int i = 0; i< 4; i++){
                    listaimagenes_adopcion_actualizar.add(clipData.getItemAt(i).getUri());
                }
            }

        }

        baseAdapter_actualizar = new GridViewAdapter(ActualizarAdopcion.this,listaimagenes_adopcion_actualizar);
        actualizar_gvImagenes_adopcion.setAdapter(baseAdapter_actualizar);



    }





}
