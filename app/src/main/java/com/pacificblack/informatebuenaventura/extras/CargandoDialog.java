package com.pacificblack.informatebuenaventura.extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.pacificblack.informatebuenaventura.R;

 public class CargandoDialog {

    private Activity actividad;
    private AlertDialog dialog;

     public CargandoDialog(Activity actividad) {
        this.actividad = actividad;
    }

        public void Mostrar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);
        LayoutInflater inflater = actividad.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.cargando,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

     public void Ocultar(){
        dialog.dismiss();
    }
}
