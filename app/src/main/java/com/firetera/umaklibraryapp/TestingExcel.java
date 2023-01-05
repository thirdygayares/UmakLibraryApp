package com.firetera.umaklibraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firetera.umaklibraryapp.Model.ExportBookModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestingExcel extends AppCompatActivity {

    ArrayList<ExportBookModel> exportBookModels = new ArrayList<>();
    private static final int STORAGE_PERMISSION_CODE = 101;

    Button btn_export;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_testing_excel);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        sampleData();

        //init xml
        initXml();

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createXlsx();
            }
        });

    }

    private void initXml() {
        btn_export = findViewById(R.id.btn_export);
    }

    private void sampleData() {
;

        ArrayList<String> BOOK_ID = new ArrayList<>();
        ArrayList<String> TITLE = new ArrayList<>();
        ArrayList<String> IMAGE_URL = new ArrayList<>();
        ArrayList<String> AUTHOR = new ArrayList<>();
        ArrayList<String> CATEGORY = new ArrayList<>();


        firestore.collection("BOOKS").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){

                                    exportBookModels.add(new ExportBookModel(documentSnapshot.getId(), documentSnapshot.get("bookdetails.TITLE").toString(), documentSnapshot.get("author").toString(), documentSnapshot.get("category").toString(), documentSnapshot.get("image_URL").toString(), documentSnapshot.get("bookdetails.ADDED_BY").toString()));


                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", e.getMessage());
                    }
                });




    }

    private void createXlsx() {
        try {
            String strDate = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss", Locale.getDefault()).format(new Date());
            File root = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "FileExcel");
            if (!root.exists())
                root.mkdirs();
            File path = new File(root, "/" + strDate + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(path);

            XSSFCellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderTop(BorderStyle.MEDIUM);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderRight(BorderStyle.MEDIUM);
            headerStyle.setBorderLeft(BorderStyle.MEDIUM);

            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setBold(true);
            headerStyle.setFont(font);

            XSSFSheet sheet = workbook.createSheet("HERONS LIBRARY BOOK Data");
            XSSFRow row = sheet.createRow(0);

            XSSFCell cell = row.createCell(0);
            cell.setCellValue("BOOK ID");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(1);
            cell.setCellValue("TITLE");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(2);
            cell.setCellValue("AUTHOR");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(3);
            cell.setCellValue("IMAGE_URL");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(4);
            cell.setCellValue("CATEGORY");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(5);
            cell.setCellValue("ADDED BY");
            cell.setCellStyle(headerStyle);

            for (int i = 0; i < exportBookModels.size(); i++) {
                row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(exportBookModels.get(i).getBOOK_ID());
                sheet.setColumnWidth(0, (exportBookModels.get(i).getBOOK_ID().length() + 30) * 256);

                cell = row.createCell(1);
                cell.setCellValue(exportBookModels.get(i).getTITLE());
                sheet.setColumnWidth(1, (exportBookModels.get(i).getTITLE().length() + 30) * 256);

                cell = row.createCell(2);
                cell.setCellValue(exportBookModels.get(i).getIMAGE_URL());
                sheet.setColumnWidth(2, (exportBookModels.get(i).getIMAGE_URL().length() + 30) * 256);


                cell = row.createCell(3);
                cell.setCellValue(exportBookModels.get(i).getAUTHOR());
                sheet.setColumnWidth(3, (exportBookModels.get(i).getAUTHOR().length() + 30) * 256);

                cell = row.createCell(4);
                cell.setCellValue(exportBookModels.get(i).getCATEGORY());
                sheet.setColumnWidth(4, (exportBookModels.get(i).getCATEGORY().length() + 30) * 25);

                cell = row.createCell(5);
                cell.setCellValue(exportBookModels.get(i).getADDEDBY());
                sheet.setColumnWidth(5, (exportBookModels.get(i).getADDEDBY().length() + 30) * 25);

            }

            workbook.write(outputStream);
            outputStream.close();
            Toast.makeText(TestingExcel.this, "SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(TestingExcel.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(TestingExcel.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(TestingExcel.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(TestingExcel.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TestingExcel.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}