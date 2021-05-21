package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.entity.Taskboards;
import com.mesh.backend.mapper.TaskboardsMapper;
import com.mesh.backend.service.ITaskboardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class TaskboardsServiceImpl extends ServiceImpl<TaskboardsMapper, Taskboards> implements ITaskboardsService {

    @Override
    public int getTaskBoardIdByProjectId(int projectId) {
        QueryWrapper<Taskboards> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Taskboards::getProjectId, projectId);
        Taskboards queryResult = getOne(queryWrapper, false);
        return queryResult == null ? -1 : queryResult.getId();
    }
}
