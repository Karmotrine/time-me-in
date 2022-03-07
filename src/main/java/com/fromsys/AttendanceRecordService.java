package com.fromsys;

import java.util.UUID;

public class AttendanceRecordService {

    public static void loginService(UUID tEmployeeId) {
        AttendanceRecordDao.queryLogin(tEmployeeId);
    }

    public static void logoutService(UUID tEmployeeId) {
        AttendanceRecordDao.queryLogout(tEmployeeId);
        AttendanceRecordDao.queryUpdateHours(tEmployeeId);
    }

} //public class AttendanceRecordService
