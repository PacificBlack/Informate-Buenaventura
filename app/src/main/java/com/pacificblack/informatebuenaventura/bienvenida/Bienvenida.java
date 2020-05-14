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
        mList.add(new ItemInicio("Informate Buenaventura","Te damos la bienvenida a informate buenavenura, una app donde podrás enterarte de todo lo que pasa en el bello puerto del mar.",R.drawable.ib));
        mList.add(new ItemInicio("Adopciones","En esta sección podrás ver las publicaciones de los animales que se encuentren en adopción en nuestra ciudad.",R.drawable.ib));
        mList.add(new ItemInicio("Avisos Funebres","Aquí encontraras información acerca de personas que hayan fallecido recientemente. Así podrás enterarte, aunque no sean buenas noticias; siempre es mejor estar enterado para poder brindar apoyo.",R.drawable.ib));
        mList.add(new ItemInicio("Bienes Raizes","En esta sección encontraras bienes muebles e inmuebles que se encuentren a la venta, podrás ver la información del vendedor para que contactes y compres aquello que deseas. Y si eres un vendedor, podrás ofrecer productos sin costos.",R.drawable.ib));
        mList.add(new ItemInicio("Clasificados","Esta es una sección de publicidad, aquí podrás explorar y conocer negocios o trabajadores independientes que talvez aun no conozcas y te puedan interesar.",R.drawable.ib));
        mList.add(new ItemInicio("Compra y Venta","¿Tienes algo que no usas pero que si conservas en buen estado? ¡Ofrécelo aquí!, Las personas podrán ver aquello que publiques en esta sección y contactarte en caso de interés.",R.drawable.ib));
        mList.add(new ItemInicio("Desaparecidos","Creo que se explica sola, como ya has de haber captado, aquí se mostraran aquellas personas que se han desaparecido, en cada publicación podrás ver la información de quien publicó, así si sabes algo, podrás contactarlo y darle información sobre aquel desaparecido.",R.drawable.ib));
        mList.add(new ItemInicio("Directorios","¿Recuerdas aquel “librito” amarillo donde podías encontrar resto de números telefónicos? Bueno, aquí una versión virtual, encontraras los números telefónicos de muchos lugares, desde comidas rápidas, hasta farmacias.",R.drawable.ib));
        mList.add(new ItemInicio("Donaciones","Aquí podrás pedir ayuda si conoces algún caso de alguien que la necesite, podrás ver la información de quien publique para contactarlo en caso de que quieras donar algo y ayudar a quien lo necesite.",R.drawable.ib));
        mList.add(new ItemInicio("Eventos","Si quieres hacer un evento, concurso, etc. Puedes publicarlo y se verá aquí, así más gente sabrá y podrá participar o asistir a él. Entre más, mejor, ¿no?",R.drawable.ib));
        mList.add(new ItemInicio("Noticias","En esta seccion estaras informado de todo lo que pasa en nuestro bello puerto",R.drawable.ib));
        mList.add(new ItemInicio("Servicios","Aquí podrás encontrar publicaciones de trabajadores independientes o empresas que estén ofreciendo sus servicios. Como en cualquier de las otras secciones también puedes publicar, así que, si eres trabajador independiente en crecimiento, no dudes publicar.",R.drawable.ib));
        mList.add(new ItemInicio("Empleos","Si estas en busca de empleo, esta sección te interesará bastante. Aquí podrás ver las publicaciones de diversas empresas que están en busca de empleador, asegúrate de leer bien sus requerimientos y si los cumples, ¡contáctalos! Puede ser tu oportunidad.",R.drawable.ib));




        mList.add(new ItemInicio("Terminos y Condiciones","Este resumen está diseñado para ayudarte a comprender algunas de las actualizaciones clave que se aplicaron a nuestras Condiciones del Servicio (las Condiciones). Esperamos que te resulte útil como guía, pero recuerda que igualmente debes leer las nuevas Condiciones en su totalidad.\n" +
                "Le damos la bienvenida a YouTube\n" +
                "\n" +
                "En esta sección, se describe nuestra relación contigo. Incluye una descripción del Servicio, define nuestro Acuerdo y menciona el nombre de tu proveedor de servicios. Estas son las actualizaciones clave:\n" +
                "\n" +
                "    Proveedor de servicios: Ahora, tu proveedor de servicios es Google LLC.\n" +
                "    Políticas: Agregamos un vínculo a la página de Políticas, seguridad y derechos de autor, así como a las Políticas publicitarias de YouTube, todas las cuales son parte del Acuerdo. Estas son las políticas que respaldan nuestros Lineamientos de la Comunidad y consideramos que es importante hacerte notar este detalle directamente en nuestras Condiciones.\n" +
                "    Afiliados: A fin de cerciorarnos de que comprendas exactamente a quiénes nos referimos cuando hablamos de las empresas de nuestro grupo, incluimos una definición de quiénes son nuestros \"Afiliados\", en referencia a las empresas del grupo corporativo de Alphabet.\n" +
                "\n" +
                "¿Quiénes pueden usar el servicio?\n" +
                "\n" +
                "En esta sección, se establecen determinados requisitos para el uso del Servicio y se definen categorías de usuarios. Estas son las actualizaciones clave:\n" +
                "\n" +
                "    Requisitos de edad: Indicamos los requisitos de edad específicos para tu país, en línea con las políticas que aplicamos en todos los productos de Google. Además, incluimos un aviso que especifica que, si eres menor de edad en tu país, debes contar con el permiso de tu padre, madre o tutor antes de usar el Servicio.\n" +
                "    Permiso parental: Agregamos una sección en la que se explica cuál es tu responsabilidad si permites que tu hijo use YouTube.\n" +
                "    Empresas: Ahora, nuestras Condiciones indican claramente que, si usas el Servicio en representación de una organización o empresa, esa entidad acepta este Acuerdo.\n" +
                "\n" +
                "Tu uso del Servicio\n" +
                "\n" +
                "En esta sección, se explican tu derecho a usar el Servicio y las condiciones que se aplican cuando lo haces. También se explica la forma en la que modificamos el Servicio. Estas son las actualizaciones clave:\n" +
                "\n" +
                "    Cuentas de Google y canales de YouTube: Agregamos detalles acerca de cuáles son las funciones del Servicio a las que se puede acceder sin una Cuenta de Google o un canal de YouTube, y cuáles son las funciones para las que se requiere una cuenta o un canal.\n" +
                "    Tu información: No cambiamos la forma en que tratamos tu información. Para conocer más detalles sobre nuestras prácticas de privacidad, puedes revisar la Política de Privacidad y el Aviso de Privacidad de YouTube Kids. Recuerda que siempre puedes revisar la configuración de privacidad y administrar el uso de tus datos en tu Cuenta de Google.\n" +
                "    Restricciones: Actualizamos esta sección para que refleje nuestros requisitos en relación con los concursos y para incluir una prohibición sobre la manipulación de las métricas. ",R.drawable.img3));


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
