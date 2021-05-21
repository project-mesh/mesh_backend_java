package com.mesh.backend.datas;

import com.mesh.backend.entity.Users;

import java.util.ArrayList;

public class BaseUserQueryData extends BaseData {
    public ArrayList<UserData> users;

    public BaseUserQueryData(ArrayList<Users> users){
        super(true, "");
        this.users = new ArrayList<>();
        for(Users user: users){
            this.users.add(new UserData(user));
        }
    }

    public BaseUserQueryData(boolean isUserData, ArrayList<UserData> userData){
        super(true, "");
        this.users = isUserData? userData : null;
    }
}
