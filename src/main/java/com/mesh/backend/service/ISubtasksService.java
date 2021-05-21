package com.mesh.backend.service;

import com.mesh.backend.datas.SubTaskData;
import com.mesh.backend.datas.SubTaskRequestData;
import com.mesh.backend.entity.Subtasks;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface ISubtasksService extends IService<Subtasks> {

    Subtasks createSubTask(SubTaskRequestData requestData, int principalId);

    Subtasks updateSubTask(SubTaskRequestData requestData);

    boolean removeSubtask(int taskId, String title);

    ArrayList<SubTaskData> getSubtaskList(int taskId, String founder);

    String getPrincipal(Subtasks subtask);
}
