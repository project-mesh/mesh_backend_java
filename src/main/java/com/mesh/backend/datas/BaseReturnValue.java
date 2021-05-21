package com.mesh.backend.datas;

public class BaseReturnValue {
    public int error_code ;
    public BaseData data;

    public BaseReturnValue(){

    }

    public BaseReturnValue(int error_code,BaseData data){
        this.error_code = error_code;
        this.data = data;
    }
}
