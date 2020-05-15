package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;
import com.pacificblack.informatebuenaventura.extras.CargandoDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.internal.Util;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Avisos.Whatsapp;
import static com.pacificblack.informatebuenaventura.texto.Avisos.id_vacio;
import static com.pacificblack.informatebuenaventura.texto.Avisos.texto_superado;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class EliminarClasificados extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    StringRequest stringRequestclasificados;
    ImageButton  eliminar_buscar_clasificados;
    Button eliminar_clasificados;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_clasificados, imagen2_eliminar_clasificados, imagen3_eliminar_clasificados, imagen4_eliminar_clasificados;
    TextView titulo_eliminar_clasificados, descripcioncorta_eliminar_clasificados, video_eliminar_clasificados, descripcion1_eliminar_clasificados, descripcion2_eliminar_clasificados;
    TextInputLayout buscar_eliminar_clasificados;
    private InterstitialAd anuncioClasificados_eliminar;
    Toolbar barra_clasificados;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarClasificados.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_clasificados);

        whatsapp = findViewById(R.id.whatsapp_eliminar_clasificados);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarClasificados.this,Whatsapp);
            }
        });
        barra_clasificados = findViewById(R.id.toolbar_eliminar_clasificados);
        barra_clasificados.setTitle("Eliminar Clasificados");
        barra_clasificados.setTitleTextColor(Color.WHITE);
        titulo_eliminar_clasificados = findViewById(R.id.eliminar_titulo_clasificados);
        descripcioncorta_eliminar_clasificados = findViewById(R.id.eliminar_descripcioncorta_clasificados);
        video_eliminar_clasificados = findViewById(R.id.eliminar_video_clasificados);
        descripcion1_eliminar_clasificados = findViewById(R.id.eliminar_descripcion1_clasificados);
        descripcion2_eliminar_clasificados = findViewById(R.id.eliminar_descripcion2_clasificados);
        imagen1_eliminar_clasificados = findViewById(R.id.imagen1_eliminar_clasificados);
        imagen2_eliminar_clasificados = findViewById(R.id.imagen2_eliminar_clasificados);
        imagen3_eliminar_clasificados = findViewById(R.id.imagen3_eliminar_clasificados);
        imagen4_eliminar_clasificados = findViewById(R.id.imagen4_eliminar_clasificados);
        buscar_eliminar_clasificados = findViewById(R.id.eliminar_id_clasificados);
        eliminar_clasificados = findViewById(R.id.eliminar_clasificados);
        eliminar_buscar_clasificados = findViewById(R.id.eliminar_buscar_clasificados);

        eliminar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarClasificados.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_clasificados();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Eliminar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_clasificados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_clasificados();
                cargandoDialog.Mostrar();

            }
        });

        anuncioClasificados_eliminar = new InterstitialAd(this);
        anuncioClasificados_eliminar.setAdUnitId("ca-app-pub-7236340326570289/5427746720");
        anuncioClasificados_eliminar.loadAd(new AdRequest.Builder().build());

    }

    private boolean validarid(){
        String idinput = buscar_eliminar_clasificados.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            buscar_eliminar_clasificados.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            buscar_eliminar_clasificados.setError(texto_superado);
            return false;
        }
        else {
            buscar_eliminar_clasificados.setError(null);
            return true;
        }
    }
    private void cargarBusqueda_clasificados() {
        String url_buscar_adopcion = DireccionServidor+"wsnJSONBuscarClasificados.php?id_clasificados="+buscar_eliminar_clasificados.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_adopcion,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargandoDialog.Ocultar();
    }
    @Override
    public void onResponse(JSONObject response) {

        Clasificados clasificados = new Clasificados();

        JSONArray json = response.optJSONArray("clasificados");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);


            clasificados.setId_clasificados(jsonObject.getInt("id_clasificados"));
            clasificados.setTitulo_row_clasificados(jsonObject.getString("titulo_clasificados"));
            clasificados.setDescripcion_row_clasificados(jsonObject.getString("descripcionrow_clasificados"));
            clasificados.setFechapublicacion_row_clasificados(jsonObject.getString("fechapublicacion_clasificados"));
            clasificados.setImagen1_clasificados(jsonObject.getString("imagen1_clasificados"));
            clasificados.setImagen2_clasificados(jsonObject.getString("imagen2_clasificados"));
            clasificados.setImagen3_clasificados(jsonObject.getString("imagen3_clasificados"));
            clasificados.setImagen4_clasificados(jsonObject.getString("imagen4_clasificados"));
            clasificados.setVideo_clasificados(jsonObject.getString("video_clasificados"));
            clasificados.setVistas_bienes(jsonObject.getInt("vistas_clasificados"));
            clasificados.setDescripcion1_clasificados(jsonObject.getString("descripcion1_clasificados"));
            clasificados.setDescripcion2_clasificados(jsonObject.getString("descripcion2_clasificados"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_clasificados.setText(clasificados.getTitulo_row_clasificados());
        descripcioncorta_eliminar_clasificados.setText(clasificados.getDescripcion_row_clasificados());
        video_eliminar_clasificados.setText(clasificados.getVideo_clasificados());
        descripcion1_eliminar_clasificados.setText(clasificados.getDescripcion1_clasificados());
        descripcion2_eliminar_clasificados.setText(clasificados.getDescripcion2_clasificados());

        Picasso.get().load(clasificados.getImagen1_clasificados())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen2_clasificados())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen2_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen3_clasificados())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen3_eliminar_clasificados);

        Picasso.get().load(clasificados.getImagen4_clasificados())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen4_eliminar_clasificados);

        cargandoDialog.Ocultar();

    }
    private void cargarEliminar_clasificados() {

        String url_clasificados = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequestclasificados= new StringRequest(Request.Method.POST, url_clasificados, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarClasificados.this);
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
                            if (anuncioClasificados_eliminar.isLoaded()) { anuncioClasificados_eliminar.show(); }
                            else { Log.d("TAG", "The interstitial wasn't loaded yet."); }
                        }
                    });
                    Log.i("Muestra",response);
                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
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

                String idinput = buscar_eliminar_clasificados.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_clasificados",idinput);
                parametros.put("publicacion","Clasificados");
                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_clasificados_actualizar = Volley.newRequestQueue(this);
        stringRequestclasificados.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_clasificados_actualizar.add(stringRequestclasificados);

    }

    @SuppressLint("NewApi")
    private void whatsapp(Activity activity, String phone) {
        String formattedNumber = Util.format(phone);
        try {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "");
            sendIntent.putExtra("jid", formattedNumber + "@s.whatsapp.net");
            sendIntent.setPackage("com.whatsapp");
            activity.startActivity(sendIntent);
        } catch (Exception e) {
            Toast.makeText(activity, "Instale whatsapp en su dispositivo o cambie a la version oficial que esta disponible en PlayStore", Toast.LENGTH_SHORT).show();
        }
    }
}