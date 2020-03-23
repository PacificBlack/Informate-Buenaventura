package com.pacificblack.informatebuenaventura.fragments.noticias;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.noticias.AdaptadorUltimaHora;
import com.pacificblack.informatebuenaventura.clases.noticias.UltimaHora;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UltimaHoraFragment extends Fragment {


    RecyclerView recyclerUltima;

    ArrayList<UltimaHora> listaUltimaHora;


    public UltimaHoraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View vista = inflater.inflate(R.layout.fragment_ultima_hora, container, false);

            listaUltimaHora = new ArrayList<>();
            recyclerUltima = vista.findViewById(R.id.recycler_ultimahora);
            recyclerUltima.setLayoutManager(new LinearLayoutManager(getContext()));

            llenarlista_ultima();

            AdaptadorUltimaHora adaptadorUltimaHora = new AdaptadorUltimaHora(listaUltimaHora);
            recyclerUltima.setAdapter(adaptadorUltimaHora);

        return vista;
    }

    private void llenarlista_ultima() {

        listaUltimaHora.add(new UltimaHora("Se murio don perenciano ayer",
                "aosmvsiduvdivnmvlsdoidfvmclxzvofv",
                "Hoy",R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,123));

    }

}
