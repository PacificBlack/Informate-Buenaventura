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

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoriosFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    RecyclerView recyclerDirectorios;
    ArrayList<Directorio> listaDirectorios;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private SwipeRefreshLayout refresh_directorios;
    AdaptadorDirectorio adaptadorDirectorio;


    public DirectoriosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista =inflater.inflate(R.layout.fragment_directorios, container, false);
        setHasOptionsMenu(true);

            listaDirectorios = new ArrayList<>();
            recyclerDirectorios = vista.findViewById(R.id.recycler_directorios);
            recyclerDirectorios.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_directorios = vista.findViewById(R.id.swipe_directorios);
        refresh_directorios.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaDirectorios.clear();
                cargarWebService_Directorios();
            }
        });

        request = Volley.newRequestQueue(getContext());
        cargarWebService_Directorios();

        refresh_directorios.setRefreshing(true);


        return vista;
    }

    private void cargarWebService_Directorios() {

        String url_Directorios = DireccionServidor+"wsnJSONllenarDirectorios.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Directorios,null,this,this);
        request.add(jsonObjectRequest);
        refresh_directorios.setRefreshing(false);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        refresh_directorios.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {

        Directorio directorio = null;
        JSONArray json_directorio = response.optJSONArray("directorio");

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
        searchView.setQueryHint("Ingrese el evento que desea buscar");

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
