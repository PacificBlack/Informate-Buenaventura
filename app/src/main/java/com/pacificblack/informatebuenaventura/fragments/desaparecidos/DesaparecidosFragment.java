package com.pacificblack.informatebuenaventura.fragments.desaparecidos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleDesaparecidos;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.AdaptadorDesaparecidos;
import com.pacificblack.informatebuenaventura.clases.desaparecidos.Desaparecidos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesaparecidosFragment extends Fragment {

    ArrayList<Desaparecidos> listaDesaparecidos;

    RecyclerView recyclerDesaparecidos;



    public DesaparecidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View vista =  inflater.inflate(R.layout.fragment_desaparecidos, container, false);


        listaDesaparecidos = new ArrayList<>();
        recyclerDesaparecidos = vista.findViewById(R.id.recycler_desaparecidos);
        recyclerDesaparecidos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_desaparecidos();


        AdaptadorDesaparecidos adaptadorDesaparecidos = new AdaptadorDesaparecidos(listaDesaparecidos);

        recyclerDesaparecidos.setAdapter(adaptadorDesaparecidos);
        adaptadorDesaparecidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Desaparecidos desaparecidos = listaDesaparecidos.get(recyclerDesaparecidos.getChildAdapterPosition(v));

                Intent intentDesaparecidos = new Intent(getContext(), DetalleDesaparecidos.class);
                Bundle envioDesaparecidos = new Bundle();
                envioDesaparecidos.putSerializable("objeto5",desaparecidos);
                intentDesaparecidos.putExtras(envioDesaparecidos);

                startActivity(intentDesaparecidos);

            }
        });


        return vista;

    }

    private void llenarlista_desaparecidos() {




    }

}
