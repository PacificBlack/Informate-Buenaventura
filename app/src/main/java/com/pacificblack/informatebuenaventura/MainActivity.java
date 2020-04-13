package com.pacificblack.informatebuenaventura;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarAdopcion;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarArticulo;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarBienes;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarClasificados;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarDesaparicion;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarDonaciones;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarEmpleos;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarEventos;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarFunebres;
import com.pacificblack.informatebuenaventura.actualizar.ActualizarServicios;
import com.pacificblack.informatebuenaventura.eliminar.EliminarAdopcion;
import com.pacificblack.informatebuenaventura.eliminar.EliminarArticulo;
import com.pacificblack.informatebuenaventura.eliminar.EliminarBienes;
import com.pacificblack.informatebuenaventura.eliminar.EliminarClasificados;
import com.pacificblack.informatebuenaventura.eliminar.EliminarDesaparicion;
import com.pacificblack.informatebuenaventura.eliminar.EliminarDonaciones;
import com.pacificblack.informatebuenaventura.eliminar.EliminarEmpleos;
import com.pacificblack.informatebuenaventura.eliminar.EliminarEventos;
import com.pacificblack.informatebuenaventura.eliminar.EliminarFunebres;
import com.pacificblack.informatebuenaventura.eliminar.EliminarServicios;
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
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity   {

    Dialog dialog;
    ImageButton abrirmenu,abrirconfiguracion;
    TextView textobarra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        abrirmenu = findViewById(R.id.abrir_menu);
        abrirconfiguracion = findViewById(R.id.abrir_configuracion);
        textobarra = findViewById(R.id.texto_barra);
        abrirmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);drawer.openDrawer(Gravity.LEFT);
            }
        });
        abrirconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Configuraciones.class);
                startActivity(intent);
            }
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        cambiartitulo("Inicio");

    }

     public void cambiartitulo(String s){
        textobarra.setText(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_adopcion:
                cambiartitulo("Adopcion");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void EliminarDonaciones(View view){
        Intent intent = new Intent(MainActivity.this, EliminarDonaciones.class);
        startActivity(intent);
    }
    public void EliminarEmpleos(View view){
        Intent intent = new Intent(MainActivity.this, EliminarEmpleos.class);
        startActivity(intent);
    }
    public void EliminarEventos(View view){
        Intent intent = new Intent(MainActivity.this, EliminarEventos.class);
        startActivity(intent);
    }
    public void EliminarFunebres(View view){
        Intent intent = new Intent(MainActivity.this, EliminarFunebres.class);
        startActivity(intent);
    }
    public void EliminarServicios(View view){
        Intent intent = new Intent(MainActivity.this, EliminarServicios.class);
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
    public void ActualizarDonaciones(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarDonaciones.class);
        startActivity(intent);
    }
    public void ActualizarEmpleos(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarEmpleos.class);
        startActivity(intent);
    }
    public void ActualizarEventos(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarEventos.class);
        startActivity(intent);
    }
    public void ActualizarFunebres(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarFunebres.class);
        startActivity(intent);
    }
    public void ActualizarServicios(View view){
        Intent intent = new Intent(MainActivity.this, ActualizarServicios.class);
        startActivity(intent);
    }



}



