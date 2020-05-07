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
import static com.pacificblack.informatebuenaventura.texto.Avisos.meta_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

public class PublicarDonaciones extends AppCompatActivity{

    GridView gvImagenes_donaciones;
    Uri imagenesdonacionesUri;
    List<Uri> listaimagenes_donaciones =  new ArrayList<>();
    List<String> listaBase64_donaciones = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_donaciones;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    private InterstitialAd anuncioDonaciones;
    TextInputLayout titulo_publicar_donaciones, descripcioncorta_publicar_donaciones, descripcion1_publicar_donaciones, meta_publicar_donaciones;
    Button publicar_final_donaciones,subirimagenes;
    Toolbar barra_donaciones;
    ImageView whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_donaciones);

        whatsapp = findViewById(R.id.whatsapp_publicar_donaciones);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(PublicarDonaciones.this,Whatsapp);
            }
        });
        barra_donaciones = findViewById(R.id.toolbar_publicar_donaciones);
        barra_donaciones.setTitle("Publicar Donaciones");

        titulo_publicar_donaciones = findViewById(R.id.publicar_titulo_donaciones);
        descripcioncorta_publicar_donaciones = findViewById(R.id.publicar_descripcioncorta_donaciones);
        descripcion1_publicar_donaciones = findViewById(R.id.publicar_descripcion1_donaciones);
        meta_publicar_donaciones = findViewById(R.id.publicar_meta_donaciones);

        anuncioDonaciones = new InterstitialAd(this);
        anuncioDonaciones.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioDonaciones.loadAd(new AdRequest.Builder().build());

        gvImagenes_donaciones = findViewById(R.id.grid_donaciones);
        subirimagenes = findViewById(R.id.subir_imagenes_donaciones);
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
        publicar_final_donaciones = findViewById(R.id.publicar_final_donaciones);
        publicar_final_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo()| !validardescripcioncorta()| ! validardescripcion1()| ! validarmeta()| ! validarfoto()){return;}
                Subirimagen_donaciones();
            }
        });
    }

    private boolean validartitulo(){
        String tituloinput = titulo_publicar_donaciones.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()){
            titulo_publicar_donaciones.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_publicar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            titulo_publicar_donaciones.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_donaciones.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_donaciones.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_publicar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_publicar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_publicar_donaciones.getEditText().getText().toString().trim();
        if (descripcion1input.isEmpty()){
            descripcion1_publicar_donaciones.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){

            descripcion1_publicar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_publicar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validarmeta(){
        String metainput = meta_publicar_donaciones.getEditText().getText().toString().trim();
        if (metainput.isEmpty()){
            meta_publicar_donaciones.setError(meta_vacio);
            return false;
        }
        else if(metainput.length()>15){
            meta_publicar_donaciones.setError(texto_superado);
            return false;
        }
        else {
            meta_publicar_donaciones.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){
        if (listaimagenes_donaciones.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else { return true;}
    }

    public void Subirimagen_donaciones(){

        listaBase64_donaciones.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_donaciones.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_donaciones.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add("imagen_donaciones" + i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){
            cargarWebService_donaciones_uno();
            CargandoSubida("Ver");
        }
        if (nombre.size() == 2){
            cargarWebService_donaciones();
            CargandoSubida("Ver");
        }
        if (nombre.size()>2){
            Toast.makeText(getApplicationContext(),imagen_maxima +" 2",Toast.LENGTH_LONG).show();
        }
    }

    private void cargarWebService_donaciones_uno() {

        String url_donaciones = DireccionServidor+"wsnJSONRegistroDonaciones.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDonaciones.this);
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
                            if (anuncioDonaciones.isLoaded()) { anuncioDonaciones.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
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

                String tituloinput = titulo_publicar_donaciones.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_donaciones.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_donaciones.getEditText().getText().toString().trim();
                String metainput = meta_publicar_donaciones.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_donaciones",tituloinput);
                parametros.put("descripcionrow_donaciones",descripcioncortainput);
                parametros.put("vistas_donaciones","0");
                parametros.put("descripcion1_donaciones",descripcion1input);
                parametros.put("meta_donaciones",metainput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Donaciones");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_donaciones1","vacio");

                return parametros;
            }
        };

        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_donaciones);

    }
    private void cargarWebService_donaciones() {

        String url_donaciones = DireccionServidor+"wsnJSONRegistroDonaciones.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDonaciones.this);
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
                            if (anuncioDonaciones.isLoaded()) { anuncioDonaciones.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
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

                String tituloinput = titulo_publicar_donaciones.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_donaciones.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_donaciones.getEditText().getText().toString().trim();
                String metainput = meta_publicar_donaciones.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_donaciones",tituloinput);
                parametros.put("descripcionrow_donaciones",descripcioncortainput);
                parametros.put("vistas_donaciones","0");
                parametros.put("descripcion1_donaciones",descripcion1input);
                parametros.put("meta_donaciones",metainput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Donaciones");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));

                return parametros;
            }
        };
        RequestQueue request_funebres = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres.add(stringRequest_donaciones);
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
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 2 imagenes"),IMAGE_PICK_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(PublicarDonaciones.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesdonacionesUri = data.getData();
                listaimagenes_donaciones.add(imagenesdonacionesUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_donaciones.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(PublicarDonaciones.this,listaimagenes_donaciones);
        gvImagenes_donaciones.setAdapter(baseAdapter);
    }

    private void CargandoSubida(String Mostrar){
        AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDonaciones.this);
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