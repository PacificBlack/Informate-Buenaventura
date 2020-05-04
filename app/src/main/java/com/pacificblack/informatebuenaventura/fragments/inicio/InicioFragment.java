package com.pacificblack.informatebuenaventura.fragments.inicio;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.pacificblack.informatebuenaventura.MainActivity;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.extras.Imagenes;
import com.pacificblack.informatebuenaventura.extras.SliderAdaptador;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    String API_KEY = "AIzaSyCjplldkmscSZfu1yMY7eJr4xiSjuAbZgo";

    RecyclerView recyclerInicio;
    ArrayList<Inicio> listaInicio;
    List<Imagenes> listaImagenes;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_inicio;
    AdaptadorInicio adaptadorInicio;
    SliderView sliderView;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        sliderView = vista.findViewById(R.id.imageSlider);

        listaInicio = new ArrayList<>();
        recyclerInicio = vista.findViewById(R.id.recycler_inicio);
        recyclerInicio.setLayoutManager(new GridLayoutManager(getContext(),2));

        refresh_inicio = vista.findViewById(R.id.swipe_inicio);
        refresh_inicio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Inicio();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Inicio();

        refresh_inicio.setRefreshing(true);

        return vista;
    }





    private void cargarWebService_Inicio() {

        String url_Inicio = DireccionServidor+"wsnJSONllenarInicio.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Inicio,null,this,this);
        request.add(jsonObjectRequest);

        refresh_inicio.setRefreshing(false);

    }



    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No funciona pa",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());

        refresh_inicio.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {

        Inicio Inicio = null;

        JSONArray json_Inicio = response.optJSONArray("inicio");

        listaInicio.clear();

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo(MainActivity.traer);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                // YouTube error
                String errorMessage = error.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });


        try {

            for (int i=0; i<json_Inicio.length(); i++ ){

                Inicio = new Inicio();
                JSONObject jsonObject = null;
                jsonObject = json_Inicio.getJSONObject(i);



                Inicio.setDescripcion_corta(jsonObject.optString("descripcion_inicio"));
                Inicio.setImagen_inicio(jsonObject.optString("imagen_inicio"));

                listaInicio.add(Inicio);

            }

            listaImagenes = new ArrayList<>();
            listaImagenes.add(new Imagenes("https://images.alphacoders.com/105/thumb-350-1050187.jpg"));
            listaImagenes.add(new Imagenes("https://image.winudf.com/v2/image1/Y29tLmJlc3QubGl2ZS53YWxscGFwZXIuYmFja2dyb3VuZHMuaGQudGhlbWUuZ2lybHkud2FsbHBhcGVycy5pbWFnZXMubHdwLm5lb24uYW5pbWFscy5saXZlLndhbGxwYXBlcl9zY3JlZW5fMTFfMTU0NzIxOTY2Nl8wMjg/screen-11.jpg?fakeurl=1&type=.jpg"));
            listaImagenes.add(new Imagenes("https://www.tuexpertomovil.com/wp-content/uploads/2019/03/fondos-de-pantalla-fortnite-movil-celular-hd.jpg"));
            listaImagenes.add(new Imagenes("https://img-l3.xvideos-cdn.com/videos/thumbslll/37/10/a5/3710a5ca0eb9b7d5b835e6ccbe265595/3710a5ca0eb9b7d5b835e6ccbe265595.16.jpg"));
            listaImagenes.add(new Imagenes("https://di1.ypncdn.com/201207/05/7811529/original/8/the-most-beautiful-erotic-babe-malena-8(m=eKw7Kgaaaa).jpg"));


            sliderView.setSliderAdapter(new SliderAdaptador(getContext(),listaImagenes));

            sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
            sliderView.startAutoCycle();


            adaptadorInicio = new AdaptadorInicio(listaInicio);
            recyclerInicio.setAdapter(adaptadorInicio);
            adaptadorInicio.notifyDataSetChanged();

        }catch (JSONException e) {

            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());
            e.printStackTrace();
        }
        refresh_inicio.setRefreshing(false);

    }


}
