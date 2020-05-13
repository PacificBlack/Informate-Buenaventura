package com.pacificblack.informatebuenaventura;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.NavigationMenu;
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
import android.widget.TextView;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

import static com.pacificblack.informatebuenaventura.texto.Servidor.DireccionServidor;

public class MainActivity extends AppCompatActivity   {
    private AppBarConfiguration mAppBarConfiguration;
    AppBarLayout mostrar;
    Dialog dialog;
    StringRequest videeos;
    public static String traer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog(this);

           //Todo: ----------------------------------------------------------------------------------
           cargarvideo();
           RequestQueue request_encuestas_eliminar = Volley.newRequestQueue(getApplicationContext());
           request_encuestas_eliminar.add(videeos);
           //Todo: ----------------------------------------------------------------------------------

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mostrar = findViewById(R.id.boton);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_desaparecidos, R.id.nav_clasificados,
                R.id.nav_noticias,R.id.nav_comprayventa, R.id.nav_eventos,
                R.id.nav_especiales , R.id.nav_quienes,
                R.id.nav_directorio,R.id.nav_ofertaservicios,R.id.nav_bienes,
                R.id.nav_ofertaempleos,R.id.nav_encuestas,R.id.nav_donaciones,
                R.id.nav_funebres,R.id.nav_ultimahora,R.id.nav_adopcion)

                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setItemIconTintList(null);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);drawer.openDrawer(Gravity.LEFT);
            }
        });
        FabSpeedDial fabSpeedDial = findViewById(R.id.fabs);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId()== R.id.flotante_publicar){
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
                if (menuItem.getItemId()== R.id.flotante_eliminar){
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
                if (menuItem.getItemId()== R.id.flotante_editar){TextView txtclose;
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
                return true;
            }
        });

    }

    private void cargarvideo() {
        String url_videos = DireccionServidor+"wsnJSONLlenarVideos.php?";
        videeos = new StringRequest(Request.Method.POST, url_videos, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Video traido ", response);
                traer = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("No actualizo","Vista Negativa");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_configuraciones) {
            Intent intent = new Intent(this, Configuraciones.class);
            this.startActivity(intent);
        }if (item.getItemId() == R.id.buscar){
        }else {
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



