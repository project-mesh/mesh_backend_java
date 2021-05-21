package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mesh.backend.datas.SubTaskData;
import com.mesh.backend.datas.SubTaskRequestData;
import com.mesh.backend.entity.Assigns;
import com.mesh.backend.entity.Subtasks;
import com.mesh.backend.entity.Users;
import com.mesh.backend.mapper.SubtasksMapper;
import com.mesh.backend.service.ISubtasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class SubtasksServiceImpl extends ServiceImpl<SubtasksMapper, Subtasks> implements ISubtasksService {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private AssignsServiceImpl assignsService;

    private Map<String, Object> getQueryMap(int taskId, String title){
        Map<String, Object> map = new HashMap<>();
        map.put("Title", title);
        map.put("TaskId", taskId);
        return map;
    }

    @Override
    @Transactional
    public Subtasks createSubTask(SubTaskRequestData requestData, int principalId) {
        Subtasks subtask = new Subtasks();
        subtask.setTitle(requestData.subTaskName);
        subtask.setDescription(requestData.description);
        subtask.setTaskId(requestData.taskId);
        Assigns assigns = new Assigns();
        assigns.setTaskId(requestData.taskId);
        assigns.setTitle(requestData.subTaskName);
        assigns.setUserId(principalId);
        boolean result = save(subtask) && assignsService.save(assigns);
        return result ? subtask : null;
    }

    @Override
    @Transactional
    public Subtasks updateSubTask(SubTaskRequestData requestData) {
        UpdateWrapper<Subtasks> updateWrapper = new UpdateWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("Title", requestData.subTaskName);
        map.put("TaskId", requestData.taskId);
        updateWrapper.allEq(map);
        updateWrapper.set("Description", requestData.description);
        boolean result = update(updateWrapper);
        if(!result){
            return null;
        }
        QueryWrapper<Subtasks> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        return getOne(queryWrapper, false);
    }

    @Override
    public ArrayList<SubTaskData> getSubtaskList(int taskId, String founder) {
        QueryWrapper<Subtasks> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Subtasks::getTaskId, taskId);
        List<Subtasks> subtasksList = list(queryWrapper);
        ArrayList<SubTaskData> subTaskData = new ArrayList<>();
        for(Subtasks s: subtasksList){
            QueryWrapper<Assigns> assignQueryWrapper = new QueryWrapper<>();
            Map<String, Object> map = getQueryMap(taskId, s.getTitle());
            assignQueryWrapper.allEq(map);
            Assigns assigns = assignsService.getOne(assignQueryWrapper, false);
            String principal = usersService.getById(assigns.getUserId()).getEmail();
            subTaskData.add(new SubTaskData(s, founder, principal));
        }
        return subTaskData;
    }

    @Override
    public boolean removeSubtask(int taskId, String title) {
        QueryWrapper<Subtasks> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = getQueryMap(taskId, title);
        queryWrapper.allEq(map);
        return remove(queryWrapper);
    }

    @Override
    public String getPrincipal(Subtasks subtask) {
        QueryWrapper<Assigns> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = getQueryMap(subtask.getTaskId(), subtask.getTitle());
        queryWrapper.allEq(map);
        Assigns assigns = assignsService.getOne(queryWrapper, false);
        if(assigns == null){
            return null;
        }
        return usersService.getById(assigns.getUserId()).getEmail();
    }
}
