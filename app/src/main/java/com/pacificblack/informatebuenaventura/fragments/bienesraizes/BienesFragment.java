package com.pacificblack.informatebuenaventura.fragments.bienesraizes;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.pacificblack.informatebuenaventura.actividades.DetalleBienes;
import com.pacificblack.informatebuenaventura.clases.bienes.AdaptadorBienes;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BienesFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerBienes;

    ArrayList<Bienes> listaBienes;

    //TODO: Aqui va todo lo de obtener de la base de datos

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

//TODO: Aqui va todo lo de obtener de la base de datos



    public BienesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_bienes, container, false);

        listaBienes = new ArrayList<>();
        recyclerBienes = vista.findViewById(R.id.recycler_bienes);
        recyclerBienes.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO: Aqui va todo lo de obtener de la base de datos

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Bienes();

        //TODO: Aqui va todo lo de obtener de la base de datos





        return vista;
    }
//TODO: Aqui va todo lo de obtener de la base de datos

    private void cargarWebService_Bienes() {

        String url_bienes = "http://192.168.0.18/InformateDB/wsnJSONllenarBienes.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_bienes,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No funciona pa",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());


    }

    @Override
    public void onResponse(JSONObject response) {

        Bienes bienes = null;

        JSONArray json_bienes = response.optJSONArray("bienes");


        try {

            for (int i=0; i<json_bienes.length(); i++ ){

                bienes = new Bienes();
                JSONObject jsonObject = null;
                jsonObject = json_bienes.getJSONObject(i);

                bienes.setTitulo_row_bienes(jsonObject.optString("titulo_bienes"));
                bienes.setDescripcion_row_bienes(jsonObject.optString("descripcionrow_bienes"));
                bienes.setFechapublicacion_row_bienes(jsonObject.optString("fechapublicacion_bienes"));
                bienes.setImagen1_bienes(jsonObject.optString("imagen1_bienes"));
                bienes.setImagen2_bienes(jsonObject.optString("imagen2_bienes"));
                bienes.setImagen3_bienes(jsonObject.optString("imagen3_bienes"));
                bienes.setImagen4_bienes(jsonObject.optString("imagen4_bienes"));
                bienes.setPrecio_row_bienes(jsonObject.optInt("precio_bienes"));
                bienes.setVistas_bienes(jsonObject.optInt("vistas_bienes"));
                bienes.setDescripcion1_bienes(jsonObject.optString("descripcion1_bienes"));
                bienes.setDescripcion2_bienes(jsonObject.optString("descripcion2_bienes"));

                listaBienes.add(bienes);

            }

            AdaptadorBienes adaptadorB = new AdaptadorBienes(listaBienes);
            recyclerBienes.setAdapter(adaptadorB);
            adaptadorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bienes bienes = listaBienes.get(recyclerBienes.getChildAdapterPosition(v));

                    Intent intentBienes = new Intent(getContext(), DetalleBienes.class);
                    Bundle bundlebienes = new Bundle();
                    bundlebienes.putSerializable("objeto2",bienes);

                    intentBienes.putExtras(bundlebienes);
                    startActivity(intentBienes);


                }
            });


        }catch (JSONException e) {

            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());
            e.printStackTrace();
        }

    }


    //TODO: Aqui va todo lo de obtener de la base de datos




}
