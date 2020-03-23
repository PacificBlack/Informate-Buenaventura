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

public class PublicarFunebres extends AppCompatActivity {


    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
    GridView gvImagenes_funebres;
    Uri imagenesfunebresUri;
    List<Uri> listaimagenes_funebres =  new ArrayList<>();
    List<String> listaBase64_funebres = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_funebres;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    //TODO: Aqui finaliza



    TextInputLayout
            titulo_publicar_funebres,
            descripcioncorta_publicar_funebres,
            descripcion1_publicar_funebres,
            descripcion2_publicar_funebres;

    Button publicar_final_funebres,subirimagenes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_funebres);

        titulo_publicar_funebres = findViewById(R.id.publicar_titulo_funebres);
        descripcioncorta_publicar_funebres = findViewById(R.id.publicar_descripcioncorta_funebres);
        descripcion1_publicar_funebres = findViewById(R.id.publicar_descripcion1_funebres);
        descripcion2_publicar_funebres = findViewById(R.id.publicar_descripcion2_funebres);


        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_funebres = findViewById(R.id.grid_funebres);
        subirimagenes = findViewById(R.id.subir_imagenes_funebres);
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


        publicar_final_funebres = findViewById(R.id.publicar_final_funebres);
        publicar_final_funebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()|
                        !validardescripcioncorta()|
                        ! validardescripcion1()|
                        ! validardescripcion2()|
                        ! validarfoto()){return;}

                //TODO: Aqui se hace el envio a la base de datos

                Subirimagen_funebres();

            }
        });




    }



    private boolean validartitulo(){
        String tituloinput = titulo_publicar_funebres.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_funebres.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_funebres.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_funebres.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_funebres.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_funebres.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_funebres.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_funebres.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){

        String descripcion1input = descripcion1_publicar_funebres.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_publicar_funebres.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion1input.length()>740){

            descripcion1_publicar_funebres.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_publicar_funebres.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){
        String descripcion2input = descripcion2_publicar_funebres.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_publicar_funebres.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion2input.length()>740){

            descripcion2_publicar_funebres.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_publicar_funebres.setError(null);
            return true;
        }

    }

    private boolean validarfoto(){

        if (listaimagenes_funebres.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 3 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_funebres.size() > 3){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 3 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_funebres.size() < 3){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_funebres.size()+"imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

    }


    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_funebres() {

        String url_funebres = "http://192.168.0.18/InformateDB/wsnJSONRegistroDos.php?";


        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
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

                String tituloinput = titulo_publicar_funebres.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_funebres.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_funebres.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_funebres.getEditText().getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_funebres",tituloinput);
                parametros.put("descripcionrow_funebres",descripcioncortainput);
                parametros.put("vistas_funebres","0");
                parametros.put("descripcion1_funebres",descripcion1input);
                parametros.put("descripcion2_funebres",descripcion2input);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Funebres");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }




                return parametros;
            }
        };

        RequestQueue request_funebres = Volley.newRequestQueue(this);
        request_funebres.add(stringRequest_funebres);

    }
    public void Subirimagen_funebres(){


        listaBase64_funebres.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_funebres.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_funebres.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_funebres"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_funebres();

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
                    Toast.makeText(PublicarFunebres.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesfunebresUri = data.getData();
                listaimagenes_funebres.add(imagenesfunebresUri);
            }else {
                for (int i = 0; i< 3; i++){
                    listaimagenes_funebres.add(clipData.getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(PublicarFunebres.this,listaimagenes_funebres);
        gvImagenes_funebres.setAdapter(baseAdapter);



    }




}
