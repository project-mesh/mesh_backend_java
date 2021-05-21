package com.mesh.backend.service;

import com.mesh.backend.datas.TeamData;
import com.mesh.backend.datas.TeamRequestData;
import com.mesh.backend.entity.Teams;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mesh.backend.entity.Users;

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
public interface ITeamsService extends IService<Teams> {

    Teams saveNewTeam(TeamRequestData teamRequestData, int adminId);

    List<Users> getTeamMembers(int teamId);

    boolean updateTeam(Teams team, String teamName);

    boolean checkTeamAdmin(Teams teams, int userId);

    ArrayList<TeamData> getUserTeams(int userId);

}
