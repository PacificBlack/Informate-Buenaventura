package com.pacificblack.informatebuenaventura.fragments.avisosfunebres;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleDifuntos;
import com.pacificblack.informatebuenaventura.clases.funebres.AdaptadorFunebres;
import com.pacificblack.informatebuenaventura.clases.funebres.Funebres;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunebresFragment extends Fragment {

    RecyclerView recyclerFunebres;

    ArrayList<Funebres> listaFunebres;


    public FunebresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_funebres, container, false);

        listaFunebres = new ArrayList<>();
        recyclerFunebres = vista.findViewById(R.id.recycler_funebres);
        recyclerFunebres.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_funebres();

        AdaptadorFunebres adaptadorFunebres = new AdaptadorFunebres(listaFunebres);
        recyclerFunebres.setAdapter(adaptadorFunebres);
        adaptadorFunebres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Funebres fune = listaFunebres.get(recyclerFunebres.getChildAdapterPosition(v));

                Intent intentFunebres = new Intent(getContext(), DetalleDifuntos.class);
                Bundle envioFunebres = new Bundle();
                envioFunebres.putSerializable("objeto9",fune);

                intentFunebres.putExtras(envioFunebres);
                startActivity(intentFunebres);

            }
        });


        return vista;
    }

    private void llenarlista_funebres() {

        listaFunebres.add(new Funebres("Hola mi papichulo eh",
                "Mañana si",
                "Mañana no",
                R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,
                39,
                "Interesantemente mañana si mañana no crack",
                "Bueno eso me alegra crack"));

    }

}
