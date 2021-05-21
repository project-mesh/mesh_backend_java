package com.mesh.backend.datas;

import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Users;

import java.util.List;

public class BaseProjectData extends BaseData{
    public ProjectData project;
    public BaseProjectData(Projects projects, List<Users> users, String adminName){
        super(true, "");
        project = new ProjectData(projects, users, adminName);
    }
}
