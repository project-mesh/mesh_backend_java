package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.controller.ProjectmemosController;
import com.mesh.backend.datas.ProjectData;
import com.mesh.backend.datas.ProjectRequestData;
import com.mesh.backend.entity.*;
import com.mesh.backend.mapper.ProjectsMapper;
import com.mesh.backend.service.IProjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class ProjectsServiceImpl extends ServiceImpl<ProjectsMapper, Projects> implements IProjectsService {

    @Autowired
    private ProjectmemocollectionsServiceImpl projectmemocollectionsService;

    @Autowired
    private TaskboardsServiceImpl taskboardsService;

    @Autowired
    private BulletinboardsServiceImpl bulletinboardsService;

    @Autowired
    private DevelopsServiceImpl developsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Transactional
    @Override
    public Projects createNewProject(ProjectRequestData requestData, int adminId) {
        Projects project = new Projects();
        project.setAdminId(adminId);
        project.setName(requestData.projectName);
        project.setTeamId(requestData.teamId);
        project.setPublicity(requestData.isPublic);
        if(!save(project)){
            return null;
        }

        Projectmemocollections projectmemocollections = new Projectmemocollections();
        projectmemocollections.setProjectId(project.getId());
        if(!projectmemocollectionsService.save(projectmemocollections)){
            return null;
        }

        Taskboards taskboards = new Taskboards();
        taskboards.setProjectId(project.getId());
        if(!taskboardsService.save(taskboards)){
            return null;
        }

        Bulletinboards bulletinboards = new Bulletinboards();
        bulletinboards.setProjectId(project.getId());
        if(!bulletinboardsService.save(bulletinboards)){
            return null;
        }

        Develops develops = new Develops();
        develops.setProjectId(project.getId());
        develops.setUserId(adminId);
        return developsService.save(develops)? project : null;
    }

    @Override
    public boolean checkProjectAdmin(Projects projects, int userId) {
        return projects.getAdminId().equals(userId);
    }

    @Override
    public List<Users> getProjectMembers(int projectId) {
        ArrayList<Users> users = new ArrayList<>();

        List<Develops> develops = developsService.getUserIds(projectId);
        for(Develops c: develops){
            users.add(usersService.getById(c.getUserId()));
        }
        return users;
    }

    @Override
    public boolean updateProject(Projects project, boolean isPublic, String projectName) {
        project.setPublicity(isPublic);
        project.setName(projectName);
        project.setUpdatedTime(LocalDateTime.now());
        return saveOrUpdate(project);
    }

    @Override
    public List<ProjectData> getTeamProjects(int teamId) {
        QueryWrapper<Projects> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TeamId", teamId);
        List<Projects> projectsArrayList = list(queryWrapper);
        List<ProjectData> projectDataList = new ArrayList<>();
        for(Projects projects: projectsArrayList){
            Users admin = usersService.getById(projects.getAdminId());
            projectDataList.add(new ProjectData(projects.getName(), projects.getId(), admin.getEmail()));
        }
        return projectDataList;
    }
}
