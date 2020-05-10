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
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;
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
import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;


public class EliminarDonaciones extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    private InterstitialAd anuncioEliminarDonaciones;
    TextView titulo_eliminar_donaciones, descripcioncorta_eliminar_donaciones, descripcion1_eliminar_donaciones, meta_eliminar_donaciones;
    TextInputLayout buscar_eliminar_donaciones;
    ImageButton eliminar_buscar_donaciones;
    Button eliminar_donaciones;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    StringRequest stringRequest_donaciones;
    ImageView imagen1_eliminar_donaciones,imagen2_eliminar_donaciones;
    Toolbar barra_donaciones;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarDonaciones.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_donaciones);

        whatsapp = findViewById(R.id.whatsapp_eliminar_donaciones);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarDonaciones.this,Whatsapp);
            }
        });
        barra_donaciones = findViewById(R.id.toolbar_eliminar_donaciones);
        barra_donaciones.setTitle("Eliminar Donaciones");
        titulo_eliminar_donaciones = findViewById(R.id.eliminar_titulo_donaciones);
        descripcioncorta_eliminar_donaciones = findViewById(R.id.eliminar_descripcioncorta_donaciones);
        descripcion1_eliminar_donaciones = findViewById(R.id.eliminar_descripcion1_donaciones);
        meta_eliminar_donaciones = findViewById(R.id.eliminar_meta_donaciones);
        eliminar_buscar_donaciones = findViewById(R.id.eliminar_buscar_donaciones);
        imagen1_eliminar_donaciones = findViewById(R.id.imagen1_eliminar_donaciones);
        imagen2_eliminar_donaciones = findViewById(R.id.imagen2_eliminar_donaciones);
        eliminar_donaciones = findViewById(R.id.eliminar_donaciones);
        buscar_eliminar_donaciones= findViewById(R.id.eliminar_id_donaciones);

        eliminar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarDonaciones.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_donaciones();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_donaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_donaciones();
                cargandoDialog.Mostrar();

            }
        });

        anuncioEliminarDonaciones = new InterstitialAd(this);
        anuncioEliminarDonaciones.setAdUnitId(AnuncioEliminar);
        anuncioEliminarDonaciones.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = buscar_eliminar_donaciones.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_donaciones.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            buscar_eliminar_donaciones.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_donaciones.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_donaciones() {

        String url_buscar_donaciones = DireccionServidor+"wsnJSONBuscarDonaciones.php?id_donaciones="+buscar_eliminar_donaciones.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_donaciones,null,this,this);
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

        Donaciones donacion = new Donaciones();

        JSONArray json = response.optJSONArray("donaciones");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            donacion.setId_donaciones(jsonObject.optInt("id_donaciones"));
            donacion.setTitulo_row_donaciones(jsonObject.optString("titulo_donaciones"));
            donacion.setDescripcion_row_donaciones(jsonObject.optString("descripcionrow_donaciones"));
            donacion.setFechapublicacion_row_donaciones(jsonObject.optString("fechapublicacion_donaciones"));
            donacion.setImagen1_donaciones(jsonObject.optString("imagen1_donaciones"));
            donacion.setImagen2_donaciones(jsonObject.getString("imagen2_donaciones"));
            donacion.setVistas_donaciones(jsonObject.optInt("vistas_donaciones"));
            donacion.setMeta_row_donaciones(jsonObject.optInt("meta_donaciones"));
            donacion.setDescripcion1_donaciones(jsonObject.optString("descripcion1_donaciones"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_donaciones.setText(donacion.getTitulo_row_donaciones());
        descripcioncorta_eliminar_donaciones.setText(donacion.getDescripcion_row_donaciones());
        descripcion1_eliminar_donaciones.setText(donacion.getDescripcion1_donaciones());
        meta_eliminar_donaciones.setText(String.valueOf(donacion.getMeta_row_donaciones()));

        Picasso.get().load(donacion.getImagen1_donaciones())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_donaciones);

        Picasso.get().load(donacion.getImagen2_donaciones())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen2_eliminar_donaciones);

        cargandoDialog.Ocultar();


    }
    private void cargarEliminar_donaciones() {

        String url_donaciones = DireccionServidor+"wsnJSONEliminar.php?";
        stringRequest_donaciones= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()){
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarDonaciones.this);
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
                            if (anuncioEliminarDonaciones.isLoaded()) { anuncioEliminarDonaciones.show(); }
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

                String idinput = buscar_eliminar_donaciones.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_donaciones",idinput);
                parametros.put("publicacion","Donaciones");
                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };
        RequestQueue request_funebres_actualizar = Volley.newRequestQueue(this);
        stringRequest_donaciones.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_funebres_actualizar.add(stringRequest_donaciones);

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
