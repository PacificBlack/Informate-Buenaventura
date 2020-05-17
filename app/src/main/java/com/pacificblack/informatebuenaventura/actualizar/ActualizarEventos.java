package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ComponentName;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.pacificblack.informatebuenaventura.clases.eventos.Eventos;
import com.pacificblack.informatebuenaventura.extras.CargandoDialog;
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

import okhttp3.internal.Util;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Avisos.Whatsapp;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_imagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.aviso_actualizar_noimagen;
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.lugar_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarEventos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
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
    TextInputLayout titulo_actualizar_eventos,id_actualizar_eventos,descripcioncorta_actualizar_eventos,lugar_actualizar_eventos;
    Button actualizar_eventos,subirimagenes;
    ImageButton actualizar_buscar_eventos;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_eventos;
    private InterstitialAd anuncioeventos;
    Toolbar barra_eventos;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(ActualizarEventos.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_eventos);

        whatsapp = findViewById(R.id.whatsapp_actualizar_eventos);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarEventos.this,Whatsapp);
            }
        });
        barra_eventos = findViewById(R.id.toolbar_actualizar_eventos);
        barra_eventos.setTitle("Actualizar Eventos");
        titulo_actualizar_eventos = findViewById(R.id.actualizar_titulo_eventos);
        descripcioncorta_actualizar_eventos = findViewById(R.id.actualizar_descripcion_eventos);
        lugar_actualizar_eventos = findViewById(R.id.actualizar_lugar_eventos);
        subirimagenes = findViewById(R.id.actualizar_imagenes_eventos);
        id_actualizar_eventos = findViewById(R.id.actualizar_id_eventos);
        actualizar_eventos = findViewById(R.id.actualizar_final_eventos);
        actualizar_buscar_eventos = findViewById(R.id.actualizar_buscar_eventos);
        imagen1_actualizar_eventos = findViewById(R.id.imagen1_actualizar_eventos);

        actualizar_eventos.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
            if (!validartitulo() | !validardescripcion() | !validarlugar() | !validarid()) { return;
                }
                if (!validarfotoupdate()){
                          AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarEventos.this);
                           mensaje.setMessage(aviso_actualizar)
                            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                                     Subirimagen_eventos_update();
                          }
                                                          }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
               @Override
                public void onClick(DialogInterface dialog, int which) {
                           cargarActualizarSinImagen_eventos();
                   cargandoDialog.Mostrar();

               }
                                                          });
                           AlertDialog titulo2 = mensaje.create();
                            titulo2.setTitle("Modificar Publicación");
                             titulo2.show();
                                return;
                                                      }
                                }
                                              }
        );

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_eventos();
                cargandoDialog.Mostrar();

            }
        });

        anuncioeventos = new InterstitialAd(this);
        anuncioeventos.setAdUnitId("ca-app-pub-7236340326570289/5647238574");
        anuncioeventos.loadAd(new AdRequest.Builder().build());


        gvImagenes_eventos = findViewById(R.id.grid_eventos_actualizar);
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



    }

    private boolean validarid(){
        String idinput = id_actualizar_eventos.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_actualizar_eventos.setError(titulo_vacio);
            return false;
        }
        else if(idinput.length()>15){

            id_actualizar_eventos.setError(texto_superado);
            return false;
        }
        else {
            id_actualizar_eventos.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_eventos.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_actualizar_eventos.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_eventos.setError(texto_superado);
            return false;
        }
        else {
            titulo_actualizar_eventos.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_actualizar_eventos.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_eventos.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>700){

            descripcioncorta_actualizar_eventos.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_actualizar_eventos.setError(null);
            return true;
        }
    }
    private boolean validarlugar(){
        String lugarinput = lugar_actualizar_eventos.getEditText().toString().trim();

        if (lugarinput.isEmpty()) {
            lugar_actualizar_eventos.setError(lugar_vacio);
            return false;
        } else if (lugarinput.length() > 120) {
            lugar_actualizar_eventos.setError(texto_superado);
            return false;
        } else {
            lugar_actualizar_eventos.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_eventos.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }
    private void cargarBusqueda_eventos() {
        String url_buscar_eventos = DireccionServidor+"wsnJSONBuscarEventos.php?id_eventos="+id_actualizar_eventos.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_eventos,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargandoDialog.Ocultar();

    }
    @Override
    public void onResponse(JSONObject response) {
        Eventos eventos = new Eventos();

        JSONArray json = response.optJSONArray("eventos");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            eventos.setId_eventos(jsonObject.getInt("id_eventos"));
            eventos.setTitulo_row_eventos(jsonObject.getString("titulo_eventos"));
            eventos.setDescripcion_row_eventos(jsonObject.getString("descripcionrow_eventos"));
            eventos.setFechapublicacion_row_eventos(jsonObject.getString("fechapublicacion_eventos"));
            eventos.setLugar_row_eventos(jsonObject.getString("lugar_eventos"));
            eventos.setImagen1_eventos(jsonObject.getString("imagen1_eventos"));
            eventos.setVistas_eventos(jsonObject.getInt("vistas_eventos"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_actualizar_eventos.getEditText().setText(eventos.getTitulo_row_eventos());
        descripcioncorta_actualizar_eventos.getEditText().setText(eventos.getDescripcion_row_eventos());
        lugar_actualizar_eventos.getEditText().setText(eventos.getLugar_row_eventos());
        Picasso.get().load(eventos.getImagen1_eventos())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_actualizar_eventos);

        cargandoDialog.Ocultar();

    }

    private void cargarActualizarConImagen_eventos() {

        String url_eventos = DireccionServidor+"wsnJSONActualizarConImagenEventos.php?";

        stringRequest_eventos= new StringRequest(Request.Method.POST, url_eventos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarEventos.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);

                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioeventos.isLoaded()) { anuncioeventos.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_eventos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_eventos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_eventos.getEditText().getText().toString().trim();
                String lugarinput = lugar_actualizar_eventos.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();


                parametros.put("id_eventos",idinput);
                parametros.put("titulo_eventos",tituloinput);
                parametros.put("descripcionrow_eventos",descripcioncortainput);
                parametros.put("lugar_eventos",lugarinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Eventos");
                for (int h = 0; h<nombre.size();h++){
                    parametros.put(nombre.get(h),cadena.get(h));
                }
                return parametros;
            }
        };
        RequestQueue request_eventos = Volley.newRequestQueue(this);
        stringRequest_eventos.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_eventos.add(stringRequest_eventos);

    }
    private void cargarActualizarSinImagen_eventos() {

        String url_eventos = DireccionServidor+"wsnJSONActualizarSinnImagenEventos.php?";

        stringRequest_eventos= new StringRequest(Request.Method.POST, url_eventos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarEventos.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
                    builder.setCancelable(false);
                    builder.setView(view);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    ImageView dialogimagen = view.findViewById(R.id.imagendialog);

                    TextView txt = view.findViewById(R.id.texto_dialog);
                    txt.setText(response);
                    Button btnEntendido = view.findViewById(R.id.btentiendo);
                    btnEntendido.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            if (anuncioeventos.isLoaded()) { anuncioeventos.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoActualizar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_eventos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_eventos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_eventos.getEditText().getText().toString().trim();
                String lugarinput = lugar_actualizar_eventos.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_eventos",idinput);
                parametros.put("titulo_eventos",tituloinput);
                parametros.put("descripcionrow_eventos",descripcioncortainput);
                parametros.put("lugar_eventos",lugarinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Eventos");

                return parametros;
            }
        };
        RequestQueue request_eventos = Volley.newRequestQueue(this);
        stringRequest_eventos.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_eventos.add(stringRequest_eventos);

    }

    public void Subirimagen_eventos_update(){

        listaBase64_eventos.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_eventos.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_eventos.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                nombre.add( "imagen_eventos"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1) {
            cargarActualizarConImagen_eventos();
            cargandoDialog.Mostrar();
        }
        if (nombre.size()>1){
            Toast.makeText(getApplicationContext(),imagen_maxima+ "1",Toast.LENGTH_LONG).show();
        }
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
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(ActualizarEventos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imageneseventosUri = data.getData();
                listaimagenes_eventos.add(imageneseventosUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_eventos.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarEventos.this,listaimagenes_eventos);
        gvImagenes_eventos.setAdapter(baseAdapter);
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
