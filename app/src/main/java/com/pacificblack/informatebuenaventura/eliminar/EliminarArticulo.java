package com.pacificblack.informatebuenaventura.eliminar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
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
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;
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

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

//TODO: Esta full pero hay que verificar el tamaño de las imagenes

public class EliminarArticulo extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {

    TextView titulo_eliminar_comprayventa, descripcioncorta_eliminar_comprayventa, descripcion_eliminar_comprayventa, descripcionextra_eliminar_comprayventa, precio_eliminar_comprayventa, ubicacion_eliminar_comprayventa, cantidad_eliminar_comprayventa, contacto_eliminar_comprayventa;
    StringRequest stringRequest_comprayventa;
    TextInputLayout buscar_eliminar_comprayventa;
    ImageButton eliminar_comprayventa, eliminar_buscar_comprayventa;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_comprayventa,imagen2_eliminar_comprayventa,imagen3_eliminar_comprayventa;

    private InterstitialAd anuncioAdopcion_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_articulo);

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
                if(!validarid()){return;}

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

                if (!validarid()){return;}

                cargarBusqueda_comprayventa();
            }
        });

        anuncioAdopcion_eliminar = new InterstitialAd(this);
        anuncioAdopcion_eliminar.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        anuncioAdopcion_eliminar.loadAd(new AdRequest.Builder().build());
    }

    private boolean validarid(){
        String idinput = buscar_eliminar_comprayventa.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            buscar_eliminar_comprayventa.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){
            buscar_eliminar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            buscar_eliminar_comprayventa.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_comprayventa() {

        String url_buscar_comprayventa = DireccionServidor+"wsnJSONBuscarComprayVenta.php?id_comprayventa="+buscar_eliminar_comprayventa.getEditText().getText().toString().trim();
        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_comprayventa,null,this,this);
        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),Nosepudobuscar,Toast.LENGTH_LONG).show();
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
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen2_comprayventa())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_comprayventa);

        Picasso.get().load(comprayVenta.getImagen3̣̣_comprayventa())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_comprayventa);

    }

    private void cargarEliminar_comprayventa() {

        String url_comprayventa = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_comprayventa= new StringRequest(Request.Method.POST, url_comprayventa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);


                if (match.find()){

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarArticulo.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anuncioAdopcion_eliminar.isLoaded()) {
                                        anuncioAdopcion_eliminar.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }
                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Eliminada exitosamente");
                    titulo.show();

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = buscar_eliminar_comprayventa.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_comprayventa",idinput);
                parametros.put("publicacion","ComprayVenta");

                return parametros;
            }
        };

        RequestQueue request_comprayventa_eliminar = Volley.newRequestQueue(this);
        request_comprayventa_eliminar.add(stringRequest_comprayventa);
    }
}