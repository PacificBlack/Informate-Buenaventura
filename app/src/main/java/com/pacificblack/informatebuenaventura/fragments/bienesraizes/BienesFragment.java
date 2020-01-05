package com.pacificblack.informatebuenaventura.fragments.bienesraizes;


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
import com.pacificblack.informatebuenaventura.actividades.DetalleBienes;
import com.pacificblack.informatebuenaventura.clases.bienes.AdaptadorBienes;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BienesFragment extends Fragment {

    RecyclerView recyclerBienes;

    ArrayList<Bienes> listaBienes;


    public BienesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_bienes, container, false);

        listaBienes = new ArrayList<>();
        recyclerBienes = vista.findViewById(R.id.recycler_bienes);
        recyclerBienes.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarlista_bienes();

        AdaptadorBienes adaptadorB = new AdaptadorBienes(listaBienes);
        recyclerBienes.setAdapter(adaptadorB);
        adaptadorB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bienes bienes = listaBienes.get(recyclerBienes.getChildAdapterPosition(v));

                Intent intentBienes = new Intent(getContext(), DetalleBienes.class);
                Bundle bundlebienes = new Bundle();
                bundlebienes.putSerializable("objeto2",bienes);

                intentBienes.putExtras(bundlebienes);
                startActivity(intentBienes);


            }
        });


        return vista;
    }

    private void llenarlista_bienes() {

        listaBienes.add(new Bienes("Se busca dueño para este guapo","Este perrito es un prieto y necesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,15000,99,
                "Uy mi perro lo que te diga de ese man es mentira","Sabe que mi perro, suerte le deseo"));



    }



}
