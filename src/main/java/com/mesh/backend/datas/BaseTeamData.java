package com.mesh.backend.datas;

import com.mesh.backend.entity.Teams;
import com.mesh.backend.entity.Users;

import java.util.List;

public class BaseTeamData extends BaseData{
    public TeamData team;
    public BaseTeamData(Teams teams, List<Users> users, String adminName){
        super(true, "");
        team = new TeamData(teams, users, adminName);
    }
    public BaseTeamData(Teams teams, List<Users> users, String adminName, List<ProjectData> projectDataList){
        super(true, "");
        team = new TeamData(teams, users, adminName, projectDataList);
    }
}
