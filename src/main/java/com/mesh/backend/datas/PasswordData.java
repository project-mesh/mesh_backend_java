package com.mesh.backend.datas;

public class PasswordData {
    public String passwordDigest;
    public String passwordSalt;

    public PasswordData(String passwordDigest, String passwordSalt){
        this.passwordDigest = passwordDigest;
        this.passwordSalt = passwordSalt;
    }
    public PasswordData(){

    }
}
