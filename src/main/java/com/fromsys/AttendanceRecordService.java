package com.fromsys;

/* File process:
 *  1. POJO/Bean => JSON => argon2(JSON) => QR
 *  2. QR => JSON => argon2.verify(JSON) => POJO/Bean
 */

import java.util.UUID;

public class AttendanceRecordService {

    public static void loginService(UUID tEmployeeId) {
        AttendanceRecordDao.queryLogin(tEmployeeId);
    }

    public static void logoutService(UUID tEmployeeId) {
        AttendanceRecordDao.queryLogout(tEmployeeId);
    }

} //public class AttendanceRecordService
