package com.pacificblack.informatebuenaventura.fragments.eventos;


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
import com.pacificblack.informatebuenaventura.clases.eventos.AdaptadorEventos;
import com.pacificblack.informatebuenaventura.clases.eventos.Eventos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerEventos;

    ArrayList<Eventos> listaEventos;

    //TODO: Aqui va todo lo de obtener de la base de datos

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

//TODO: Aqui va todo lo de obtener de la base de datos

    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View vista = inflater.inflate(R.layout.fragment_eventos, container, false);


        listaEventos = new ArrayList<>();
        recyclerEventos = vista.findViewById(R.id.recycler_eventos);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO: Aqui va todo lo de obtener de la base de datos

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Eventos();

        //TODO: Aqui va todo lo de obtener de la base de datos




return vista;
    }

    //TODO: Aqui va todo lo de obtener de la base de datos

    private void cargarWebService_Eventos() {

        String url_eventos = "http://192.168.0.18/InformateDB/wsnJSONllenarEventos.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_eventos,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No funciona pa",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());


    }

    @Override
    public void onResponse(JSONObject response) {

        Eventos eventos = null;

        JSONArray json_eventos = response.optJSONArray("eventos");


        try {

        for (int i=0; i<json_eventos.length(); i++ ){

            eventos = new Eventos();
            JSONObject jsonObject = null;
            jsonObject = json_eventos.getJSONObject(i);

            eventos.setTitulo_row_eventos(jsonObject.optString("titulo_eventos"));
            eventos.setDescripcion_row_eventos(jsonObject.optString("descripcionrow_eventos"));
            eventos.setFechapublicacion_row_eventos(jsonObject.optString("fechapublicacion_eventos"));
            eventos.setLugar_row_eventos(jsonObject.optString("lugar_eventos"));
            eventos.setImagen1_eventos(jsonObject.optString("imagen1_eventos"));
            eventos.setVistas_eventos(jsonObject.optInt("vistas_eventos "));

            listaEventos.add(eventos);

        }


            AdaptadorEventos adaptadorEventos = new AdaptadorEventos(listaEventos);
            recyclerEventos.setAdapter(adaptadorEventos);


            }catch (JSONException e) {

            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());
            e.printStackTrace();
        }

    }


    //TODO: Aqui va todo lo de obtener de la base de datos




}
