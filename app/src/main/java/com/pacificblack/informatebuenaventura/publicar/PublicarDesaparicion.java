package com.pacificblack.informatebuenaventura.publicar;

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
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
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
import android.widget.TextView;
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
import com.pacificblack.informatebuenaventura.MainActivity;
import com.pacificblack.informatebuenaventura.R;

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

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes

public class PublicarDesaparicion extends AppCompatActivity {

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
    private InterstitialAd anuncioActualizardesaparicion;

    TextInputLayout titulo_publicar_desaparicion, descripcioncorta_publicar_desaparicion, recompensa_publicar_desaparicion, ultimolugar_publicar_desaparicion, descripcion1_publicar_desaparicion, descripcion2_publicar_desaparicion;
    TextView  diadesa_publicar_desaparicion;
    String dia_desaparicion;
    DatePickerDialog.OnDateSetListener dateSetListener;
    AutoCompleteTextView queseperdio_publicar_desaparicion, estado_publicar_desaparicion;
    Button publicar_final_desaparicion,subirimagenes;
    String estado[] = new String[]{"Desaparecido","Encontrado"};
    String quees[]  = new String[]{"Animal","Persona","Dococumento","Vehiculo","Otro objeto"};

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
        gvImagenes_desaparicion = findViewById(R.id.grid_desaparicion);
        subirimagenes = findViewById(R.id.subir_imagenes_desaparicion);
        queseperdio_publicar_desaparicion = findViewById(R.id.publicar_quese_desapariciom);
        publicar_final_desaparicion = findViewById(R.id.publicar_final_desaparicion);
        estado_publicar_desaparicion = findViewById(R.id.publicar_estadodes_desapariciom);

        diadesa_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int año = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PublicarDesaparicion.this, android.R.style.Theme_Material_Dialog_MinWidth,dateSetListener, año,mes,dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int mesi =month+1;
                dia_desaparicion = year+"/"+mesi+"/"+dayOfMonth;

                diadesa_publicar_desaparicion.setText(dia_desaparicion);

            }
        };

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

        ArrayAdapter<String>adapterque = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,quees);
        queseperdio_publicar_desaparicion.setAdapter(adapterque);
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


        publicar_final_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo()| !validardescripcioncorta()| !validarrecompensa()| !validardiadesa()| ! validarultimolugar()| ! validardescripcion1()| ! validardescripcion2()| ! validarqueseperdio()| ! validarestado()| ! validarfoto()){return;}
                Subirimagen_desaparicion();
            }
        });


        anuncioActualizardesaparicion = new InterstitialAd(this);
        anuncioActualizardesaparicion.setAdUnitId(AnuncioActualizar);
        anuncioActualizardesaparicion.loadAd(new AdRequest.Builder().build());

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

        if (dia_desaparicion.isEmpty()){
            diadesa_publicar_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
          return true;
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
    private boolean validarfoto(){

        if (listaimagenes_desaparicion.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 3 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_desaparicion.size() > 3){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 3 imagenes",Toast.LENGTH_LONG).show();
            return true;
        }

        else if (listaimagenes_desaparicion.size() < 3){
            Toast.makeText(getApplicationContext(),"Has agregado"+listaimagenes_desaparicion.size()+"imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return false;

        }

        else {
            return true;}

    }

    private void cargarWebService_desaparicion() {

        String url_desaparicion = DireccionServidor+"wsnJSONRegistroDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarDesaparicion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioActualizardesaparicion.isLoaded()) {
                                        anuncioActualizardesaparicion.show();
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
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion2input = descripcion2_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = queseperdio_publicar_desaparicion.getText().toString().trim();
                String estadoinput = estado_publicar_desaparicion.getText().toString().trim();

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
    public void Subirimagen_desaparicion(){


        listaBase64_desaparicion.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_desaparicion.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_desaparicion.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add("imagen_desaparecidos" + i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){

            }
        }
        cargarWebService_desaparicion();
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
                    Toast.makeText(PublicarDesaparicion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
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

        baseAdapter = new GridViewAdapter(PublicarDesaparicion.this,listaimagenes_desaparicion);
        gvImagenes_desaparicion.setAdapter(baseAdapter);
    }
}