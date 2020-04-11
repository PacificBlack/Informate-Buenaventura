package com.pacificblack.informatebuenaventura.fragments.noticias;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleNoticias;
import com.pacificblack.informatebuenaventura.clases.noticias.AdaptadorNoticias;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerNoticias;
    ArrayList<Noticias> listaNoticias;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_noticias, container, false);

        listaNoticias =  new ArrayList<>();
        recyclerNoticias = vista.findViewById(R.id.recycler_noticias);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());
        cargarWebService_Noticias();

        return vista;
    }

    private void cargarWebService_Noticias() {

        String url_Noticias = DireccionServidor+"wsnJSONllenarNoticias.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Noticias,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());

    }


    @Override
    public void onResponse(JSONObject response) {

        Noticias noticias = null;
        JSONArray json_noticias = response.optJSONArray("noticias");

        try {

            for (int i = 0; i < json_noticias.length() ; i++) {

                noticias = new Noticias();
                JSONObject jsonObject = null;
                jsonObject = json_noticias.getJSONObject(i);

                noticias.setVideo(jsonObject.optString("video_noticias"));
                noticias.setTitulo_row_noticias(jsonObject.optString("titulo_noticias"));
                noticias.setDescripcion_row_noticias(jsonObject.optString("descripcionrow_noticias"));
                noticias.setFechapublicacion_row_noticias(jsonObject.optString("fechapublicacion_noticias"));
                noticias.setId_noticias(jsonObject.optInt("id_noticias"));
                noticias.setImagen1_noticias(jsonObject.optString("imagen1_noticias"));
                noticias.setImagen2_noticias(jsonObject.optString("imagen2_noticias"));
                noticias.setImagen3_noticias(jsonObject.optString("imagen3_noticias"));
                noticias.setImagen4_noticias(jsonObject.optString("imagen4_noticias"));
                noticias.setLikes_noticias(jsonObject.optInt("likes_noticias"));
                noticias.setDislikes_noticias(jsonObject.optInt("dislikes_noticias"));
                noticias.setVistas_noticias(jsonObject.optInt("vistas_noticias"));
                noticias.setDescripcion1_noticias(jsonObject.optString("descripcion1_noticias"));
                noticias.setDescripcion2_noticias(jsonObject.optString("descripcion2_noticias"));
                noticias.setDescripcion3_noticias(jsonObject.optString("descripcion3_noticias"));

                listaNoticias.add(noticias);

            }
            AdaptadorNoticias adaptadorNoticias = new AdaptadorNoticias(listaNoticias);
            recyclerNoticias.setAdapter(adaptadorNoticias);
            adaptadorNoticias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Noticias noticis = listaNoticias.get(recyclerNoticias.getChildAdapterPosition(v));

                    Intent intentNoticias = new Intent(getContext(), DetalleNoticias.class);
                    Bundle envioNoticias = new Bundle();
                    envioNoticias.putSerializable("objeto10",noticis);

                    intentNoticias.putExtras(envioNoticias);
                    startActivity(intentNoticias);


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
