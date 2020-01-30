package com.pacificblack.informatebuenaventura.fragments.donacionesparacausas;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleDonaciones;
import com.pacificblack.informatebuenaventura.clases.donaciones.AdaptadorDonaciones;
import com.pacificblack.informatebuenaventura.clases.donaciones.Donaciones;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonacionesFragment extends Fragment {

    RecyclerView recyclerDonacion;

    ArrayList<Donaciones> listaDonaciones;


    public DonacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

View vista = inflater.inflate(R.layout.fragment_donaciones, container, false);

listaDonaciones = new ArrayList<>();
recyclerDonacion = vista.findViewById(R.id.recycler_donaciones);
recyclerDonacion.setLayoutManager(new LinearLayoutManager(getContext()));

llenarlista_donaciones();

        AdaptadorDonaciones adapatadorDonaciones = new AdaptadorDonaciones(listaDonaciones);
        recyclerDonacion.setAdapter(adapatadorDonaciones);
        adapatadorDonaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Donaciones donacion = listaDonaciones.get(recyclerDonacion.getChildAdapterPosition(v));

                Intent intentDonacion = new Intent(getContext(), DetalleDonaciones.class);
                Bundle enviarDonacion = new Bundle();
                enviarDonacion.putSerializable("objeto6",donacion);

                intentDonacion.putExtras(enviarDonacion);
                startActivity(intentDonacion);


            }
        });


        return vista;
    }

    private void llenarlista_donaciones() {

        listaDonaciones.add(new Donaciones(12,"Bienvenido a su donacion plus",
                "Aqui puede hacer sus donaciones rapidito",
                "La vida te da sorpresas",
                "www.google.com","www.google.com",
                54,
                5000000,
                "Yo que wua saber"));
    }

}
