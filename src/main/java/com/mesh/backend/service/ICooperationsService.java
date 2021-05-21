package com.mesh.backend.service;

import com.mesh.backend.entity.Cooperations;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mesh.backend.entity.Teams;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface ICooperationsService extends IService<Cooperations> {

    boolean saveNewCooperation(int teamId, int userId);

    boolean addAccessCount(int teamId, int userId);

    boolean checkTeamMember(int teamId, int userId);

    boolean quitTeam(int teamId, int userId);

    List<Cooperations> getUserIds(int teamId);

    int getPreferenceTeam(int userId);
}
