package com.pacificblack.informatebuenaventura.fragments.avisosfunebres;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.pacificblack.informatebuenaventura.actividades.DetalleDifuntos;
import com.pacificblack.informatebuenaventura.clases.funebres.AdaptadorFunebres;
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunebresFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener   {

    RecyclerView recyclerFunebres;
    ArrayList<Funebres> listaFunebres;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_funebres;

    public FunebresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_funebres, container, false);

        listaFunebres = new ArrayList<>();
        recyclerFunebres = vista.findViewById(R.id.recycler_funebres);
        recyclerFunebres.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_funebres = vista.findViewById(R.id.swipe_funebres);
        refresh_funebres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaFunebres.clear();
                cargarWebService_Funebres();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Funebres();
        refresh_funebres.setRefreshing(true);


        return vista;
    }

    private void cargarWebService_Funebres() {

        String url_Funebres = DireccionServidor+"wsnJSONllenarFunebres.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Funebres,null,this,this);
        request.add(jsonObjectRequest);

        refresh_funebres.setRefreshing(false);

    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        refresh_funebres.setRefreshing(false);


    }

    @Override
    public void onResponse(JSONObject response) {
        Funebres funebres = null;

        JSONArray json_funebres = response.optJSONArray("funebres");

        try {

            for (int i=0; i<json_funebres.length(); i++){

                funebres = new Funebres();
                JSONObject jsonObject = null;
                jsonObject = json_funebres.getJSONObject(i);

                funebres.setId_funebres(jsonObject.getInt("id_funebres"));
                funebres.setTitulo_row_funebres(jsonObject.getString("titulo_funebres"));
                funebres.setDescripcion_row_funebres(jsonObject.getString("descripcionrow_funebres"));
                funebres.setFechapublicacion_row_funebres(jsonObject.getString("fechapublicacion_funebres"));
                funebres.setImagen1_funebres(jsonObject.getString("imagen1_funebres"));
                funebres.setImagen2_funebres(jsonObject.getString("imagen2_funebres"));
                funebres.setImagen3_funebres(jsonObject.getString("imagen3_funebres"));
                funebres.setVistas_funebres(jsonObject.getInt("vistas_funebres"));
                funebres.setDescripcion1_funebres(jsonObject.getString("descripcion1_funebres"));
                funebres.setDescripcion2_funebres(jsonObject.getString("descripcion2_funebres"));

                listaFunebres.add(funebres);

            }


            AdaptadorFunebres adaptadorFunebres = new AdaptadorFunebres(listaFunebres);
            recyclerFunebres.setAdapter(adaptadorFunebres);
            adaptadorFunebres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Funebres fune = listaFunebres.get(recyclerFunebres.getChildAdapterPosition(v));

                    Intent intentFunebres = new Intent(getContext(), DetalleDifuntos.class);
                    Bundle envioFunebres = new Bundle();
                    envioFunebres.putSerializable("objeto9", fune);

                    intentFunebres.putExtras(envioFunebres);
                    startActivity(intentFunebres);

                }
            });


        } catch (JSONException e) {

            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());
            e.printStackTrace();
        }

        refresh_funebres.setRefreshing(false);
    }
}
