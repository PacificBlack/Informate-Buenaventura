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
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaEmpleos;
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

public class EliminarEmpleos extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    TextInputLayout id_eliminar_empleos;
    TextView titulo_eliminar_empleos, descripcioncorta_eliminar_empleos, necesidad_eliminar_empleos;
    ImageButton eliminar_buscar_empleos;
    Button eliminar_empleos;
    RequestQueue requestbuscar;
    StringRequest stringRequest_empleos;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_empleos;
    private InterstitialAd anuncioempleos;
    Toolbar barra_empleos;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarEmpleos.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_empleos);

        whatsapp = findViewById(R.id.whatsapp_eliminar_empleos);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarEmpleos.this,Whatsapp);
            }
        });
        barra_empleos = findViewById(R.id.toolbar_eliminar_empleos);
        barra_empleos.setTitle("Eliminar Empleos");
        barra_empleos.setTitleTextColor(Color.WHITE);
        titulo_eliminar_empleos = findViewById(R.id.eliminar_titulo_empleos);
        descripcioncorta_eliminar_empleos = findViewById(R.id.eliminar_descripcion_empleos);
        necesidad_eliminar_empleos = findViewById(R.id.eliminar_necesidad_empleos);
        id_eliminar_empleos = findViewById(R.id.eliminar_id_empleos);
        eliminar_empleos = findViewById(R.id.eliminar_empleos);
        eliminar_buscar_empleos = findViewById(R.id.eliminar_buscar_empleos);
        imagen1_eliminar_empleos = findViewById(R.id.imagen1_eliminar_empleos);

        eliminar_empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarEmpleos.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_empleos();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Eliminar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_empleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_empleos();
                cargandoDialog.Mostrar();

            }
        });

        anuncioempleos = new InterstitialAd(this);
        anuncioempleos.setAdUnitId("ca-app-pub-7236340326570289/2026723612");
        anuncioempleos.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = id_eliminar_empleos.getEditText().getText().toString().trim();
        if (idinput.isEmpty()){
            id_eliminar_empleos.setError(id_vacio);
            return false;
        }
        else if(idinput.length()>15){
            id_eliminar_empleos.setError(texto_superado);
            return false;
        }
        else {
            id_eliminar_empleos.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_empleos() {
        String url_buscar_empleos = DireccionServidor+"wsnJSONBuscarEmpleos.php?id_ofertaempleos="+id_eliminar_empleos.getEditText().getText().toString().trim();
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

        titulo_eliminar_empleos.setText(empleos.getTitulo_row_ofertasempleos());
        descripcioncorta_eliminar_empleos.setText(empleos.getDescripcion_row_ofertasempleos());
        necesidad_eliminar_empleos.setText(empleos.getNecesidad_row_ofertasempleos());
        Picasso.get().load(empleos.getImagen1_ofertasempleos())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_empleos);

        cargandoDialog.Ocultar();

    }

    private void cargarEliminar_empleos() {

        String url_empleos = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_empleos= new StringRequest(Request.Method.POST, url_empleos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarEmpleos.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_personalizado,null);
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
                            if (anuncioempleos.isLoaded()) { anuncioempleos.show(); }
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

                String idinput = id_eliminar_empleos.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_ofertaempleos",idinput);
                parametros.put("publicacion","Empleos");

                return parametros;
            }
        };
        RequestQueue request_empleos = Volley.newRequestQueue(this);
        stringRequest_empleos.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_empleos.add(stringRequest_empleos);

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
