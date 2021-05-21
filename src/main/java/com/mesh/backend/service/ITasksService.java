package com.mesh.backend.service;

import com.mesh.backend.datas.SubTaskData;
import com.mesh.backend.datas.TaskData;
import com.mesh.backend.datas.TaskRequestData;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Tasks;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface ITasksService extends IService<Tasks> {

    Tasks createTask(TaskRequestData requestData, int principalId);

    Tasks updateTask(TaskRequestData requestData, int principalId);

    ArrayList<TaskData> getTeamTasks(int userId, int teamId);

    ArrayList<TaskData> getProjectTasks(Projects project);

    String getFounderByBoardId(int boardId);

    boolean checkTaskPrincipal(int userId, int taskId);

    ArrayList<SubTaskData> getSubtasks(Tasks task);
}
