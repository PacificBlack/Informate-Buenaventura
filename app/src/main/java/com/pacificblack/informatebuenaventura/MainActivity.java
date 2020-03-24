package com.pacificblack.informatebuenaventura;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarAdopcion;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarArticulo;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarBienes;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarClasificados;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarDesaparicion;
import com.pacificblack.informatebuenaventura.eliminar.EliminarAdopcion;
import com.pacificblack.informatebuenaventura.eliminar.EliminarArticulo;
import com.pacificblack.informatebuenaventura.eliminar.EliminarBienes;
import com.pacificblack.informatebuenaventura.eliminar.EliminarClasificados;
import com.pacificblack.informatebuenaventura.eliminar.EliminarDesaparicion;
import com.pacificblack.informatebuenaventura.fragments.configuracion.Configuraciones;
import com.pacificblack.informatebuenaventura.publicar.PublicarAdopcion;
import com.pacificblack.informatebuenaventura.publicar.PublicarArticulo;
import com.pacificblack.informatebuenaventura.publicar.PublicarBienes;
import com.pacificblack.informatebuenaventura.publicar.PublicarClasificados;
import com.pacificblack.informatebuenaventura.publicar.PublicarDesaparicion;
import com.pacificblack.informatebuenaventura.publicar.PublicarDonaciones;
import com.pacificblack.informatebuenaventura.publicar.PublicarEmpleos;
import com.pacificblack.informatebuenaventura.publicar.PublicarEventos;
import com.pacificblack.informatebuenaventura.publicar.PublicarFunebres;
import com.pacificblack.informatebuenaventura.publicar.PublicarServicios;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity   {

    private AppBarConfiguration mAppBarConfiguration;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_desaparecidos, R.id.nav_clasificados,
                R.id.nav_noticias,R.id.nav_comprayventa, R.id.nav_eventos,
                R.id.nav_especiales ,R.id.nav_compartir, R.id.nav_quienes,
                R.id.nav_directorio,R.id.nav_ofertaservicios,R.id.nav_bienes,
                R.id.nav_ofertaempleos,R.id.nav_encuestas,R.id.nav_donaciones,
                R.id.nav_funebres,R.id.nav_ultimahora,R.id.nav_adopcion)

                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_configuraciones) {
            Intent intent = new Intent(this, Configuraciones.class);
            this.startActivity(intent);
        } else {
            super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    public void ShowPopup(View v) {
        TextView txtclose;
        dialog.setContentView(R.layout.ventana);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void PublicarAdopcion(View v){
        Intent intent = new Intent(MainActivity.this, PublicarAdopcion.class);
        startActivity(intent);
    }
    public void PublicarDesaparicion(View v){
        Intent intent = new Intent(MainActivity.this, PublicarDesaparicion.class);
        startActivity(intent);

    }
    public void PublicarArticulo(View view){
        Intent intent = new Intent(MainActivity.this, PublicarArticulo.class);
        startActivity(intent);
    }
    public void PublicarEventos(View view){
        Intent intent = new Intent(MainActivity.this, PublicarEventos.class);
        startActivity(intent);
    }
    public void PublicarServicios(View view){
        Intent intent = new Intent(MainActivity.this, PublicarServicios.class);
        startActivity(intent);
    }
    public void PublicarEmpleos(View view){
        Intent intent = new Intent(MainActivity.this, PublicarEmpleos.class);
        startActivity(intent);
    }
    public void PublicarBienes(View view){
        Intent intent = new Intent(MainActivity.this, PublicarBienes.class);
        startActivity(intent);
    }
    public void PublicarClasificados(View view){
        Intent intent = new Intent(MainActivity.this, PublicarClasificados.class);
        startActivity(intent);
    }
    public void PublicarFunebres(View view){

        Intent intent = new Intent(MainActivity.this, PublicarFunebres.class);
        startActivity(intent);
    }
    public void PublicarDonaciones(View view){
        Intent intent = new Intent(MainActivity.this, PublicarDonaciones.class);
        startActivity(intent);
    }
    public void PublicarEncuestas(View view){

    }

/////////////////////////////////////////////////////////////////

    public void MostrarEliminar(View view){
        TextView txtclose;
        dialog.setContentView(R.layout.ventana_eliminar);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose_eliminar);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void EliminarAdopcion(View view){
        Intent intent = new Intent(MainActivity.this, EliminarAdopcion.class);
        startActivity(intent);
    }
    public void EliminarArticulo(View view){
        Intent intent = new Intent(MainActivity.this, EliminarArticulo.class);
        startActivity(intent);
    }
    public void EliminarBienes(View v){
        Intent intent = new Intent(MainActivity.this, EliminarBienes.class);
        startActivity(intent);
    }
    public void EliminarClasificados(View view){
        Intent intent = new Intent(MainActivity.this, EliminarClasificados.class);
        startActivity(intent);
    }
    public void EliminarDesaparicion(View view){
        Intent intent = new Intent(MainActivity.this, EliminarDesaparicion.class);
        startActivity(intent);
    }


/////////////////////////////////////////////////////////////

    public void MostrarActualizar(View view){
        TextView txtclose;
        dialog.setContentView(R.layout.ventana_actualizar);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose_eliminar);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void ActualizarAdopcion(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarAdopcion.class);
        startActivity(intent);
    }
    public void ActualizarArticulo(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarArticulo.class);
        startActivity(intent);
    }
    public void ActualizarBienes(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarBienes.class);
        startActivity(intent);
    }
    public void ActualizarClasificados(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarClasificados.class);
        startActivity(intent);
    }
    public void ActualizarDesaparicion(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarDesaparicion.class);
        startActivity(intent);
    }


}



