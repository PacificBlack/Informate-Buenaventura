package com.pacificblack.informatebuenaventura.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicarEventos extends AppCompatActivity {

    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
    GridView gvImagenes_eventos;
    Uri imageneseventosUri;
    List<Uri> listaimagenes_eventos =  new ArrayList<>();
    List<String> listaBase64_eventos = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_eventos;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    //TODO: Aqui finaliza

    TextInputLayout titulo_publicar_eventos,descripcioncorta_publicar_eventos,lugar_publicar_eventos;
    Button publicarfinal_eventos,subirimagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_eventos);

        titulo_publicar_eventos = findViewById(R.id.publicar_titulo_eventos);
        descripcioncorta_publicar_eventos = findViewById(R.id.publicar_descripcion_eventos);
        lugar_publicar_eventos = findViewById(R.id.publicar_lugar_eventos);
        publicarfinal_eventos = findViewById(R.id.publicar_final_eventos);

        publicarfinal_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcion() | !validarlugar() | !validarfoto()) {
                    return;
                }
                Subirimagen_eventos();

            }

        });


        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_eventos = findViewById(R.id.grid_eventos);
        subirimagenes = findViewById(R.id.subir_imagenes_eventos);
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

        //TODO: Aqui va todo lo del grid para mostrar en la pantall


    }



    private boolean validartitulo(){
        String tituloinput = titulo_publicar_eventos.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_eventos.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_eventos.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_eventos.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_publicar_eventos.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_eventos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_publicar_eventos.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_eventos.setError(null);
            return true;
        }
    }
    private boolean validarlugar(){
        String lugarinput = lugar_publicar_eventos.getEditText().toString().trim();

        if (lugarinput.isEmpty()) {
            lugar_publicar_eventos.setError("" + String.valueOf(R.string.error_descripcioncorta));
            return false;
        } else if (lugarinput.length() > 120) {

            lugar_publicar_eventos.setError("" + R.string.supera);
            return false;
        } else {
            lugar_publicar_eventos.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){

        if (listaimagenes_eventos.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar como minimo una foto",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_eventos.size() > 1){
            Toast.makeText(getApplicationContext(),"Solo se agregara una imagen",Toast.LENGTH_LONG).show();
            return true;
        }else {
            return true;}

    }


//TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_eventos() {

        String url_eventos = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequest_eventos= new StringRequest(Request.Method.POST, url_eventos, new Response.Listener<String>() {
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

                String tituloinput = titulo_publicar_eventos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_eventos.getEditText().getText().toString().trim();
                String lugarinput = lugar_publicar_eventos.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_eventos",tituloinput);
                parametros.put("descripcionrow_eventos",descripcioncortainput);
                parametros.put("vistas_eventos","0");
                parametros.put("lugar_eventos",lugarinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Eventos");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }




                return parametros;
            }
        };

        RequestQueue request_empleos = Volley.newRequestQueue(this);
        request_empleos.add(stringRequest_eventos);

    }
    public void Subirimagen_eventos(){


        listaBase64_eventos.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_eventos.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_eventos.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_eventos"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_eventos();

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
        startActivityForResult(Intent.createChooser(intent,"Selecciona la imagen"),IMAGE_PICK_CODE);

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
                    Toast.makeText(PublicarEventos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imageneseventosUri = data.getData();
                listaimagenes_eventos.add(imageneseventosUri);
            }else {
                for (int i = 0; i< 1; i++){
                    listaimagenes_eventos.add(clipData.getItemAt(i).getUri());
                }
            }




        }

        baseAdapter = new GridViewAdapter(PublicarEventos.this,listaimagenes_eventos);
        gvImagenes_eventos.setAdapter(baseAdapter);



    }




}
