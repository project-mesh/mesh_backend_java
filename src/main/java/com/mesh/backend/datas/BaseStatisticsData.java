package com.mesh.backend.datas;

import java.util.ArrayList;
import java.util.Map;

public class BaseStatisticsData extends BaseData{
    public int maleUser;
    public int femaleUser;
    public int unknownUser;
    public ArrayList<UserAge> userAge;
    public ArrayList<UserLocation> userLocation;
    public int currentOnlineUser;
    public double avgTeamUser;
    public double avgTeamProject;
    public int currentTotalUser;
    public ArrayList<HistoryTotalUser> historyTotalUser;

    public BaseStatisticsData(int currentTotalUser, ArrayList<Integer> users){
        super(true, "");
        this.currentTotalUser = currentTotalUser;
        this.historyTotalUser = new ArrayList<>();
        for(int i: users){
            this.historyTotalUser.add(new HistoryTotalUser(i));
        }
    }

    public BaseStatisticsData(int maleUser, int femaleUser, int unknownUser,
                              Map<Integer, Integer> userAge, Map<String, Integer> userLocation){
        super(true, "");
        this.maleUser = maleUser;
        this.femaleUser = femaleUser;
        this.unknownUser = unknownUser;
        this.userAge = new ArrayList<>();
        this.userLocation = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : userAge.entrySet()){
            this.userAge.add(new UserAge(entry.getKey(), entry.getValue()));
        }
        for(Map.Entry<String, Integer> entry : userLocation.entrySet()){
            this.userLocation.add(new UserLocation(entry.getKey(), entry.getValue()));
        }
    }

    public BaseStatisticsData(int currentOnlineUser, double avgTeamUser, double avgTeamProject){
        super(true, "");
        this.currentOnlineUser = currentOnlineUser;
        this.avgTeamProject = avgTeamProject;
        this.avgTeamUser = avgTeamUser;
    }

}

class UserAge{
    public int age;
    public int userCount;
    public UserAge(int age, int userCount){
        this.age = age;
        this.userCount = userCount;
    }
}

class UserLocation{
    public String userLocation;
    public int userCount;
    public UserLocation(String userLocation, int userCount){
        this.userLocation = userLocation;
        this.userCount = userCount;
    }
}

class HistoryTotalUser{
    public int totalUser;
    public HistoryTotalUser(int totalUser){
        this.totalUser = totalUser;
    }
}