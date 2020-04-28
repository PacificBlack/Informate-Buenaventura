package com.pacificblack.informatebuenaventura.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;


public class DetalleNoticias extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    TextView titulo_noticias,descripcion1_noticias,
            descripcion2_noticias,descripcion3_noticias;

    ImageView imagen1_noticias,imagen2_noticias,
            imagen3_noticias,imagen4_noticias;


    YouTubePlayerView video_noticias;
    String API_KEY = "AIzaSyCjplldkmscSZfu1yMY7eJr4xiSjuAbZgo";

    String video;

    StringRequest stringRequest_noticias_actualizar;
    int id_actualizar;

    private AdView baner1,baner2,baner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticias);

        titulo_noticias = findViewById(R.id.titulo_detalle_noticias);
        descripcion1_noticias = findViewById(R.id.descricion1_detalle_noticias);
        descripcion2_noticias = findViewById(R.id.descripcion2_detalle_noticias);
        descripcion3_noticias = findViewById(R.id.descripcion3_detalle_noticias);
        imagen1_noticias = findViewById(R.id.imagen1_detalle_noticias);
        imagen2_noticias = findViewById(R.id.imagen2_detalle_noticias);
        imagen3_noticias = findViewById(R.id.imagen3_detalle_noticias);
        imagen4_noticias = findViewById(R.id.imagen4_detalle_noticias);

        descripcion3_noticias.setMovementMethod(LinkMovementMethod.getInstance());

        ///////////////////////////////////////////////////////////////////////////7
        video_noticias = findViewById(R.id.video_noticias);
        video_noticias.initialize(API_KEY,this);

        Bundle objetoNoticias = getIntent().getExtras();

        Noticias noticias = null;


        if (objetoNoticias != null){

            noticias = (Noticias) objetoNoticias.getSerializable("objeto10");

            id_actualizar = noticias.getId_noticias();
            titulo_noticias.setText(noticias.getTitulo_row_noticias());
            descripcion1_noticias.setText(noticias.getDescripcion1_noticias());
            descripcion2_noticias.setText(noticias.getDescripcion2_noticias());
            descripcion3_noticias.setText(noticias.getDescripcion3_noticias());

            Picasso.get().load(noticias.getImagen1_noticias())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen1_noticias);

            Picasso.get().load(noticias.getImagen2_noticias())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen2_noticias);

            Picasso.get().load(noticias.getImagen3_noticias())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen3_noticias);

            Picasso.get().load(noticias.getImagen4_noticias())
                    .placeholder(R.drawable.imagennodisponible)
                    .error(R.drawable.imagennodisponible)
                    .into(imagen4_noticias);


            final String imagen1_link = noticias.getImagen1_noticias();
            imagen1_noticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNoticias = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentNoticias.putExtras(envioimg);
                    startActivity(intentNoticias);
                }
            });
            final String imagen2_link = noticias.getImagen2_noticias();
            imagen2_noticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNoticias = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentNoticias.putExtras(envioimg);
                    startActivity(intentNoticias);
                }
            });
            final String imagen3_link = noticias.getImagen3_noticias();
            imagen3_noticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNoticias = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen3_link);
                    intentNoticias.putExtras(envioimg);
                    startActivity(intentNoticias);
                }
            });
            final String imagen4_link = noticias.getImagen4_noticias();
            imagen4_noticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNoticias = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen4_link);
                    intentNoticias.putExtras(envioimg);
                    startActivity(intentNoticias);
                }
            });


            video = noticias.getVideo();




            //TODO://////////////////////////////////////////////////////////////



            String url_noticias = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_noticias_actualizar= new StringRequest(Request.Method.POST, url_noticias, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i("Si actualizo",response);

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
                    parametros.put("id_noticias",idinput);
                    parametros.put("publicacion","Noticias");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_noticias_eliminar = Volley.newRequestQueue(this);
            request_noticias_eliminar.add(stringRequest_noticias_actualizar);



        }

        baner1 = findViewById(R.id.baner_noticias1);
        baner2 = findViewById(R.id.baner_noticias2);
        baner3 = findViewById(R.id.baner_noticias3);


        AdRequest adRequest = new AdRequest.Builder().build();

        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);
        baner3.loadAd(adRequest);





    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            youTubePlayer.cueVideo(video);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(DetalleNoticias.this,1).show();
        }else {
            String m ="Error al iniciar el video"+youTubeInitializationResult.toString();

            Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode ==1){
            getYoutubePlayerProvider().initialize(API_KEY,this);
        }
    }


    protected YouTubePlayer.Provider getYoutubePlayerProvider(){
        return video_noticias;
    }


    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
