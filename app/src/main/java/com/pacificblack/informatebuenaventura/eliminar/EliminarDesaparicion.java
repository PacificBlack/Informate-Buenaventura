package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
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
//Todo: Clase completamente lista.

public class EliminarDesaparicion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    StringRequest stringRequest_desaparicion;
    TextInputLayout id_desaparicion;
    TextView titulo_eliminar_desaparicion, descripcioncorta_eliminar_desaparicion,recompensa_eliminar_desaparicion, ultimolugar_eliminar_desaparicion, descripcion1_eliminar_desaparicion, descripcion2_eliminar_desaparicion,diadesa_eliminar_desaparicion,queseperdio_eliminar_desaparicion, estado_eliminar_desaparicion;
    ImageButton eliminar_buscar_adopcion;
    Button eliminar_desaparicion;
    RequestQueue requestbuscar_eliminar;
    JsonObjectRequest jsonObjectRequestBuscar_eliminar;
    ImageView imagen1_eliminar_desaparicion,imagen2_eliminar_desaparicion,imagen3_eliminar_desaparicion;
    private InterstitialAd anunciodesaparicion_eliminar;
    Toolbar barra_desaparicion;
    ImageView whatsapp;
    ProgressBar cargandopublicar;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarDesaparicion.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_desaparicion);

        whatsapp = findViewById(R.id.whatsapp_eliminar_desaparicion);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarDesaparicion.this,Whatsapp);
            }
        });
        barra_desaparicion = findViewById(R.id.toolbar_eliminar_desaparicion);
        cargandopublicar = findViewById(R.id.CargandoEliminar_desaparicion);
        barra_desaparicion.setTitle("Eliminar Desaparicion");
        cargandopublicar.setVisibility(View.GONE);
        titulo_eliminar_desaparicion = findViewById(R.id.eliminar_titulo_desaparicion);
        descripcioncorta_eliminar_desaparicion= findViewById(R.id.eliminar_descripcioncorta_desaparicion);
        recompensa_eliminar_desaparicion = findViewById(R.id.eliminar_recompensa_desaparicion);
        diadesa_eliminar_desaparicion = findViewById(R.id.eliminar_fechade_desaparicion);
        ultimolugar_eliminar_desaparicion = findViewById(R.id.eliminar_ultimolugar_desaparicion);
        descripcion1_eliminar_desaparicion = findViewById(R.id.eliminar_descripcion_desaparicion);
        descripcion2_eliminar_desaparicion = findViewById(R.id.eliminar_descripcionextra_desaparicion);
        queseperdio_eliminar_desaparicion = findViewById(R.id.eliminar_quese_desaparicion);
        estado_eliminar_desaparicion = findViewById(R.id.eliminar_estadodes_desapariciom);
        imagen1_eliminar_desaparicion = findViewById(R.id.imagen1_eliminar_desaparicion);
        imagen2_eliminar_desaparicion = findViewById(R.id.imagen2_eliminar_desaparicion);
        imagen3_eliminar_desaparicion = findViewById(R.id.imagen3_eliminar_desaparicion);
        id_desaparicion = findViewById(R.id.eliminar_id_desaparicion);
        eliminar_desaparicion = findViewById(R.id.eliminar_desaparicion);
        eliminar_buscar_adopcion = findViewById(R.id.eliminar_buscar_desaparicion);

        eliminar_desaparicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarDesaparicion.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_desaparicion();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar_eliminar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()){return;}
                cargarBusqueda_desaparicion();
                cargandoDialog.Mostrar();

            }
        });

        anunciodesaparicion_eliminar = new InterstitialAd(this);
        anunciodesaparicion_eliminar.setAdUnitId("ca-app-pub-7236340326570289/3791424949");
        anunciodesaparicion_eliminar.loadAd(new AdRequest.Builder().build());
    }
    private boolean validarid(){
        String idinput = id_desaparicion.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            id_desaparicion.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            id_desaparicion.setError(texto_superado);
            return false;
        }
        else {
            id_desaparicion.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_desaparicion() {
        String url_buscar_bienes = DireccionServidor+"wsnJSONBuscarDesaparicion.php?id_desaparecidos="+id_desaparicion.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar_eliminar = new JsonObjectRequest(Request.Method.GET,url_buscar_bienes,null,this,this);
        requestbuscar_eliminar.add(jsonObjectRequestBuscar_eliminar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargandoDialog.Ocultar();
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

        titulo_eliminar_desaparicion.setText(desaparecidos.getTitulo_row_desaparecidos());
        descripcioncorta_eliminar_desaparicion.setText(desaparecidos.getDescripcion_row_desaparecidos());
        recompensa_eliminar_desaparicion.setText(desaparecidos.getRecompensa_row_desaparecidos());
        diadesa_eliminar_desaparicion.setText(desaparecidos.getFechadesaparecido_desaparecidos());
        ultimolugar_eliminar_desaparicion.setText(desaparecidos.getUltimolugar_desaparecidos());
        descripcion1_eliminar_desaparicion.setText(desaparecidos.getDescripcion1_desaparecidos());
        descripcion2_eliminar_desaparicion.setText(desaparecidos.getDescripcion2_desaparecidos());
        queseperdio_eliminar_desaparicion.setText(desaparecidos.getQue_desaparecidos());
        estado_eliminar_desaparicion.setText(desaparecidos.getEstado_desaparecidos());


        Picasso.get().load(desaparecidos.getImagen1_desaparecidos())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen2_desaparecidos())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen2_eliminar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen3̣̣_desaparecidos())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen3_eliminar_desaparicion);

        cargandoDialog.Ocultar();

    }
    private void cargarEliminar_desaparicion() {

        String url_desaparicion = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_desaparicion = new StringRequest(Request.Method.POST, url_desaparicion, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarDesaparicion.this);
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
                            if (anunciodesaparicion_eliminar.isLoaded()) { anunciodesaparicion_eliminar.show(); }
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

                String idinput = id_desaparicion.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_desaparicion",idinput);
                parametros.put("publicacion","Desaparicion");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_bienes_actualizar = Volley.newRequestQueue(this);
        stringRequest_desaparicion.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_bienes_actualizar.add(stringRequest_desaparicion);
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