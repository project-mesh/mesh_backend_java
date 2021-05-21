package com.mesh.backend.datas;

import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectData {
    public int projectId;
    public LocalDate createTime;
    public String projectName;
    public String adminName;
    public boolean isPublic;
    public ArrayList<MemberInfo> members;

    public ProjectData(Projects projects, List<Users> users, String adminName){
        this.adminName = adminName;
        this.projectId = projects.getId();
        this.createTime = projects.getCreatedTime().toLocalDate();
        this.projectName = projects.getName();
        this.isPublic = projects.getPublicity();
        this.members = new ArrayList<>();
        for(Users u: users){
            this.members.add(new MemberInfo(u.getEmail(), u.getNickname()));
        }
    }

    public ProjectData(){

    }
    public ProjectData(String projectName, int projectId, String adminName){
        this.projectName = projectName;
        this.projectId = projectId;
        this.adminName = adminName;
    }
}
