package com.pacificblack.informatebuenaventura.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.encuestas.Encuestas;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleEncuestas extends AppCompatActivity implements RewardedVideoAdListener {

    TextView tituloencuestas, descripcion, voto1,voto2,voto3,voto4;
    ImageView imagenencuesta;
    RadioButton opcion1encuesta,opcion2encuesta,opcion3encuesta,opcion4encuesta;
    Button votarencuesta;
    StringRequest stringRequest_encuestas_actualizar,stringRequest_encuestas_votos;
    int id_actualizar;
    private RewardedVideoAd AnuncioVotar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_encuestas);

        MobileAds.initialize(this, "ca-app-pub-7236340326570289/7068324972");
        AnuncioVotar = MobileAds.getRewardedVideoAdInstance(this);
        AnuncioVotar.setRewardedVideoAdListener((RewardedVideoAdListener) this);



        tituloencuestas = findViewById(R.id.titulo_detalle_encuesta);
        descripcion = findViewById(R.id.descricion1_detalle_encuesta);
        imagenencuesta = findViewById(R.id.imagen1_detalle_encuesta);
        opcion1encuesta = findViewById(R.id.opcion1);
        opcion2encuesta = findViewById(R.id.opcion2);
        opcion3encuesta = findViewById(R.id.opcion3);
        opcion4encuesta = findViewById(R.id.opcion4);

        voto1 = findViewById(R.id.votos1);
        voto2 = findViewById(R.id.votos2);
        voto3 = findViewById(R.id.votos3);
        voto4 = findViewById(R.id.votos4);

        votarencuesta   = findViewById(R.id.votar_encuesta);

        votarencuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionado();
            }
        });



        Bundle objetoEncuesta = getIntent().getExtras();

        Encuestas encuesta = null;

        if (objetoEncuesta!=null){

            encuesta = (Encuestas) objetoEncuesta.getSerializable("objeto7");

            id_actualizar = encuesta.getId_encuestas();
            tituloencuestas.setText(encuesta.getTitulo_row_encuestas());
            descripcion.setText(encuesta.getDescripcion1_encuestas());

            Picasso.get().load(encuesta.getImagen1_encuestas())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(imagenencuesta);

            final String imagen1_link = encuesta.getImagen1_encuestas();
            imagenencuesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentencuestas = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentencuestas.putExtras(envioimg);
                    startActivity(intentencuestas);
                }
            });

            opcion1encuesta.setText(encuesta.getOpcion1());
            opcion2encuesta.setText(encuesta.getOpcion2());
            opcion3encuesta.setText(encuesta.getOpcion3());
            opcion4encuesta.setText(encuesta.getOpcion4());

            voto1.setText(String.valueOf(encuesta.getVoto1_encuestas()));
            voto2.setText(String.valueOf(encuesta.getVoto2_encuestas()));
            voto3.setText(String.valueOf(encuesta.getVoto3_encuestas()));
            voto4.setText(String.valueOf(encuesta.getVoto4_encuestas()));


            String url_encuestas = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_encuestas_actualizar= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i("Si actualizo","Vista Positiva");

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("No actualizo","Vista Negativa");
                        }
                    }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    String idinput = String.valueOf(id_actualizar);

                    Map<String,String> parametros = new HashMap<>();
                    parametros.put("id_encuestas",idinput);
                    parametros.put("publicacion","Encuestas");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
            request_encuestas_eliminar.add(stringRequest_encuestas_actualizar);



        }



    cargaranuncio();}

    private void cargaranuncio() {
    AnuncioVotar.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
    }


    public void seleccionado(){

        if (opcion1encuesta.isChecked()){

            AlertDialog.Builder mensaje = new AlertDialog.Builder(DetalleEncuestas.this);

            mensaje.setMessage("Usted ha seleccionado la opcion "+opcion1encuesta.getText()+" \n Para evitar que gente inescrupulosa vote varias veces, usted debe ver un anuncio antes de votar, si no lo hace su voto no sera efectuado correctamente")
                    .setCancelable(true)
                    .setPositiveButton("Votar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            if (AnuncioVotar.isLoaded()) {
                                AnuncioVotar.show();
                                registrarvoto1();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        }
                    });

            AlertDialog titulo = mensaje.create();
            titulo.setTitle("Aviso");
            titulo.show();


        }
        if (opcion2encuesta.isChecked()){

            AlertDialog.Builder mensaje = new AlertDialog.Builder(DetalleEncuestas.this);

            mensaje.setMessage("Usted ha seleccionado la opcion "+opcion2encuesta.getText()+" \n Para evitar que gente inescrupulosa vote varias veces, usted debe ver un anuncio antes de votar, si no lo hace su voto no sera efectuado correctamente")
                    .setCancelable(true)
                    .setPositiveButton("Votar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            if (AnuncioVotar.isLoaded()) {
                                AnuncioVotar.show();
                                registrarvoto2();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        }
                    });

            AlertDialog titulo = mensaje.create();
            titulo.setTitle("Aviso");
            titulo.show();

        }
        if (opcion3encuesta.isChecked()){

            AlertDialog.Builder mensaje = new AlertDialog.Builder(DetalleEncuestas.this);

            mensaje.setMessage("Usted ha seleccionado la opcion "+opcion3encuesta.getText()+" \n Para evitar que gente inescrupulosa vote varias veces, usted debe ver un anuncio antes de votar, si no lo hace su voto no sera efectuado correctamente")
                    .setCancelable(true)
                    .setPositiveButton("Votar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            if (AnuncioVotar.isLoaded()) {
                                AnuncioVotar.show();
                                registrarvoto3();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        }
                    });

            AlertDialog titulo = mensaje.create();
            titulo.setTitle("Aviso");
            titulo.show();

        }
        if (opcion4encuesta.isChecked()){

            AlertDialog.Builder mensaje = new AlertDialog.Builder(DetalleEncuestas.this);

            mensaje.setMessage("Usted ha seleccionado la opcion "+opcion4encuesta.getText()+" \n Para evitar que gente inescrupulosa vote varias veces, usted debe ver un anuncio antes de votar, si no lo hace su voto no sera efectuado correctamente")
                    .setCancelable(true)
                    .setPositiveButton("Votar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                            if (AnuncioVotar.isLoaded()) {
                                AnuncioVotar.show();
                                registrarvoto4();
                            } else {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                            }

                        }
                    });

            AlertDialog titulo = mensaje.create();
            titulo.setTitle("Aviso");
            titulo.show();

        }

    }


    private void registrarvoto4() {
        String url_encuestas = DireccionServidor+"wsnJSONVotarEncuesta.php?";

        stringRequest_encuestas_votos= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Si actualizo","Voto Positiva");

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("No actualizo","Voto Negativa");
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = String.valueOf(id_actualizar);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_encuestas",idinput);
                parametros.put("Opcion","Opcion4");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
        request_encuestas_eliminar.add(stringRequest_encuestas_votos);

    }

    private void registrarvoto3() {
        String url_encuestas = DireccionServidor+"wsnJSONVotarEncuesta.php?";

        stringRequest_encuestas_votos= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Si actualizo","Voto Positiva");

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("No actualizo","Voto Negativa");
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = String.valueOf(id_actualizar);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_encuestas",idinput);
                parametros.put("Opcion","Opcion3");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
        request_encuestas_eliminar.add(stringRequest_encuestas_votos);

    }

    private void registrarvoto2() {
        String url_encuestas = DireccionServidor+"wsnJSONVotarEncuesta.php?";

        stringRequest_encuestas_votos= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Si actualizo","Voto Positiva");

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("No actualizo","Voto Negativa");
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = String.valueOf(id_actualizar);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_encuestas",idinput);
                parametros.put("Opcion","Opcion2");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
        request_encuestas_eliminar.add(stringRequest_encuestas_votos);

    }

    private void registrarvoto1() {
        String url_encuestas = DireccionServidor+"wsnJSONVotarEncuesta.php?";

        stringRequest_encuestas_votos= new StringRequest(Request.Method.POST, url_encuestas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Si actualizo","Voto Positiva");

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("No actualizo","Voto Negativa");
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String idinput = String.valueOf(id_actualizar);

                Map<String,String> parametros = new HashMap<>();
                parametros.put("id_encuestas",idinput);
                parametros.put("Opcion","Opcion1");

                Log.i("Parametros", String.valueOf(parametros));

                return parametros;
            }
        };

        RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(this);
        request_encuestas_eliminar.add(stringRequest_encuestas_votos);

    }

    @Override
    public void onRewarded(RewardItem reward) {

        //Todo: Aqui pongo cuando termine

        Toast.makeText(getApplicationContext(),"Voto registrado correctamente",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
          }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(this,"Gracias" ,Toast.LENGTH_SHORT).show();

    }

}
