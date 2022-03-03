package com.fromsys;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.*;

import com.fromsys.services.AttendanceRecordDao;
import com.fromsys.services.AttendanceRecordService;
import com.fromsys.services.Employee;
import com.fromsys.services.EmployeeService;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import static com.fromsys.services.AttendanceRecordDao.*;


public class App extends JFrame implements Runnable, ThreadFactory, ActionListener {
    private Executor exeThreadExecutor = Executors.newSingleThreadExecutor(this);

    JMenuBar mbarMain;
    JMenu menuMain;
    JMenuItem mitemScan, mitemGen, mitemExit;

    private Webcam objWebcam = null;
    private WebcamPanel pnlWebcam = null;
    private JTextArea txtResultField = null;

    Dimension[] nonStandardResolutions = new Dimension[] {
            WebcamResolution.PAL.getSize(),
            new Dimension(640, 480)
    };

    public App() {
        super();

        this.setLayout(new BoxLayout(getContentPane(),BoxLayout.PAGE_AXIS));
        this.setTitle("Alphabet | timeMeIn");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension size = new Dimension(432, 243);

        objWebcam = Webcam.getDefault();
        objWebcam.setCustomViewSizes(nonStandardResolutions);
        objWebcam.setViewSize(WebcamResolution.PAL.getSize());

        pnlWebcam = new WebcamPanel(objWebcam);
        pnlWebcam.setPreferredSize(size);
        pnlWebcam.setFPSDisplayed(true);

        txtResultField = new JTextArea();
        txtResultField.setEditable(false);
        txtResultField.setPreferredSize(size);

        add(pnlWebcam);
        add(txtResultField);

        this.pack();
        this.setVisible(true);
        exeThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        /* Waits for a matching frame to have Luminance Values to match those of a QR code
         * It attempts to decode every possible frame, and then displays it.
         */
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Result msgBuffer = null;
            BufferedImage imgBuffer = null;
            if (objWebcam.isOpen()) {
                if ((imgBuffer = objWebcam.getImage()) == null) {
                    continue;
                }
                LuminanceSource objLumiValSource = new BufferedImageLuminanceSource(imgBuffer);
                BinaryBitmap objQRBitmap = new BinaryBitmap(new HybridBinarizer(objLumiValSource));
                try {
                    msgBuffer = new MultiFormatReader().decode(objQRBitmap);
                } catch (NotFoundException e) {
                    // pass through the loop, it means there is no QR code in image
                }
            } // if (webcam.isOpen())

            // Display string if there is a QR Code present:
            if (msgBuffer != null) {
                SimpleDateFormat sdfDateTimeNow = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                String strPrompt = " ";
                UUID uuidEmployee = UUID.fromString(msgBuffer.toString());
                Employee objScannedEmployee = EmployeeService.getEmployee(uuidEmployee).get(0);
                if (queryHasRecord(uuidEmployee)) {
                    if (queryIsLoggedin(uuidEmployee)) {
                        queryLogout(uuidEmployee);
                        queryUpdateHours(uuidEmployee);
                        System.out.println("Logged Out.");
                        strPrompt = String.format("Logged out.\nEmployee Name:%s\nDate & Time: %s",
                                                   objScannedEmployee.getName(),
                                                   sdfDateTimeNow.format(new java.util.Date()));
                        txtResultField.setText(strPrompt);
                    } else if (queryIsLoggedout(uuidEmployee)) {
                        queryLogin(uuidEmployee);
                        System.out.println("Logged in.");
                        strPrompt = String.format("Logged in.\nEmployee Name:%s\nDate & Time: %s",
                                objScannedEmployee.getName(),
                                sdfDateTimeNow.format(new java.util.Date()));
                        txtResultField.setText(strPrompt);
                    }
                } else {
                    queryLogin(uuidEmployee);
                    System.out.println("Logged in.");
                    strPrompt = String.format("Logged in.\nEmployee Name:%s\nDate & Time: %s",
                            objScannedEmployee.getName(),
                            sdfDateTimeNow.format(new java.util.Date()));
                    txtResultField.setText(strPrompt);
                }

                try {
                    Thread.sleep(2500); // 2.5 seconds
                    txtResultField.setText("Welcome | timeMeIn");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } while (true); // do..while(true)
    } //public void run()

    @Override
    public Thread newThread(Runnable objRunnable) {
        Thread objThread = new Thread(objRunnable, "example-runner");
        objThread.setDaemon(true);
        return objThread;
    } // public Thread newThread(Runnable objRunnable)

    @Override
    public void actionPerformed(ActionEvent objEvent) {

        if(objEvent.getSource() == mitemScan) {
            System.out.println("Testing Scan");
        } else if (objEvent.getSource() == mitemGen) {
            System.out.println("Testing Generation");
        } else if (objEvent.getSource() == mitemExit) {
            System.exit(0);
        }
    } // public void actionPerformed(ActionEvent objEvent)

    public static void main(String[] args) {
        new App();
    } // public static void main(String[] args)

} // public class App extends JFrame implements Runnable, ThreadFactory