package com.pacificblack.informatebuenaventura.fragments.ofertas;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.ofertas.AdaptadorServicios;
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaServicios;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfertaServiciosFragment extends Fragment {

    RecyclerView recyclerServicios;

    ArrayList<OfertaServicios> listaServicios;



    public OfertaServiciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_ofertaservicios, container, false);

        listaServicios = new ArrayList<>();
        recyclerServicios = vista.findViewById(R.id.recycler_ofertaservicios);
        recyclerServicios.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_servicios();

        AdaptadorServicios adaptadorServicios = new AdaptadorServicios(listaServicios);
        recyclerServicios.setAdapter(adaptadorServicios);


        return vista;
    }

    private void llenarlista_servicios() {



    }

}
