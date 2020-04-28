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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioPublicar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

public class PublicarArticulo extends AppCompatActivity {

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
    TextInputLayout titulo_publicar_comprayventa, descripcioncorta_publicar_comprayventa, descripcion_publicar_comprayventa, descripcionextra_publicar_comprayventa, precio_publicar_comprayventa, ubicacion_publicar_comprayventa, cantidad_publicar_comprayventa, contacto_publicar_comprayventa, buscar_publicar_comprayventa;
    Button publicarfinal_comprayventa,subirimagenes;
    private InterstitialAd anuncioArticulo;
    private ProgressDialog articulo;



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
        gvImagenes_comprayventa = findViewById(R.id.grid_comprayventa);
        subirimagenes = findViewById(R.id.subir_imagenes_comprayventa);

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

        publicarfinal_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion() | !validardescripcionextra() | !validarprecio() | !validarubicacion() | !validarcantidad() | !validarcontacto() | !validarfoto()) {
                    return;
                }
                Subirimagen_comprayventa();

            }
        });
        anuncioArticulo = new InterstitialAd(this);
        anuncioArticulo.setAdUnitId(AnuncioPublicar);
        anuncioArticulo.loadAd(new AdRequest.Builder().build());

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
        else if (listaimagenes_comprayventa.size() > 1){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 3 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return true;}
    }

    public void Subirimagen_comprayventa(){

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
        if (nombre.size() == 1){cargarWebService_comprayventa_uno();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 2){cargarWebService_comprayventa_dos();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 3){cargarWebService_comprayventa();
            CargandoSubida("Ver");
        }
        if (nombre.size()>3){
            Toast.makeText(getApplicationContext(),"Solo se pueden subir 3 imagenes, por favor borre una",Toast.LENGTH_LONG).show();
        }

    }

    private void cargarWebService_comprayventa() {

        String url_comprayventa = DireccionServidor+"wsnJSONRegistroArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);
                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioArticulo.isLoaded()) {
                                        anuncioArticulo.show();
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
                        Log.i("Error",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
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
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));

                Log.i("Sale",parametros.toString());
                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_comprayventa);

    }
    private void cargarWebService_comprayventa_uno() {

        String url_comprayventa = DireccionServidor+"wsnJSONRegistroArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioArticulo.isLoaded()) {
                                        anuncioArticulo.show();
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
                        Log.i("Error",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
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
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_comprayventa1","vacio");
                parametros.put("imagen_comprayventa2","vacio");

                Log.i("Sale",parametros.toString());
                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_comprayventa);

    }
    private void cargarWebService_comprayventa_dos() {

        String url_comprayventa = DireccionServidor+"wsnJSONRegistroArticulo.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    CargandoSubida("Ocultar");
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarArticulo.this);
                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    if (anuncioArticulo.isLoaded()) {
                                        anuncioArticulo.show();
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
                        Log.i("Error",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
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
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_comprayventa2","vacio");

                Log.i("Sale",parametros.toString());
                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_comprayventa);

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
                for (int i = 0; i< clipData.getItemCount(); i++){
                    listaimagenes_comprayventa.add(clipData.getItemAt(i).getUri());
                }
            }

        }
        baseAdapter = new GridViewAdapter(PublicarArticulo.this,listaimagenes_comprayventa);
        gvImagenes_comprayventa.setAdapter(baseAdapter);

    }
    private void CargandoSubida(String Mostrar){
        articulo=new ProgressDialog(this);
        articulo.setMessage("Subiendo su Empleos");
        articulo.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        articulo.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            articulo.show();
        } if(Mostrar.equals("Ver")){
            articulo.hide();
        }
    }
}