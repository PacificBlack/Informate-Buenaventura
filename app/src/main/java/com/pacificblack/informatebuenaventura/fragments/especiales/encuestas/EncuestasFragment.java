package com.pacificblack.informatebuenaventura.fragments.especiales.encuestas;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleEncuestas;
import com.pacificblack.informatebuenaventura.clases.encuestas.AdaptadorEncuestas;
import com.pacificblack.informatebuenaventura.clases.encuestas.Encuestas;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EncuestasFragment extends Fragment {

    RecyclerView recyclerEncuestas;

    ArrayList<Encuestas> listaEncuestas;



    public EncuestasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_encuestas, container, false);

        listaEncuestas = new ArrayList<>();
        recyclerEncuestas = vista.findViewById(R.id.recycler_encuestas);
        recyclerEncuestas.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_encuestas();

        AdaptadorEncuestas adaptadorEncuestas = new AdaptadorEncuestas(listaEncuestas);
        recyclerEncuestas.setAdapter(adaptadorEncuestas);
        adaptadorEncuestas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Encuestas encu = listaEncuestas.get(recyclerEncuestas.getChildAdapterPosition(v));

                Intent intentEncuestas = new Intent(getContext(), DetalleEncuestas.class);
                Bundle encues = new Bundle();
                encues.putSerializable("objeto7",encu);

                intentEncuestas.putExtras(encues);
                startActivity(intentEncuestas);

            }
        });


        return vista;
    }

    private void llenarlista_encuestas() {


   listaEncuestas.add(new Encuestas("Bienvenido a la encuesta bien melitica",
           "En esta encuesta te preguntaremos tu nivel de ingles plus everi dai",
           "Publicado hoy por la mañana",
           R.drawable.imagencita,
           99,
           9,
           2,
           4,
           3,
           "Comer carne",
           "Comer papachina",
           "Comer Picada",
           "Comer salchichon",
           "Esta es la mejor encuesta para saber que bien estamos de cultura"
   ));


    }


}
