package com.pacificblack.informatebuenaventura.fragments.noticias;


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
import com.pacificblack.informatebuenaventura.clases.noticias.AdaptadorUltimaHora;
import com.pacificblack.informatebuenaventura.clases.noticias.UltimaHora;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltimaHoraFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{


    RecyclerView recyclerUltima;
    ArrayList<UltimaHora> listaUltimaHora;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private SwipeRefreshLayout refresh_ultimahora;
    AdaptadorUltimaHora adaptadorUltimaHora;


    public UltimaHoraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View vista = inflater.inflate(R.layout.fragment_ultima_hora, container, false);
            setHasOptionsMenu(true);
            listaUltimaHora = new ArrayList<>();
            recyclerUltima = vista.findViewById(R.id.recycler_ultimahora);
            recyclerUltima.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_ultimahora = vista.findViewById(R.id.swipe_ultimahora);
        refresh_ultimahora.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaUltimaHora.clear();
                cargarWebService_UltimaHora();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_UltimaHora();
        refresh_ultimahora.setRefreshing(true);


        return vista;
    }

    private void cargarWebService_UltimaHora() {

        String url_UltimaHora = DireccionServidor+"wsnJSONllenarUltimaHora.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_UltimaHora,null,this,this);
        request.add(jsonObjectRequest);
        refresh_ultimahora.setRefreshing(false);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());
        refresh_ultimahora.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {
        UltimaHora ultimaHora = null;
        JSONArray json_ultimahora = response.optJSONArray("ultimahora");
        try {

            for (int i = 0; i < json_ultimahora.length(); i++) {

                ultimaHora = new UltimaHora();
                JSONObject jsonObject = null;
                jsonObject = json_ultimahora.getJSONObject(i);

                ultimaHora.setImagen_row_ultimahora(jsonObject.optString("imagen1_ultimahora"));
                ultimaHora.setId_ultimahora(jsonObject.optInt("id_ultimahora"));
                ultimaHora.setTitulo_row_ultimahora(jsonObject.optString("titulo_ultimahora"));
                ultimaHora.setDescripcion_row_ultimahora(jsonObject.optString("descripcionrow_ultimahora"));
                ultimaHora.setFechapublicacion_row_ultimahora(jsonObject.optString("fechapublicacion_ultimahora"));
                ultimaHora.setVistas_row_ultimahora(jsonObject.optInt("vistas_ultimahora"));

                listaUltimaHora.add(ultimaHora);

            }

            adaptadorUltimaHora = new AdaptadorUltimaHora(listaUltimaHora);
            recyclerUltima.setAdapter(adaptadorUltimaHora);

        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh_ultimahora.setRefreshing(false);

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
                adaptadorUltimaHora.getFilter().filter(newText);
                return false;
            }
        });    }
}
