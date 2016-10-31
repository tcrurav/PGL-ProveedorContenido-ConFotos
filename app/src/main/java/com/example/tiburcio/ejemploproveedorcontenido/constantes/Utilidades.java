package com.example.tiburcio.ejemploproveedorcontenido.constantes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tiburcio on 30/10/2016.
 */

public class Utilidades {

    static public void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img) throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);
    }

    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {
        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
    }

    public static void mostrarImagen(Context contexto, String imagenFichero, ImageView mImagenView, Resources resources, int ancho, int alto){
        File filePath = contexto.getFileStreamPath(imagenFichero);
        if(filePath.exists()) {
            UtilidadesImagenes.loadBitmap(
                    filePath.toString(),
                    mImagenView, resources, ancho, alto);
        }
    }


    private static void storeImage(byte[] bitmapdata, Context contexto, String fileName) throws IOException {
        Bitmap bitmap = decodeFileDeByteArray(bitmapdata,400,400);
        storeImage(bitmap,contexto,fileName);
    }

    public static void guardarImagen(byte[] bitmapdata, Context contexto, String fileName) throws IOException {
        if (bitmapdata!=null){
            storeImage(bitmapdata, contexto, fileName);
        }
    }

    public static Bitmap decodeFileDeByteArray(byte[] bitmapdata, int reqWidth, int reqHeight){

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = 1000;
        options.inTargetDensity = 1000;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata.length, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
