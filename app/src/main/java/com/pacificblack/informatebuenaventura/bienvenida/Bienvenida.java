package com.pacificblack.informatebuenaventura.bienvenida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pacificblack.informatebuenaventura.MainActivity;
import com.pacificblack.informatebuenaventura.R;

import java.util.ArrayList;
import java.util.List;

public class Bienvenida extends AppCompatActivity {

    private ViewPager viewPager;
    InicioViewpagerAdaptador inicioViewpagerAdaptador;
    TabLayout tabindicador;
    Button siguiente;
    int posicion = 0;
    Button iniciar;
    Animation btnAnimacion;
    TextView saltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class );
            startActivity(mainActivity);
            finish();


        }

        setContentView(R.layout.activity_bienvenida);

        getSupportActionBar().hide();

        // ini views
        siguiente = findViewById(R.id.btn_next);
        iniciar = findViewById(R.id.btn_get_started);
        tabindicador = findViewById(R.id.tab_indicator);
        btnAnimacion = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        saltar = findViewById(R.id.tv_skip);



        final List<ItemInicio> mList = new ArrayList<>();
        mList.add(new ItemInicio("Fresh Food","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",R.drawable.img1));
        mList.add(new ItemInicio("Fast Delivery","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",R.drawable.img2));
        mList.add(new ItemInicio("Easy Payment","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua, consectetur  consectetur adipiscing elit",R.drawable.img3));


        viewPager =findViewById(R.id.screen_viewpager);
        inicioViewpagerAdaptador = new InicioViewpagerAdaptador(this,mList);
        viewPager.setAdapter(inicioViewpagerAdaptador);

        // setup tablayout with viewpager

        tabindicador.setupWithViewPager(viewPager);


        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posicion = viewPager.getCurrentItem();
                if (posicion < mList.size()) {

                    posicion++;
                    viewPager.setCurrentItem(posicion);


                }

                if (posicion == mList.size()-1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();

                }


            }
        });



        tabindicador.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {

                    loaddLastScreen();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open main activity

                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();


            }
        });



        saltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mList.size());
            }
        });

    }


    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return  isIntroActivityOpnendBefore;
    }



    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    private void loaddLastScreen() {

        siguiente.setVisibility(View.INVISIBLE);
        iniciar.setVisibility(View.VISIBLE);
        saltar.setVisibility(View.INVISIBLE);
        tabindicador.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        iniciar.setAnimation(btnAnimacion);



    }

}
