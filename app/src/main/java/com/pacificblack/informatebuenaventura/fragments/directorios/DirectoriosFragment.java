package com.pacificblack.informatebuenaventura.fragments.directorios;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.directorio.AdaptadorDirectorio;
import com.pacificblack.informatebuenaventura.clases.directorio.Directorio;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoriosFragment extends Fragment {

    RecyclerView recyclerDirectorios;

    ArrayList<Directorio> listaDirectorios;



    public DirectoriosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View vista =inflater.inflate(R.layout.fragment_directorios, container, false);


            listaDirectorios = new ArrayList<>();
            recyclerDirectorios = vista.findViewById(R.id.recycler_directorios);
            recyclerDirectorios.setLayoutManager(new LinearLayoutManager(getContext()));

            llenarlista_directorios();

        AdaptadorDirectorio adaptadorDirectorio  = new AdaptadorDirectorio(listaDirectorios);
        recyclerDirectorios.setAdapter(adaptadorDirectorio);



        return vista;
    }

    private void llenarlista_directorios() {

        listaDirectorios.add(new Directorio("SHolaso",
                "Este perritoasfgdsfrsfecesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",
                "Aqui van los contacots de la gente",
                R.drawable.imagencita,
                2948));



    }

}
