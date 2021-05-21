package com.mesh.backend.service;

import com.mesh.backend.datas.KnowledgeData;
import com.mesh.backend.datas.KnowledgeRequestData;
import com.mesh.backend.entity.Teammemos;
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
public interface ITeammemosService extends IService<Teammemos> {

    Teammemos createNewTeamMemo(KnowledgeRequestData requestData, int userId);

    List<KnowledgeData> queryTeamMemo(int teamId);

    Teammemos updateTeamMemo(KnowledgeRequestData requestData);
}
