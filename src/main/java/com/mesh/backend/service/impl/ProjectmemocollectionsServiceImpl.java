package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.entity.Projectmemocollections;
import com.mesh.backend.mapper.ProjectmemocollectionsMapper;
import com.mesh.backend.service.IProjectmemocollectionsService;
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
public class ProjectmemocollectionsServiceImpl extends ServiceImpl<ProjectmemocollectionsMapper, Projectmemocollections> implements IProjectmemocollectionsService {

    @Override
    public int getCollectionIdByProjectId(int projectId) {
        QueryWrapper<Projectmemocollections> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Projectmemocollections::getProjectId, projectId);
        Projectmemocollections projectmemocollections =  getOne(queryWrapper, false);
        return projectmemocollections==null? -1:projectmemocollections.getId();
    }
}
