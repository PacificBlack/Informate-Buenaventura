package com.pacificblack.informatebuenaventura.actualizar;
//Todo: Clase completamente lista.

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
import com.pacificblack.informatebuenaventura.publicar.PublicarDesaparicion;
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

import okhttp3.internal.Util;

import static com.pacificblack.informatebuenaventura.texto.Avisos.Whatsapp;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar;
import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_imagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_noimagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.queseperdio_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.recompensa_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.ultimolugar_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;

public class ActualizarDesaparicion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;
    ProgressBar cargandoactualizar;
    RadioButton opcion1_desaparicion,opcion2_desaparicion,opcion3_desaparicion,opcion4_desaparicion,opcion5_desaparicion; String estado_texto = "Ninguno";
    RadioButton opcion1_desaparicion_que,opcion2_desaparicion_que,opcion3_desaparicion_que,opcion4_desaparicion_que,opcion5_desaparicion_que; String estado_texto_que = "Ninguno";
    TextInputLayout titulo_actualizar_desaparicion, descripcioncorta_actualizar_desaparicion, recompensa_actualizar_desaparicion, ultimolugar_actualizar_desaparicion, descripcion1_actualizar_desaparicion, descripcion2_actualizar_desaparicion, buscar_actualizar_desaparicion;
    EditText diadesa_actualizar_desaparicion;
    public static String dia_desaparicion;
    Button actualizar_editar_desaparicion,subirimagenes;
    ImageButton  buscar_desaparicion;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_desaparicion,imagen2_actualizar_desaparicion, imagen3_actualizar_desaparicion;
    private InterstitialAd anunciodesaparicion;
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
    Toolbar barra_desaparicion;
    ImageView whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_desaparicion);

        whatsapp = findViewById(R.id.whatsapp_actualizar_desaparicion);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarDesaparicion.this,Whatsapp);
            }
        });
        cargandoactualizar = findViewById(R.id.CargandoActualizar_desaparicion);
        cargandoactualizar.setVisibility(View.GONE);
        barra_desaparicion = findViewById(R.id.toolbar_publicar_desaparicion);
        barra_desaparicion.setTitle("Actualizar Desaparicion");
        titulo_actualizar_desaparicion = findViewById(R.id.actualizar_titulo_desaparicion);
        descripcioncorta_actualizar_desaparicion= findViewById(R.id.actualizar_descripcioncorta_desaparicion);
        recompensa_actualizar_desaparicion = findViewById(R.id.actualizar_recompensa_desaparicion);
        diadesa_actualizar_desaparicion = findViewById(R.id.actualizar_fechade_desaparicion);
        ultimolugar_actualizar_desaparicion = findViewById(R.id.actualizar_ultimolugar_desaparicion);
        descripcion1_actualizar_desaparicion = findViewById(R.id.actualizar_descripcion_desaparicion);
        descripcion2_actualizar_desaparicion = findViewById(R.id.actualizar_descripcionextra_desaparicion);
        imagen1_actualizar_desaparicion = findViewById(R.id.imagen1_actualizar_desaparicion);
        imagen2_actualizar_desaparicion = findViewById(R.id.imagen2_actualizar_desaparicion);
        imagen3_actualizar_desaparicion = findViewById(R.id.imagen3_actualizar_desaparicion);
        buscar_actualizar_desaparicion = findViewById(R.id.actualizar_id_desaparicion);
        actualizar_editar_desaparicion = findViewById(R.id.actualizar_final_desaparicion);
        buscar_desaparicion = findViewById(R.id.actualizar_buscar_desaparicion);
        gvImagenes_desaparicion = findViewById(R.id.grid_actualizar_desaparicion);
        subirimagenes = findViewById(R.id.actualizar_imagenes_desaparicion);
        opcion1_desaparicion = findViewById(R.id.opcion1_estado_desaparicion_actualizar); opcion1_desaparicion.setText("Desaparecido");
        opcion2_desaparicion = findViewById(R.id.opcion2_estado_desaparicion_actualizar); opcion2_desaparicion.setText("Escondido");
        opcion3_desaparicion = findViewById(R.id.opcion3_estado_desaparicion_actualizar); opcion3_desaparicion.setText("Robado");
        opcion4_desaparicion = findViewById(R.id.opcion4_estado_desaparicion_actualizar); opcion4_desaparicion.setText("Secuestrado");
        opcion5_desaparicion = findViewById(R.id.opcion5_estado_desaparicion_actualizar); opcion5_desaparicion.setText("Encontrado");
        opcion1_desaparicion_que = findViewById(R.id.opcion1_estado_desaparicion_que_actualizar); opcion1_desaparicion_que.setText("Animal");
        opcion2_desaparicion_que = findViewById(R.id.opcion2_estado_desaparicion_que_actualizar); opcion2_desaparicion_que.setText("Persona");
        opcion3_desaparicion_que = findViewById(R.id.opcion3_estado_desaparicion_que_actualizar); opcion3_desaparicion_que.setText("Documentos");
        opcion4_desaparicion_que = findViewById(R.id.opcion4_estado_desaparicion_que_actualizar); opcion4_desaparicion_que.setText("Vehiculo");
        opcion5_desaparicion_que = findViewById(R.id.opcion5_estado_desaparicion_que_actualizar); opcion5_desaparicion_que.setText("Otro objeto");
        diadesa_actualizar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mostrarDialogoFecha(); }
        });
        actualizar_editar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()| !validartitulo()| !validardescripcioncorta()| !validarrecompensa()| ! validarultimolugar()| ! validardescripcion1()| ! validarqueseperdio()| ! validarestado()){return;}
                if (!validarfotoupdate()){
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarDesaparicion.this);
                    mensaje.setMessage(aviso_actualizar).setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Subirimagen_desaparicion_update();
                        }
                    }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cargarActualizarSinImagen_desaparicion();
                            CargandoSubida("Ver");
                        }
                    });
                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Modificar Publicación");
                    titulo.show();
                    return;
                }
            }
        });
        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        buscar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_desaparicion();
                CargandoSubida("Ver");
            }
        });
        anunciodesaparicion = new InterstitialAd(this);
        anunciodesaparicion.setAdUnitId(AnuncioActualizar);
        anunciodesaparicion.loadAd(new AdRequest.Builder().build());
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
    }

    private boolean validarid(){
        String idinput = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_actualizar_desaparicion.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_actualizar_desaparicion.setError(texto_superado);
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
            titulo_actualizar_desaparicion.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_desaparicion.setError(texto_superado);
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
            descripcioncorta_actualizar_desaparicion.setError(descripcion_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>150){
            descripcioncorta_actualizar_desaparicion.setError(texto_superado);
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
            recompensa_actualizar_desaparicion.setError(recompensa_vacio);
            return false;
        }
        else if(recompensainput.length()>15){
            recompensa_actualizar_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            recompensa_actualizar_desaparicion.setError(null);
            return true;
        }
    }
    private boolean validarultimolugar(){
        String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
        if (ultimolugarinput.isEmpty()){
            ultimolugar_actualizar_desaparicion.setError(ultimolugar_vacio);
            return false;
        }
        else if(ultimolugarinput.length()>250){
            ultimolugar_actualizar_desaparicion.setError(texto_superado);
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
            descripcion1_actualizar_desaparicion.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcion1input.length()>850){
            descripcion1_actualizar_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            descripcion1_actualizar_desaparicion.setError(null);
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
        if (opcion1_desaparicion.isChecked() || opcion2_desaparicion.isChecked() || opcion3_desaparicion.isChecked() || opcion4_desaparicion.isChecked() || opcion5_desaparicion.isChecked()){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Selecione el estado actual de esta publicacion",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_desaparicion.size() == 0){ Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show(); return false; }
        else { return true; }
    }
    private void cargarBusqueda_desaparicion() {
        String url_buscar_bienes = DireccionServidor+"wsnJSONBuscarDesaparicion.php?id_desaparecidos="+buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        CargandoSubida("Ocultar");
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
        dia_desaparicion = desaparecidos.getFechadesaparecido_desaparecidos();
        ultimolugar_actualizar_desaparicion.getEditText().setText(desaparecidos.getUltimolugar_desaparecidos());
        descripcion1_actualizar_desaparicion.getEditText().setText(desaparecidos.getDescripcion1_desaparecidos());
        descripcion2_actualizar_desaparicion.getEditText().setText(desaparecidos.getDescripcion2_desaparecidos());

        if (desaparecidos.getQue_desaparecidos().equals("Animal")){opcion1_desaparicion_que.setChecked(true);}
        if (desaparecidos.getQue_desaparecidos().equals("Persona")){opcion2_desaparicion_que.setChecked(true);}
        if (desaparecidos.getQue_desaparecidos().equals("Documentos")){opcion3_desaparicion_que.setChecked(true);}
        if (desaparecidos.getQue_desaparecidos().equals("Vehiculo")){opcion4_desaparicion_que.setChecked(true); }
        if (desaparecidos.getQue_desaparecidos().equals("Otro objeto")){ opcion5_desaparicion_que.setChecked(true);}

        if (desaparecidos.getEstado_desaparecidos().equals("Desaparecido")){opcion1_desaparicion.setChecked(true);}
        if (desaparecidos.getEstado_desaparecidos().equals("Escondido")){opcion2_desaparicion.setChecked(true);}
        if (desaparecidos.getEstado_desaparecidos().equals("Robado")){opcion3_desaparicion.setChecked(true);}
        if (desaparecidos.getEstado_desaparecidos().equals("Secuestrado")){opcion4_desaparicion.setChecked(true);}
        if (desaparecidos.getEstado_desaparecidos().equals("Encontrado")){opcion5_desaparicion.setChecked(true);}

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

        CargandoSubida("Ocultar");
    }
    public void Subirimagen_desaparicion_update(){
        listaBase64_desaparicion.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_desaparicion.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_desaparicion.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                nombre.add( "imagen_desaparecidos"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1){ cargarActualizarConImagen_desaparicion_uno(); CargandoSubida("Ver"); }
        if (nombre.size() == 2){ cargarActualizarConImagen_desaparicion_dos(); CargandoSubida("Ver"); }
        if (nombre.size() == 3){ cargarActualizarConImagen_desaparicion_tres(); CargandoSubida("Ver"); }
        if (nombre.size() == 4){ cargarActualizarConImagen_desaparicion(); CargandoSubida("Ver"); }
        if (nombre.size()>4){ Toast.makeText(getApplicationContext(),imagen_maxima +"4",Toast.LENGTH_LONG).show(); }
    }
    private void cargarActualizarConImagen_desaparicion_uno(){

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString(); }
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString(); }
        if (opcion5_desaparicion.isChecked()){ estado_texto = opcion5_desaparicion.getText().toString(); }
        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONActualizarConImagenDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarDesaparicion.this);
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
                            if (anunciodesaparicion.isLoaded()) {
                                anunciodesaparicion.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String iddesaparecidos = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_desaparecidos",iddesaparecidos);
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
    private void cargarActualizarConImagen_desaparicion_dos(){

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString();}
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString();}
        if (opcion5_desaparicion.isChecked()){ estado_texto = opcion5_desaparicion.getText().toString(); }

        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONActualizarConImagenDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarDesaparicion.this);
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
                            if (anunciodesaparicion.isLoaded()) {
                                anunciodesaparicion.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String iddesaparecidos = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();

                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_desaparecidos",iddesaparecidos);
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
    private void cargarActualizarConImagen_desaparicion_tres(){

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString();}
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString();}
        if (opcion5_desaparicion.isChecked()){ estado_texto = opcion5_desaparicion.getText().toString(); }

        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONActualizarConImagenDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarDesaparicion.this);
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
                            if (anunciodesaparicion.isLoaded()) {
                                anunciodesaparicion.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String iddesaparecidos = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_desaparecidos",iddesaparecidos);
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
    private void cargarActualizarConImagen_desaparicion(){

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString();}
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString();}
        if (opcion5_desaparicion.isChecked()){ estado_texto = opcion5_desaparicion.getText().toString(); }

        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }

        String url_desaparicion = DireccionServidor+"wsnJSONActualizarConImagenDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarDesaparicion.this);
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
                            if (anunciodesaparicion.isLoaded()) {
                                anunciodesaparicion.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String iddesaparecidos = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_desaparecidos",iddesaparecidos);
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
                parametros.put(nombre.get(3),cadena.get(3));

                return parametros;
            }
        };
        RequestQueue request_desaparicion = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_desaparicion.add(stringRequest_desaparicion);


    }
    private void cargarActualizarSinImagen_desaparicion(){

        if (opcion1_desaparicion.isChecked()){ estado_texto = opcion1_desaparicion.getText().toString(); }
        if (opcion2_desaparicion.isChecked()){ estado_texto = opcion2_desaparicion.getText().toString(); }
        if (opcion3_desaparicion.isChecked()){ estado_texto = opcion3_desaparicion.getText().toString();}
        if (opcion4_desaparicion.isChecked()){ estado_texto = opcion4_desaparicion.getText().toString();}
        if (opcion5_desaparicion.isChecked()){ estado_texto = opcion5_desaparicion.getText().toString(); }

        if (opcion1_desaparicion_que.isChecked()){ estado_texto_que = opcion1_desaparicion_que.getText().toString(); }
        if (opcion2_desaparicion_que.isChecked()){ estado_texto_que = opcion2_desaparicion_que.getText().toString(); }
        if (opcion3_desaparicion_que.isChecked()){ estado_texto_que = opcion3_desaparicion_que.getText().toString(); }
        if (opcion4_desaparicion_que.isChecked()){ estado_texto_que = opcion4_desaparicion_que.getText().toString(); }
        if (opcion5_desaparicion_que.isChecked()){ estado_texto_que = opcion5_desaparicion_que.getText().toString(); }


        String url_desaparicion = DireccionServidor+"wsnJSONActualizarSinImagenDesaparicion.php?";

        stringRequest_desaparicion= new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    CargandoSubida("Ocultar");

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarDesaparicion.this);
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
                            if (anunciodesaparicion.isLoaded()) {
                                anunciodesaparicion.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });

                    Log.i("Muestra",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
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

                String iddesaparecidos = buscar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_desaparicion.getEditText().getText().toString().trim();
                String recompensainput = recompensa_actualizar_desaparicion.getEditText().getText().toString().trim();
                String ultimolugarinput = ultimolugar_actualizar_desaparicion.getEditText().getText().toString().trim();
                String descripcion1input = descripcion1_actualizar_desaparicion.getEditText().getText().toString().trim();
                String queseperdioinput = estado_texto_que;
                String estadoinput = estado_texto;

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_desaparecidos",iddesaparecidos);
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
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ seleccionarimagen(); }
                else{ Toast.makeText(ActualizarDesaparicion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG); }
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
        baseAdapter = new GridViewAdapter(ActualizarDesaparicion.this,listaimagenes_desaparicion);
        gvImagenes_desaparicion.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){

        if(Mostrar.equals("Ver")){
            cargandoactualizar.setVisibility(View.VISIBLE);
            cargandoactualizar.isShown();
            final int totalProgressTime = 100;
            final Thread t = new Thread() {
                @Override
                public void run() {
                    int jumpTime = 0;

                    while(jumpTime < totalProgressTime) {
                        try {
                            jumpTime += 5;
                            cargandoactualizar.setProgress(jumpTime);
                            sleep(200);
                        }
                        catch (InterruptedException e) {
                            Log.e("Cargando Barra", e.getMessage());
                        }
                    }
                }
            };
            t.start();

        }if(Mostrar.equals("Ocultar")){ cargandoactualizar.setVisibility(View.GONE);        }
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
        diadesa_actualizar_desaparicion.setText(fecha);
        dia_desaparicion = fecha;
    }
    private void mostrarDialogoFecha() {
        DatePickerDialog dialogoFecha = new DatePickerDialog(this, listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
        dialogoFecha.show();
    }
    @SuppressLint("NewApi")
    public static void whatsapp(Activity activity, String phone) {
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