package com.pacificblack.informatebuenaventura.fragments.clasificados;


import android.app.Activity;
import android.content.Context;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleClasificados;
import com.pacificblack.informatebuenaventura.clases.clasificados.AdaptadorClasificados;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

public class ClasificadosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerClasificados;
    ArrayList<Clasificados> listaClasificados;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_clasificados;
    AdaptadorClasificados adaptadorC;
    LinearLayout internet, vacio;
    Button reintentar, verificar;


    public ClasificadosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment_clasificados, container, false);
        internet = vista.findViewById(R.id.internet_fragment_clasificados);

        vacio = vista.findViewById(R.id.vacio_fragment_clasificados);
        verificar = vista.findViewById(R.id.verificar_fragment_clasificados);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Clasificados();
                refresh_clasificados.setRefreshing(true);
            }
        });

        reintentar = vista.findViewById(R.id.reintentar_fragment_clasificados);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Clasificados();
                refresh_clasificados.setRefreshing(true);
            }
        });
        setHasOptionsMenu(true);

        listaClasificados = new ArrayList<>();
        recyclerClasificados = vista.findViewById(R.id.recycler_clasificados);
        recyclerClasificados.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_clasificados = vista.findViewById(R.id.swipe_clasificados);
        refresh_clasificados.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Clasificados();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Clasificados();
        refresh_clasificados.setRefreshing(true);

        return vista;
    }

    private void cargarWebService_Clasificados() {
        internet.setVisibility(View.GONE);
        vacio.setVisibility(View.GONE);
        String url_Clasificados = DireccionServidor+"wsnJSONllenarClasificados.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Clasificados,null,this,this);
        request.add(jsonObjectRequest);
        refresh_clasificados.setRefreshing(false);
        recyclerClasificados.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listaClasificados.isEmpty()) {
            vacio.setVisibility(View.VISIBLE);
        } else {
            internet.setVisibility(View.VISIBLE);
            recyclerClasificados.setVisibility(View.GONE);
            Log.i("ERROR", error.toString());
            refresh_clasificados.setRefreshing(false);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerClasificados.setVisibility(View.VISIBLE);

        Clasificados clasificados = null;
        JSONArray json_clasificados = response.optJSONArray("clasificados");

        listaClasificados.clear();

        try {

            for (int i = 0; i < json_clasificados.length() ; i++) {

                clasificados = new Clasificados();
                JSONObject jsonObject = null;
                jsonObject = json_clasificados.getJSONObject(i);

                clasificados.setId_clasificados(jsonObject.getInt("id_clasificados"));
                clasificados.setTitulo_row_clasificados(jsonObject.getString("titulo_clasificados"));
                clasificados.setDescripcion_row_clasificados(jsonObject.getString("descripcionrow_clasificados"));
                clasificados.setFechapublicacion_row_clasificados(jsonObject.getString("fechapublicacion_clasificados"));
                clasificados.setImagen1_clasificados(jsonObject.getString("imagen1_clasificados"));
                clasificados.setImagen2_clasificados(jsonObject.getString("imagen2_clasificados"));
                clasificados.setImagen3_clasificados(jsonObject.getString("imagen3_clasificados"));
                clasificados.setImagen4_clasificados(jsonObject.getString("imagen4_clasificados"));
                clasificados.setVideo_clasificados(jsonObject.getString("video_clasificados"));
                clasificados.setVistas_bienes(jsonObject.getInt("vistas_clasificados"));
                clasificados.setDescripcion1_clasificados(jsonObject.getString("descripcion1_clasificados"));
                clasificados.setDescripcion2_clasificados(jsonObject.getString("descripcion2_clasificados"));

                listaClasificados.add(clasificados);

            }

             adaptadorC = new AdaptadorClasificados(listaClasificados);
            recyclerClasificados.setAdapter(adaptadorC);
            adaptadorC.notifyDataSetChanged();

            adaptadorC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Clasificados clasificados = listaClasificados.get(recyclerClasificados.getChildAdapterPosition(v));

                    Intent intentClasificados = new Intent(getContext(), DetalleClasificados.class);
                    Bundle bundleclasificados = new Bundle();
                    bundleclasificados.putSerializable("objeto3",clasificados);

                    intentClasificados.putExtras(bundleclasificados);
                    startActivity(intentClasificados);

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());
            e.printStackTrace();
        }
        refresh_clasificados.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora,menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese el clasificado que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorC.getFilter().filter(newText);
                return false;
            }
        });
    }
}
