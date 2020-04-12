package com.pacificblack.informatebuenaventura.extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;

public class Cargando {

private Activity activity;
private AlertDialog alertDialog;
private TextView texto;

public Cargando(Activity myactivity){
activity = myactivity;
}


public void iniciarprogress(){
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

    LayoutInflater inflater = activity.getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.cargando,null));
    builder.setCancelable(false);

    alertDialog = builder.create();
    alertDialog.show();
}

public void cancelarprogress(){
    alertDialog.dismiss();
}


}
