package com.fromsys.services;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.File;

/* File process:
 *  1. POJO/Bean => JSON => argon2(JSON) => QR
 *  2. QR => JSON => argon2.verify(JSON) => POJO/Bean
 */

public class EmployeeService {

    public static void generateQR(int tEmployeeId, String tFilename) {
        String strQrdata = String.valueOf(tEmployeeId);
        String strPath = "D:/Alpabhetdemo/" + tFilename + ".png";
        try {
            QRCodeWriter qrcwInstance = new QRCodeWriter();
            BitMatrix bmInstance = qrcwInstance.encode(strQrdata, BarcodeFormat.QR_CODE, 300, 300);
            MatrixToImageWriter.writeToPath(bmInstance, "PNG", new File(strPath).toPath());
        } catch (Exception objException) {
            objException.printStackTrace();
        }
    }

    public static int decodeQR(String tstrEmployeeId)  {
        int intEmployeeId = Integer.parseInt(tstrEmployeeId);
        return intEmployeeId;
    }

} // public class EmployeeService
