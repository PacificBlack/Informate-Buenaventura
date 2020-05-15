package com.pacificblack.informatebuenaventura.fragments.desaparecidos;


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
import com.pacificblack.informatebuenaventura.actividades.DetalleDesaparecidos;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.AdaptadorDesaparecidos;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;


public class DesaparecidosFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<Desaparecidos> listaDesaparecidos;
    RecyclerView recyclerDesaparecidos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_desaparecidos;
    AdaptadorDesaparecidos adaptadorDesaparecidos;
    LinearLayout internet, vacio;
    Button reintentar, verificar;


    public DesaparecidosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_desaparecidos, container, false);

        internet = vista.findViewById(R.id.internet_fragment_desaparecidos);

        vacio = vista.findViewById(R.id.vacio_fragment_desaparecidos);
        verificar = vista.findViewById(R.id.verificar_fragment_desaparecidos);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Desaparecidos();
                refresh_desaparecidos.setRefreshing(true);
            }
        });

        reintentar = vista.findViewById(R.id.reintentar_fragment_desaparecidos);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Desaparecidos();
                refresh_desaparecidos.setRefreshing(true);
            }
        });
        setHasOptionsMenu(true);

        listaDesaparecidos = new ArrayList<>();
        recyclerDesaparecidos = vista.findViewById(R.id.recycler_desaparecidos);
        recyclerDesaparecidos.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_desaparecidos = vista.findViewById(R.id.swipe_desaparecidos);
        refresh_desaparecidos.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Desaparecidos();
            }
        });

        request = Volley.newRequestQueue(getContext());
        cargarWebService_Desaparecidos();

        refresh_desaparecidos.setRefreshing(true);

        return vista;

    }

    private void cargarWebService_Desaparecidos() {
        internet.setVisibility(View.GONE);
        vacio.setVisibility(View.GONE);
        String url_Desaparecidos = DireccionServidor + "wsnJSONllenarDesaparecidos.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_Desaparecidos, null, this, this);
        request.add(jsonObjectRequest);
        refresh_desaparecidos.setRefreshing(false);
        recyclerDesaparecidos.setVisibility(View.VISIBLE);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        if (listaDesaparecidos.isEmpty()) {
            vacio.setVisibility(View.VISIBLE);
        } else {
            internet.setVisibility(View.VISIBLE);
            recyclerDesaparecidos.setVisibility(View.GONE);
            Log.i("ERROR", error.toString());
            refresh_desaparecidos.setRefreshing(false);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerDesaparecidos.setVisibility(View.VISIBLE);
        Desaparecidos desaparecidos = null;
        JSONArray json_desaparecidos = response.optJSONArray("desaparecidos");

        listaDesaparecidos.clear();

        try {
            for (int i = 0; i < json_desaparecidos.length(); i++) {

                desaparecidos = new Desaparecidos();
                JSONObject jsonObject = null;
                jsonObject = json_desaparecidos.getJSONObject(i);

                desaparecidos.setId_desaparecidos(jsonObject.getInt("id_desaparecidos"));
                desaparecidos.setQue_desaparecidos(jsonObject.getString("que_desaparecidos"));
                desaparecidos.setTitulo_row_desaparecidos(jsonObject.getString("titulo_desaparecidos"));
                desaparecidos.setDescripcion_row_desaparecidos(jsonObject.getString("descripcionrow_desaparecidos"));
                desaparecidos.setFechapublicacion_row_desaparecidos(jsonObject.getString("fechapublicacion_desaparecidos"));
                desaparecidos.setRecompensa_row_desaparecidos(jsonObject.getString("recompensa_desaparecidos"));
                desaparecidos.setVista_row_desaparecidos(jsonObject.getInt("vistas_desaparecidos"));
                desaparecidos.setImagen1_desaparecidos(jsonObject.getString("imagen1_desaparecidos"));
                desaparecidos.setImagen2_desaparecidos(jsonObject.getString("imagen2_desaparecidos"));
                desaparecidos.setImagen3̣̣_desaparecidos(jsonObject.getString("imagen3_desaparecidos"));
                desaparecidos.setDescripcion1_desaparecidos(jsonObject.getString("descripcion1_desaparecidos"));
                desaparecidos.setDescripcion2_desaparecidos(jsonObject.getString("descripcion2_desaparecidos"));
                desaparecidos.setFechadesaparecido_desaparecidos(jsonObject.getString("fechadesaparecido_desaparecidos"));
                desaparecidos.setEstado_desaparecidos(jsonObject.getString("estado_desaparecidos"));
                desaparecidos.setUltimolugar_desaparecidos(jsonObject.getString("ultimolugar_desaparecidos"));

                listaDesaparecidos.add(desaparecidos);

            }

            adaptadorDesaparecidos = new AdaptadorDesaparecidos(listaDesaparecidos);

            recyclerDesaparecidos.setAdapter(adaptadorDesaparecidos);
            adaptadorDesaparecidos.notifyDataSetChanged();

            adaptadorDesaparecidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Desaparecidos desaparecidos = listaDesaparecidos.get(recyclerDesaparecidos.getChildAdapterPosition(v));

                    Intent intentDesaparecidos = new Intent(getContext(), DetalleDesaparecidos.class);
                    Bundle envioDesaparecidos = new Bundle();
                    envioDesaparecidos.putSerializable("objeto5", desaparecidos);
                    intentDesaparecidos.putExtras(envioDesaparecidos);

                    startActivity(intentDesaparecidos);

                }
            });

        } catch (JSONException e) {

            Toast.makeText(getContext(), "No se puede obtener", Toast.LENGTH_LONG).show();
            Log.i("ERROR", response.toString());
            e.printStackTrace();
        }
        refresh_desaparecidos.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora, menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese el desaparecido que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorDesaparecidos.getFilter().filter(newText);
                return false;
            }
        });
    }
}
