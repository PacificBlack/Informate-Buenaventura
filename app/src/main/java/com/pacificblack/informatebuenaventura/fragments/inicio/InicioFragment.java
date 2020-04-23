package com.pacificblack.informatebuenaventura.fragments.inicio;


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
import com.pacificblack.informatebuenaventura.R;

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
    String videotraido;

    RecyclerView recyclerInicio;
    ArrayList<Inicio> listaInicio;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest videeos;

    private SwipeRefreshLayout refresh_inicio;

    AdaptadorInicio adaptadorInicio;

    public InicioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cargarvideo();

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_layout, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo("OGkss7DFrMs");
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


    private void cargarvideo() {
        String url_videos = DireccionServidor+"wsnJSONLlenarVideos.php?";

        videeos = new StringRequest(Request.Method.POST, url_videos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                videotraido = response.toString();
                Log.i("Video traido ", videotraido);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("No actualizo","Vista Negativa");

            }
        });

        RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(getContext());
        request_encuestas_eliminar.add(videeos);

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
