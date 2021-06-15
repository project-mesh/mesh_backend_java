package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.TeamData;
import com.mesh.backend.datas.TeamRequestData;
import com.mesh.backend.entity.Cooperations;
import com.mesh.backend.entity.Teammemocollections;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.entity.Users;
import com.mesh.backend.mapper.TeamsMapper;
import com.mesh.backend.service.ITeamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class TeamsServiceImpl extends ServiceImpl<TeamsMapper, Teams> implements ITeamsService {

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TeammemocollectionsServiceImpl teammemocollectionsService;

    @Override
    @Transactional
    public Teams saveNewTeam(TeamRequestData teamRequestData, int adminId) {
        Teams teams = new Teams();
        teams.setAdminId(adminId);
        teams.setName(teamRequestData.teamName);
        if(!save(teams)){
            return null;
        }

        Teammemocollections teammemocollections = new Teammemocollections();
        teammemocollections.setTeamId(teams.getId());
        if(!teammemocollectionsService.save(teammemocollections)){
            return null;
        }

        Cooperations cooperations = new Cooperations();
        cooperations.setAccessCount(0);
        cooperations.setTeamId(teams.getId());
        cooperations.setUserId(adminId);
        return cooperationsService.save(cooperations)? teams:null;
    }

    @Override
    public List<Users> getTeamMembers(int teamId) {
        ArrayList<Users> users = new ArrayList<>();

        List<Cooperations> cooperations = cooperationsService.getUserIds(teamId);
        for(Cooperations c: cooperations){
            users.add(usersService.getById(c.getUserId()));
        }
        return users;
    }

    @Override
    public boolean updateTeam(Teams team, String teamName) {
        team.setName(teamName);
        team.setUpdatedTime(LocalDateTime.now());
        return saveOrUpdate(team);
    }

    @Override
    public boolean checkTeamAdmin(Teams teams, int userId) {
        return teams.getAdminId().equals(userId);
    }

    @Override
    public ArrayList<TeamData> getUserTeams(int userId) {
        QueryWrapper<Cooperations> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Cooperations::getUserId, userId);
        ArrayList<Cooperations> cooperations = new ArrayList<>(cooperationsService.list(queryWrapper));
        ArrayList<TeamData> teamData = new ArrayList<>();
        for(Cooperations c: cooperations){
            Teams t = getById(c.getTeamId());
            String adminName = usersService.getById(c.getUserId()).getEmail();
            teamData.add(new TeamData(t, adminName));
        }
        return teamData;
    }
}
