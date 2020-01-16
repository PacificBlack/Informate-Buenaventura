package com.pacificblack.informatebuenaventura.publicar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.R;

public class PublicarEventos extends AppCompatActivity {

    ImageView imagen1_publicar_eventos;

    TextInputLayout titulo_publicar_eventos,
            descripcioncorta_publicar_eventos,lugar_publicar_eventos;

    Button publicarfinal_eventos;


    private static final int IMAGE_PICK_CODE1 = 1000;
    private static final int PERMISSON_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_eventos);

        titulo_publicar_eventos = findViewById(R.id.publicar_titulo_eventos);
        descripcioncorta_publicar_eventos = findViewById(R.id.publicar_descripcion_eventos);
        lugar_publicar_eventos = findViewById(R.id.publicar_lugar_eventos);
        imagen1_publicar_eventos = findViewById(R.id.publicar_imagen1_eventos);
        publicarfinal_eventos = findViewById(R.id.publicar_final_eventos);


        imagen1_publicar_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        //permiso denegado
                        String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //Mostrar emergente del menu
                        requestPermissions(permisos,PERMISSON_CODE);
                    }else {
                        //permiso ya obtenido
                        seleccionarimagen1();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen1();
                }
            }
        });




        publicarfinal_eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcion() | !validarlugar()){}
                return; }



        });





    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permiso autorizado
                    seleccionarimagen1();


                }
                else{
                    //Permiso denegado
                    Toast.makeText(PublicarEventos.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

                }
            }

        }
    }

    public void seleccionarimagen1() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE1);

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE1){

            imagen1_publicar_eventos.setImageURI(data.getData());

        }



    }



    private boolean validartitulo(){
        String tituloinput = titulo_publicar_eventos.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_eventos.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_eventos.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_eventos.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_publicar_eventos.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_eventos.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_publicar_eventos.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_eventos.setError(null);
            return true;
        }
    }


    private boolean validarlugar(){
        String lugarinput = lugar_publicar_eventos.getEditText().toString().trim();

        if (lugarinput.isEmpty()) {
            lugar_publicar_eventos.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (lugarinput.length() > 15) {

            lugar_publicar_eventos.setError("" + R.string.supera);
            return false;
        } else {
            lugar_publicar_eventos.setError(null);
            return true;
        }
    }





}
