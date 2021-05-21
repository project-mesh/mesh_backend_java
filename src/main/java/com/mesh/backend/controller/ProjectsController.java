package com.mesh.backend.controller;


import com.mesh.backend.datas.BaseData;
import com.mesh.backend.datas.BaseProjectData;
import com.mesh.backend.datas.BaseReturnValue;
import com.mesh.backend.datas.ProjectRequestData;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.DevelopsServiceImpl;
import com.mesh.backend.service.impl.ProjectsServiceImpl;
import com.mesh.backend.service.impl.TeamsServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/project")
public class ProjectsController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TeamsServiceImpl teamsService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private DevelopsServiceImpl developsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createProject(@RequestBody ProjectRequestData requestData){

        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Users admin = usersService.getUserByUsername(requestData.adminName);
        if(admin == null){
            BaseData baseData = new BaseData("Invalid adminName");
            return new BaseReturnValue(301, baseData);
        }

        Teams team = teamsService.getById(requestData.teamId);
        if(team == null){
            BaseData baseData = new BaseData("Team does not exist.");
            return new BaseReturnValue(201, baseData);
        }

        if(!teamsService.checkTeamAdmin(team, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Projects createResult = projectsService.createNewProject(requestData, admin.getId());
        if(createResult == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        ArrayList<Users> usersArrayList = new ArrayList<>();
        usersArrayList.add(admin);
        BaseProjectData projectData = new BaseProjectData(createResult,usersArrayList,admin.getEmail());
        return new BaseReturnValue(0, projectData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object queryProject(@RequestParam String username, int projectId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Projects project = projectsService.getById(projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!developsService.checkProjectMember(projectId, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Users admin = usersService.getById(project.getAdminId());

        List<Users> usersList = projectsService.getProjectMembers(projectId);
        BaseProjectData projectData = new BaseProjectData(project, usersList, admin.getEmail());
        return new BaseReturnValue(0, projectData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteProject(@RequestParam String username, int teamId, int projectId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Projects project = projectsService.getById(projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        projectsService.removeById(projectId);

        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateProject(@RequestBody ProjectRequestData requestData){

        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Projects project = projectsService.getById(requestData.projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }
        boolean updateResult = projectsService.updateProject(project, requestData.isPublic, requestData.projectName);
        if(!updateResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        Users admin = usersService.getById(project.getAdminId());

        List<Users> usersList = projectsService.getProjectMembers(project.getId());
        BaseProjectData projectData = new BaseProjectData(project, usersList,admin.getEmail() );
        return new BaseReturnValue(0, projectData);
    }

    @ResponseBody
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public Object inviteNewProjectMember(@RequestBody ProjectRequestData requestData){

        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Users inviteUser = usersService.getUserByUsername(requestData.inviteName);
        if(inviteUser == null){
            BaseData baseData = new BaseData("Invalid inviteName.");
            return new BaseReturnValue(302, baseData);
        }

        Projects project = projectsService.getById(requestData.projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        boolean inviteResult = developsService.saveNewDevelop(project.getId(), inviteUser.getId());
        if(!inviteResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }
}

