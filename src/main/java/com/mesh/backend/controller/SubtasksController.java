package com.mesh.backend.controller;


import com.mesh.backend.datas.BaseData;
import com.mesh.backend.datas.BaseReturnValue;
import com.mesh.backend.datas.BaseSubTaskData;
import com.mesh.backend.datas.SubTaskRequestData;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Subtasks;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.ProjectsServiceImpl;
import com.mesh.backend.service.impl.SubtasksServiceImpl;
import com.mesh.backend.service.impl.TasksServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/subtask")
public class SubtasksController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private SubtasksServiceImpl subtasksService;

    @Autowired
    private TasksServiceImpl tasksService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createSubtask(@RequestBody SubTaskRequestData requestData){
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

        Subtasks subtask = subtasksService.createSubTask(requestData, principal.getId());
        if(subtask == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        String founder = usersService.getById(project.getAdminId()).getEmail();
        BaseSubTaskData baseSubTaskData = new BaseSubTaskData(subtask, founder, principal.getEmail());
        return new BaseReturnValue(0, baseSubTaskData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteSubtask(@RequestParam String username, int projectId, int taskId, String subTaskName){
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

        boolean result = subtasksService.removeSubtask(taskId, subTaskName);
        if(!result){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateSubtask(@RequestBody SubTaskRequestData requestData){
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

        Subtasks subtask = subtasksService.updateSubTask(requestData);
        if(subtask == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        String founder = usersService.getById(project.getAdminId()).getEmail();
        BaseSubTaskData baseSubTaskData = new BaseSubTaskData(subtask, founder, subtasksService.getPrincipal(subtask));
        return new BaseReturnValue(0, baseSubTaskData);
    }
}

