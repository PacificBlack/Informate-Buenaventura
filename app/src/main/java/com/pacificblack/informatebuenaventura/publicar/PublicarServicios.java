package com.pacificblack.informatebuenaventura.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
import com.pacificblack.informatebuenaventura.R;

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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioPublicar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

public class PublicarServicios extends AppCompatActivity {

    TextInputLayout titulo_publicar_servicios,descripcioncorta_publicar_servicios;
    AutoCompleteTextView necesidad_publicar_servicios;
    Button publicarfinal,subirimagenes;

    String servi[] = new String[]{"Hoy mismo","Cuando quiera","Cada 3 años"};
    private InterstitialAd anuncioservicios;
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
    private ProgressDialog servicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_servicios);

        titulo_publicar_servicios = findViewById(R.id.publicar_titulo_servicios);
        descripcioncorta_publicar_servicios = findViewById(R.id.publicar_descripcion_servicios);
        necesidad_publicar_servicios = findViewById(R.id.publicar_necesidad_servicios);
        ArrayAdapter<String> adapternece = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,servi);
        necesidad_publicar_servicios.setAdapter(adapternece);
        necesidad_publicar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                necesidad_publicar_servicios.showDropDown();
            }
        });
        publicarfinal = findViewById(R.id.publicar_final_servicios);
        publicarfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validartitulo() | !validardescripcion() | !validarnececidad()  | !validarfoto()){return;}
                Subirimagen_servicios();
            }
        });
        gvImagenes_servicios = findViewById(R.id.grid_servicios);
        subirimagenes = findViewById(R.id.subir_imagenes_servicios);
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

        anuncioservicios = new InterstitialAd(this);
        anuncioservicios.setAdUnitId(AnuncioPublicar);
        anuncioservicios.loadAd(new AdRequest.Builder().build());
    }

    private boolean validartitulo(){
        String tituloinput = titulo_publicar_servicios.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_servicios.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_servicios.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_publicar_servicios.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_servicios.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_publicar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        String necesidadinput = necesidad_publicar_servicios.getText().toString().trim();

        if (necesidadinput.isEmpty()) {
            necesidad_publicar_servicios.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (necesidadinput.length() > 15) {

            necesidad_publicar_servicios.setError("" + R.string.supera);
            return false;
        } else {
            necesidad_publicar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarfoto(){
        if (listaimagenes_servicios.size() == 0){
            Toast.makeText(getApplicationContext(),"Debe agregar como minimo una foto",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;}
    }

    private void cargarWebService_servicios() {

        String url_servicios = DireccionServidor+"wsnJSONRegistroServicios.php?";
        stringRequest_servicios= new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    CargandoSubida("Ocultar");
                    AlertDialog.Builder mensaje = new AlertDialog.Builder(PublicarServicios.this);
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
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    CargandoSubida("Ocultar");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        CargandoSubida("Ocultar");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_publicar_servicios.getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

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
    public void Subirimagen_servicios(){
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
            cargarWebService_servicios();
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
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona las 1 imagenes"),IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    seleccionarimagen();
                }
                else{
                    Toast.makeText(PublicarServicios.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
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
        baseAdapter = new GridViewAdapter(PublicarServicios.this,listaimagenes_servicios);
        gvImagenes_servicios.setAdapter(baseAdapter);
    }
    private void CargandoSubida(String Mostrar){
        servicios=new ProgressDialog(this);
        servicios.setMessage("Subiendo su Empleos");
        servicios.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        servicios.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            servicios.show();
        }if(Mostrar.equals("Ocultar")){
            servicios.hide();
        }
    }
}