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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pacificblack.informatebuenaventura.R;

public class PublicarAdopcion extends AppCompatActivity {

    ImageView imagen1_publicar_adopcion,imagen2_publicar_adopcion,imagen3_publicar_adopcion,imagen4_publicar_adopcion;
    TextInputLayout titulo_publicar_adopcion,descripcioncorta_publicar_adopcion,descripcion1_publicar_adopcion,descripcion2_publicar_adopcion;

    Button publicarfinal_adopcion;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int IMAGE_PICK_CODE2 = 1002;
    private static final int IMAGE_PICK_CODE3 = 1003;
    private static final int IMAGE_PICK_CODE4 = 1004;

    private static final int PERMISSON_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_adopcion);

        imagen1_publicar_adopcion = findViewById(R.id.publicar_imagen1_adopcion);
        imagen2_publicar_adopcion = findViewById(R.id.publicar_imagen2_adopcion);
        imagen3_publicar_adopcion = findViewById(R.id.publicar_imagen3_adopcion);
        imagen4_publicar_adopcion = findViewById(R.id.publicar_imagen4_adopcion);


        titulo_publicar_adopcion = findViewById(R.id.publicar_titulo_adopcion);
        descripcioncorta_publicar_adopcion = findViewById(R.id.publicar_descripcioncorta_adopcion);
        descripcion1_publicar_adopcion = findViewById(R.id.publicar_descripcion1_adopcion);
        descripcion2_publicar_adopcion = findViewById(R.id.publicar_descripcion2_adopcion);

        publicarfinal_adopcion = findViewById(R.id.publicar_final_adopcion);

        publicarfinal_adopcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validartitulo() | !validardescripcioncorta() | !validardescripcion1() | !validardescripcion2()){
                    return;
                }

                String resultado = "Titulo: "+titulo_publicar_adopcion.getEditText().getText().toString();
                resultado += "\n";
                resultado += "Corta: "+descripcioncorta_publicar_adopcion.getEditText().getText().toString();
                resultado += "\n";
                resultado += "Total la 1: "+descripcion1_publicar_adopcion.getEditText().getText().toString();
                resultado += "\n";
                resultado += "Total la 2: "+descripcion2_publicar_adopcion.getEditText().getText().toString();


                Toast.makeText(PublicarAdopcion.this, resultado ,Toast.LENGTH_LONG).show();


            }
        });




        imagen1_publicar_adopcion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen();
                }
            }
        });



        imagen2_publicar_adopcion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen2();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen2();
                }
            }
        });


        imagen3_publicar_adopcion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen3();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen3();
                }
            }
        });





        imagen4_publicar_adopcion.setOnClickListener(new View.OnClickListener() {
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
                        seleccionarimagen4();
                    }

                }else{
                    //para android masmelos
                    seleccionarimagen4();
                }
            }
        });



    }


    private boolean validartitulo(){
        String tituloinput = titulo_publicar_adopcion.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_adopcion.setError("Debe ingresar un titulo para la publicación");
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_adopcion.setError("El titulo es demasiado largo");
            return false;
        }
        else {
            titulo_publicar_adopcion.setError(null);
            return true;
        }
    }


    private boolean validardescripcioncorta(){

        String descripcioncortainput = descripcioncorta_publicar_adopcion.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_adopcion.setError("Debe ingresar un descripcion corta para la publicación");
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_adopcion.setError("La descripcion es demasiada larga");
            return false;
        }
        else {
            descripcioncorta_publicar_adopcion.setError(null);
            return true;
        }

    }




    private boolean validardescripcion1(){
        String descripcion1input = descripcion1_publicar_adopcion.getEditText().getText().toString().trim();

        if (descripcion1input.isEmpty()){
            descripcion1_publicar_adopcion.setError("Debe ingresar un descripcion  para la publicación");
            return false;
        }
        else if(descripcion1input.length()>150){

            descripcion1_publicar_adopcion.setError("La descripcion es demasiada larga");
            return false;
        }
        else {
            descripcion1_publicar_adopcion.setError(null);
            return true;
        }
    }
    private boolean validardescripcion2(){

        String descripcion2input = descripcion2_publicar_adopcion.getEditText().getText().toString().trim();

        if (descripcion2input.isEmpty()){
            descripcion2_publicar_adopcion.setError("Debe ingresar un descripcion  para la publicación");
            return false;
        }
        else if(descripcion2input.length()>150){

            descripcion2_publicar_adopcion.setError("La descripcion es demasiada larga");
            return false;
        }
        else {
            descripcion2_publicar_adopcion.setError(null);
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  //Permiso autorizado
                    seleccionarimagen();
                    seleccionarimagen2();
                    seleccionarimagen3();
                    seleccionarimagen4();

                }
                else{
                    //Permiso denegado
                    Toast.makeText(PublicarAdopcion.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

                }
            }

        }
    }

    public void seleccionarimagen() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }

    public void seleccionarimagen2() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE2);

    }

    public void seleccionarimagen3() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE3);

    }
    public void seleccionarimagen4() {

        //intent para seleccionar imagen
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE4);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){

            imagen1_publicar_adopcion.setImageURI(data.getData());

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE2){

            imagen2_publicar_adopcion.setImageURI(data.getData());

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE3){

            imagen3_publicar_adopcion.setImageURI(data.getData());

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE4){

            imagen4_publicar_adopcion.setImageURI(data.getData());

        }


    }
}

