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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.pacificblack.informatebuenaventura.extras.Cargando;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioPublicar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

//TODO: Esta full adopcion solo faltan retoques


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

    Cargando cargando = new Cargando(PublicarAdopcion.this);

    private InterstitialAd anuncioAdopcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_adopcion);

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

        publicarfinal_adopcion = findViewById(R.id.publicar_final_adopcion);
        publicarfinal_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validardescripcion2() | !validarfoto()){return;}
                Subirimagen_adopcion();
                cargando.iniciarprogress();

            }
        });


    }

    private boolean validartitulo(){
        String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_adopcion.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_adopcion.setError(""+R.string.supera);
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
            descripcioncorta_publicar_adopcion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_adopcion.setError(""+R.string.supera);
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
            descripcion1_publicar_adopcion.setError(""+R.string.error_descripcion1);
            return false;
        }
        else if(descripcion1input.length()>150){

            descripcion1_publicar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_publicar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){

        String descripcion2input = descripcion2_publicar_adopcion.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_publicar_adopcion.setError(""+R.string.error_descripcion2);
            return false;
        }
        else if(descripcion2input.length()>150){

            descripcion2_publicar_adopcion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_publicar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){

        if (listaimagenes_adopcion.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 4 imagenes para la publicacion (Puede subir la misma 4 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_adopcion.size() > 4){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 4 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_adopcion.size() < 4){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_adopcion.size()+" imagenes, pero deben ser 4",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

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

                    cargando.cancelarprogress();

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarAdopcion.this);

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
                    titulo.setTitle("Registrado exitosamente");
                    titulo.show();

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    cargando.cancelarprogress();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        cargando.cancelarprogress();

                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_adopcion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_adopcion",tituloinput);
                parametros.put("descripcionrow_adopcion",descripcioncortainput);
                parametros.put("vistas_adopcion","0");
                parametros.put("descripcion1_adopcion",descripcion1input);
                parametros.put("descripcion2_adopcion",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Adopcion");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_adopcion = Volley.newRequestQueue(this);
        request_adopcion.add(stringRequest_adopcion);

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
            }catch (IOException e){        }

        }
        cargarWebService_adopcion();
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
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
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
                    Toast.makeText(PublicarAdopcion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                listaimagenes_adopcion.add(imagenesadopcionUri);
            }else {
                for (int i = 0; i< 4; i++){
                    listaimagenes_adopcion.add(clipData.getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(PublicarAdopcion.this,listaimagenes_adopcion);
        gvImagenes_adopcion.setAdapter(baseAdapter);

    }
}