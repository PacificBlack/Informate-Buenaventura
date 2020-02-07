package com.pacificblack.informatebuenaventura.fragments.clasificados;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleClasificados;
import com.pacificblack.informatebuenaventura.clases.clasificados.AdaptadorClasificados;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClasificadosFragment extends Fragment {

    RecyclerView recyclerClasificados;

    ArrayList<Clasificados> listaClasificados;


    public ClasificadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment_clasificados, container, false);

        listaClasificados = new ArrayList<>();
        recyclerClasificados = vista.findViewById(R.id.recycler_clasificados);
        recyclerClasificados.setLayoutManager(new LinearLayoutManager(getContext()));

        AdaptadorClasificados adaptadorC = new AdaptadorClasificados(listaClasificados);
        recyclerClasificados.setAdapter(adaptadorC);
        adaptadorC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Clasificados clasificados = listaClasificados.get(recyclerClasificados.getChildAdapterPosition(v));

                Intent intentClasificados = new Intent(getContext(), DetalleClasificados.class);
                Bundle bundleclasificados = new Bundle();
                bundleclasificados.putSerializable("objeto3",clasificados);

                intentClasificados.putExtras(bundleclasificados);
                startActivity(intentClasificados);

            }
        });

        return vista;
    }


}
