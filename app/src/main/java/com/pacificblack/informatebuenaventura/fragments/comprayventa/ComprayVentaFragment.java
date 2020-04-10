package com.pacificblack.informatebuenaventura.fragments.comprayventa;


import android.app.Activity;
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
import com.pacificblack.informatebuenaventura.actividades.DetalleComprayVenta;
import com.pacificblack.informatebuenaventura.clases.comprayventa.AdaptadorComprayVenta;
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;
import static com.pacificblack.informatebuenaventura.texto.Servidor.Nohayinternet;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprayVentaFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    RecyclerView recyclerComprayventa;
    ArrayList<ComprayVenta> listaComprayVenta;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    public ComprayVentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_compray_venta, container, false);


        listaComprayVenta = new ArrayList<>();
        recyclerComprayventa = vista.findViewById(R.id.recycler_comprayventa);
        recyclerComprayventa.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());

        cargarWebService_ComprayVenta();

        return vista;
    }

    private void cargarWebService_ComprayVenta() {

        String url_ComprayVenta = DireccionServidor+"wsnJSONllenarComprayVenta.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url_ComprayVenta,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),Nohayinternet,Toast.LENGTH_LONG).show();
        Log.i("ERROR",error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        ComprayVenta comprayVenta = null;
        JSONArray json_comprayventa = response.optJSONArray("comprayventa");

        try {

            for (int i = 0; i < json_comprayventa.length() ; i++) {

                comprayVenta = new ComprayVenta();
                JSONObject jsonObject = null;
                jsonObject = json_comprayventa.getJSONObject(i);

                comprayVenta.setId_comprayventa(jsonObject.getInt("id_comprayventa"));
                comprayVenta.setTitulo_row_comprayventa(jsonObject.getString("titulo_comprayventa"));
                comprayVenta.setDescripcion_row_comprayventa(jsonObject.getString("descripcionrow_comprayventa"));
                comprayVenta.setDescripcion_comprayventa(jsonObject.getString("descripcion_comprayventa"));
                comprayVenta.setFechapublicacion_row_comprayventa(jsonObject.getString("fechapublicacion_comprayventa"));
                comprayVenta.setPrecio_row_comprayventa(jsonObject.getString("precio_comprayventa"));
                comprayVenta.setContacto_comprayventa(jsonObject.getString("contacto_comprayventa"));
                comprayVenta.setUbicacion_comprayventa(jsonObject.getString("ubicacion_comprayventa"));
                comprayVenta.setDescripcionextra_comprayventa(jsonObject.getString("descripcionextra_comprayventa"));
                comprayVenta.setImagen1_comprayventa(jsonObject.getString("imagen1_comprayventa"));
                comprayVenta.setImagen2_comprayventa(jsonObject.getString("imagen2_comprayventa"));
                comprayVenta.setImagen3̣̣_comprayventa(jsonObject.getString("imagen3_comprayventa"));
                comprayVenta.setCantidad_comprayventa(jsonObject.getInt("cantidad_comprayventa"));
                comprayVenta.setVista_comprayventa(jsonObject.getInt("vistas_comprayventa"));

                listaComprayVenta.add(comprayVenta);

            }
            AdaptadorComprayVenta adaptadorComprayVenta = new AdaptadorComprayVenta(listaComprayVenta);
            recyclerComprayventa.setAdapter(adaptadorComprayVenta);
            adaptadorComprayVenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ComprayVenta comprayVenta = listaComprayVenta.get(recyclerComprayventa.getChildAdapterPosition(v));

                    Intent intentComprayVenta = new Intent(getContext(), DetalleComprayVenta.class);
                    Bundle bundleComprayVenta = new Bundle();
                    bundleComprayVenta.putSerializable("objeto4",comprayVenta);

                    intentComprayVenta.putExtras(bundleComprayVenta);
                    startActivity(intentComprayVenta);

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(),"No se puede obtener",Toast.LENGTH_LONG).show();

            Log.i("ERROR",response.toString());

            e.printStackTrace();
        }
    }
}
