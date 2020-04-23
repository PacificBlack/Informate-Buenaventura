package com.pacificblack.informatebuenaventura.fragments.ofertas;


import android.content.Intent;
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
import com.pacificblack.informatebuenaventura.clases.ofertas.AdaptadorEmpleos;
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaEmpleos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfertaEmpleosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerEmpleos;
    ArrayList<OfertaEmpleos> listaEmpleos;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_ofertaempleos;
    AdaptadorEmpleos adaptadorEmpleos;


    public OfertaEmpleosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_oferta_empleos, container, false);
        setHasOptionsMenu(true);
        listaEmpleos = new ArrayList<>();
        recyclerEmpleos = vista.findViewById(R.id.recycler_ofertaempleos);
        recyclerEmpleos.setLayoutManager(new LinearLayoutManager(getContext()));
        refresh_ofertaempleos = vista.findViewById(R.id.swipe_ofertaempleos);
        refresh_ofertaempleos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Empleos();
            }
        });


        request = Volley.newRequestQueue(getContext());

        cargarWebService_Empleos();
        refresh_ofertaempleos.setRefreshing(true);

        return vista;
    }

    private void cargarWebService_Empleos() {

        String url_Empleos = DireccionServidor+"wsnJSONllenarEmpleos.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Empleos,null,this,this);
        request.add(jsonObjectRequest);
        refresh_ofertaempleos.setRefreshing(false);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        refresh_ofertaempleos.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {

        OfertaEmpleos empleos  = null;
        JSONArray json_empleos = response.optJSONArray("empleos");

        listaEmpleos.clear();

        try {

            for (int i = 0; i < json_empleos.length() ; i++) {

                empleos = new OfertaEmpleos();
                JSONObject jsonObject = null;
                jsonObject = json_empleos.getJSONObject(i);

                empleos.setId_empleos(jsonObject.getInt("id_ofertaempleos"));
                empleos.setTitulo_row_ofertasempleos(jsonObject.getString("titulo_ofertaempleos"));
                empleos.setDescripcion_row_ofertasempleos(jsonObject.getString("descripcionrow_ofertaempleos"));
                empleos.setFechapublicacion_row_ofertasempleos(jsonObject.getString("fechapublicacion_ofertaempleos"));
                empleos.setNecesidad_row_ofertasempleos(jsonObject.getString("necesidad_ofertaempleos"));
                empleos.setImagen1_ofertasempleos(jsonObject.getString("imagen1_ofertaempleos"));
                empleos.setVistas_ofertasempleos(jsonObject.getInt("vistas_ofertaempleos"));

                listaEmpleos.add(empleos);

            }

            adaptadorEmpleos = new AdaptadorEmpleos(listaEmpleos);
            recyclerEmpleos.setAdapter(adaptadorEmpleos);
            adaptadorEmpleos.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh_ofertaempleos.setRefreshing(false);


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
                adaptadorEmpleos.getFilter().filter(newText);
                return false;
            }
        });    }
}
