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
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaServicios;
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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class EliminarServicios extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextInputLayout id_eliminar_servicios;
    TextView titulo_eliminar_servicios,descripcioncorta_eliminar_servicios,necesidad_eliminar_servicios;
    ImageButton eliminar_buscar_servicios;
    RequestQueue requestbuscar;
    Button eliminar_servicios;
    JsonObjectRequest jsonObjectRequestBuscar;
    StringRequest stringRequest_servicios;
    ImageView imagen1_eliminar_servicios;
    private InterstitialAd anuncioservicios;
    Toolbar barra_servicios;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarServicios.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_servicios);

        whatsapp = findViewById(R.id.whatsapp_eliminar_servicios);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarServicios.this,Whatsapp);
            }
        });
        barra_servicios = findViewById(R.id.toolbar_eliminar_servicios);
        barra_servicios.setTitle("Eliminar Servicios");
        titulo_eliminar_servicios = findViewById(R.id.eliminar_titulo_servicios);
        descripcioncorta_eliminar_servicios = findViewById(R.id.eliminar_descripcion_servicios);
        necesidad_eliminar_servicios = findViewById(R.id.eliminar_necesidad_servicios);
        id_eliminar_servicios = findViewById(R.id.id_eliminar_servicios);
        eliminar_servicios = findViewById(R.id.eliminar_final_servicios);
        eliminar_buscar_servicios = findViewById(R.id.eliminar_buscar_servicios);
        imagen1_eliminar_servicios = findViewById(R.id.imagen1_eliminar_servicios);

        eliminar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarServicios.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_servicios();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_servicios();
                cargandoDialog.Mostrar();

            }
        });

        anuncioservicios = new InterstitialAd(this);
        anuncioservicios.setAdUnitId(AnuncioEliminar);
        anuncioservicios.loadAd(new AdRequest.Builder().build());


    }

    private boolean validarid(){
        String idinput = id_eliminar_servicios.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_eliminar_servicios.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            id_eliminar_servicios.setError(texto_superado);
            return false;
        }
        else {
            id_eliminar_servicios.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_servicios() {
        String url_buscar_servicios = DireccionServidor+"wsnJSONBuscarServicios.php?id_ofertaservicios="+id_eliminar_servicios.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_servicios,null,this,this);
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

        titulo_eliminar_servicios.setText(servicios.getTitulo_row_ofertaservicios());
        descripcioncorta_eliminar_servicios.setText(servicios.getDescripcion_row_ofertaservicios());
        necesidad_eliminar_servicios.setText(servicios.getNecesidad_row_ofertaservicios());
        Picasso.get().load(servicios.getImagen1_ofertaservicios())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_servicios);

        cargandoDialog.Ocultar();

    }

    private void cargarEliminar_servicios() {

        String url_servicios = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_servicios= new StringRequest(Request.Method.POST, url_servicios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarServicios.this);
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
                            if (anuncioservicios.isLoaded()) { anuncioservicios.show(); }
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
                String idinput = id_eliminar_servicios.getEditText().getText().toString().trim();
                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_ofertaservicios",idinput);
                parametros.put("publicacion","Servicios");
                return parametros;
            }
        };
        RequestQueue request_servicios = Volley.newRequestQueue(this);
        stringRequest_servicios.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_servicios.add(stringRequest_servicios);
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
