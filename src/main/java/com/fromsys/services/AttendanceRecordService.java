package com.fromsys.services;

/* File process:
 *  1. POJO/Bean => JSON => argon2(JSON) => QR
 *  2. QR => JSON => argon2.verify(JSON) => POJO/Bean
 */

public class AttendanceRecordService {

    public static void loginService(int tEmployeeId) {
        //pass
    }

    public static void logoutService(int tEmployeeId) {
        //pass
    }

} //public class AttendanceRecordService
