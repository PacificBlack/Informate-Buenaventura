package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaServicios;
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

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarServicios extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    GridView gvImagenes_servicios;
    Uri imagenesserviciosUri;
    List<Uri> listaimagenes_servicios =  new ArrayList<>();
    List<String> listaBase64_servicios = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_servicios;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    TextInputLayout id_actualizar_servicios,titulo_actualizar_servicios,descripcioncorta_actualizar_servicios;
    AutoCompleteTextView necesidad_actualizar_servicios;
    Button subirimagenes;
    ImageButton actualizar_servicios,actualizar_buscar_servicios;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_servicios;
    private InterstitialAd anuncioservicios;
    private ProgressDialog servicios;
    String servi[] = new String[]{"Hoy mismo","Cuando quiera","Cada 3 años"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_servicios);

        titulo_actualizar_servicios = findViewById(R.id.actualizar_titulo_servicios);
        descripcioncorta_actualizar_servicios = findViewById(R.id.actualizar_descripcion_servicios);
        necesidad_actualizar_servicios = findViewById(R.id.actualizar_necesidad_servicios);
        subirimagenes = findViewById(R.id.subir_imagenes_actualizar_servicios);
        id_actualizar_servicios = findViewById(R.id.id_actualizar_servicios);
        actualizar_servicios = findViewById(R.id.actualizar_final_servicios);
        actualizar_buscar_servicios = findViewById(R.id.actualizar_buscar_servicios);
        imagen1_actualizar_servicios = findViewById(R.id.imagen1_actualizar_servicios);


        actualizar_servicios.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
         if (!validartitulo() | !validardescripcion() | !validarnececidad() | !validarid()) { return;
          }
          if (!validarfotoupdate()){
              AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarServicios.this);
               mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
                .setCancelable(false).setNegativeButton("Modificar tambien las imagenes", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                                    Subirimagen_servicios_update();

              }
                   }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                           cargarActualizarSinImagen_servicios();
                                                                  CargandoSubida("Ver");

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
        actualizar_buscar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_servicios();
                CargandoSubida("Ver");

            }
        });

        anuncioservicios = new InterstitialAd(this);
        anuncioservicios.setAdUnitId(AnuncioActualizar);
        anuncioservicios.loadAd(new AdRequest.Builder().build());


        gvImagenes_servicios = findViewById(R.id.grid_actualizar_servicios);
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
        String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_actualizar_servicios.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            id_actualizar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            id_actualizar_servicios.setError(null);
            return true;
        }
    }


    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_actualizar_servicios.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_servicios.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_actualizar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        String necesidadinput = necesidad_actualizar_servicios.getText().toString().trim();

        if (necesidadinput.isEmpty()) {
            necesidad_actualizar_servicios.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (necesidadinput.length() > 15) {

            necesidad_actualizar_servicios.setError("" + R.string.supera);
            return false;
        } else {
            necesidad_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_servicios.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }

    }

    private void cargarBusqueda_servicios() {
        String url_buscar_servicios = DireccionServidor+"wsnJSONBuscarServicios.php?id_ofertaservicios="+id_actualizar_servicios.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_servicios,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        CargandoSubida("Ocultar");

    }
    @Override
    public void onResponse(JSONObject response) {
        OfertaServicios servicios = new OfertaServicios();

        JSONArray json = response.optJSONArray("servicios");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            servicios.setId_servicios(jsonObject.getInt("id_ofertaservicios"));
            servicios.setTitulo_row_ofertaservicios(jsonObject.getString("titulo_ofertaservicios"));
            servicios.setDescripcion_row_ofertaservicios(jsonObject.getString("descripcionrow_ofertaservicios"));
            servicios.setFechapublicacion_row_ofertaservicios(jsonObject.getString("fechapublicacion_ofertaservicios"));
            servicios.setNecesidad_row_ofertaservicios(jsonObject.getString("necesidad_ofertaservicios"));
            servicios.setImagen1_ofertaservicios(jsonObject.getString("imagen1_ofertaservicios"));
            servicios.setVistas_ofertaservicios(jsonObject.getInt("vistas_ofertaservicios"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_actualizar_servicios.getEditText().setText(servicios.getTitulo_row_ofertaservicios());
        descripcioncorta_actualizar_servicios.getEditText().setText(servicios.getDescripcion_row_ofertaservicios());
        necesidad_actualizar_servicios.setText(servicios.getNecesidad_row_ofertaservicios());
        Picasso.get().load(servicios.getImagen1_ofertaservicios())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_servicios);

        CargandoSubida("Ocultar");

    }


    private void cargarActualizarConImagen_servicios() {

        String url_servicios = DireccionServidor+"wsnJSONActualizarConImagenServicios.php?";
        stringRequest_servicios= new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    CargandoSubida("Ocultar");

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarServicios.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioservicios.isLoaded()) {
                                        anuncioservicios.show();
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

                String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_actualizar_servicios.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_ofertaservicios",idinput);
                parametros.put("titulo_servicios",tituloinput);
                parametros.put("descripcionrow_servicios",descripcioncortainput);
                parametros.put("vistas_servicios","0");
                parametros.put("necesidad_servicios",necesidadinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Servicios");

                for (int h = 0; h<nombre.size();h++){
                    parametros.put(nombre.get(h),cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_servicios = Volley.newRequestQueue(this);
        stringRequest_servicios.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_servicios.add(stringRequest_servicios);

    }


    private void cargarActualizarSinImagen_servicios() {

        String url_servicios = DireccionServidor+"wsnJSONActualizarSinnImagenServicios.php?";

        stringRequest_servicios= new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    CargandoSubida("Ocultar");


                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarServicios.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioservicios.isLoaded()) {
                                        anuncioservicios.show();
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

                String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_actualizar_servicios.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_ofertaservicios",idinput);
                parametros.put("titulo_servicios",tituloinput);
                parametros.put("descripcionrow_servicios",descripcioncortainput);
                parametros.put("vistas_servicios","0");
                parametros.put("necesidad_servicios",necesidadinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Servicios");

                return parametros;
            }
        };

        RequestQueue request_servicios = Volley.newRequestQueue(this);
        stringRequest_servicios.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_servicios.add(stringRequest_servicios);

    }

    public void Subirimagen_servicios_update(){

        listaBase64_servicios.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_servicios.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_servicios.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                nombre.add( "imagen_servicios"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        if (nombre.size() == 1) {
            cargarActualizarConImagen_servicios();
            CargandoSubida("Ver");
        }
        if (nombre.size()>1){
            Toast.makeText(getApplicationContext(),"Solo se pueden subir 3 imagenes, por favor borre una",Toast.LENGTH_LONG).show();
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
                    //Permiso autorizado
                    seleccionarimagen();
                }
                else{
                    //Permiso denegado
                    Toast.makeText(ActualizarServicios.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesserviciosUri = data.getData();
                listaimagenes_servicios.add(imagenesserviciosUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_servicios.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarServicios.this,listaimagenes_servicios);
        gvImagenes_servicios.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        servicios=new ProgressDialog(this);
        servicios.setMessage("Subiendo su Empleos");
        servicios.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        servicios.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            servicios.show();
        } if(Mostrar.equals("Ocultar")){
            servicios.hide();
        }
    }

}
