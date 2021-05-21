package com.mesh.backend.helper;

import com.mesh.backend.datas.PasswordData;

import java.time.LocalDateTime;
import java.util.Base64;

public class PasswordVerifier {

    public static boolean verify(String password, PasswordData passwordData){
        byte[] data = (password + passwordData.passwordSalt).getBytes();
        String passwordDigest = Base64.getEncoder().encodeToString(data);
        return passwordDigest.equals(passwordData.passwordDigest);
    }

    public static PasswordData encryption(String password){
        byte[] salt = LocalDateTime.now().toString().getBytes();
        String passwordSalt = Base64.getEncoder().encodeToString(salt);
        String rawData = password + passwordSalt;
        byte[] data = rawData.getBytes();
        String passwordDigest = Base64.getEncoder().encodeToString(data);
        return new PasswordData(passwordDigest, passwordSalt);
    }
}
