package com.pacificblack.informatebuenaventura.fragments.especiales.encuestas;


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




    public EncuestasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_encuestas, container, false);

        listaEncuestas = new ArrayList<>();
        recyclerEncuestas = vista.findViewById(R.id.recycler_encuestas);
        recyclerEncuestas.setLayoutManager(new LinearLayoutManager(getContext()));


        request = Volley.newRequestQueue(getContext());

        cargarWebService_Encuestas();

        return vista;
    }



    private void cargarWebService_Encuestas() {

        String url_Encuestas = DireccionServidor+"wsnJSONllenarEncuestas.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_Encuestas,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
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

            AdaptadorEncuestas adaptadorEncuestas = new AdaptadorEncuestas(listaEncuestas);
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
    }
}
