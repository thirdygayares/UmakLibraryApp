package com.firetera.umaklibraryapp;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ActivityLogs extends AppCompatActivity {

    //call xml
    TextView txt_activity;
    ImageView img_qr;

    //call bitmap
    Bitmap qrCode;
    Bitmap saveQr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        //initiate xml
        initXml();


        //activity Text
        Homepage homepage = new Homepage();
        txt_activity.setText(homepage.activityLog);


        //generating qr code
        generateQrCodeMethod();


    }


    private void generateQrCodeMethod() {
        Homepage homepage = new Homepage();
        String random =  homepage.code;
        qrCode = createBitmap(random);

        img_qr.setImageBitmap(qrCode);

        // Log.d(TAG, saveQr.);
        //convertBitmapQrCode = getImageUri(RegisterBook.this,saveQr);
        //Log.d(TAG, convertBitmapQrCode.toString());
    }

    private Bitmap createBitmap(String random) {
        BitMatrix result = null;
        try{
            result = new MultiFormatWriter().encode(random, BarcodeFormat.QR_CODE, 300,300, null);

        }catch (WriterException e){
            e.printStackTrace();
            return  null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];

        for(int x = 0; x < height; x++){
            int offset = x * width;
            for(int k = 0; k<width; k++){
                pixels[offset + k] = result.get(k,x) ? BLACK : WHITE;
            }
        }
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels, 0, width ,0 ,0 ,width, height);
        return myBitmap;
    }

    private void initXml() {
        img_qr = findViewById(R.id.img_qr);
        txt_activity = findViewById(R.id.txt_activity);
    }
}