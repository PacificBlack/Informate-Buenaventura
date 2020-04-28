package com.pacificblack.informatebuenaventura.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

public class PublicarClasificados extends AppCompatActivity {
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
    TextInputLayout titulo_publicar_clasificados,descripcioncorta_publicar_clasificados,video_clasificados,descripcion1_publicar_clasificados,descripcion2_publicar_clasificados,buscar_publicar_clasificados;
    Button publicarfinal_clasificados,subirimagenes;
    private InterstitialAd anuncioClasificados;
    private ProgressDialog clasificados;



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
        gvImagenes_clasificados = findViewById(R.id.grid_clasificados);
        subirimagenes = findViewById(R.id.subir_imagenes_clasificados);
        publicarfinal_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validardescripcion2() | !validarfoto() | !validarvideo()){
                    return;
                }
                Subirimagen_clasificados();
            }
        });

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

        anuncioClasificados = new InterstitialAd(this);
        anuncioClasificados.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioClasificados.loadAd(new AdRequest.Builder().build());

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

        else if (listaimagenesclasificados.size() > 1){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 4 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else {
            return true;}

    }

    public void Subirimagen_clasificados(){

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
            cargarWebService_clasificados_uno();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 2){
            cargarWebService_clasificados_dos();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 3){
            cargarWebService_clasificados_tres();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 4){
            cargarWebService_clasificados();
            CargandoSubida("Ver");
        }
        if (nombre.size()>4){
            Toast.makeText(getApplicationContext(),"Solo se pueden subir 4 imagenes, por favor borre una",Toast.LENGTH_LONG).show();
        }
    }

    private void cargarWebService_clasificados_uno() {

        String url_clasificados = DireccionServidor+"wsnJSONRegistroClasificados.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");

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
                    titulo.setTitle("Registrado exitosamente");
                    titulo.show();

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
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

                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("vistas_clasificados","0");
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_clasificados1","vacio");
                parametros.put("imagen_clasificados2","vacio");
                parametros.put("imagen_clasificados3","vacio");

                return parametros;
            }
        };
        RequestQueue request_clasificados = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados.add(stringRequestclasificados);

    }
    private void cargarWebService_clasificados_dos() {

        String url_clasificados = DireccionServidor+"wsnJSONRegistroClasificados.php?";
        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");

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
                    titulo.setTitle("Registrado exitosamente");
                    titulo.show();

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
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

                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("vistas_clasificados","0");
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_clasificados2","vacio");
                parametros.put("imagen_clasificados3","vacio");

                return parametros;
            }
        };

        RequestQueue request_clasificados = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados.add(stringRequestclasificados);

    }
    private void cargarWebService_clasificados_tres() {

        String url_clasificados = DireccionServidor+"wsnJSONRegistroClasificados.php?";
        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");

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
                    titulo.setTitle("Registrado exitosamente");
                    titulo.show();

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
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

                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("vistas_clasificados","0");
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put("imagen_clasificados3","vacio");
                return parametros;
            }
        };
        RequestQueue request_clasificados = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados.add(stringRequestclasificados);
    }
    private void cargarWebService_clasificados() {

        String url_clasificados = DireccionServidor+"wsnJSONRegistroClasificados.php?";
        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");

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
                    titulo.setTitle("Registrado exitosamente");
                    titulo.show();

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
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

                String tituloinput = titulo_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_clasificados.getEditText().getText().toString().trim();
                String videoinput = video_clasificados.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_clasificados.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_clasificados",tituloinput);
                parametros.put("descripcionrow_clasificados",descripcioncortainput);
                parametros.put("video_clasificados",videoinput);
                parametros.put("vistas_clasificados","0");
                parametros.put("descripcion1_clasificados",descripcion1input);
                parametros.put("descripcion2_clasificados",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Clasificados");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put(nombre.get(3),cadena.get(3));

                return parametros;
            }
        };

        RequestQueue request_clasificados = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados.add(stringRequestclasificados);
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
                for (int i = 0; i< clipData.getItemCount(); i++){
                    listaimagenesclasificados.add(clipData.getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(PublicarClasificados.this,listaimagenesclasificados);
        gvImagenes_clasificados.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        clasificados=new ProgressDialog(this);
        clasificados.setMessage("Subiendo su Empleos");
        clasificados.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        clasificados.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            clasificados.show();
        }if(Mostrar.equals("Ocultar")){
            clasificados.hide();
        }
    }
}