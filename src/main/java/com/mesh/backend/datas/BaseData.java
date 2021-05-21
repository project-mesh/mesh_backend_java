package com.mesh.backend.datas;

public class BaseData {

    public boolean isSuccess;
    public String msg;

    public BaseData (boolean isSuccess, String msg){
        this.isSuccess = isSuccess;
        this.msg = msg;
    }
    public BaseData(){

    }
    public BaseData(String msg){
        this.isSuccess = false;
        this.msg = msg;
    }
}
