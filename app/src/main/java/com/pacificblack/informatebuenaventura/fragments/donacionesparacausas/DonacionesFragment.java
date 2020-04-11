package com.pacificblack.informatebuenaventura.fragments.donacionesparacausas;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    public DonacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View vista = inflater.inflate(R.layout.fragment_donaciones, container, false);

listaDonaciones = new ArrayList<>();
recyclerDonacion = vista.findViewById(R.id.recycler_donaciones);
recyclerDonacion.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());

        cargarWebService_Donaciones();

        return vista;
    }

    private void cargarWebService_Donaciones() {

        String url_Donaciones = DireccionServidor+"wsnJSONllenarDonaciones.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Donaciones,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        Donaciones donacion = null;
        JSONArray json_donaciones = response.optJSONArray("donaciones");

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

                listaDonaciones.add(donacion);

            }

            AdaptadorDonaciones adapatadorDonaciones = new AdaptadorDonaciones(listaDonaciones);
            recyclerDonacion.setAdapter(adapatadorDonaciones);
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

    }
}
