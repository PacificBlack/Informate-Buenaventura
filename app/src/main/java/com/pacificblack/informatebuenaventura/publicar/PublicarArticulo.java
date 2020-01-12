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

public class PublicarArticulo extends AppCompatActivity {

    ImageView imagen1_publicar_comprayventa,
            imagen2_publicar_comprayventa,
            imagen3_publicar_comprayventa;

    TextInputLayout titulo_publicar_comprayventa,
            descripcioncorta_publicar_comprayventa,
            descripcion_publicar_comprayventa,
            descripcionextra_publicar_comprayventa,
            precio_publicar_comprayventa,
            ubicacion_publicar_comprayventa,
            cantidad_publicar_comprayventa,
            contacto_publicar_comprayventa;

    Button publicarfinal_comprayventa;

    private static final int IMAGE_PICK_CODE1 = 1000;
    private static final int IMAGE_PICK_CODE2 = 1002;
    private static final int IMAGE_PICK_CODE3 = 1003;

    private static final int PERMISSON_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_articulo);

        titulo_publicar_comprayventa = findViewById(R.id.publicar_titulo_comprayventa);
        descripcioncorta_publicar_comprayventa = findViewById(R.id.publicar_descripcioncorta_comprayventa);
        descripcion_publicar_comprayventa = findViewById(R.id.publicar_descripcion_comprayventa);
        descripcionextra_publicar_comprayventa = findViewById(R.id.publicar_descripcionextra_comprayventa);
        precio_publicar_comprayventa = findViewById(R.id.publicar_precio_comprayventa);
        ubicacion_publicar_comprayventa = findViewById(R.id.publicar_ubicacion_comprayventa);
        cantidad_publicar_comprayventa = findViewById(R.id.publicar_cantidad_comprayventa);
        contacto_publicar_comprayventa = findViewById(R.id.publicar_contacto_comprayventa);
        publicarfinal_comprayventa = findViewById(R.id.publicar_final_comprayventa);

        imagen1_publicar_comprayventa = findViewById(R.id.publicar_imagen1_comprayventa);
        imagen2_publicar_comprayventa = findViewById(R.id.publicar_imagen2_comprayventa);
        imagen3_publicar_comprayventa = findViewById(R.id.publicar_imagen3_comprayventa);


        publicarfinal_comprayventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        !validartitulo() | !validardescripcioncorta()| !validardescripcion()| !validardescripcionextra()|
                                !validarprecio()| !validarubicacion()| !validarcantidad()| ! validarcontacto()){

                    return;
                }

                String resultado = "Listo:  "+titulo_publicar_comprayventa.getEditText().getText().toString();

            }
        });


        imagen1_publicar_comprayventa.setOnClickListener(new View.OnClickListener() {
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



        imagen2_publicar_comprayventa.setOnClickListener(new View.OnClickListener() {
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


        imagen3_publicar_comprayventa.setOnClickListener(new View.OnClickListener() {
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



    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSON_CODE: {

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //Permiso autorizado
                    seleccionarimagen1();
                    seleccionarimagen2();
                    seleccionarimagen3();

                }
                else{
                    //Permiso denegado
                    Toast.makeText(PublicarArticulo.this,"Debe otorgar permisos de almacenamiento",Toast.LENGTH_LONG);

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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE1){

            imagen1_publicar_comprayventa.setImageURI(data.getData());

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE2){

            imagen2_publicar_comprayventa.setImageURI(data.getData());

        }
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE3){

            imagen3_publicar_comprayventa.setImageURI(data.getData());

        }


    }





    private boolean validartitulo(){
        String tituloinput = titulo_publicar_comprayventa.getEditText().getText().toString().trim();

        if (tituloinput.isEmpty()){
            titulo_publicar_comprayventa.setError(""+R.string.error_titulo);
            return false;
        }
        else if(tituloinput.length()>120){

            titulo_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            titulo_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcioncorta(){
        String descripcioncortainput = descripcioncorta_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcioncortainput.isEmpty()){
            descripcioncorta_publicar_comprayventa.setError(""+R.string.error_descripcioncorta);
            return false;
        }
        else if(descripcioncortainput.length()>150){

            descripcioncorta_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcioncorta_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcion(){
        String descripcioninput = descripcion_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcioninput.isEmpty()){
            descripcion_publicar_comprayventa.setError(""+R.string.error_descripcion1);
            return false;
        }
        else if(descripcioninput.length()>740){

            descripcion_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcion_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validardescripcionextra(){
        String descripcionextrainput = descripcionextra_publicar_comprayventa.getEditText().getText().toString().trim();

        if (descripcionextrainput.isEmpty()){
            descripcionextra_publicar_comprayventa.setError(""+R.string.error_descripcion2);
            return false;
        }
        else if(descripcionextrainput.length()>740){

            descripcionextra_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            descripcionextra_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarprecio(){
        String precioinput = precio_publicar_comprayventa.getEditText().getText().toString().trim();

        if (precioinput.isEmpty()){
            precio_publicar_comprayventa.setError(""+R.string.error_precio);
            return false;
        }
        else if(precioinput.length()>15){

            precio_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            precio_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarubicacion(){

        String ubicacioninput = ubicacion_publicar_comprayventa.getEditText().getText().toString().trim();

        if (ubicacioninput.isEmpty()){
            ubicacion_publicar_comprayventa.setError(""+R.string.error_ubicacion);
            return false;
        }
        else if(ubicacioninput.length()>150){

            ubicacion_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            ubicacion_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcantidad(){

        String cantidadinput = cantidad_publicar_comprayventa.getEditText().getText().toString().trim();

        if (cantidadinput.isEmpty()){
            cantidad_publicar_comprayventa.setError(""+R.string.error_cantidad);
            return false;
        }
        else if(cantidadinput.length()>150){

            cantidad_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            cantidad_publicar_comprayventa.setError(null);
            return true;
        }
    }
    private boolean validarcontacto(){
        String contactoinput = contacto_publicar_comprayventa.getEditText().getText().toString().trim();

        if (contactoinput.isEmpty()){
            contacto_publicar_comprayventa.setError(""+R.string.error_contacto);
            return false;
        }
        else if(contactoinput.length()>150){

            contacto_publicar_comprayventa.setError(""+R.string.supera);
            return false;
        }
        else {
            contacto_publicar_comprayventa.setError(null);
            return true;
        }
    }


}
