package com.pacificblack.informatebuenaventura.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;
import com.pacificblack.informatebuenaventura.extras.FullImagen;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class DetalleDonaciones  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,YouTubePlayer.PlaybackEventListener {

    TextView titulodonaciones,descripcion1donaciones,metadonaciones;
    ImageView imagen1donaciones,imagen2donaciones;
    StringRequest stringRequest_donaciones_actualizar;
    int id_actualizar;
    private AdView baner1,baner2;
    YouTubePlayerView video_donaciones;
    String API_KEY = "AIzaSyCjplldkmscSZfu1yMY7eJr4xiSjuAbZgo";
    String video;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_donaciones);

        titulodonaciones = findViewById(R.id.titulo_detalle_donaciones);
        descripcion1donaciones = findViewById(R.id.descricion1_detalle_donaciones);
        imagen1donaciones = findViewById(R.id.imagen1_detalle_donaciones);
        imagen2donaciones = findViewById(R.id.imagen2_detalle_donaciones);
        metadonaciones = findViewById(R.id.meta_detalle_donaciones);

        video_donaciones = findViewById(R.id.video_donaciones);
        video_donaciones.initialize(API_KEY,this);


        Bundle objetoDonaciones = getIntent().getExtras();
        Donaciones dona = null;

        if (objetoDonaciones != null){

            dona = (Donaciones) objetoDonaciones.getSerializable("objeto6");

            id_actualizar = dona.getId_donaciones();
            titulodonaciones.setText(dona.getTitulo_row_donaciones());
            descripcion1donaciones.setText(dona.getDescripcion1_donaciones());


            Picasso.get().load(dona.getImagen1_donaciones())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(imagen1donaciones);

            Picasso.get().load(dona.getImagen2_donaciones())
                    .placeholder(R.drawable.ib)
                    .error(R.drawable.ib)
                    .into(imagen2donaciones);

            metadonaciones.setText(String.valueOf(dona.getMeta_row_donaciones()));


            final String imagen1_link = dona.getImagen1_donaciones();
            imagen1donaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentdonaciones = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen1_link);
                    intentdonaciones.putExtras(envioimg);
                    startActivity(intentdonaciones);
                }
            });
            final String imagen2_link = dona.getImagen2_donaciones();
            imagen2donaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentdonaciones = new Intent(getApplicationContext(), FullImagen.class);
                    Bundle envioimg = new Bundle();
                    envioimg.putString("imagen", imagen2_link);
                    intentdonaciones.putExtras(envioimg);
                    startActivity(intentdonaciones);
                }
            });

            video = dona.getVideo_donaciones();


            String url_donaciones = DireccionServidor+"wsnJSONActualizarVista.php?";

            stringRequest_donaciones_actualizar= new StringRequest(Request.Method.POST, url_donaciones, new Response.Listener<String>() {
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
                    parametros.put("id_donaciones",idinput);
                    parametros.put("publicacion","Donaciones");

                    Log.i("Parametros", String.valueOf(parametros));

                    return parametros;
                }
            };

            RequestQueue request_donaciones_eliminar = Volley.newRequestQueue(this);
            request_donaciones_eliminar.add(stringRequest_donaciones_actualizar);

        }


        baner1 = findViewById(R.id.baner_donacion1);
        baner2 = findViewById(R.id.baner_donacion2);


        AdRequest adRequest = new AdRequest.Builder().build();

        baner1.loadAd(adRequest);
        baner2.loadAd(adRequest);

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
            youTubeInitializationResult.getErrorDialog(DetalleDonaciones.this,1).show();
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
        return video_donaciones;
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
