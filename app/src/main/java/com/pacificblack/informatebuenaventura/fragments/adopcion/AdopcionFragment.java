package com.pacificblack.informatebuenaventura.fragments.adopcion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleAdopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.AdaptadorAdopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;


public class AdopcionFragment extends Fragment  implements Response.Listener<JSONObject>,Response.ErrorListener {
    //Declaramos lo que vamos a usar

    RecyclerView recyclerAdopcion;


    //Agregamos un arraylist ya que estamos usando uno

    ArrayList<Adopcion> listaAdopcion;

    //TODO: Aqui va todo lo de obtener de la base de datos

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

//TODO: Aqui va todo lo de obtener de la base de datos



    public AdopcionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_adopcion, container, false);

        listaAdopcion = new ArrayList<>();
        recyclerAdopcion = vista.findViewById(R.id.recycler_adopcion);
        recyclerAdopcion.setLayoutManager(new LinearLayoutManager(getContext()));


        //TODO: Aqui va todo lo de obtener de la base de datos

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Adopcion();

        //TODO: Aqui va todo lo de obtener de la base de datos




        return vista;
    }



    //TODO: Aqui va todo lo de obtener de la base de datos

    private void cargarWebService_Adopcion() {

        String url_adopcion = DireccionServidor+"wsnJSONllenarAdopcion.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_adopcion,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No funciona pa",Toast.LENGTH_LONG).show();

        Log.i("ERROR",error.toString());


    }

    @Override
    public void onResponse(JSONObject response) {

        Adopcion adopcion = null;

        JSONArray json_adopcion = response.optJSONArray("adopcion");


        try {

            for (int i=0; i<json_adopcion.length(); i++ ){

                adopcion = new Adopcion();
                JSONObject jsonObject = null;
                jsonObject = json_adopcion.getJSONObject(i);


                adopcion.setTitulo_row_adopcion(jsonObject.optString("titulo_adopcion"));
                adopcion.setDescripcion_row_adopcion(jsonObject.optString("descripcionrow_adopcion"));
                adopcion.setFechapublicacion_row_desaparecidos(jsonObject.optString("fechapublicacion_adopcion"));
                adopcion.setImagen1_adopcion(jsonObject.optString("imagen1_adopcion"));
                adopcion.setImagen2_adopcion(jsonObject.optString("imagen2_adopcion"));
                adopcion.setImagen3_adopcion(jsonObject.optString("imagen3_adopcion"));
                adopcion.setImagen4_adopcion(jsonObject.optString("imagen4_adopcion"));
                adopcion. setVistas_adopcion(jsonObject.optInt("vistas_adopcion"));
                adopcion.setDescripcion1_adopcion(jsonObject.optString("descripcion1_adopcion"));
                adopcion. setDescripcion2_adopcion(jsonObject.optString("descripcion2_adopcion"));

                listaAdopcion.add(adopcion);

            }

            AdaptadorAdopcion adaptador = new AdaptadorAdopcion(listaAdopcion);
            recyclerAdopcion.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Adopcion adop = listaAdopcion.get(recyclerAdopcion.getChildAdapterPosition(v));

                    Intent intentAdopcion = new Intent(getContext(), DetalleAdopcion.class);
                    Bundle ensayo = new Bundle();
                    ensayo.putSerializable("objeto1",adop);

                    intentAdopcion.putExtras(ensayo);
                    startActivity(intentAdopcion);
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
