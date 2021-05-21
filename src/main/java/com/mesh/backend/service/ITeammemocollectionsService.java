package com.mesh.backend.service;

import com.mesh.backend.entity.Teammemocollections;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface ITeammemocollectionsService extends IService<Teammemocollections> {

    int getCollectionIdByTeamId(int teamId);

}
