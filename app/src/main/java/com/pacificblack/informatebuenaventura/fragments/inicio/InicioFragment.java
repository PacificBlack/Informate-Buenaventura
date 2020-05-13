package com.pacificblack.informatebuenaventura.fragments.inicio;


import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    StringRequest stringRequest_imagenes;
    RecyclerView recyclerInicio;
    ArrayList<Inicio> listaInicio;
    List<Imagenes> listaImagenes;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_inicio;
    AdaptadorInicio adaptadorInicio;
    SliderView sliderView;
    LinearLayout internet;
    CardView cardinicio;
    Button reintentar;
    FrameLayout yutuinicio;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        yutuinicio = vista.findViewById(R.id.youtube_layout);
        cardinicio = vista.findViewById(R.id.cardinicio);
        internet = vista.findViewById(R.id.internet_fragment_inicio);
        reintentar = vista.findViewById(R.id.reintentar_fragment_inicio);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Inicio();
                cargarWebService_Imagenes();
                refresh_inicio.setRefreshing(true);
            }
        });


        sliderView = vista.findViewById(R.id.imageSlider);

        listaInicio = new ArrayList<>();
        recyclerInicio = vista.findViewById(R.id.recycler_inicio);
        recyclerInicio.setLayoutManager(new GridLayoutManager(getContext(),2));

        listaImagenes = new ArrayList<>();


        refresh_inicio = vista.findViewById(R.id.swipe_inicio);
        refresh_inicio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Inicio();
                cargarWebService_Imagenes();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Inicio();
        cargarWebService_Imagenes();

        refresh_inicio.setRefreshing(true);

        return vista;
    }




    private void cargarWebService_Imagenes(){
        internet.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url_imagenes = DireccionServidor+"wsnJSONllenarImagenes.php";


        JsonObjectRequest jsObjectRequest = new JsonObjectRequest(Request.Method.GET, url_imagenes, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Imagenes imagenes = null;
                JSONArray json_imagenes = null;
                try {
                    json_imagenes = response.getJSONArray("imagenes");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listaImagenes.clear();
                try {
                    for (int i = 0; i < json_imagenes.length() ; i++) {
                    imagenes = new Imagenes();
                    JSONObject jsonObject = null;
                    jsonObject = json_imagenes.getJSONObject(i);

                    imagenes.setImagenes(jsonObject.getString("publicidad_inicio"));

                    listaImagenes.add(imagenes);

                    }

                    sliderView.setSliderAdapter(new SliderAdaptador(getContext(),listaImagenes));

                    sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                    sliderView.startAutoCycle();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Error Respuesta en JSON: " + error.getMessage());

                    }
                });

        requestQueue.add(jsObjectRequest);
    }



    private void cargarWebService_Inicio() {
        internet.setVisibility(View.GONE);
        String url_Inicio = DireccionServidor+"wsnJSONllenarInicio.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Inicio,null,this,this);
        request.add(jsonObjectRequest);
        refresh_inicio.setRefreshing(false);
        recyclerInicio.setVisibility(View.VISIBLE);
        sliderView.setVisibility(View.VISIBLE);
        cardinicio.setVisibility(View.VISIBLE);
        yutuinicio.setVisibility(View.VISIBLE);
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        internet.setVisibility(View.VISIBLE);
        recyclerInicio.setVisibility(View.GONE);
        sliderView.setVisibility(View.GONE);
        cardinicio.setVisibility(View.GONE);
        yutuinicio.setVisibility(View.GONE);
        Log.i("ERROR",error.toString());
        refresh_inicio.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerInicio.setVisibility(View.VISIBLE);
        sliderView.setVisibility(View.VISIBLE);
        cardinicio.setVisibility(View.VISIBLE);
        yutuinicio.setVisibility(View.VISIBLE);
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
