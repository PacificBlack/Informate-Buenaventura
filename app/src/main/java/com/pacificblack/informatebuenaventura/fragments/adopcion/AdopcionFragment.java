package com.pacificblack.informatebuenaventura.fragments.adopcion;

import android.app.Activity;
import android.content.Context;
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
import com.pacificblack.informatebuenaventura.clases.adopcion.AdaptadorAdopcion;
import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
import com.pacificblack.informatebuenaventura.interfaces.IComunicaFragments;

import java.util.ArrayList;


public class AdopcionFragment extends Fragment {
    //Declaramos lo que vamos a usar

    RecyclerView recyclerAdopcion;


    //Agregamos un arraylist ya que estamos usando uno

    ArrayList<Adopcion> listaAdopcion;

    Activity activity;
    IComunicaFragments interfaceComunicaFragments;



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
                Toast.makeText(getContext(),"Usted selecciono:"+
                        listaAdopcion.get(recyclerAdopcion.getChildAdapterPosition(v)).getTitulo_row_adopcion(),
                        Toast.LENGTH_SHORT).show();

                interfaceComunicaFragments.enviarAdopcion(listaAdopcion.get(recyclerAdopcion.getChildAdapterPosition(v)));

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
                "Uy mi perro lo que te diga de ese man es mentira","Sabe que mi perro, suerte le deseo"));

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){

            this.activity = (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) this.activity;

        }

    }
}
