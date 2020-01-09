package com.pacificblack.informatebuenaventura.fragments.eventos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.eventos.AdaptadorEventos;
import com.pacificblack.informatebuenaventura.clases.eventos.Eventos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment {

    RecyclerView recyclerEventos;

    ArrayList<Eventos> listaEventos;



    public EventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View vista = inflater.inflate(R.layout.fragment_eventos, container, false);


        listaEventos = new ArrayList<>();
        recyclerEventos = vista.findViewById(R.id.recycler_eventos);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_eventos();

        AdaptadorEventos adaptadorEventos = new AdaptadorEventos(listaEventos);
        recyclerEventos.setAdapter(adaptadorEventos);

return vista;
    }

    private void llenarlista_eventos() {

        listaEventos.add(new Eventos("Bienvenidos al perreo sondiacal",
                "Clasifsafojsmewf osdfkfe wyo no lsoe las esdf",
                "Otro texto bien chilin",
                "Yo ni se mi parcerito crack",
                R.drawable.imagencita,
                2599));

    }

}
