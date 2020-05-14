package com.pacificblack.informatebuenaventura.actualizar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaEmpleos;
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
import static com.pacificblack.informatebuenaventura.texto.Avisos.descripcion_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarEmpleos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    RadioButton opcion1_empleos,opcion2_empleos,opcion3_empleos; String necesidad_texto = "Ninguno";
    TextInputLayout titulo_actualizar_empleos, descripcioncorta_actualizar_empleos, id_actualizar_empleos;
    Button actualizar_empleos,subirimagenes;
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
    ImageButton actualizar_buscar_empleos;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_empleos;
    private InterstitialAd anuncioempleos;
    Toolbar barra_empleos;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(ActualizarEmpleos.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_empleos);

        whatsapp = findViewById(R.id.whatsapp_actualizar_empleos);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarEmpleos.this,Whatsapp);
            }
        });
        barra_empleos = findViewById(R.id.toolbar_actualizar_empleos);
        barra_empleos.setTitle("Actualizar Empleos");
        opcion1_empleos = findViewById(R.id.opcion1_necesidad_empleos_actualizar); opcion1_empleos.setText("Urgente");
        opcion2_empleos = findViewById(R.id.opcion2_necesidad_empleos_actualizar); opcion2_empleos.setText("Sin Urgencia");
        opcion3_empleos = findViewById(R.id.opcion3_necesidad_empleos_actualizar); opcion3_empleos.setText("Para hoy mismo");


        titulo_actualizar_empleos = findViewById(R.id.actualizar_titulo_empleos);
        descripcioncorta_actualizar_empleos = findViewById(R.id.actualizar_descripcion_empleos);
        subirimagenes = findViewById(R.id.subir_imagenes_actualizar_empleos);
        id_actualizar_empleos = findViewById(R.id.actualizar_id_empleos);
        actualizar_empleos = findViewById(R.id.actualizar_empleos);
        actualizar_buscar_empleos = findViewById(R.id.actualizar_buscar_empleos);
        imagen1_actualizar_empleos = findViewById(R.id.imagen1_actualizar_empleos);

    actualizar_empleos.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
        if (!validartitulo() | !validardescripcion() | !validarnececidad() | !validarid()) { return; }

        if (!validarfotoupdate()){
             AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarEmpleos.this);
            mensaje.setMessage(aviso_actualizar)
            .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
               @Override
                public void onClick(DialogInterface dialog, int which) {
               Subirimagen_empleos_update();
             }
            }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
          @Override
           public void onClick(DialogInterface dialog, int which) {
            cargarActualizarSinImagen_empleos();
              cargandoDialog.Mostrar();
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
                cargandoDialog.Mostrar();

            }
        });
        anuncioempleos = new InterstitialAd(this);
        anuncioempleos.setAdUnitId("ca-app-pub-7236340326570289/3212646928");
        anuncioempleos.loadAd(new AdRequest.Builder().build());
        gvImagenes_empleos = findViewById(R.id.actualizar_grid_empleos);
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
        String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            id_actualizar_empleos.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            id_actualizar_empleos.setError(texto_superado);
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
            titulo_actualizar_empleos.setError(titulo_vacio);
            return false;
        }
        else if(tituloinput.length()>120){
            titulo_actualizar_empleos.setError(texto_superado);
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
            descripcioncorta_actualizar_empleos.setError(descripcio1_vacio);
            return false;
        }
        else if(descripcioncortainput.length()>700){
            descripcioncorta_actualizar_empleos.setError(descripcion_vacio);
            return false;
        }
        else {
            descripcioncorta_actualizar_empleos.setError(null);
            return true;
        }
    }
    private boolean validarnececidad(){
        if (opcion1_empleos.isChecked() || opcion2_empleos.isChecked() || opcion3_empleos.isChecked() ){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Marque que la necesidad con la que precisa un empleado",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarfotoupdate(){
        if (listaimagenes_empleos.size() == 0){ return true; }
        else { return true; }
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
        cargandoDialog.Ocultar();
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

        if (empleos.getNecesidad_row_ofertasempleos().equals("Urgente")){opcion1_empleos.setChecked(true);}
        if (empleos.getNecesidad_row_ofertasempleos().equals("Sin Urgencia")){opcion2_empleos.setChecked(true);}
        if (empleos.getNecesidad_row_ofertasempleos().equals("Para hoy mismo")){opcion3_empleos.setChecked(true);}

        Picasso.get().load(empleos.getImagen1_ofertasempleos()).placeholder(R.drawable.ib).error(R.drawable.ib).into(imagen1_actualizar_empleos);
        cargandoDialog.Ocultar();

    }


    private void cargarActualizarConImagen_empleos() {

        if (opcion1_empleos.isChecked()){ necesidad_texto = opcion1_empleos.getText().toString(); }
        if (opcion2_empleos.isChecked()){ necesidad_texto = opcion2_empleos.getText().toString(); }
        if (opcion3_empleos.isChecked()){ necesidad_texto = opcion3_empleos.getText().toString(); }

        String url_empleos = DireccionServidor+"wsnJSONActualizarConImagenEmpleos.php?";

        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarEmpleos.this);
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
                            if (anuncioempleos.isLoaded()) { anuncioempleos.show(); }
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

                String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_empleos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_empleos.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_texto;

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
        stringRequest_empleos.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_empleos.add(stringRequest_empleos);

    }
    private void cargarActualizarSinImagen_empleos() {
        if (opcion1_empleos.isChecked()){ necesidad_texto = opcion1_empleos.getText().toString(); }
        if (opcion2_empleos.isChecked()){ necesidad_texto = opcion2_empleos.getText().toString(); }
        if (opcion3_empleos.isChecked()){ necesidad_texto = opcion3_empleos.getText().toString(); }

        String url_empleos = DireccionServidor+"wsnJSONActualizarSinnImagenEmpleos.php?";

        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarEmpleos.this);
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
                            if (anuncioempleos.isLoaded()) { anuncioempleos.show(); }
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

                String idinput = id_actualizar_empleos.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_empleos.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_empleos.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_texto;

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
        stringRequest_empleos.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
        if (nombre.size() == 1) {
            cargarActualizarConImagen_empleos();
            cargandoDialog.Mostrar();
        }

        if (nombre.size()>1){
            Toast.makeText(getApplicationContext(),imagen_maxima+" 1",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ActualizarEmpleos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if (data.getClipData() == null){
                imagenesempleosUri = data.getData();
                listaimagenes_empleos.add(imagenesempleosUri);
            }else {
                for (int i = 0; i< data.getClipData().getItemCount(); i++){
                    listaimagenes_empleos.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarEmpleos.this,listaimagenes_empleos);
        gvImagenes_empleos.setAdapter(baseAdapter);
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
