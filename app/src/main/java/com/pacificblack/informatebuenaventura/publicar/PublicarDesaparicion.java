package com.pacificblack.informatebuenaventura.publicar;
//Todo: Clase completamente lista.
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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

import okhttp3.internal.Util;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Avisos.Whatsapp;
import static com.pacificblack.informatebuenaventura.texto.Avisos.caracteres_especiales;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.recompensa_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.ultimolugar_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;
public class PublicarDesaparicion extends AppCompatActivity {
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;
    ProgressBar cargandopublicar;
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
    EditText diadesa_publicar_desaparicion;
    public static String dia_desaparicion;
    Button publicar_final_desaparicion,subirimagenes;
    RadioButton opcion1_desaparicion,opcion2_desaparicion,opcion3_desaparicion,opcion4_desaparicion; String estado_texto = "Ninguno";
    RadioButton opcion1_desaparicion_que,opcion2_desaparicion_que,opcion3_desaparicion_que,opcion4_desaparicion_que,opcion5_desaparicion_que; String estado_texto_que = "Ninguno";
    Toolbar barra_desaparicion;
    ImageView whatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_desaparicion);

        whatsapp = findViewById(R.id.whatsapp_publicar_desaparicion);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(PublicarDesaparicion.this,Whatsapp);
            }
        });
        barra_desaparicion = findViewById(R.id.toolbar_publicar_desaparicion);
        cargandopublicar = findViewById(R.id.CargandoPublicar_desaparicion);
        barra_desaparicion.setTitle("Publicar Desaparicion");
        cargandopublicar.setVisibility(View.GONE);
        titulo_publicar_desaparicion = findViewById(R.id.publicar_titulo_desaparicion);
        descripcioncorta_publicar_desaparicion= findViewById(R.id.publicar_descripcioncorta_desaparicion);
        recompensa_publicar_desaparicion = findViewById(R.id.publicar_recompensa_desaparicion);
        diadesa_publicar_desaparicion = findViewById(R.id.publicar_fechade_desaparicion);
        ultimolugar_publicar_desaparicion = findViewById(R.id.publicar_ultimolugar_desaparicion);
        descripcion1_publicar_desaparicion = findViewById(R.id.publicar_descripcion_desaparicion);
        descripcion2_publicar_desaparicion = findViewById(R.id.publicar_descripcionextra_desaparicion);
        gvImagenes_desaparicion = findViewById(R.id.grid_desaparicion);
        subirimagenes = findViewById(R.id.subir_imagenes_desaparicion);
        opcion1_desaparicion = findViewById(R.id.opcion1_estado_desaparicion); opcion1_desaparicion.setText("Desaparecido");
        opcion2_desaparicion = findViewById(R.id.opcion2_estado_desaparicion); opcion2_desaparicion.setText("Escondido");
        opcion3_desaparicion = findViewById(R.id.opcion3_estado_desaparicion); opcion3_desaparicion.setText("Robado");
        opcion4_desaparicion = findViewById(R.id.opcion4_estado_desaparicion); opcion4_desaparicion.setText("Secuestrado");
        opcion1_desaparicion_que = findViewById(R.id.opcion1_estado_desaparicion_que); opcion1_desaparicion_que.setText("Animal");
        opcion2_desaparicion_que = findViewById(R.id.opcion2_estado_desaparicion_que); opcion2_desaparicion_que.setText("Persona");
        opcion3_desaparicion_que = findViewById(R.id.opcion3_estado_desaparicion_que); opcion3_desaparicion_que.setText("Documentos");
        opcion4_desaparicion_que = findViewById(R.id.opcion4_estado_desaparicion_que); opcion4_desaparicion_que.setText("Vehiculo");
        opcion5_desaparicion_que = findViewById(R.id.opcion5_estado_desaparicion_que); opcion5_desaparicion_que.setText("Otro objeto");
        diadesa_publicar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mostrarDialogoFecha();
            }
        });
        final Calendar calendario = Calendar.getInstance();
        ultimoAnio = calendario.get(Calendar.YEAR);
        ultimoMes = calendario.get(Calendar.MONTH);
        ultimoDiaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
        refrescarFechaEnEditText();
        publicar_final_desaparicion = findViewById(R.id.publicar_final_desaparicion);
        subirimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisos,PERMISSON_CODE);
                    }else {seleccionarimagen(); }
                }else{ seleccionarimagen(); }
            }
        });
        publicar_final_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo()| !validardescripcioncorta()| !validarrecompensa()|  ! validarultimolugar()| ! validardescripcion1()| ! validarqueseperdio()| ! validarestado()| ! validarfoto()){return;}
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
            titulo_publicar_desaparicion.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_publicar_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            titulo_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_desaparicion.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_publicar_desaparicion.setError(texto_superado);
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
            recompensa_publicar_desaparicion.setError(recompensa_vacio);
            return false;
        }
        else if(recompensainput.length()>15){
            recompensa_publicar_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            recompensa_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarultimolugar(){
        String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
        if (ultimolugarinput.isEmpty()){
            ultimolugar_publicar_desaparicion.setError(ultimolugar_vacio);
            return false;
        }
        else if(ultimolugarinput.length()>250){
            ultimolugar_publicar_desaparicion.setError(texto_superado);
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
            descripcion1_publicar_desaparicion.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){
            descripcion1_publicar_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_publicar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarqueseperdio() {
        if (opcion1_desaparicion_que.isChecked() || opcion2_desaparicion_que.isChecked() || opcion3_desaparicion_que.isChecked() || opcion4_desaparicion_que.isChecked() || opcion5_desaparicion_que.isChecked()){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Marque que se perdio",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarestado(){
        if (opcion1_desaparicion.isChecked() || opcion2_desaparicion.isChecked() || opcion3_desaparicion.isChecked() || opcion4_desaparicion.isChecked()){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Selecione el estado actual de esta publicacion",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarfoto(){
        if (listaimagenes_desaparicion.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else { return true;}
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
        if (nombre.size() == 1){ cargarWebService_desaparicion_uno(); CargandoSubida("Ver"); }
        if (nombre.size() == 2){ cargarWebService_desaparicion_dos(); CargandoSubida("Ver"); }
        if (nombre.size() == 3){ cargarWebService_desaparicion_tres();CargandoSubida("Ver"); }
        if (nombre.size() == 4){ cargarWebService_desaparicion(); CargandoSubida("Ver"); }
        if (nombre.size()>4){ Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show(); }
    }
    private void cargarWebService_desaparicion_uno() {

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString(); }
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString(); }
        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONRegistroDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDesaparicion.this);
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
                            if (anuncioActualizardesaparicion.isLoaded()) { anuncioActualizardesaparicion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
                    CargandoSubida("Ocultar");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos","Vacio");
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put("imagen_desaparecidos1","vacio");
                parametros.put("imagen_desaparecidos2","vacio");
                parametros.put("imagen_desaparecidos3","vacio");

                return parametros;
            }
        };
        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_desaparicion);
    }
    private void cargarWebService_desaparicion_dos() {

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString(); }
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString(); }
        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONRegistroDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDesaparicion.this);
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
                            if (anuncioActualizardesaparicion.isLoaded()) { anuncioActualizardesaparicion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
                    CargandoSubida("Ocultar");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos","vacio");
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put("imagen_desaparecidos2","vacio");
                parametros.put("imagen_desaparecidos3","vacio");

                return parametros;
            }
        };
        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_desaparicion);
    }
    private void cargarWebService_desaparicion_tres() {

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString(); }
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString(); }
        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONRegistroDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDesaparicion.this);
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
                            if (anuncioActualizardesaparicion.isLoaded()) { anuncioActualizardesaparicion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
                    CargandoSubida("Ocultar");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos","vacio");
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put("imagen_desaparecidos3","vacio");

                return parametros;
            }
        };
        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_desaparicion);
    }
    private void cargarWebService_desaparicion() {

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString(); }
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString(); }
        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONRegistroDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrado exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarDesaparicion.this);
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
                            if (anuncioActualizardesaparicion.isLoaded()) { anuncioActualizardesaparicion.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("SA",response.toString());
                   CargandoSubida("Ocultar");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_publicar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_publicar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_publicar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("titulo_desaparecidos",tituloinput);
                parametros.put("descripcionrow_desaparecidos",descripcioncortainput);
                parametros.put("recompensa_desaparecidos",recompensainput);
                parametros.put("vistas_desaparecidos","0");
                parametros.put("fechadesaparecido_desaparecidos",dia_desaparicion);
                parametros.put("ultimolugar_desaparecidos",ultimolugarinput);
                parametros.put("descripcion1_desaparecidos",descripcion1input);
                parametros.put("descripcion2_desaparecidos","Vacio");
                parametros.put("que_desaparecidos",queseperdioinput);
                parametros.put("estado_desaparecidos",estadoinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Desaparicion");
                parametros.put(nombre.get(0),cadena.get(0));
                parametros.put(nombre.get(1),cadena.get(1));
                parametros.put(nombre.get(2),cadena.get(2));
                parametros.put(nombre.get(3),cadena.get(3));

                return parametros;
            }
        };
        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_desaparicion);
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


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesdesaparicionUri = data.getData();
                listaimagenes_desaparicion.add(imagenesdesaparicionUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_desaparicion.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }

        baseAdapter = new GridViewAdapter(PublicarDesaparicion.this,listaimagenes_desaparicion);
        gvImagenes_desaparicion.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){

        if(Mostrar.equals("Ver")){
            cargandopublicar.setVisibility(View.VISIBLE);
            cargandopublicar.isShown();
            final int totalProgressTime = 100;
            final Thread t = new Thread() {
                @Override
                public void run() {
                    int jumpTime = 0;

                    while(jumpTime < totalProgressTime) {
                        try {
                            jumpTime += 5;
                            cargandopublicar.setProgress(jumpTime);
                            sleep(200);
                        }
                        catch (InterruptedException e) {
                            Log.e("Cargando Barra", e.getMessage());
                        }
                    }
                }
            };
            t.start();

        }if(Mostrar.equals("Ocultar")){ cargandopublicar.setVisibility(View.GONE);        }
    }
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            ultimoAnio = year;
            ultimoMes = month;
            ultimoDiaDelMes = dayOfMonth;
            refrescarFechaEnEditText();
        }
    };
    private void refrescarFechaEnEditText() {
        String fecha = ultimoAnio+"/"+(ultimoMes+1)+"/"+ultimoDiaDelMes;
        diadesa_publicar_desaparicion.setText(fecha);
        dia_desaparicion = fecha;
    }
    private void mostrarDialogoFecha() {
        DatePickerDialog dialogoFecha = new DatePickerDialog(this, listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
        dialogoFecha.show();

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