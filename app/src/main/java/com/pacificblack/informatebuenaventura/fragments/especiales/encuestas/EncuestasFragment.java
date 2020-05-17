package com.pacificblack.informatebuenaventura.fragments.especiales.encuestas;


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
import com.pacificblack.informatebuenaventura.actividades.DetalleEncuestas;
import com.pacificblack.informatebuenaventura.clases.encuestas.AdaptadorEncuestas;
import com.pacificblack.informatebuenaventura.clases.encuestas.Encuestas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class EncuestasFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerEncuestas;
    ArrayList<Encuestas> listaEncuestas;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_encuestas;
    AdaptadorEncuestas adaptadorEncuestas;
    LinearLayout internet, vacio;
    Button reintentar, verificar;


    public EncuestasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_encuestas, container, false);
        internet = vista.findViewById(R.id.internet_fragment_encuestas);

        vacio = vista.findViewById(R.id.vacio_fragment_encuestas);
        verificar = vista.findViewById(R.id.verificar_fragment_encuestas);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Encuestas();
                refresh_encuestas.setRefreshing(true);
            }
        });

        reintentar = vista.findViewById(R.id.reintentar_fragment_encuestas);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Encuestas();
                refresh_encuestas.setRefreshing(true);
            }
        });
        setHasOptionsMenu(true);

        listaEncuestas = new ArrayList<>();
        recyclerEncuestas = vista.findViewById(R.id.recycler_encuestas);
        recyclerEncuestas.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh_encuestas = vista.findViewById(R.id.swipe_encuestas);
        refresh_encuestas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listaEncuestas.clear();
                cargarWebService_Encuestas();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Encuestas();
        refresh_encuestas.setRefreshing(true);

        return vista;
    }



    private void cargarWebService_Encuestas() {
        internet.setVisibility(View.GONE);
        vacio.setVisibility(View.GONE);
        String url_Encuestas = DireccionServidor+"wsnJSONllenarEncuestas.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Encuestas,null,this,this);
        request.add(jsonObjectRequest);
        refresh_encuestas.setRefreshing(false);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listaEncuestas.isEmpty()) {
            vacio.setVisibility(View.VISIBLE);
        }else {
            internet.setVisibility(View.VISIBLE);
            recyclerEncuestas.setVisibility(View.GONE);
            Log.i("ERROR", error.toString());
            refresh_encuestas.setRefreshing(false);
        }

    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerEncuestas.setVisibility(View.VISIBLE);

        Encuestas encuestas = null;
        JSONArray json_encuestas = response.optJSONArray("encuestas");

        try {
            for (int i = 0; i < json_encuestas.length(); i++) {

                encuestas = new Encuestas();
                JSONObject jsonObject = null;
                jsonObject = json_encuestas.getJSONObject(i);

                encuestas.setOpcion1(jsonObject.optString("opcion1_encuestas"));
                encuestas.setOpcion2(jsonObject.optString("opcion2_encuestas"));
                encuestas.setOpcion3(jsonObject.optString("opcion3_encuestas"));
                encuestas.setOpcion4(jsonObject.optString("opcion4_encuestas"));
                encuestas.setVoto1_encuestas(jsonObject.optInt("voto1_encuestas"));
                encuestas.setVoto2_encuestas(jsonObject.optInt("voto2_encuestas"));
                encuestas.setVoto3_encuestas(jsonObject.optInt("voto3_encuestas"));
                encuestas.setVoto4_encuestas(jsonObject.optInt("voto4_encuestas"));
                encuestas.setTitulo_row_encuestas(jsonObject.optString("titulo_encuestas"));
                encuestas.setDescripcion_row_encuestas(jsonObject.optString("descripcionrow_encuestas"));
                encuestas.setFechapublicacion_row_encuestas(jsonObject.optString("fechapublicacion_encuestas"));
                encuestas.setVistas_encuestas(jsonObject.optInt("vistas_encuestas"));
                encuestas.setDescripcion1_encuestas(jsonObject.optString("descripcion1_encuestas"));
                encuestas.setId_encuestas(jsonObject.optInt("id_encuestas"));
                encuestas.setImagen1_encuestas(jsonObject.optString("imagen1_encuestas"));

                listaEncuestas.add(encuestas);

            }

            adaptadorEncuestas = new AdaptadorEncuestas(listaEncuestas);
            recyclerEncuestas.setAdapter(adaptadorEncuestas);
            adaptadorEncuestas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Encuestas encu = listaEncuestas.get(recyclerEncuestas.getChildAdapterPosition(v));

                    Intent intentEncuestas = new Intent(getContext(), DetalleEncuestas.class);
                    Bundle encues = new Bundle();
                    encues.putSerializable("objeto7",encu);

                    intentEncuestas.putExtras(encues);
                    startActivity(intentEncuestas);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        refresh_encuestas.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora,menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese la encuesta que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptadorEncuestas.getFilter().filter(newText);
                return false;
            }
        });
    }
}
