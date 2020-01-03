package com.pacificblack.informatebuenaventura.fragments.adopcion;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adaptador_adopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdopcionFragment extends Fragment {


    //Declaramos lo que vamos a usar

    RecyclerView recyclerAdopcion;


    //Agregamos un arraylist ya que estamos usando uno

    ArrayList<Adopcion> lista_adopcion;



    public AdopcionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


    View vista = inflater.inflate(R.layout.fragment_adopcion, container, false);


    //Aqui referenciamos
        lista_adopcion = new ArrayList<>();
        recyclerAdopcion = vista.findViewById(R.id.recycler_adopcion);
        recyclerAdopcion.setLayoutManager(new LinearLayoutManager(getContext()));


        llenarlista_adopcion();

        Adaptador_adopcion adaptador = new Adaptador_adopcion(lista_adopcion);
        recyclerAdopcion.setAdapter(adaptador);

         return vista;
    }



    private void llenarlista_adopcion() {

        lista_adopcion.add(new Adopcion("Se busca dueño para este guapo","Este perrito es un prieto y necesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,15,
                "Uy mi perro lo que te diga de ese man es mentira","Sabe que mi perro, suerte le deseo"));

    }

}
