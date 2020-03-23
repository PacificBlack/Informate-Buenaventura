package com.pacificblack.informatebuenaventura.fragments.comprayventa;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleComprayVenta;
import com.pacificblack.informatebuenaventura.clases.comprayventa.AdaptadorComprayVenta;
import com.pacificblack.informatebuenaventura.clases.comprayventa.ComprayVenta;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprayVentaFragment extends Fragment {

    RecyclerView recyclerComprayventa;


    ArrayList<ComprayVenta> listaComprayVenta;



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


        llenarlista_comprayventa();

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

        return vista;
    }

    private void llenarlista_comprayventa() {

        listaComprayVenta.add(new ComprayVenta(123,"Se busca dueño para este guapo",
                "Este perrito es un prieto y necesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",
                "Yo ni se que esto",
                "Segun el precio es string",
                "Estring otro",
                "Estring que falta",
                "y Otra descriocion",
                "www.gugle.com",
                "www.gugle.com",
                "www.gugle.com",
                28,15));

    }

}
