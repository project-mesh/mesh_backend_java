package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mesh.backend.entity.Cooperations;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.mapper.CooperationsMapper;
import com.mesh.backend.service.ICooperationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class CooperationsServiceImpl extends ServiceImpl<CooperationsMapper, Cooperations> implements ICooperationsService {

    private Cooperations getCooperation(int teamId, int userId){
        QueryWrapper<Cooperations> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("TeamId", String.valueOf(teamId));
        map.put("UserId", String.valueOf(userId));
        queryWrapper.allEq(map);
        return getOne(queryWrapper, false);
    }

    @Override
    public boolean saveNewCooperation(int teamId, int userId) {
        Cooperations cooperations = new Cooperations();
        cooperations.setTeamId(teamId);
        cooperations.setUserId(userId);
        boolean saveResult = true;
        try {
            save(cooperations);
        } catch (Exception e) {
            saveResult = false;
        }
        return saveResult;
    }

    @Override
    public boolean addAccessCount(int teamId, int userId) {
        Cooperations cooperations = getCooperation(teamId, userId);
        if(cooperations == null){
            return false;
        }
        cooperations.setAccessCount(cooperations.getAccessCount() + 1);
        UpdateWrapper<Cooperations> updateWrapper = new UpdateWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("TeamId", String.valueOf(teamId));
        map.put("UserId", String.valueOf(userId));
        updateWrapper.allEq(map);
        saveOrUpdate(cooperations, updateWrapper);
        return true;
    }

    @Override
    public boolean checkTeamMember(int teamId, int userId) {
        return getCooperation(teamId, userId) != null;
    }

    @Override
    public List<Cooperations> getUserIds(int teamId) {
        QueryWrapper<Cooperations> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("UserId").eq("TeamId", teamId);
        return list(queryWrapper);
    }

    @Override
    public boolean quitTeam(int teamId, int userId) {
        QueryWrapper<Cooperations> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("TeamId", String.valueOf(teamId));
        map.put("UserId", String.valueOf(userId));
        queryWrapper.allEq(map);
        return remove(queryWrapper);
    }

    @Override
    public int getPreferenceTeam(int userId) {
        QueryWrapper<Cooperations> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("UserId", userId);
        queryWrapper.orderByDesc("AccessCount", "TeamId");
        Cooperations cooperations = getOne(queryWrapper, false);
        return cooperations == null? -1: cooperations.getTeamId();
    }
}
