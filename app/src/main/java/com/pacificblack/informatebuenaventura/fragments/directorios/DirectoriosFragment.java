package com.pacificblack.informatebuenaventura.fragments.directorios;


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
import com.pacificblack.informatebuenaventura.clases.directorio.AdaptadorDirectorio;
import com.pacificblack.informatebuenaventura.clases.directorio.Directorio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

public class DirectoriosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerDirectorios;
    ArrayList<Directorio> listaDirectorios;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_directorios;
    AdaptadorDirectorio adaptadorDirectorio;
    LinearLayout internet,vacio;
    Button reintentar,verificar;


    public DirectoriosFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista =inflater.inflate(R.layout.fragment_directorios, container, false);
        internet = vista.findViewById(R.id.internet_fragment_directorios);

        vacio = vista.findViewById(R.id.vacio_fragment_directorios);
        verificar = vista.findViewById(R.id.verificar_fragment_directorios);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Directorios();
                refresh_directorios.setRefreshing(true);
            }
        });

        reintentar = vista.findViewById(R.id.reintentar_fragment_directorios);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Directorios();
                refresh_directorios.setRefreshing(true);
            }
        });
        setHasOptionsMenu(true);

            listaDirectorios = new ArrayList<>();
            recyclerDirectorios = vista.findViewById(R.id.recycler_directorios);
            recyclerDirectorios.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_directorios = vista.findViewById(R.id.swipe_directorios);
        refresh_directorios.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Directorios();
            }
        });

        request = Volley.newRequestQueue(getContext());
        cargarWebService_Directorios();

        refresh_directorios.setRefreshing(true);


        return vista;
    }

    private void cargarWebService_Directorios() {
        internet.setVisibility(View.GONE);
        vacio.setVisibility(View.GONE);
        String url_Directorios = DireccionServidor+"wsnJSONllenarDirectorios.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Directorios,null,this,this);
        request.add(jsonObjectRequest);
        refresh_directorios.setRefreshing(false);
        recyclerDirectorios.setVisibility(View.VISIBLE);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        if (listaDirectorios.isEmpty()){
            vacio.setVisibility(View.VISIBLE);
        } else {
            internet.setVisibility(View.VISIBLE);
            recyclerDirectorios.setVisibility(View.GONE);
            Log.i("ERROR", error.toString());
            refresh_directorios.setRefreshing(false);
                }
    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerDirectorios.setVisibility(View.VISIBLE);
        Directorio directorio = null;
        JSONArray json_directorio = response.optJSONArray("directorio");

        listaDirectorios.clear();

        try {
            for (int i = 0; i < json_directorio.length() ; i++) {

                directorio = new Directorio();
                JSONObject jsonObject = null;
                jsonObject = json_directorio.getJSONObject(i);

                directorio.setId_directorio(jsonObject.getInt("id_directorios"));
                directorio.setTitulo_row_directorio(jsonObject.getString("titulo_directorios"));
                directorio.setDescripcion_row_directorio(jsonObject.getString("descripcionrow_directorios"));
                directorio.setFechapublicacion_row_directorio(jsonObject.getString("fechapublicacion_directorios"));
                directorio.setContactos_row_directorio(jsonObject.getString("contactos_directorios"));
                directorio.setVistas_row_directorio(jsonObject.getInt("vistas_directorios"));
                directorio.setImagen1_directorio(jsonObject.getString("imagen1_directorios"));

                listaDirectorios.add(directorio);

            }

           adaptadorDirectorio  = new AdaptadorDirectorio(listaDirectorios);
            recyclerDirectorios.setAdapter(adaptadorDirectorio);
            adaptadorDirectorio.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh_directorios.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora,menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese el contacto que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorDirectorio.getFilter().filter(newText);
                return false;
            }
        });
    }
}
