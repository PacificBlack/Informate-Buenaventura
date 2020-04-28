package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.extras.Contants.MY_DEFAULT_TIMEOUT;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes


public class EliminarDesaparicion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    StringRequest stringRequest_desaparicion;
    TextInputLayout id_desaparicion;
    TextView titulo_eliminar_desaparicion, descripcioncorta_eliminar_desaparicion,recompensa_eliminar_desaparicion, ultimolugar_eliminar_desaparicion, descripcion1_eliminar_desaparicion, descripcion2_eliminar_desaparicion,diadesa_eliminar_desaparicion,queseperdio_eliminar_desaparicion, estado_eliminar_desaparicion;
    ImageButton eliminar_buscar_adopcion,eliminar_desaparicion;
    RequestQueue requestbuscar_eliminar;
    JsonObjectRequest jsonObjectRequestBuscar_eliminar;
    ImageView imagen1_eliminar_desaparicion,imagen2_eliminar_desaparicion,imagen3_eliminar_desaparicion;
    private InterstitialAd anunciodesaparicion_eliminar;
    private ProgressDialog desaparicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_desaparicion);

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
                        CargandoSubida("Ver");

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
                CargandoSubida("Ver");

            }
        });

        anunciodesaparicion_eliminar = new InterstitialAd(this);
        anunciodesaparicion_eliminar.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anunciodesaparicion_eliminar.loadAd(new AdRequest.Builder().build());
    }


    private boolean validarid(){
        String idinput = id_desaparicion.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_desaparicion.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            id_desaparicion.setError(""+R.string.supera);
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
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen2_desaparecidos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_desaparicion);

        Picasso.get().load(desaparecidos.getImagen3̣̣_desaparecidos())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_desaparicion);

        CargandoSubida("Ocultar");

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

                    CargandoSubida("Ocultar");


                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarDesaparicion.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciodesaparicion_eliminar.isLoaded()) {
                                        anunciodesaparicion_eliminar.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }

                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Eliminada exitosamente");
                    titulo.show();

                    Log.i("Funciona : ",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
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
    private void CargandoSubida(String Mostrar){
        desaparicion=new ProgressDialog(this);
        desaparicion.setMessage("Subiendo su Empleos");
        desaparicion.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        desaparicion.setIndeterminate(true);
        if(Mostrar.equals("Ver")){
            desaparicion.show();
        }if(Mostrar.equals("Ocultar")){
            desaparicion.hide();
        }
    }

}