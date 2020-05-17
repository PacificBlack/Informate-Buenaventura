package com.pacificblack.informatebuenaventura.publicar;

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
import android.graphics.Color;
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
import android.widget.GridView;
import android.widget.ImageView;
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
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.CargandoDialog;

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
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcio1_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioPublicar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoPublicar;

public class PublicarServicios extends AppCompatActivity {

    RadioButton opcion1_servicios,opcion2_servicios,opcion3_servicios; String necesidad_texto = "Ninguno";
    TextInputLayout titulo_publicar_servicios,descripcioncorta_publicar_servicios;
    Button publicarfinal,subirimagenes;
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
    Toolbar barra_servicios;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(PublicarServicios.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_servicios);

        whatsapp = findViewById(R.id.whatsapp_publicar_servicios);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(PublicarServicios.this, Whatsapp);
            }
        });
        barra_servicios = findViewById(R.id.toolbar_publicar_servicios);
        barra_servicios.setTitle("Publicar Servicios");
        barra_servicios.setTitleTextColor(Color.WHITE);
        opcion1_servicios = findViewById(R.id.opcion1_necesidad_servicios_publicar);
        opcion1_servicios.setText("Solo por hoy");
        opcion2_servicios = findViewById(R.id.opcion2_necesidad_servicios_publicar);
        opcion2_servicios.setText("Solo por esta semana");
        opcion3_servicios = findViewById(R.id.opcion3_necesidad_servicios_publicar);
        opcion3_servicios.setText("Por tiempo indefinido");

        titulo_publicar_servicios = findViewById(R.id.publicar_titulo_servicios);
        descripcioncorta_publicar_servicios = findViewById(R.id.publicar_descripcion_servicios);
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
        anuncioservicios.setAdUnitId("ca-app-pub-7236340326570289/6354735455");
        anuncioservicios.loadAd(new AdRequest.Builder().build());
    }

    private boolean validartitulo(){
        String tituloinput = titulo_publicar_servicios.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_servicios.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_servicios.setError(texto_superado);
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
            descripcioncorta_publicar_servicios.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>700){

            descripcioncorta_publicar_servicios.setError(texto_superado);
            return false;
        }
        else {
            descripcioncorta_publicar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        if (opcion1_servicios.isChecked() || opcion2_servicios.isChecked() || opcion3_servicios.isChecked() ){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Marque que la necesidad con la que dara el servicio",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarfoto(){
        if (listaimagenes_servicios.size() == 0){
            Toast.makeText(getApplicationContext(),imagen_minima,Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;}
    }

    private void cargarWebService_servicios() {

        if (opcion1_servicios.isChecked()){ necesidad_texto = opcion1_servicios.getText().toString(); }
        if (opcion2_servicios.isChecked()){ necesidad_texto = opcion2_servicios.getText().toString(); }
        if (opcion3_servicios.isChecked()){ necesidad_texto = opcion3_servicios.getText().toString(); }

        String url_servicios = DireccionServidor+"wsnJSONRegistroServicios.php?";
        stringRequest_servicios= new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Registrada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(PublicarServicios.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado, null);
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
                            if (anuncioservicios.isLoaded()) {
                                anuncioservicios.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra", response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoPublicar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargandoDialog.Ocultar();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"pero no voy a limpiar",Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargandoDialog.Ocultar();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tituloinput = titulo_publicar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_publicar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_texto;
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
            cargandoDialog.Mostrar();
        }
        if (nombre.size()>1){
            Toast.makeText(getApplicationContext(),imagen_maxima +" 1",Toast.LENGTH_LONG).show();
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