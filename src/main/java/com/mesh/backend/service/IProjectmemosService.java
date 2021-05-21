package com.mesh.backend.service;

import com.mesh.backend.datas.KnowledgeData;
import com.mesh.backend.datas.KnowledgeRequestData;
import com.mesh.backend.entity.Projectmemos;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IProjectmemosService extends IService<Projectmemos> {
    Projectmemos createNewProjectMemo(KnowledgeRequestData requestData, int userId);

    List<KnowledgeData> queryProjectMemo(int projectId);

    Projectmemos updateProjectMemo(KnowledgeRequestData requestData);
}
