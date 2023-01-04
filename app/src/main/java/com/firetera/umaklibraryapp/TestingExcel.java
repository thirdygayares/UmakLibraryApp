package com.firetera.umaklibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firetera.umaklibraryapp.Model.ExportBookModel;

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

        BOOK_ID.add("121323");
        BOOK_ID.add("1232r1323");
        BOOK_ID.add("34121323");

        TITLE.add("121323");
        TITLE.add("1232r1323");
        TITLE.add("34121323");

        AUTHOR.add("121323");
        AUTHOR.add("1232r1323");
        AUTHOR.add("34121323");

        CATEGORY.add("121323");
        CATEGORY.add("1232r1323");
        CATEGORY.add("34121323");

        IMAGE_URL.add("121323");
        IMAGE_URL.add("1232r1323");
        IMAGE_URL.add("34121323");

        for(int i=0; i<TITLE.size(); i++){
            exportBookModels.add(new ExportBookModel(BOOK_ID.get(i), TITLE.get(i),AUTHOR.get(i), CATEGORY.get(i), IMAGE_URL.get(i)));
        }



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
            cell.setCellValue("IMAGE_URL");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(3);
            cell.setCellValue("AUTHOR");
            cell.setCellStyle(headerStyle);

            cell = row.createCell(4);
            cell.setCellValue("CATEGORY");
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
                sheet.setColumnWidth(4, (exportBookModels.get(i).getCATEGORY().length() + 30) * 256);

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