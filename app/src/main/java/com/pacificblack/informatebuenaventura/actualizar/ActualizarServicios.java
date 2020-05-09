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
import android.widget.AutoCompleteTextView;
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
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaServicios;
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
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_maxima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.imagen_minima;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Avisos.titulo_vacio;
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoActualizar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class ActualizarServicios extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    GridView gvImagenes_servicios;
    Uri imagenesserviciosUri;
    List<Uri> listaimagenes_servicios = new ArrayList<>();
    List<String> listaBase64_servicios = new ArrayList<>();
    GridViewAdapter baseAdapter;
    List<String> cadena = new ArrayList<>();
    List<String> nombre = new ArrayList<>();
    StringRequest stringRequest_servicios;
    private static final int IMAGE_PICK_CODE = 100;
    private static final int PERMISSON_CODE = 1001;
    TextInputLayout id_actualizar_servicios, titulo_actualizar_servicios, descripcioncorta_actualizar_servicios;
    Button actualizar_servicios, subirimagenes;
    ImageButton actualizar_buscar_servicios;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_actualizar_servicios;
    private InterstitialAd anuncioservicios;
    RadioButton opcion1_servicios, opcion2_servicios, opcion3_servicios;
    String necesidad_texto = "Ninguno";
    Toolbar barra_servicios;
    ImageView whatsapp;
    CargandoDialog  cargandoDialog = new CargandoDialog(ActualizarServicios.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_servicios);


        whatsapp = findViewById(R.id.whatsapp_actualizar_servicios);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(ActualizarServicios.this, Whatsapp);
            }
        });
        barra_servicios = findViewById(R.id.toolbar_actualizar_servicios);
        barra_servicios.setTitle("Actualizar servicios");
        opcion1_servicios = findViewById(R.id.opcion1_necesidad_servicios_actualizar);
        opcion1_servicios.setText("Solo por hoy");
        opcion2_servicios = findViewById(R.id.opcion2_necesidad_servicios_actualizar);
        opcion2_servicios.setText("Solo por esta semana");
        opcion3_servicios = findViewById(R.id.opcion3_necesidad_servicios_actualizar);
        opcion3_servicios.setText("Por tiempo indefinido");


        titulo_actualizar_servicios = findViewById(R.id.actualizar_titulo_servicios);
        descripcioncorta_actualizar_servicios = findViewById(R.id.actualizar_descripcion_servicios);
        subirimagenes = findViewById(R.id.subir_imagenes_actualizar_servicios);
        id_actualizar_servicios = findViewById(R.id.id_actualizar_servicios);
        actualizar_servicios = findViewById(R.id.actualizar_final_servicios);
        actualizar_buscar_servicios = findViewById(R.id.actualizar_buscar_servicios);
        imagen1_actualizar_servicios = findViewById(R.id.imagen1_actualizar_servicios);


        actualizar_servicios.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (!validartitulo() | !validardescripcion() | !validarnececidad() | !validarid()) {
                                                            return;
                                                        }
                                                        if (!validarfotoupdate()) {
                                                            AlertDialog.Builder mensaje = new AlertDialog.Builder(ActualizarServicios.this);
                                                            mensaje.setMessage(aviso_actualizar)
                                                                    .setCancelable(false).setNegativeButton(aviso_actualizar_imagen, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Subirimagen_servicios_update();

                                                                }
                                                            }).setPositiveButton(aviso_actualizar_noimagen, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    cargarActualizarSinImagen_servicios();
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
        actualizar_buscar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()) {
                    return;
                }
                cargarBusqueda_servicios();
                cargandoDialog.Mostrar();

            }
        });

        anuncioservicios = new InterstitialAd(this);
        anuncioservicios.setAdUnitId(AnuncioActualizar);
        anuncioservicios.loadAd(new AdRequest.Builder().build());


        gvImagenes_servicios = findViewById(R.id.grid_actualizar_servicios);
        subirimagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permisos, PERMISSON_CODE);
                    } else {
                        seleccionarimagen();
                    }

                } else {
                    seleccionarimagen();
                }
            }
        });


    }


    private boolean validarid() {
        String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();

        if (idinput.isEmpty()) {
            id_actualizar_servicios.setError(id_vacio);
            return false;
        } else if (idinput.length() > 15) {
            id_actualizar_servicios.setError(texto_superado);
            return false;
        } else {
            id_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean validartitulo() {
        String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();
        if (tituloinput.isEmpty()) {
            titulo_actualizar_servicios.setError(titulo_vacio);
            return false;
        } else if (tituloinput.length() > 120) {
            titulo_actualizar_servicios.setError(texto_superado);
            return false;
        } else {
            titulo_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean validardescripcion() {
        String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();
        if (descripcioncortainput.isEmpty()) {
            descripcioncorta_actualizar_servicios.setError(descripcio1_vacio);
            return false;
        } else if (descripcioncortainput.length() > 700) {
            descripcioncorta_actualizar_servicios.setError(texto_superado);
            return false;
        } else {
            descripcioncorta_actualizar_servicios.setError(null);
            return true;
        }
    }
    private boolean validarnececidad() {
        if (opcion1_servicios.isChecked() || opcion2_servicios.isChecked() || opcion3_servicios.isChecked() ){
            return true;
        }else {
            Toast.makeText(getApplicationContext(),"Marque que la necesidad con la que dara el servicio",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean validarfotoupdate() {
        if (listaimagenes_servicios.size() == 0) {
            Toast.makeText(getApplicationContext(), imagen_minima, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    private void cargarBusqueda_servicios() {
        String url_buscar_servicios = DireccionServidor + "wsnJSONBuscarServicios.php?id_ofertaservicios=" + id_actualizar_servicios.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET, url_buscar_servicios, null, this, this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
        cargandoDialog.Ocultar();
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_actualizar_servicios.getEditText().setText(servicios.getTitulo_row_ofertaservicios());
        descripcioncorta_actualizar_servicios.getEditText().setText(servicios.getDescripcion_row_ofertaservicios());
        if (servicios.getNecesidad_row_ofertaservicios().equals("Solo por hoy")){opcion1_servicios.setChecked(true);}
        if (servicios.getNecesidad_row_ofertaservicios().equals("Solo por esta semana")){opcion2_servicios.setChecked(true);}
        if (servicios.getNecesidad_row_ofertaservicios().equals("Por tiempo indefinido")){opcion3_servicios.setChecked(true);}

        Picasso.get().load(servicios.getImagen1_ofertaservicios())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_actualizar_servicios);

        cargandoDialog.Ocultar();
    }


    private void cargarActualizarConImagen_servicios() {

        if (opcion1_servicios.isChecked()){ necesidad_texto = opcion1_servicios.getText().toString(); }
        if (opcion2_servicios.isChecked()){ necesidad_texto = opcion2_servicios.getText().toString(); }
        if (opcion3_servicios.isChecked()){ necesidad_texto = opcion3_servicios.getText().toString(); }

        String url_servicios = DireccionServidor + "wsnJSONActualizarConImagenServicios.php?";
        stringRequest_servicios = new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarServicios.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado, null);
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
                            if (anuncioservicios.isLoaded()) {
                                anuncioservicios.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra", response);
                } else {
                    Toast.makeText(getApplicationContext(), NosepudoActualizar, Toast.LENGTH_LONG).show();
                    Log.i("Error", response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR", error.toString());
                        cargandoDialog.Ocultar();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_texto;
                Map<String, String> parametros = new HashMap<>();

                parametros.put("id_ofertaservicios", idinput);
                parametros.put("titulo_servicios", tituloinput);
                parametros.put("descripcionrow_servicios", descripcioncortainput);
                parametros.put("vistas_servicios", "0");
                parametros.put("necesidad_servicios", necesidadinput);
                parametros.put("subida", "pendiente");
                parametros.put("publicacion", "Servicios");

                for (int h = 0; h < nombre.size(); h++) {
                    parametros.put(nombre.get(h), cadena.get(h));
                }

                return parametros;
            }
        };

        RequestQueue request_servicios = Volley.newRequestQueue(this);
        stringRequest_servicios.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_servicios.add(stringRequest_servicios);

    }
    private void cargarActualizarSinImagen_servicios() {

        if (opcion1_servicios.isChecked()){ necesidad_texto = opcion1_servicios.getText().toString(); }
        if (opcion2_servicios.isChecked()){ necesidad_texto = opcion2_servicios.getText().toString(); }
        if (opcion3_servicios.isChecked()){ necesidad_texto = opcion3_servicios.getText().toString(); }

        String url_servicios = DireccionServidor + "wsnJSONActualizarSinnImagenServicios.php?";

        stringRequest_servicios = new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Actualizada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActualizarServicios.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado, null);
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
                            if (anuncioservicios.isLoaded()) {
                                anuncioservicios.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra", response);
                } else {
                    Toast.makeText(getApplicationContext(), NosepudoActualizar, Toast.LENGTH_LONG).show();
                    Log.i("Error", response);
                    cargandoDialog.Ocultar();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        Log.i("ERROR", error.toString());
                        cargandoDialog.Ocultar();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_actualizar_servicios.getEditText().getText().toString().trim();
                String tituloinput = titulo_actualizar_servicios.getEditText().getText().toString().trim();
                String descripcioncortainput = descripcioncorta_actualizar_servicios.getEditText().getText().toString().trim();
                String necesidadinput = necesidad_texto;
                Map<String, String> parametros = new HashMap<>();

                parametros.put("id_ofertaservicios", idinput);
                parametros.put("titulo_servicios", tituloinput);
                parametros.put("descripcionrow_servicios", descripcioncortainput);
                parametros.put("vistas_servicios", "0");
                parametros.put("necesidad_servicios", necesidadinput);
                parametros.put("subida", "pendiente");
                parametros.put("publicacion", "Servicios");

                return parametros;
            }
        };

        RequestQueue request_servicios = Volley.newRequestQueue(this);
        stringRequest_servicios.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_servicios.add(stringRequest_servicios);

    }
    public void Subirimagen_servicios_update() {

        listaBase64_servicios.clear();
        nombre.clear();
        cadena.clear();
        for (int i = 0; i < listaimagenes_servicios.size(); i++) {
            try {
                InputStream is = getContentResolver().openInputStream(listaimagenes_servicios.get(i));
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                nombre.add("imagen_servicios" + i);
                cadena.add(convertirUriEnBase64(bitmap));
                bitmap.recycle();
            } catch (IOException e) {
            }
        }
        if (nombre.size() == 1) {
            cargarActualizarConImagen_servicios();
            cargandoDialog.Mostrar();
        }
        if (nombre.size() > 1) {
            Toast.makeText(getApplicationContext(), imagen_maxima +" 1", Toast.LENGTH_LONG).show();
        }
    }
    public String convertirUriEnBase64(Bitmap bmp) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, array);

        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    public void seleccionarimagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona las 3 imagenes"), IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSON_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    seleccionarimagen();
                } else {
                    Toast.makeText(ActualizarServicios.this, "Debe otorgar permisos de almacenamiento", Toast.LENGTH_LONG);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (data.getClipData() == null) {
                imagenesserviciosUri = data.getData();
                listaimagenes_servicios.add(imagenesserviciosUri);
            } else {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    listaimagenes_servicios.add(data.getClipData().getItemAt(i).getUri());
                }
            }
        }
        baseAdapter = new GridViewAdapter(ActualizarServicios.this, listaimagenes_servicios);
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
