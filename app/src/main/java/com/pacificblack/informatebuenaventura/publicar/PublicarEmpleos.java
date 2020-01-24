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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicarEmpleos extends AppCompatActivity {


    TextInputLayout titulo_publicar_empleos, descripcioncorta_publicar_empleos;
    AutoCompleteTextView necesidad_publicar_empleos;
    Button publicarfinal_empleos,subirimagenes;
    String nece[] = new String[]{"Urgente","Rapido","Para hoy mismo"};

    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
    GridView gvImagenes_empleos;
    Uri imagenesempleosUri;
    List<Uri> listaimagenes_empleos =  new ArrayList<>();
    List<String> listaBase64_empleos = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_empleos;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    //TODO: Aqui finaliza


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_empleos);

        titulo_publicar_empleos = findViewById(R.id.publicar_titulo_empleos);
        descripcioncorta_publicar_empleos = findViewById(R.id.publicar_descripcion_empleos);
        necesidad_publicar_empleos = findViewById(R.id.publicar_necesidad_empleos);
        ArrayAdapter<String> adapternece = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,nece);

        necesidad_publicar_empleos.setAdapter(adapternece);

        necesidad_publicar_empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                necesidad_publicar_empleos.showDropDown();
            }
        });

        publicarfinal_empleos = findViewById(R.id.publicar_final_empleos);
        publicarfinal_empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcion() | !validarnececidad() | !validarfoto()) {
                    return;
                }

                Subirimagen_empleos();

            }




                                                 }

        );





        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_empleos = findViewById(R.id.grid_empleos);
        subirimagenes = findViewById(R.id.subir_imagenes_empleos);
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


    private boolean validartitulo(){
        String tituloinput = titulo_publicar_empleos.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_empleos.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_empleos.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_empleos.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_publicar_empleos.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_empleos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_publicar_empleos.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_empleos.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        String necesidadinput = necesidad_publicar_empleos.getText().toString().trim();

        if (necesidadinput.isEmpty()) {
            necesidad_publicar_empleos.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (necesidadinput.length() > 15) {

            necesidad_publicar_empleos.setError("" + R.string.supera);
            return false;
        } else {
            necesidad_publicar_empleos.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){

        if (listaimagenes_empleos.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar como minimo una foto",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_empleos.size() > 1){
            Toast.makeText(getApplicationContext(),"Solo se agregara una imagen",Toast.LENGTH_LONG).show();
            return true;
        }else {
            return true;}

    }


//TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarWebService_empleos() {

        String url_empleos = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
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

                String tituloinput = titulo_publicar_empleos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_empleos.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_publicar_empleos.getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_empleos",tituloinput);
                parametros.put("descripcionrow_empleos",descripcioncortainput);
                parametros.put("vistas_empleos","0");
                parametros.put("necesidad_empleos",necesidadinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Empleos");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }




                return parametros;
            }
        };

        RequestQueue request_empleos = Volley.newRequestQueue(this);
        request_empleos.add(stringRequest_empleos);

    }
    public void Subirimagen_empleos(){


        listaBase64_empleos.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_empleos.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_empleos.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_empleos"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarWebService_empleos();

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
                    Toast.makeText(PublicarEmpleos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesempleosUri = data.getData();
                listaimagenes_empleos.add(imagenesempleosUri);
            }else {
                for (int i = 0; i< 1; i++){
                    listaimagenes_empleos.add(clipData.getItemAt(i).getUri());
                }
            }




        }

        baseAdapter = new GridViewAdapter(PublicarEmpleos.this,listaimagenes_empleos);
        gvImagenes_empleos.setAdapter(baseAdapter);



    }



}
