package com.pacificblack.informatebuenaventura.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;
import com.squareup.picasso.Picasso;


public class DetalleNoticias extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    TextView titulo_noticias,descripcion1_noticias,
            descripcion2_noticias,descripcion3_noticias;

    ImageView imagen1_noticias,imagen2_noticias,
            imagen3_noticias,imagen4_noticias;


    YouTubePlayerView video_noticias;
    String API_KEY = "AIzaSyCjplldkmscSZfu1yMY7eJr4xiSjuAbZgo";

    String video;

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

            video = noticias.getVideo();


        }




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
