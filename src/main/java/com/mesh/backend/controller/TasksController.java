package com.mesh.backend.controller;


import com.mesh.backend.datas.*;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Tasks;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/task")
public class TasksController {

    @Autowired
    private TasksServiceImpl tasksService;

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private DevelopsServiceImpl developsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public Object queryTeamTasks(@RequestParam String username, int teamId){
        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        if(!cooperationsService.checkTeamMember(teamId, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        ArrayList<TaskData> tasks = tasksService.getTeamTasks(users.getId(), teamId);

        BaseTaskData baseTaskData = new BaseTaskData(tasks);
        return new BaseReturnValue(0 ,baseTaskData);
    }

    @ResponseBody
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public Object queryProjectTasks(@RequestParam String username, int projectId){
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

        ArrayList<TaskData> tasks = tasksService.getProjectTasks(project);
        BaseTaskData baseTaskData = new BaseTaskData(tasks);
        return new BaseReturnValue(0, baseTaskData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createTask(@RequestBody TaskRequestData requestData){
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

        Users principal = usersService.getUserByUsername(requestData.principal);
        if(principal == null){
            BaseData baseData = new BaseData("Invalid principal.");
            return new BaseReturnValue(601, baseData);
        }

        Tasks tasks = tasksService.createTask(requestData,principal.getId());
        if(tasks == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseTaskData baseTaskData = new BaseTaskData(tasks, users.getEmail(), principal.getEmail(), new ArrayList<>());
        return new BaseReturnValue(0, baseTaskData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteTask(@RequestParam String username,int projectId, int taskId){
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
        if(!projectsService.checkProjectAdmin(project, users.getId())
                && !tasksService.checkTaskPrincipal(users.getId(), taskId)){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }
        boolean result = tasksService.removeById(taskId);
        if(!result){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateTask(@RequestBody TaskRequestData requestData){
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
        if(!projectsService.checkProjectAdmin(project, users.getId())
                && !tasksService.checkTaskPrincipal(users.getId(), requestData.taskId)){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Users principal = usersService.getUserByUsername(requestData.principal);
        if(principal == null){
            BaseData baseData = new BaseData("Invalid principal.");
            return new BaseReturnValue(601, baseData);
        }

        Tasks task = tasksService.updateTask(requestData, principal.getId());
        if(task == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        String founder = usersService.getById(project.getAdminId()).getEmail();
        ArrayList<SubTaskData> subTasks = tasksService.getSubtasks(task);
        BaseTaskData baseTaskData = new BaseTaskData(task, founder, principal.getEmail(), subTasks);
        return new BaseReturnValue(0, baseTaskData);
    }

}

