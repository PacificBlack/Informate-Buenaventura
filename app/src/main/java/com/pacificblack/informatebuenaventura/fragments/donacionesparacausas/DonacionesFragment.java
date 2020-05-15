package com.pacificblack.informatebuenaventura.fragments.donacionesparacausas;


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
import com.pacificblack.informatebuenaventura.actividades.DetalleDonaciones;
import com.pacificblack.informatebuenaventura.clases.donaciones.AdaptadorDonaciones;
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonacionesFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {


    RecyclerView recyclerDonacion;
    ArrayList<Donaciones> listaDonaciones;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    private SwipeRefreshLayout refresh_donaciones;
    AdaptadorDonaciones adapatadorDonaciones;
    LinearLayout internet, vacio;
    Button reintentar, verificar;


    public DonacionesFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_donaciones, container, false);
        internet = vista.findViewById(R.id.internet_fragment_donaciones);

        vacio = vista.findViewById(R.id.vacio_fragment_donaciones);
        verificar = vista.findViewById(R.id.verificar_fragment_donaciones);
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Donaciones();
                refresh_donaciones.setRefreshing(true);
            }
        });

        reintentar = vista.findViewById(R.id.reintentar_fragment_donaciones);
        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService_Donaciones();
                refresh_donaciones.setRefreshing(true);
            }
        });
        setHasOptionsMenu(true);

        listaDonaciones = new ArrayList<>();
        recyclerDonacion = vista.findViewById(R.id.recycler_donaciones);
        recyclerDonacion.setLayoutManager(new LinearLayoutManager(getContext()));


        refresh_donaciones = vista.findViewById(R.id.swipe_donaciones);
        refresh_donaciones.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarWebService_Donaciones();
            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Donaciones();

        refresh_donaciones.setRefreshing(true);

        return vista;
    }

    private void cargarWebService_Donaciones() {
        internet.setVisibility(View.GONE);
        vacio.setVisibility(View.GONE);
        String url_Donaciones = DireccionServidor+"wsnJSONllenarDonaciones.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Donaciones,null,this,this);
        request.add(jsonObjectRequest);
        refresh_donaciones.setRefreshing(false);
        recyclerDonacion.setVisibility(View.VISIBLE);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        if (listaDonaciones.isEmpty()) {
            vacio.setVisibility(View.VISIBLE);
        } else {
            internet.setVisibility(View.VISIBLE);
            recyclerDonacion.setVisibility(View.GONE);
            Log.i("ERROR", error.toString());
            refresh_donaciones.setRefreshing(false);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        recyclerDonacion.setVisibility(View.VISIBLE);

        Donaciones donacion = null;
        JSONArray json_donaciones = response.optJSONArray("donaciones");

        listaDonaciones.clear();

        try {
            for (int i = 0; i < json_donaciones.length() ; i++) {

                donacion = new Donaciones();
                JSONObject jsonObject = null;
                jsonObject = json_donaciones.getJSONObject(i);

                donacion.setId_donaciones(jsonObject.optInt("id_donaciones"));
                donacion.setTitulo_row_donaciones(jsonObject.optString("titulo_donaciones"));
                donacion.setDescripcion_row_donaciones(jsonObject.optString("descripcionrow_donaciones"));
                donacion.setFechapublicacion_row_donaciones(jsonObject.optString("fechapublicacion_donaciones"));
                donacion.setImagen1_donaciones(jsonObject.optString("imagen1_donaciones"));
                donacion.setImagen2_donaciones(jsonObject.getString("imagen2_donaciones"));
                donacion.setVistas_donaciones(jsonObject.optInt("vistas_donaciones"));
                donacion.setMeta_row_donaciones(jsonObject.optInt("meta_donaciones"));
                donacion.setDescripcion1_donaciones(jsonObject.optString("descripcion1_donaciones"));
                donacion.setVideo_donaciones(jsonObject.optString("video_donaciones"));
                listaDonaciones.add(donacion);

            }

            adapatadorDonaciones = new AdaptadorDonaciones(listaDonaciones);
            recyclerDonacion.setAdapter(adapatadorDonaciones);
            adapatadorDonaciones.notifyDataSetChanged();
            adapatadorDonaciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Donaciones donacion = listaDonaciones.get(recyclerDonacion.getChildAdapterPosition(v));

                    Intent intentDonacion = new Intent(getContext(), DetalleDonaciones.class);
                    Bundle enviarDonacion = new Bundle();
                    enviarDonacion.putSerializable("objeto6",donacion);

                    intentDonacion.putExtras(enviarDonacion);
                    startActivity(intentDonacion);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh_donaciones.setRefreshing(false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.buscadora,menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Ingrese la donación que desea buscar");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapatadorDonaciones.getFilter().filter(newText);
                return false;
            }
        });
    }
}
