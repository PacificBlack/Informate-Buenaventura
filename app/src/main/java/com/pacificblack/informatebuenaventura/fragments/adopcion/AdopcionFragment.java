package com.pacificblack.informatebuenaventura.fragments.adopcion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pacificblack.informatebuenaventura.R;
import com.pacificblack.informatebuenaventura.actividades.DetalleAdopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.AdaptadorAdopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;

import java.util.ArrayList;


public class AdopcionFragment extends Fragment {
    //Declaramos lo que vamos a usar

    RecyclerView recyclerAdopcion;


    //Agregamos un arraylist ya que estamos usando uno

    ArrayList<Adopcion> listaAdopcion;




    public AdopcionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {


        View vista = inflater.inflate(R.layout.fragment_adopcion, container, false);

        listaAdopcion = new ArrayList<>();
        recyclerAdopcion = vista.findViewById(R.id.recycler_adopcion);
        recyclerAdopcion.setLayoutManager(new LinearLayoutManager(getContext()));


        llenarlista_adopcion();

        AdaptadorAdopcion adaptador = new AdaptadorAdopcion(listaAdopcion);
        recyclerAdopcion.setAdapter(adaptador);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Adopcion adop = listaAdopcion.get(recyclerAdopcion.getChildAdapterPosition(v));

                Intent intentAdopcion = new Intent(getContext(), DetalleAdopcion.class);
                Bundle ensayo = new Bundle();
                ensayo.putSerializable("objeto1",adop);

                intentAdopcion.putExtras(ensayo);
                startActivity(intentAdopcion);
            }
        });

        return vista;
    }



    private void llenarlista_adopcion() {

        listaAdopcion.add(new Adopcion("Se busca dueño para este guapo","Este perrito es un prieto y necesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,15,
                "Uy mi perro lo que te diga de ese man es mentira","Sabe que mi perro, suerte le deseo"));


        listaAdopcion.add(new Adopcion("SHolaso","Este perritoasfgdsfrsfecesita de tu ayuda crack, jelpme",
                "Domingo 12 del 2019",R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,R.drawable.imagencita,15,
                "Uy mi perro lo que te diga de ese man es mentira consigalo en https://www.google.com","Sabe que mi perro, suerte le deseo"));

    }


}
