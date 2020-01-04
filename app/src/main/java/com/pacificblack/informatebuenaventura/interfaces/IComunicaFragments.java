package com.pacificblack.informatebuenaventura.interfaces;

import com.pacificblack.informatebuenaventura.clases.adopcion.Adopcion;
import com.pacificblack.informatebuenaventura.clases.bienes.Bienes;
import com.pacificblack.informatebuenaventura.clases.clasificados.Clasificados;

public interface IComunicaFragments {

    public void enviarAdopcion(Adopcion adopcion);
    public void enviarBienes(Bienes bienes);
    public void enviarClasificados(Clasificados clasificados);
}
