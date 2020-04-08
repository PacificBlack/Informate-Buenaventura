package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.AdaptadoresGrid.GridViewAdapter;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaEmpleos;
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

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes


public class ActualizarEmpleos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{


    TextInputLayout titulo_actualizar_empleos, descripcioncorta_actualizar_empleos, id_actualizar_empleos;
    AutoCompleteTextView necesidad_actualizar_empleos;
    Button subirimagenes;
    String nece[] = new String[]{"Urgente","Rapido","Para hoy mismo"};

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

    ImageButton actualizar_empleos,actualizar_buscar_empleos;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
        ImageView imagen1_actualizar_empleos;
    private InterstitialAd anuncioempleos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_empleos);

        titulo_actualizar_empleos = findViewById(R.id.actualizar_titulo_empleos);
        descripcioncorta_actualizar_empleos = findViewById(R.id.actualizar_descripcion_empleos);
        necesidad_actualizar_empleos = findViewById(R.id.actualizar_necesidad_empleos);
        subirimagenes = findViewById(R.id.subir_imagenes_actualizar_empleos);
        id_actualizar_empleos = findViewById(R.id.actualizar_id_empleos);
        actualizar_empleos = findViewById(R.id.actualizar_empleos);
        actualizar_buscar_empleos = findViewById(R.id.actualizar_buscar_empleos);
        imagen1_actualizar_empleos = findViewById(R.id.imagen1_actualizar_empleos);

    actualizar_empleos.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
        if (!validartitulo() | !validardescripcion() | !validarnececidad() | !validarid()) { return;
            }

        if (!validarfotoupdate()){
             AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarEmpleos.this);
            mensaje.setMessage("¿Desea modificar Su publicacion y las imagenes?")
            .setCancelable(false).setNegativeButton("Modificar tambien las imagen", new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog, int which) {

         if (listaimagenes_empleos.size() == 1){
               Subirimagen_empleos_update();
                     }
             }
            }).setPositiveButton("Modificar sin cambiar las imagenes", new DialogInterface.OnClickListener() {
          @Override
           public void onClick(DialogInterface dialog, int which) {

            cargarActualizarSinImagen_empleos();

           }
              });
             AlertDialog titulo = mensaje.create();
              titulo.setTitle("Modificar Publicación");
              titulo.show();
     return;
        }
             }
                               }
        );


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        actualizar_buscar_empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_empleos();
            }
        });

        anuncioempleos = new InterstitialAd(this);
        anuncioempleos.setAdUnitId(AnuncioActualizar);
        anuncioempleos.loadAd(new AdRequest.Builder().build());


        gvImagenes_empleos = findViewById(R.id.actualizar_grid_empleos);
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
        String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_actualizar_empleos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            id_actualizar_empleos.setError(""+R.string.supera);
            return false;
        }
        else {
            id_actualizar_empleos.setError(null);
            return true;
        }
    }
    private boolean validartitulo(){
        String tituloinput = titulo_actualizar_empleos.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_actualizar_empleos.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_actualizar_empleos.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_actualizar_empleos.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_actualizar_empleos.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_actualizar_empleos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_actualizar_empleos.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_actualizar_empleos.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        String necesidadinput = necesidad_actualizar_empleos.getText().toString().trim();

        if (necesidadinput.isEmpty()) {
            necesidad_actualizar_empleos.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (necesidadinput.length() > 15) {

            necesidad_actualizar_empleos.setError("" + R.string.supera);
            return false;
        } else {
            necesidad_actualizar_empleos.setError(null);
            return true;
        }
    }
    private boolean validarfotoupdate(){

        if (listaimagenes_empleos.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar 2 imagenes para la publicacion (Puede subir la misma 3 veces si no tiene otra",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_empleos.size() > 1){
            Toast.makeText(getApplicationContext(),"Solo se agregaran 2 imagenes",Toast.LENGTH_LONG).show();
            return false;
        }

        else if (listaimagenes_empleos.size() < 1){
            Toast.makeText(getApplicationContext(),"Has agregado "+listaimagenes_empleos.size()+" imagenes, pero deben ser 3",Toast.LENGTH_LONG).show();
            return true;

        }

        else if(listaimagenes_empleos.size() == 1){
            return false;
        }

        else {
            return true;
        }

    }

    private void cargarBusqueda_empleos() {

        String url_buscar_empleos = DireccionServidor+"wsnJSONBuscarEmpleos.php?id_ofertaempleos="+id_actualizar_empleos.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_empleos,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        OfertaEmpleos empleos = new OfertaEmpleos();

        JSONArray json = response.optJSONArray("empleos");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            empleos.setId_empleos(jsonObject.getInt("id_ofertaempleos"));
            empleos.setTitulo_row_ofertasempleos(jsonObject.getString("titulo_ofertaempleos"));
            empleos.setDescripcion_row_ofertasempleos(jsonObject.getString("descripcionrow_ofertaempleos"));
            empleos.setFechapublicacion_row_ofertasempleos(jsonObject.getString("fechapublicacion_ofertaempleos"));
            empleos.setNecesidad_row_ofertasempleos(jsonObject.getString("necesidad_ofertaempleos"));
            empleos.setImagen1_ofertasempleos(jsonObject.getString("imagen1_ofertaempleos"));
            empleos.setVistas_ofertasempleos(jsonObject.getInt("vistas_ofertaempleos"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_actualizar_empleos.getEditText().setText(empleos.getTitulo_row_ofertasempleos());
        descripcioncorta_actualizar_empleos.getEditText().setText(empleos.getDescripcion_row_ofertasempleos());
        necesidad_actualizar_empleos.setText(empleos.getNecesidad_row_ofertasempleos());
        Picasso.get().load(empleos.getImagen1_ofertasempleos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_empleos);
    }


    private void cargarActualizarConImagen_empleos() {

        String url_empleos = DireccionServidor+"wsnJSONActualizarConImagenEmpleos.php?";

        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarEmpleos.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioempleos.isLoaded()) {
                                        anuncioempleos.show();
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
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_empleos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_empleos.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_actualizar_empleos.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_ofertaempleos",idinput);
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


    private void cargarActualizarSinImagen_empleos() {

        String url_empleos = DireccionServidor+"wsnJSONActualizarSinnImagenEmpleos.php?";

        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarEmpleos.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioempleos.isLoaded()) {
                                        anuncioempleos.show();
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
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_empleos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_empleos.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_actualizar_empleos.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_ofertaempleos",idinput);
                parametros.put("titulo_empleos",tituloinput);
                parametros.put("descripcionrow_empleos",descripcioncortainput);
                parametros.put("vistas_empleos","0");
                parametros.put("necesidad_empleos",necesidadinput);
                parametros.put("subida","pendiente");
                parametros.put("publicacion","Empleos");


                return parametros;
            }
        };

        RequestQueue request_empleos = Volley.newRequestQueue(this);
        request_empleos.add(stringRequest_empleos);

    }

    public void Subirimagen_empleos_update(){

        listaBase64_empleos.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_empleos.size(); i++){
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_empleos.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                nombre.add( "imagen_empleos"+i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            }catch (IOException e){
            }
        }
        cargarActualizarConImagen_empleos();
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
                    Toast.makeText(ActualizarEmpleos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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
                for (int i = 0; i<= 1; i++){
                    listaimagenes_empleos.add(clipData.getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarEmpleos.this,listaimagenes_empleos);
        gvImagenes_empleos.setAdapter(baseAdapter);
    }

}
