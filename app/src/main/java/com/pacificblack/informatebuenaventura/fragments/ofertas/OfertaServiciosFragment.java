package com.pacificblack.informatebuenaventura.fragments.ofertas;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.ofertas.AdaptadorServicios;
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaServicios;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfertaServiciosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerServicios;
    ArrayList<OfertaServicios> listaServicios;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_ofertaservicios;
    AdaptadorServicios adaptadorServicios;



    public OfertaServiciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ofertaservicios, container, false);
        setHasOptionsMenu(true);
        listaServicios = new ArrayList<>();
        recyclerServicios = vista.findViewById(R.id.recycler_ofertaservicios);
        recyclerServicios.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_ofertaservicios = vista.findViewById(R.id.swipe_ofertaservicios);
        refresh_ofertaservicios.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Servicios();
            }
        });
        request = Volley.newRequestQueue(getContext());

        cargarWebService_Servicios();
        refresh_ofertaservicios.setRefreshing(true);


        return vista;
    }


    private void cargarWebService_Servicios() {

        String url_Servicios = DireccionServidor+"wsnJSONllenarServicios.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Servicios,null,this,this);
        request.add(jsonObjectRequest);

        refresh_ofertaservicios.setRefreshing(false);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        refresh_ofertaservicios.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {

        OfertaServicios servicios  = null;
        JSONArray json_servicios = response.optJSONArray("servicios");

        listaServicios.clear();

        try {

            for (int i = 0; i < json_servicios.length() ; i++) {

                servicios = new OfertaServicios();
                JSONObject jsonObject = null;
                jsonObject = json_servicios.getJSONObject(i);

                servicios.setId_servicios(jsonObject.getInt("id_ofertaservicios"));
                servicios.setTitulo_row_ofertaservicios(jsonObject.getString("titulo_ofertaservicios"));
                servicios.setDescripcion_row_ofertaservicios(jsonObject.getString("descripcionrow_ofertaservicios"));
                servicios.setFechapublicacion_row_ofertaservicios(jsonObject.getString("fechapublicacion_ofertaservicios"));
                servicios.setNecesidad_row_ofertaservicios(jsonObject.getString("necesidad_ofertaservicios"));
                servicios.setImagen1_ofertaservicios(jsonObject.getString("imagen1_ofertaservicios"));
                servicios.setVistas_ofertaservicios(jsonObject.getInt("vistas_ofertaservicios"));

                listaServicios.add(servicios);

            }

            adaptadorServicios = new AdaptadorServicios(listaServicios);
            recyclerServicios.setAdapter(adaptadorServicios);
            adaptadorServicios.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh_ofertaservicios.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora,menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese el evento que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorServicios.getFilter().filter(newText);
                return false;
            }
        });
    }
}
