package com.mesh.backend.datas;

import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.entity.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamData{
    public int teamId;
    public LocalDate createTime;
    public String teamName;
    public String adminName;
    public ArrayList<MemberInfo> members;
    public ArrayList<ProjectInfo> teamProjects;


    public TeamData(Teams teams, String adminName){
        this.teamId = teams.getId();
        this.createTime = teams.getCreatedTime().toLocalDate();
        this.teamName = teams.getName();
        this.adminName = adminName;
    }

    public TeamData(Teams teams, List<Users> users, String adminName){
        this.teamId = teams.getId();
        this.createTime = teams.getCreatedTime().toLocalDate();
        this.teamName = teams.getName();
        this.adminName = adminName;
        this.members = new ArrayList<>();
        for (Users user:users) {
            members.add(new MemberInfo(user.getEmail(), user.getNickname()));
        }
        this.teamProjects = new ArrayList<>();
    }

    public TeamData(Teams teams, List<Users> users, String adminName, List<ProjectData> projectDataList){
        this.teamId = teams.getId();
        this.createTime = teams.getCreatedTime().toLocalDate();
        this.teamName = teams.getName();
        this.adminName = adminName;
        this.members = new ArrayList<>();
        for (Users user:users) {
            members.add(new MemberInfo(user.getEmail(), user.getNickname()));
        }
        this.teamProjects = new ArrayList<>();
        for(ProjectData projectData: projectDataList){
            teamProjects.add(new ProjectInfo(projectData.projectName, projectData.projectId, projectData.adminName));
        }
    }

    public TeamData(){

    }
}

class MemberInfo{
    public String username;
    public String nickname;

    public MemberInfo(String username, String nickname){
        this.nickname = nickname;
        this.username = username;
    }
}

class ProjectInfo{
    public String projectName;
    public int projectId;
    public String adminName;

    public ProjectInfo(String projectName, int projectId, String adminName){
        this.adminName = adminName;
        this.projectId = projectId;
        this.projectName = projectName;
    }
}