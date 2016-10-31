package com.example.tiburcio.ejemploproveedorcontenido;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tiburcio.ejemploproveedorcontenido.ciclo.CicloActivity;
import com.example.tiburcio.ejemploproveedorcontenido.constantes.Utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_FROM_GALLERY = 2;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.buttonIrALosCiclos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CicloActivity.class);
                startActivity(intent);
            }
        });

        imageView = (ImageView) findViewById(R.id.image_view_mi_foto);

        Button buttonCamara = (Button) findViewById(R.id.buttonCamara);
        buttonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }
        });

        Button buttonGaleria = (Button) findViewById(R.id.buttonImagenDeGaleria);
        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirFotoDeGaleria();
            }
        });

        try {
            Utilidades.loadImageFromStorage(getApplicationContext(), "imagen.jpg", imageView);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No existe la imagen", Toast.LENGTH_LONG);
        }

    }

    void elegirFotoDeGaleria(){
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_FROM_GALLERY);
    }

    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(getApplicationContext(), "Error: ¿Tiene cámara su móvil?", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE:
                Log.i("resultCode mierda","resultCode:"+resultCode+" "+ RESULT_OK + " " + RESULT_CANCELED);
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    try {
                        Utilidades.storeImage(bitmap, getApplicationContext(), "imagen.jpg");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Se ha producido un error al guardar la imagen", Toast.LENGTH_LONG).show();
                    }
                    return;
                } else {
                    //EL usuario canceló la operación de tomar foto
                }
                break;
            case REQUEST_IMAGE_FROM_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                    try {
                        Utilidades.storeImage(((BitmapDrawable) imageView.getDrawable()).getBitmap(), getApplicationContext(), "imagen.jpg");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Se ha producido un error al guardar la imagen", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //EL usuario canceló la operación de tomar foto
                }
                break;
        }

    }

/*    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = getFilesDir();
        //File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
*/

}
