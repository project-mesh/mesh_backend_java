package com.mesh.backend.service;

import com.mesh.backend.entity.Projectmemocollections;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IProjectmemocollectionsService extends IService<Projectmemocollections> {

    int getCollectionIdByProjectId(int ProjectId);

}
