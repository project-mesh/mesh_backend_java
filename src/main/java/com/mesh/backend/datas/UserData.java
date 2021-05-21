package com.mesh.backend.datas;

import com.mesh.backend.entity.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserData extends BaseData{

    public String username;
    public String nickname;
    public int gender;
    public int status;
    public String address;
    public String description;
    public String birthday;
    public String avatar;
    public String role;
    public String token;
    public String password;
    public Preference preference;
    public ArrayList<TeamData> teams;

    public UserData(Users users, String role, int preferenceTeam){
        super(true, "");
        this.preference = new Preference(users.getColorPreference(),
                users.getRevealedPreference(), users.getLayoutPreference(),
                preferenceTeam);
        this.username = users.getEmail();
        this.nickname = users.getNickname();
        this.gender = users.getGender();
        this.status = users.getStatus();
        this.address = users.getAddress();
        this.description = users.getDescription();
        this.birthday = users.getBirthday().toLocalDate().toString();
        this.avatar = users.getAvatar();
        this.role = role;
        this.teams = new ArrayList<>();
    }

    public UserData(Users users){
        this.username = users.getEmail();
        this.nickname = users.getNickname();
        this.gender = users.getGender();
        this.status = users.getStatus();
        this.address = users.getAddress();
        this.description = users.getDescription();
        this.birthday = users.getBirthday().toLocalDate().toString();
        this.avatar = users.getAvatar();
        this.role = "user";
    }

    public UserData(Users users, String role, int preferenceTeam, ArrayList<TeamData> teams){
        super(true, "");
        this.preference = new Preference(users.getColorPreference(),
                users.getRevealedPreference(), users.getLayoutPreference(),
                preferenceTeam);
        this.username = users.getEmail();
        this.nickname = users.getNickname();
        this.gender = users.getGender();
        this.status = users.getStatus();
        this.address = users.getAddress();
        this.description = users.getDescription();
        this.birthday = users.getBirthday().toLocalDate().toString();
        this.avatar = users.getAvatar();
        this.role = role;
        this.teams = teams;
    }

    public UserData(){
        super(true, "");
    }
}

class Preference{
    public String preferenceShowMode;
    public String preferenceColor;
    public String preferenceLayout;
    public int preferenceTeam;


    public Preference (String preferenceColor, String preferenceShowMode,
                       String preferenceLayout, int preferenceTeam){
        this.preferenceColor = preferenceColor;
        this.preferenceLayout = preferenceLayout;
        this.preferenceTeam = preferenceTeam;
        this.preferenceShowMode = preferenceShowMode;
    }

    public Preference(){

    }
}
