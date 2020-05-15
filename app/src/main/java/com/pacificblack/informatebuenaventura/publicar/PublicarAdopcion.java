package com.pacificblack.informatebuenaventura.publicar;

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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.CargandoDialog;

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
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioPublicar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;
//Todo: Clase completamente lista.


public class PublicarAdopcion extends AppCompatActivity {
    TextInputLayout titulo_publicar_adopcion, descripcioncorta_publicar_adopcion, descripcion1_publicar_adopcion, descripcion2_publicar_adopcion;
    Button publicarfinal_adopcion,subirimagenes;
    GridView gvImagenes_adopcion;
    Uri imagenesadopcionUri;
    List<Uri> listaimagenes_adopcion =  new ArrayList<>();
    List<String> listaBase64_adopcion = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_adopcion;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    private InterstitialAd anuncioAdopcion;
    Toolbar barra_adopcion;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(PublicarAdopcion.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_adopcion);

        whatsapp = findViewById(R.id.whatsapp_publicar_adopcion);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(PublicarAdopcion.this,Whatsapp);
            }
        });
        barra_adopcion = findViewById(R.id.toolbar_publicar_adopcion);
        barra_adopcion.setTitle("Publicar Adopción");
        barra_adopcion.setTitleTextColor(Color.WHITE);
        titulo_publicar_adopcion = findViewById(R.id.publicar_titulo_adopcion);
        descripcioncorta_publicar_adopcion = findViewById(R.id.publicar_descripcioncorta_adopcion);
        descripcion1_publicar_adopcion = findViewById(R.id.publicar_descripcion1_adopcion);
        descripcion2_publicar_adopcion = findViewById(R.id.publicar_descripcion2_adopcion);
        anuncioAdopcion = new InterstitialAd(this);
        anuncioAdopcion.setAdUnitId(AnuncioPublicar);
        anuncioAdopcion.loadAd(new AdRequest.Builder().build());
        gvImagenes_adopcion = findViewById(R.id.grid_adopcion);
        subirimagenes = findViewById(R.id.subir_imagenes_adopcion);
        subirimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisos,PERMISSON_CODE); }
                    else { seleccionarimagen(); }
                }else{ seleccionarimagen(); }
            }
        });

        publicarfinal_adopcion = findViewById(R.id.publicar_final_adopcion);
        publicarfinal_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validarfoto()){ return; }
                Subirimagen_adopcion();
            }
        });
    }
    private boolean validartitulo(){
        String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_publicar_adopcion.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_publicar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            titulo_publicar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_adopcion.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_publicar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_publicar_adopcion.setError(null);
            return true;
        }

    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){
            descripcion1_publicar_adopcion.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){
            descripcion1_publicar_adopcion.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_publicar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){
        if (listaimagenes_adopcion.size() == 0){ Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show(); return false; }
        else { return true;}
    }
    public void Subirimagen_adopcion(){

        listaBase64_adopcion.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_adopcion.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_adopcion.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_adopcion"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }

        if (nombre.size() == 1){ cargarWebService_adopcion_uno(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 2){ cargarWebService_adopcion_dos(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 3){ cargarWebService_adopcion_tres(); cargandoDialog.Mostrar(); }
        if (nombre.size() == 4){ cargarWebService_adopcion(); cargandoDialog.Mostrar(); }
        if (nombre.size()>4){ Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show(); }
    }
    private void cargarWebService_adopcion_uno() {

        String url_adopcion = DireccionServidor+"wsnJSONRegistroAdopcion.php?";

        stringRequest_adopcion= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarAdopcion.this);
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
                            if (anuncioAdopcion.isLoaded()) { anuncioAdopcion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cargandoDialog.Ocultar();
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_adopcion2","vacio");
                parametros.put("imagen_adopcion3","vacio");
                parametros.put("imagen_adopcion4","vacio");

                Log.i("Lo que se sube",parametros.toString());
                return parametros;
            }

        };
        RequestQueue request_adopcion = Volley.newRequestQueue(this);
        stringRequest_adopcion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion.add(stringRequest_adopcion);

    }
    private void cargarWebService_adopcion_dos() {

        String url_adopcion = DireccionServidor+"wsnJSONRegistroAdopcion.php?";

        stringRequest_adopcion= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarAdopcion.this);
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
                            if (anuncioAdopcion.isLoaded()) { anuncioAdopcion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cargandoDialog.Ocultar();
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_adopcion3","vacio");
                parametros.put("imagen_adopcion4","vacio");

                Log.i("Lo que se sube",parametros.toString());
                return parametros;
            }
        };
        RequestQueue request_adopcion = Volley.newRequestQueue(this);
        stringRequest_adopcion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion.add(stringRequest_adopcion);

    }
    private void cargarWebService_adopcion_tres() {

        String url_adopcion = DireccionServidor+"wsnJSONRegistroAdopcion.php?";

        stringRequest_adopcion= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarAdopcion.this);
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
                            if (anuncioAdopcion.isLoaded()) { anuncioAdopcion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cargandoDialog.Ocultar();
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put("imagen_adopcion4","vacio");
                Log.i("Lo que se sube",parametros.toString());
                return parametros;
            }
        };
        RequestQueue request_adopcion = Volley.newRequestQueue(this);
        stringRequest_adopcion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion.add(stringRequest_adopcion);
    }
    private void cargarWebService_adopcion() {

        String url_adopcion = DireccionServidor+"wsnJSONRegistroAdopcion.php?";

        stringRequest_adopcion= new StringRequest(Request.Method.POST, url_adopcion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarAdopcion.this);
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
                            if (anuncioAdopcion.isLoaded()) { anuncioAdopcion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cargandoDialog.Ocultar();
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion","Vacio");
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put(nombre.get(3),cadena.get(3));

                return parametros;
            }
        };
        RequestQueue request_adopcion = Volley.newRequestQueue(this);
        stringRequest_adopcion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_adopcion.add(stringRequest_adopcion);
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
        startActivityForResult(Intent.createChooser(intent, "Selecciona las 4 imagenes"), IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(PublicarAdopcion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (data.getClipData() == null) {
                imagenesadopcionUri = data.getData();
                listaimagenes_adopcion.add(imagenesadopcionUri);
            } else {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    listaimagenes_adopcion.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(PublicarAdopcion.this, listaimagenes_adopcion);
        gvImagenes_adopcion.setAdapter(baseAdapter);
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