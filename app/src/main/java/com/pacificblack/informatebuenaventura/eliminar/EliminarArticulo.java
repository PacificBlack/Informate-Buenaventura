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
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;
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

public class EliminarArticulo extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextView titulo_eliminar_comprayventa, descripcioncorta_eliminar_comprayventa, descripcion_eliminar_comprayventa, descripcionextra_eliminar_comprayventa, precio_eliminar_comprayventa, ubicacion_eliminar_comprayventa, cantidad_eliminar_comprayventa, contacto_eliminar_comprayventa;
    StringRequest stringRequest_comprayventa;
    TextInputLayout buscar_eliminar_comprayventa;
    ImageButton eliminar_buscar_comprayventa;
    Button eliminar_comprayventa;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_comprayventa, imagen2_eliminar_comprayventa, imagen3_eliminar_comprayventa;
    private InterstitialAd anuncioArticulo_eliminar;
    Toolbar barra_articulo;
    ImageView whatsapp;
    CargandoDialog cargandoDialog = new CargandoDialog(EliminarArticulo.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_articulo);

        whatsapp = findViewById(R.id.whatsapp_eliminar_articulo);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsapp(EliminarArticulo.this, Whatsapp);
            }
        });
        barra_articulo = findViewById(R.id.toolbar_eliminar_articulo);
        barra_articulo.setTitle("Eliminar Desaparicion");
        titulo_eliminar_comprayventa = findViewById(R.id.eliminar_titulo_comprayventa);
        descripcioncorta_eliminar_comprayventa = findViewById(R.id.eliminar_descripcioncorta_comprayventa);
        descripcion_eliminar_comprayventa = findViewById(R.id.eliminar_descripcion_comprayventa);
        descripcionextra_eliminar_comprayventa = findViewById(R.id.eliminar_descripcionextra_comprayventa);
        precio_eliminar_comprayventa = findViewById(R.id.eliminar_precio_comprayventa);
        ubicacion_eliminar_comprayventa = findViewById(R.id.eliminar_ubicacion_comprayventa);
        cantidad_eliminar_comprayventa = findViewById(R.id.eliminar_cantidad_comprayventa);
        contacto_eliminar_comprayventa = findViewById(R.id.eliminar_contacto_comprayventa);
        imagen1_eliminar_comprayventa = findViewById(R.id.imagen1_eliminar_comprayventa);
        imagen2_eliminar_comprayventa = findViewById(R.id.imagen2_eliminar_comprayventa);
        imagen3_eliminar_comprayventa = findViewById(R.id.imagen3_eliminar_comprayventa);
        buscar_eliminar_comprayventa = findViewById(R.id.eliminar_id_comprayventa);
        eliminar_comprayventa = findViewById(R.id.eliminar_comprayventa);
        eliminar_buscar_comprayventa = findViewById(R.id.eliminar_buscar_comprayventa);

        eliminar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()) {
                    return;
                }

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarArticulo.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_comprayventa();
                        cargandoDialog.Mostrar();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Eliminar Publicación");
                titulo.show();

            }
        });

        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validarid()) {
                    return;
                }
                cargarBusqueda_comprayventa();
                cargandoDialog.Mostrar();
            }
        });

        anuncioArticulo_eliminar = new InterstitialAd(this);
        anuncioArticulo_eliminar.setAdUnitId("ca-app-pub-7236340326570289/2218295306");
        anuncioArticulo_eliminar.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid() {
        String idinput = buscar_eliminar_comprayventa.getEditText().getText().toString().trim();
        if (idinput.isEmpty()) {
            buscar_eliminar_comprayventa.setError(id_vacio);
            return false;
        } else if (idinput.length() > 15) {
            buscar_eliminar_comprayventa.setError(texto_superado);
            return false;
        } else {
            buscar_eliminar_comprayventa.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_comprayventa() {
        String url_buscar_comprayventa = DireccionServidor + "wsnJSONBuscarComprayVenta.php?id_comprayventa=" + buscar_eliminar_comprayventa.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET, url_buscar_comprayventa, null, this, this);
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

        ComprayVenta comprayVenta = new ComprayVenta();

        JSONArray json = response.optJSONArray("comprayventa");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);

            comprayVenta.setId_comprayventa(jsonObject.getInt("id_comprayventa"));
            comprayVenta.setImagen1_comprayventa(jsonObject.getString("imagen1_comprayventa"));
            comprayVenta.setImagen2_comprayventa(jsonObject.getString("imagen2_comprayventa"));
            comprayVenta.setImagen3̣̣_comprayventa(jsonObject.getString("imagen3_comprayventa"));
            comprayVenta.setTitulo_row_comprayventa(jsonObject.getString("titulo_comprayventa"));
            comprayVenta.setDescripcion_row_comprayventa(jsonObject.getString("descripcionrow_comprayventa"));
            comprayVenta.setDescripcion_comprayventa(jsonObject.getString("descripcion_comprayventa"));
            comprayVenta.setFechapublicacion_row_comprayventa(jsonObject.getString("fechapublicacion_comprayventa"));
            comprayVenta.setPrecio_row_comprayventa(jsonObject.getString("precio_comprayventa"));
            comprayVenta.setContacto_comprayventa(jsonObject.getString("contacto_comprayventa"));
            comprayVenta.setUbicacion_comprayventa(jsonObject.getString("ubicacion_comprayventa"));
            comprayVenta.setDescripcionextra_comprayventa(jsonObject.getString("descripcionextra_comprayventa"));
            comprayVenta.setCantidad_comprayventa(jsonObject.getInt("cantidad_comprayventa"));
            comprayVenta.setVista_comprayventa(jsonObject.getInt("vistas_comprayventa"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        titulo_eliminar_comprayventa.setText(comprayVenta.getTitulo_row_comprayventa());
        descripcioncorta_eliminar_comprayventa.setText(comprayVenta.getDescripcion_row_comprayventa());
        descripcion_eliminar_comprayventa.setText(comprayVenta.getDescripcion_comprayventa());
        descripcionextra_eliminar_comprayventa.setText(comprayVenta.getDescripcionextra_comprayventa());
        precio_eliminar_comprayventa.setText(comprayVenta.getPrecio_row_comprayventa());
        ubicacion_eliminar_comprayventa.setText(comprayVenta.getUbicacion_comprayventa());
        cantidad_eliminar_comprayventa.setText(String.valueOf(comprayVenta.getCantidad_comprayventa()));
        contacto_eliminar_comprayventa.setText(comprayVenta.getContacto_comprayventa());

        Picasso.get().load(comprayVenta.getImagen1_comprayventa())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen1_eliminar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen2_comprayventa())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen2_eliminar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen3̣̣_comprayventa())
                .placeholder(R.drawable.ib)
                .error(R.drawable.ib)
                .into(imagen3_eliminar_comprayventa);

        cargandoDialog.Ocultar();


    }

    private void cargarEliminar_comprayventa() {

        String url_comprayventa = DireccionServidor + "wsnJSONEliminar.php?";

        stringRequest_comprayventa = new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {
                    cargandoDialog.Ocultar();
                    AlertDialog.Builder builder = new AlertDialog.Builder(EliminarArticulo.this);
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
                            if (anuncioArticulo_eliminar.isLoaded()) {
                                anuncioArticulo_eliminar.show();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }
                        }
                    });
                    Log.i("Muestra", response);
                } else {
                    Toast.makeText(getApplicationContext(), NosepudoEliminar, Toast.LENGTH_LONG).show();
                    cargandoDialog.Ocultar();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), Nohayinternet, Toast.LENGTH_LONG).show();
                        cargandoDialog.Ocultar();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idinput = buscar_eliminar_comprayventa.getEditText().getText().toString().trim();
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_comprayventa", idinput);
                parametros.put("publicacion", "ComprayVenta");
                return parametros;
            }
        };
        RequestQueue request_comprayventa_eliminar = Volley.newRequestQueue(this);
        stringRequest_comprayventa.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request_comprayventa_eliminar.add(stringRequest_comprayventa);
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