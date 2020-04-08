package com.pacificblack.informatebuenaventura.fragments.ofertas;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.ofertas.AdaptadorEmpleos;
import com.pacificblack.informatebuenaventura.clases.ofertas.OfertaEmpleos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfertaEmpleosFragment extends Fragment {

    RecyclerView recyclerEmpleos;

    ArrayList<OfertaEmpleos> listaEmpleos;


    public OfertaEmpleosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_oferta_empleos, container, false);

        listaEmpleos = new ArrayList<>();
        recyclerEmpleos = vista.findViewById(R.id.recycler_ofertaempleos);
        recyclerEmpleos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_empleos();

        AdaptadorEmpleos adaptadorEmpleos = new AdaptadorEmpleos(listaEmpleos);
        recyclerEmpleos.setAdapter(adaptadorEmpleos);



        return vista;
    }

    private void llenarlista_empleos() {

    }

}
