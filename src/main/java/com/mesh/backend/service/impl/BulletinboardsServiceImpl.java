package com.mesh.backend.service.impl;

import com.mesh.backend.entity.Bulletinboards;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.mapper.BulletinboardsMapper;
import com.mesh.backend.service.IBulletinboardsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BulletinboardsServiceImpl extends ServiceImpl<BulletinboardsMapper, Bulletinboards> implements IBulletinboardsService {

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Override
    public int getBoardIdByProjectId(int projectId) {
        Projects project = projectsService.getById(projectId);
        if(project == null){
            return -1;
        }
        return project.getId();
    }
}
