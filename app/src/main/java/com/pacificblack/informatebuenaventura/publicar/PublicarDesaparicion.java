package com.pacificblack.informatebuenaventura.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PublicarDesaparicion extends AppCompatActivity {

    ImageView imagen1_publicar_desaparicion,
            imagen2_publicar_desaparicion,
            imagen3_publicar_desaparicion;

    TextInputLayout
            titulo_publicar_desaparicion,
            descripcioncorta_publicar_desaparicion,
            recompensa_publicar_desaparicion,
            diadesa_publicar_desaparicion,
            ultimolugar_publicar_desaparicion,
            descripcion1_publicar_desaparicion,
            descripcion2_publicar_desaparicion;


    AutoCompleteTextView queseperdio_publicar_desaparicion,
            estado_publicar_desaparicion;

    Button publicar_final_desaparicion;


    String estado[] = new String[]{"Desaparecido","Encontrado"};
    String quees[]  = new String[]{"Animal","Persona","Dococumento","Vehiculo","Otro objeto"};

    StringRequest stringRequest_desaparicion;

    Bitmap bitmap_desaparicion;


    private static final int IMAGE_PICK_CODE1 = 1000;
    private static final int IMAGE_PICK_CODE2 = 1002;
    private static final int IMAGE_PICK_CODE3 = 1003;

    private static final int PERMISSON_CODE = 1001;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_desaparicion);




        titulo_publicar_desaparicion = findViewById(R.id.publicar_titulo_desaparicion);
        descripcioncorta_publicar_desaparicion= findViewById(R.id.publicar_descripcioncorta_desaparicion);
        recompensa_publicar_desaparicion = findViewById(R.id.publicar_recompensa_desaparicion);
        diadesa_publicar_desaparicion = findViewById(R.id.publicar_fechade_desaparicion);
        ultimolugar_publicar_desaparicion = findViewById(R.id.publicar_ultimolugar_desaparicion);
        descripcion1_publicar_desaparicion = findViewById(R.id.publicar_descripcion_desaparicion);
        descripcion2_publicar_desaparicion = findViewById(R.id.publicar_descripcionextra_desaparicion);

        queseperdio_publicar_desaparicion = findViewById(R.id.publicar_quese_desapariciom);
        ArrayAdapter<String>adapterque = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,quees);
        queseperdio_publicar_desaparicion.setAdapter(adapterque);
        estado_publicar_desaparicion = findViewById(R.id.publicar_estadodes_desapariciom);
        ArrayAdapter<String>esta = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,estado);
        estado_publicar_desaparicion.setAdapter(esta);












        queseperdio_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queseperdio_publicar_desaparicion.showDropDown();
            }
        });

        estado_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_publicar_desaparicion.showDropDown();
            }
        });


        imagen1_publicar_desaparicion = findViewById(R.id.publicar_imagen1_desaparicion);
        imagen2_publicar_desaparicion = findViewById(R.id.publicar_imagen2_desaparicion);
        imagen3_publicar_desaparicion = findViewById(R.id.publicar_imagen3_desaparicion);



        imagen1_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen1();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen1();
                }
            }
        });



        imagen2_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen2();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen2();
                }
            }
        });



        imagen3_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen3();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen3();
                }
            }
        });



        publicar_final_desaparicion = findViewById(R.id.publicar_final_desaparicion);


        publicar_final_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()|
                !validardescripcioncorta()|
                !validarrecompensa()|
                !validardiadesa()|
                ! validarultimolugar()|
                ! validardescripcion1()|
                ! validardescripcion2()|
                ! validarqueseperdio()|
                ! validarestado()){return;}
                
                
                cargarWebService_desaparicion();


            }
        });


    }

    private void cargarWebService_desaparicion() {

        String url_desaparicion = "http://192.168.0.18/InformateDB/wsnJSONRegistro.php?";


        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equalsIgnoreCase("registra")){
                    Toast.makeText(getApplicationContext(),"Registro papito, pero no voy a limpiar",Toast.LENGTH_LONG).show();

                    Log.i("Error",response);

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String diadesainput = diadesa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = queseperdio_publicar_desaparicion.getText().toString().trim();
                String estadoinput = estado_publicar_desaparicion.getText().toString().trim();
                String imagen1 = convertirImagen1(bitmap_desaparicion);
                String imagen2 = convertirImagen2(bitmap_desaparicion);
                String imagen3 = convertirImagen3(bitmap_desaparicion);



                Log.i("Mostrar",imagen1);
                Log.i("Mostrar",imagen2);



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                //parametros.put("fechapublicacion_desaparecidos","2016/10/10");
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos","2016/10/10");
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos",descripcion2input);
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("imagen1_desaparecidos",imagen1);
                parametros.put("imagen2_desaparecidos",imagen2);
                parametros.put("imagen3_desaparecidos",imagen3);



                return parametros;
            }
        };

       RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        request_desaparicion.add(stringRequest_desaparicion);

    }

    private String convertirImagen1(Bitmap bmp) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,array);

        byte[] imagenByte = array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }
    private String convertirImagen2(Bitmap bmp2) {


        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp2.compress(Bitmap.CompressFormat.JPEG,100,array);

        byte[] imagenByte = array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }

    private String convertirImagen3(Bitmap bmp3) {


        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp3.compress(Bitmap.CompressFormat.JPEG,100,array);

        byte[] imagenByte = array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }


    public void seleccionarimagen1() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent,IMAGE_PICK_CODE1);

    }
    public void seleccionarimagen2() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent,IMAGE_PICK_CODE2);

    }

    public void seleccionarimagen3() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent,IMAGE_PICK_CODE3);

    }





    private boolean validartitulo(){
        String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_desaparicion.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarrecompensa(){
        String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();

        if (recompensainput.isEmpty()){
            recompensa_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(recompensainput.length()>15){

            recompensa_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            recompensa_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardiadesa(){
        String diadesainput = diadesa_publicar_desaparicion.getEditText().getText().toString().trim();

        if (diadesainput.isEmpty()){
            diadesa_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(diadesainput.length()>15){

            diadesa_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            diadesa_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarultimolugar(){
        String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();

        if (ultimolugarinput.isEmpty()){
            ultimolugar_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(ultimolugarinput.length()>250){

            ultimolugar_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            ultimolugar_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){

        String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion1input.length()>740){

            descripcion1_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){
        String descripcion2input = descripcion2_publicar_desaparicion.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion2input.length()>740){

            descripcion2_publicar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_publicar_desaparicion.setError(null);
            return true;
        }

    }
    private boolean validarqueseperdio() {
        String queseperdioinput = queseperdio_publicar_desaparicion.getText().toString().trim();

        if (queseperdioinput.toString().isEmpty()) {
            queseperdio_publicar_desaparicion.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (queseperdioinput.length() > 15) {

            queseperdio_publicar_desaparicion.setError("" + R.string.supera);
            return false;
        } else {
            queseperdio_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarestado(){
        String estadoinput = estado_publicar_desaparicion.getText().toString().trim();

        if (estadoinput.isEmpty()) {
            estado_publicar_desaparicion.setError("" + estadoinput);
            return false;
        } else if (estadoinput.length() > 15) {

            estado_publicar_desaparicion.setError("" + estadoinput);
            return false;
        } else {
            estado_publicar_desaparicion.setError(null);
            return true;
        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permiso autorizado
                    seleccionarimagen1();
                    seleccionarimagen2();

                }
                else{
                    //Permiso denegado
                    Toast.makeText(PublicarDesaparicion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

                }
            }

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE1){

            Uri patch1 = data.getData();

            try {
                bitmap_desaparicion = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),patch1);
                imagen1_publicar_desaparicion.setImageBitmap(bitmap_desaparicion);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE2){
            Uri patch2 = data.getData();
            imagen2_publicar_desaparicion.setImageURI(patch2);
            try {
                bitmap_desaparicion = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),patch2);
                imagen2_publicar_desaparicion.setImageBitmap(bitmap_desaparicion);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE3){
            Uri patch3 = data.getData();
            imagen3_publicar_desaparicion.setImageURI(patch3);
            try {
                bitmap_desaparicion = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),patch3);
                imagen3_publicar_desaparicion.setImageBitmap(bitmap_desaparicion);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }


}
