package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.ProjectData;
import com.mesh.backend.datas.SubTaskData;
import com.mesh.backend.datas.TaskData;
import com.mesh.backend.datas.TaskRequestData;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Taskboards;
import com.mesh.backend.entity.Tasks;
import com.mesh.backend.entity.Users;
import com.mesh.backend.mapper.TasksMapper;
import com.mesh.backend.service.ITasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements ITasksService {

    @Autowired
    private TaskboardsServiceImpl taskboardsService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private DevelopsServiceImpl developsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private SubtasksServiceImpl subtasksService;

    @Override
    public Tasks createTask(TaskRequestData requestData, int principalId) {
        int boardId = taskboardsService.getTaskBoardIdByProjectId(requestData.projectId);
        if(boardId == -1){
            return null;
        }
        Tasks task = new Tasks();
        task.setBoardId(boardId);
        task.setDescription(requestData.description);
        int priority = 0;
        if(requestData.priority != null) {
            priority = Integer.parseInt(requestData.priority);
        }
        task.setPriority(priority);
        task.setEndTime(LocalDate.parse(requestData.deadline).atStartOfDay());
        task.setName(requestData.taskName);
        task.setLeaderId(principalId);
        return save(task) ? task : null;
    }

    @Override
    public Tasks updateTask(TaskRequestData requestData, int principalId) {
        Tasks task = getById(requestData.taskId);
        if(task == null){
            return null;
        }
        task.setName(requestData.taskName);
        int priority = task.getPriority();
        if(requestData.priority != null) {
            priority = Integer.parseInt(requestData.priority);
        }
        task.setPriority(priority);
        task.setFinished(requestData.isFinished);
        task.setEndTime(LocalDate.parse(requestData.deadline).atStartOfDay());
        task.setDescription(requestData.description);
        task.setUpdatedTime(LocalDateTime.now());
        task.setLeaderId(principalId);
        return updateById(task) ? task : null;
    }

    @Override
    public ArrayList<TaskData> getTeamTasks(int userId, int teamId) {
        List<ProjectData> teamProjects = projectsService.getTeamProjects(teamId);
        ArrayList<Integer> boardIdList = new ArrayList<>();
        for(ProjectData p : teamProjects){
            if(developsService.checkProjectMember(p.projectId, userId)) {
                boardIdList.add(taskboardsService.getTaskBoardIdByProjectId(p.projectId));
            }
        }
        QueryWrapper<Tasks> queryWrapper = new QueryWrapper<>();
        ArrayList<Tasks> teamTasks = new ArrayList<>();
        for(Integer i : boardIdList){
            queryWrapper.lambda().eq(Tasks::getBoardId, i);
            teamTasks.addAll(list(queryWrapper));
        }
        ArrayList<TaskData> taskData = new ArrayList<>();
        for(Tasks t: teamTasks){
            String founder = getFounderByBoardId(t.getBoardId());
            String principal = usersService.getById(t.getLeaderId()).getEmail();
            taskData.add(new TaskData(t,founder ,principal, null));
        }
        return taskData;
    }

    @Override
    public ArrayList<TaskData> getProjectTasks(Projects project) {
        int boardId = taskboardsService.getTaskBoardIdByProjectId(project.getId());
        QueryWrapper<Tasks> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tasks::getBoardId, boardId);
        ArrayList<Tasks> tasks = new ArrayList<>(list(queryWrapper));
        ArrayList<TaskData> taskDataArrayList = new ArrayList<>();
        String founder = usersService.getById(project.getAdminId()).getEmail();
        for(Tasks t: tasks){
            String principal = usersService.getById(t.getLeaderId()).getEmail();
            taskDataArrayList.add(new TaskData(t, founder, principal, getSubtasks(t)));
        }
        return  taskDataArrayList;
    }

    @Override
    public String getFounderByBoardId(int boardId) {
        Taskboards taskboards = taskboardsService.getById(boardId);
        Projects projects = projectsService.getById(taskboards.getProjectId());
        Users user = usersService.getById(projects.getAdminId());
        return user.getEmail();
    }

    @Override
    public boolean checkTaskPrincipal(int userId, int taskId) {
        Tasks task = getById(taskId);
        return (task != null) && (task.getLeaderId() == userId);
    }

    @Override
    public ArrayList<SubTaskData> getSubtasks(Tasks task) {
        return subtasksService.getSubtaskList(task.getId(),getFounderByBoardId(task.getBoardId()));
    }
}
