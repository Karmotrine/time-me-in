package com.fromsys.services;

import com.google.gson.Gson;

/* File process:
 *  1. POJO/Bean => JSON => argon2(JSON) => QR
 *  2. QR => JSON => argon2.verify(JSON) => POJO/Bean
 */

public class EmployeeService {

    public static String convertJSONtoQR(Employee tobjEmployee) {
        JSONObject jobjEmployee = new JSONObject(tobjEmployee);
        return jobjEmployee.toString();
    }

    public static Employee convertQRtoJSON(String tstrEmployee)  {
        JSONObject jobjParsed = new JSONObject(tstrEmployee);
        return jobjParsed;
        // Use Gson since json => POJO is hard using org.json
    }

} // public class EmployeeService
