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

public class PublicarServicios extends AppCompatActivity {

    ImageView imagen1_publicar_servicios;

    TextInputLayout titulo_publicar_servicios,
            descripcioncorta_publicar_servicios;

    AutoCompleteTextView necesidad_publicar_servicios;

    Button publicarfinal_servicios;

    String servi[] = new String[]{"Hoy mismo","Cuando quiera","Cada 3 años"};



    private static final int IMAGE_PICK_CODE1 = 1000;
    private static final int PERMISSON_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_servicios);




        titulo_publicar_servicios = findViewById(R.id.publicar_titulo_servicios);
        descripcioncorta_publicar_servicios = findViewById(R.id.publicar_descripcion_servicios);
        necesidad_publicar_servicios = findViewById(R.id.publicar_necesidad_servicios);
        ArrayAdapter<String> adapternece = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,servi);

        necesidad_publicar_servicios.setAdapter(adapternece);

        necesidad_publicar_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                necesidad_publicar_servicios.showDropDown();
            }
        });

        imagen1_publicar_servicios = findViewById(R.id.publicar_imagen1_servicios);
        publicarfinal_servicios = findViewById(R.id.publicar_final_servicios);


        imagen1_publicar_servicios.setOnClickListener(new View.OnClickListener() {
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




        publicarfinal_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validartitulo() | !validardescripcion() | !validarnececidad()){}
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
                    Toast.makeText(PublicarServicios.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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

            imagen1_publicar_servicios.setImageURI(data.getData());

        }



    }



    private boolean validartitulo(){
        String tituloinput = titulo_publicar_servicios.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_servicios.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_servicios.setError(null);
            return true;
        }
    }
    private boolean  validardescripcion(){
        String descripcioncortainput = descripcioncorta_publicar_servicios.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_servicios.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>740){

            descripcioncorta_publicar_servicios.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_servicios.setError(null);
            return true;
        }
    }


    private boolean validarnececidad(){
        String necesidadinput = necesidad_publicar_servicios.getAdapter().toString().trim();

        if (necesidadinput.isEmpty()) {
            necesidad_publicar_servicios.setError("" + R.string.error_descripcioncorta);
            return false;
        } else if (necesidadinput.length() > 15) {

            necesidad_publicar_servicios.setError("" + R.string.supera);
            return false;
        } else {
            necesidad_publicar_servicios.setError(null);
            return true;
        }
    }






}
