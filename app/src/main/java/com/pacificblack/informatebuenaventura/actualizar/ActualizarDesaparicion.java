package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActualizarDesaparicion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {


    TextInputLayout
            titulo_actualizar_desaparicion,
            descripcioncorta_actualizar_desaparicion,
            recompensa_actualizar_desaparicion,
            ultimolugar_actualizar_desaparicion,
            descripcion1_actualizar_desaparicion,
            descripcion2_actualizar_desaparicion,
            buscar_actualizar_desaparicion;

    TextView diadesa_actualizar_desaparicion;
    String dia_desaparicion;

    DatePickerDialog.OnDateSetListener dateSetListener;

    AutoCompleteTextView queseperdio_actualizar_desaparicion,
            estado_actualizar_desaparicion;

    Button actualizar_final_desaparicion,subirimagenes;


    String estado[] = new String[]{"Desaparecido","Encontrado"};
    String quees[]  = new String[]{"Animal","Persona","Dococumento","Vehiculo","Otro objeto"};



    //TODO: Modificar y Eliminar
    ImageButton actualizar_editar_desaparicion,
            buscar_desaparicion;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    HorizontalScrollView imagenes_desaparicion;
    ImageView imagen1_actualizar_desaparicion,imagen2_actualizar_desaparicion,
            imagen3_actualizar_desaparicion;


    //TODO: Modificar y Eliminar

    private InterstitialAd anunciodesaparicion;


    //TODO: Aqui comienza todo lo que se necesita para lo de la bd y el grid de subir
    GridView gvImagenes_desaparicion;
    Uri imagenesdesaparicionUri;
    List<Uri> listaimagenes_desaparicion =  new ArrayList<>();
    List<String> listaBase64_desaparicion = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_desaparicion;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;

    //TODO: Aqui finaliza



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_desaparicion);

        titulo_actualizar_desaparicion = findViewById(R.id.actualizar_titulo_desaparicion);
        descripcioncorta_actualizar_desaparicion= findViewById(R.id.actualizar_descripcioncorta_desaparicion);
        recompensa_actualizar_desaparicion = findViewById(R.id.actualizar_recompensa_desaparicion);
        diadesa_actualizar_desaparicion = findViewById(R.id.actualizar_fechade_desaparicion);
        diadesa_actualizar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActualizarDesaparicion.this, android.R.style.Theme_Material_Dialog_MinWidth,dateSetListener,
                        año,mes,dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int mesi =month+1;
                dia_desaparicion = year+"/"+mesi+"/"+dayOfMonth;

                diadesa_actualizar_desaparicion.setText(dia_desaparicion);

            }
        };

        ultimolugar_actualizar_desaparicion = findViewById(R.id.actualizar_ultimolugar_desaparicion);
        descripcion1_actualizar_desaparicion = findViewById(R.id.actualizar_descripcion_desaparicion);
        descripcion2_actualizar_desaparicion = findViewById(R.id.actualizar_descripcionextra_desaparicion);


        imagen1_actualizar_desaparicion = findViewById(R.id.imagen1_actualizar_desaparicion);
        imagen2_actualizar_desaparicion = findViewById(R.id.imagen2_actualizar_desaparicion);
        imagen3_actualizar_desaparicion = findViewById(R.id.imagen3_actualizar_desaparicion);


        buscar_actualizar_desaparicion = findViewById(R.id.actualizar_id_desaparicion);
        actualizar_editar_desaparicion = findViewById(R.id.actualizar_final_desaparicion);
        actualizar_editar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        !validarid()|
                        !validartitulo()|
                        !validardescripcioncorta()|
                        !validarrecompensa()|
                        !validardiadesa()|
                        ! validarultimolugar()|
                        ! validardescripcion1()|
                        ! validardescripcion2()|
                        ! validarqueseperdio()|
                        ! validarestado()){return;}

                if (!validarfotoupdate()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDesaparicion.this);
                    mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (listaimagenes_desaparicion.size() == 3){
                                Subirimagen_desaparicion_update();
                            }

                        }
                    }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cargarActualizarSinImagen_desaparicion();

                        }
                    });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();


                    return; }

            }
        });

        buscar_desaparicion = findViewById(R.id.actualizar_buscar_desaparicion);


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        buscar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validarid()){return;}

                cargarBusqueda_desaparicion();
            }
        });



        //TODO: Modificar y Eliminar

        //TODO: Anuncios

        anunciodesaparicion = new InterstitialAd(this);
        anunciodesaparicion.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anunciodesaparicion.loadAd(new AdRequest.Builder().build());
        //TODO: Anuncios






        //TODO: Aqui va todo lo del grid para mostrar en la pantalla

        gvImagenes_desaparicion = findViewById(R.id.grid_desaparicion);
        subirimagenes = findViewById(R.id.subir_imagenes_desaparicion);
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

        queseperdio_actualizar_desaparicion = findViewById(R.id.actualizar_quese_desapariciom);
        ArrayAdapter<String> adapterque = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,quees);
        queseperdio_actualizar_desaparicion.setAdapter(adapterque);
        estado_actualizar_desaparicion = findViewById(R.id.actualizar_estadodes_desapariciom);
        ArrayAdapter<String>esta = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,estado);
        estado_actualizar_desaparicion.setAdapter(esta);

        queseperdio_actualizar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queseperdio_actualizar_desaparicion.showDropDown();
            }
        });

        estado_actualizar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_actualizar_desaparicion.showDropDown();
            }
        });

    }


    private boolean validarid(){
        String idinput = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_actualizar_desaparicion.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean  validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarrecompensa(){
        String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (recompensainput.isEmpty()){
            recompensa_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(recompensainput.length()>15){

            recompensa_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            recompensa_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardiadesa(){

        if (dia_desaparicion.isEmpty()){
            diadesa_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        return true;
    }
    private boolean validarultimolugar(){
        String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (ultimolugarinput.isEmpty()){
            ultimolugar_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(ultimolugarinput.length()>250){

            ultimolugar_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            ultimolugar_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion1(){

        String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion1input.length()>740){

            descripcion1_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion1_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){
        String descripcion2input = descripcion2_actualizar_desaparicion.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_actualizar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcion2input.length()>740){

            descripcion2_actualizar_desaparicion.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion2_actualizar_desaparicion.setError(null);
            return true;
        }

    }
    private boolean validarqueseperdio() {
        String queseperdioinput = queseperdio_actualizar_desaparicion.getText().toString().trim();

        if (queseperdioinput.toString().isEmpty()) {
            queseperdio_actualizar_desaparicion.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (queseperdioinput.length() > 15) {

            queseperdio_actualizar_desaparicion.setError("" + R.string.supera);
            return false;
        } else {
            queseperdio_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarestado(){
        String estadoinput = estado_actualizar_desaparicion.getText().toString().trim();

        if (estadoinput.isEmpty()) {
            estado_actualizar_desaparicion.setError("" + estadoinput);
            return false;
        } else if (estadoinput.length() > 15) {

            estado_actualizar_desaparicion.setError("" + estadoinput);
            return false;
        } else {
            estado_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){

        if (listaimagenes_desaparicion.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 3 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_desaparicion.size() > 3){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 3 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_desaparicion.size() < 3){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_desaparicion.size()+"imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }
        else if(listaimagenes_desaparicion.size() == 4){
            return false;
        }

        else {
            return true;
        }

    }


    private void cargarBusqueda_desaparicion() {

        String url_buscar_bienes = "http://192.168.0.18/InformateDB/wsnJSONBuscarDesaparicion.php?id_bienes="+buscar_actualizar_desaparicion.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }



    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        Desaparecidos desaparecidos = new Desaparecidos();

        JSONArray json = response.optJSONArray("desaparecidos");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            desaparecidos.setId_desaparecidos(jsonObject.getInt("id_desaparecidos"));
            desaparecidos.setQue_desaparecidos(jsonObject.getString("que_desaparecidos"));
            desaparecidos.setTitulo_row_desaparecidos(jsonObject.getString("titulo_desaparecidos"));
            desaparecidos.setDescripcion_row_desaparecidos(jsonObject.getString("descripcionrow_desaparecidos"));
            desaparecidos.setFechapublicacion_row_desaparecidos(jsonObject.getString("fechapublicacion_desaparecidos"));
            desaparecidos.setRecompensa_row_desaparecidos(jsonObject.getString("recompensa_desaparecidos"));
            desaparecidos.setVista_row_desaparecidos(jsonObject.getInt("vistas_desaparecidos"));
            desaparecidos.setImagen1_desaparecidos(jsonObject.getString("imagen1_desaparecidos"));
            desaparecidos.setImagen2_desaparecidos(jsonObject.getString("imagen2_desaparecidos"));
            desaparecidos.setImagen3̣̣_desaparecidos(jsonObject.getString("imagen3_desaparecidos"));
            desaparecidos.setDescripcion1_desaparecidos(jsonObject.getString("descripcion1_desaparecidos"));
            desaparecidos.setDescripcion2_desaparecidos(jsonObject.getString("descripcion2_desaparecidos"));
            desaparecidos.setFechadesaparecido_desaparecidos(jsonObject.getString("fechadesaparecido_desaparecidos"));
            desaparecidos.setEstado_desaparecidos(jsonObject.getString("estado_desaparecidos"));
            desaparecidos.setUltimolugar_desaparecidos(jsonObject.getString("ultimolugar_desaparecidos"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        titulo_actualizar_desaparicion.getEditText().setText(desaparecidos.getTitulo_row_desaparecidos());
        descripcioncorta_actualizar_desaparicion.getEditText().setText(desaparecidos.getDescripcion_row_desaparecidos());
        recompensa_actualizar_desaparicion.getEditText().setText(desaparecidos.getRecompensa_row_desaparecidos());
        diadesa_actualizar_desaparicion.setText(desaparecidos.getFechadesaparecido_desaparecidos());
        ultimolugar_actualizar_desaparicion.getEditText().setText(desaparecidos.getUltimolugar_desaparecidos());
        descripcion1_actualizar_desaparicion.getEditText().setText(desaparecidos.getDescripcion1_desaparecidos());
        descripcion2_actualizar_desaparicion.getEditText().setText(desaparecidos.getDescripcion2_desaparecidos());
        queseperdio_actualizar_desaparicion.setText(desaparecidos.getQue_desaparecidos());
        estado_actualizar_desaparicion.setText(desaparecidos.getEstado_desaparecidos());


        Picasso.get().load(desaparecidos.getImagen1_desaparecidos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen2_desaparecidos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_actualizar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen3̣̣_desaparecidos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_actualizar_desaparicion);


    }



    //TODO: De aquí para abajo va todo lo que tiene que ver con la subidad de datos a la BD De la seccion desaparecidos

    private void cargarActualizarConImagen_desaparicion(){

        String url_desaparicion = "http://192.168.0.18/InformateDB/wsnJSONActualizarConImagenDesaparicion.php?";


        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDesaparicion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciodesaparicion.isLoaded()) {
                                        anunciodesaparicion.show();
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

                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = queseperdio_actualizar_desaparicion.getText().toString().trim();
                String estadoinput = estado_actualizar_desaparicion.getText().toString().trim();


                for (int h = 0; h<nombre.size();h++){

                    Log.i("Mostrar name------------------------------------------------------------------",nombre.get(h));

                    Log.i("Mostrar**********************************************************************",cadena.get(h));

                }



                Map<String,String> parametros = new HashMap<>();
                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos",descripcion2input);
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");

                for (int h = 0; h<nombre.size();h++){

                    parametros.put(nombre.get(h),cadena.get(h));
                }




                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        request_desaparicion.add(stringRequest_desaparicion);


    }
    private void cargarActualizarSinImagen_desaparicion(){

        String url_desaparicion = "http://192.168.0.18/InformateDB/wsnJSONActualizarSinImagenDesaparicion.php?";


        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDesaparicion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciodesaparicion.isLoaded()) {
                                        anunciodesaparicion.show();
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

                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = queseperdio_actualizar_desaparicion.getText().toString().trim();
                String estadoinput = estado_actualizar_desaparicion.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();


                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos",descripcion2input);
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");


                return parametros;
            }
        };

        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        request_desaparicion.add(stringRequest_desaparicion);


    }

    public void Subirimagen_desaparicion_update(){


        listaBase64_desaparicion.clear();
        nombre.clear();
        cadena.clear();
        //Tratar de solucionar el borrado de los arreglos de envio
        for (int i = 0; i < listaimagenes_desaparicion.size(); i++){

            try {

                InputStream is = getContentResolver().openInputStream(listaimagenes_desaparicion.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

//Solucionar para poder guardar

                nombre.add( "imagen_desaparecidos"+i);

                cadena.add(convertirUriEnBase64(bitmap));

                bitmap.recycle();


            }catch (IOException e){

            }

        }
        cargarActualizarConImagen_desaparicion();

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
                    Toast.makeText(ActualizarDesaparicion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                imagenesdesaparicionUri = data.getData();
                listaimagenes_desaparicion.add(imagenesdesaparicionUri);
            }else {
                for (int i = 0; i< 3; i++){
                    listaimagenes_desaparicion.add(clipData.getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(ActualizarDesaparicion.this,listaimagenes_desaparicion);
        gvImagenes_desaparicion.setAdapter(baseAdapter);



    }




}