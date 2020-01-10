package com.pacificblack.informatebuenaventura.fragments.noticias;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleNoticias;
import com.pacificblack.informatebuenaventura.clases.noticias.AdaptadorNoticias;
import com.pacificblack.informatebuenaventura.clases.noticias.Noticias;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticiasFragment extends Fragment {

    RecyclerView recyclerNoticias;

    ArrayList<Noticias> listaNoticias;




    public NoticiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_noticias, container, false);

        listaNoticias =  new ArrayList<>();
        recyclerNoticias = vista.findViewById(R.id.recycler_noticias);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_noticias();

        AdaptadorNoticias adaptadorNoticias = new AdaptadorNoticias(listaNoticias);
        recyclerNoticias.setAdapter(adaptadorNoticias);
        adaptadorNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Noticias noticis = listaNoticias.get(recyclerNoticias.getChildAdapterPosition(v));

                Intent intentNoticias = new Intent(getContext(), DetalleNoticias.class);
                Bundle envioNoticias = new Bundle();
                envioNoticias.putSerializable("objeto10",noticis);

                intentNoticias.putExtras(envioNoticias);
                startActivity(intentNoticias);


            }
        });


        return vista;
    }

    private void llenarlista_noticias() {


        listaNoticias.add(new Noticias("Hace pocos minutos fue capturado alias popeye",
                "El man lastimosamente lo cogio la tomba mi pana",
                "Hoy por la mañana",
                R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,
                13,
                23,
                23543,
                "Como juan caloer era conocido el mayor narco traficante de los unites estates",
                "Nadie sabia quien era el hasta que hablo el muy torpe",
                "Bueno, ya no se que decir para que esta mierda quede bien chula  www.youtube.com","https://www.google.com"));

    }

}
