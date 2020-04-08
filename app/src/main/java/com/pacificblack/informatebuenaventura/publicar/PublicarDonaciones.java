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
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;
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

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_donaciones);

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
            titulo_publicar_donaciones.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_donaciones.setError(""+R.string.supera);
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
            descripcioncorta_publicar_donaciones.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_donaciones.setError(""+R.string.supera);
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
            descripcion1_publicar_donaciones.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion1input.length()>740){

            descripcion1_publicar_donaciones.setError(""+R.string.supera);
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
            meta_publicar_donaciones.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(metainput.length()>15){

            meta_publicar_donaciones.setError(""+R.string.supera);
            return false;
        }
        else {
            meta_publicar_donaciones.setError(null);
            return true;
        }

    }
    private boolean validarfoto(){

        if (listaimagenes_donaciones.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_donaciones.size() > 2){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_donaciones.size() < 2){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenes_donaciones.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

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

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarDonaciones.this);
                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            if (anuncioDonaciones.isLoaded()) {
                                anuncioDonaciones.show();
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
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                    }
                }){
            @SuppressLint("LongLogTag")
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

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_funebres = Volley.newRequestQueue(this);
        request_funebres.add(stringRequest_donaciones);

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
        cargarWebService_donaciones();
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 2 imagenes"),IMAGE_PICK_CODE);
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
                    Toast.makeText(PublicarDonaciones.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesdonacionesUri = data.getData();
                listaimagenes_donaciones.add(imagenesdonacionesUri);
            }else {
                for (int i = 0; i< 2; i++){
                    listaimagenes_donaciones.add(clipData.getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(PublicarDonaciones.this,listaimagenes_donaciones);
        gvImagenes_donaciones.setAdapter(baseAdapter);
    }
}