package com.pacificblack.informatebuenaventura.eliminar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;
import com.pacificblack.informatebuenaventura.extras.Cargando;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.pacificblack.informatebuenaventura.texto.Servidor.AnuncioEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;
import static com.pacificblack.informatebuenaventura.texto.Servidor.NosepudoEliminar;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nosepudobuscar;

public class EliminarFunebres extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener  {

    StringRequest stringRequest_funebres;


    TextInputLayout id_eliminar_funebres;

    TextView titulo_eliminar_funebres, descripcioncorta_eliminar_funebres, descripcion1_eliminar_funebres, descripcion2_eliminar_funebres;

    ImageButton eliminar_funebres,eliminar_buscar_funebres;
    RequestQueue requestbuscar;
    JsonObjectRequest jsonObjectRequestBuscar;
    ImageView imagen1_eliminar_funebres,imagen2_eliminar_funebres,imagen3_eliminar_funebres;
    private InterstitialAd anunciofunebres;

    Cargando cargando = new Cargando(EliminarFunebres.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_funebres);

        id_eliminar_funebres = findViewById(R.id.id_eliminar_funebres);
        titulo_eliminar_funebres = findViewById(R.id.eliminar_titulo_funebres);
        descripcioncorta_eliminar_funebres = findViewById(R.id.eliminar_descripcioncorta_funebres);
        descripcion1_eliminar_funebres = findViewById(R.id.eliminar_descripcion1_funebres);
        descripcion2_eliminar_funebres = findViewById(R.id.eliminar_descripcion2_funebres);
        imagen1_eliminar_funebres = findViewById(R.id.imagen1_eliminar_funebres);
        imagen2_eliminar_funebres = findViewById(R.id.imagen2_eliminar_funebres);
        imagen3_eliminar_funebres = findViewById(R.id.imagen3_eliminar_funebres);
        eliminar_funebres = findViewById(R.id.eliminar_final_funebres);
        eliminar_buscar_funebres = findViewById(R.id.eliminar_buscar_funebres);


        eliminar_funebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validarid()){return;}

                AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarFunebres.this);
                mensaje.setMessage("¿Esta seguro que desea eliminar la publicación?")
                        .setCancelable(false).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cargarEliminar_funebres();
                        cargando.iniciarprogress();

                    }
                });

                AlertDialog titulo = mensaje.create();
                titulo.setTitle("Modificar Publicación");
                titulo.show();

            }
        });


        requestbuscar = Volley.newRequestQueue(getApplicationContext());
        eliminar_buscar_funebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validarid()){return;}
                cargarBusqueda_funebres();
                cargando.iniciarprogress();

            }
        });

        anunciofunebres = new InterstitialAd(this);
        anunciofunebres.setAdUnitId(AnuncioEliminar);
        anunciofunebres.loadAd(new AdRequest.Builder().build());

    }

    private boolean validarid(){
        String idinput = id_eliminar_funebres.getEditText().getText().toString().trim();

        if (idinput.isEmpty()){
            id_eliminar_funebres.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(idinput.length()>15){

            id_eliminar_funebres.setError(""+R.string.supera);
            return false;
        }
        else {
            id_eliminar_funebres.setError(null);
            return true;
        }
    }

    private void cargarBusqueda_funebres() {

        String url_buscar_funebres = DireccionServidor+"wsnJSONBuscarFunebres.php?id_funebres="+id_eliminar_funebres.getEditText().getText().toString().trim();

        jsonObjectRequestBuscar = new JsonObjectRequest(Request.Method.GET,url_buscar_funebres,null,this,this);

        requestbuscar.add(jsonObjectRequestBuscar);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), Nosepudobuscar, Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        cargando.cancelarprogress();

    }
    @Override
    public void onResponse(JSONObject response) {
        Funebres funebres = new Funebres();

        JSONArray json = response.optJSONArray("funebres");
        JSONObject jsonObject = null;

        try {

            jsonObject = json.getJSONObject(0);

            funebres.setId_funebres(jsonObject.getInt("id_funebres"));
            funebres.setTitulo_row_funebres(jsonObject.getString("titulo_funebres"));
            funebres.setDescripcion_row_funebres(jsonObject.getString("descripcionrow_funebres"));
            funebres.setFechapublicacion_row_funebres(jsonObject.getString("fechapublicacion_funebres"));
            funebres.setImagen1_funebres(jsonObject.getString("imagen1_funebres"));
            funebres.setImagen2_funebres(jsonObject.getString("imagen2_funebres"));
            funebres.setImagen3_funebres(jsonObject.getString("imagen3_funebres"));
            funebres.setVistas_funebres(jsonObject.getInt("vistas_funebres"));
            funebres.setDescripcion1_funebres(jsonObject.getString("descripcion1_funebres"));
            funebres.setDescripcion2_funebres(jsonObject.getString("descripcion2_funebres"));


        }catch (JSONException e){
            e.printStackTrace();
        }

        titulo_eliminar_funebres.setText(funebres.getTitulo_row_funebres());
        descripcioncorta_eliminar_funebres.setText(funebres.getDescripcion_row_funebres());
        descripcion1_eliminar_funebres.setText(funebres.getDescripcion1_funebres());
        descripcion2_eliminar_funebres.setText(funebres.getDescripcion2_funebres());

        Picasso.get().load(funebres.getImagen1_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen1_eliminar_funebres);

        Picasso.get().load(funebres.getImagen2_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen2_eliminar_funebres);

        Picasso.get().load(funebres.getImagen3_funebres())
                .placeholder(R.drawable.imagennodisponible)
                .error(R.drawable.imagennodisponible)
                .into(imagen3_eliminar_funebres);

        cargando.cancelarprogress();



    }


    private void cargarEliminar_funebres() {

        String url_funebres = DireccionServidor+"wsnJSONEliminar.php?";

        stringRequest_funebres= new StringRequest(Request.Method.POST, url_funebres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String resul = "Eliminada exitosamente";
                Pattern regex = Pattern.compile("\\b" + Pattern.quote(resul) + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher match = regex.matcher(response);

                if (match.find()) {

                    cargando.cancelarprogress();

                    AlertDialog.Builder mensaje = new AlertDialog.Builder(EliminarFunebres.this);

                    mensaje.setMessage(response)
                            .setCancelable(false)
                            .setPositiveButton("Entiendo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                    if (anunciofunebres.isLoaded()) {
                                        anunciofunebres.show();
                                    } else {
                                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    }
                                }
                            });

                    AlertDialog titulo = mensaje.create();
                    titulo.setTitle("Recuerda");
                    titulo.show();

                    Log.i("Funciona : ",response);

                }else {
                    Toast.makeText(getApplicationContext(),NosepudoEliminar,Toast.LENGTH_LONG).show();
                    Log.i("Error",response);
                    cargando.cancelarprogress();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),Nohayinternet,Toast.LENGTH_LONG).show();
                        Log.i("ERROR",error.toString());
                        cargando.cancelarprogress();

                    }
                }){
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = id_eliminar_funebres.getEditText().getText().toString().trim();

                Map<String,String> parametros = new HashMap<>();

                parametros.put("id_funebres",idinput);
                parametros.put("publicacion","Funebres");

                return parametros;
            }
        };

        RequestQueue request_funebres = Volley.newRequestQueue(this);
        request_funebres.add(stringRequest_funebres);

    }


}
