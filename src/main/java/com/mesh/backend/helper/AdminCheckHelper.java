package com.mesh.backend.helper;

import com.mesh.backend.datas.UserData;

public class AdminCheckHelper {
    public static boolean checkIfAdmin(UserData requestData){
        return requestData.username.equals("root") && requestData.password.equals("mesh");
    }
}
