package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.entity.Cooperations;
import com.mesh.backend.entity.Develops;
import com.mesh.backend.mapper.DevelopsMapper;
import com.mesh.backend.service.IDevelopsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DevelopsServiceImpl extends ServiceImpl<DevelopsMapper, Develops> implements IDevelopsService {

    private Develops getDevelop(int projectId, int userId){
        QueryWrapper<Develops> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("ProjectId", String.valueOf(projectId));
        map.put("UserId", String.valueOf(userId));
        queryWrapper.allEq(map);
        return getOne(queryWrapper, false);
    }

    @Override
    public boolean checkProjectMember(int projectId, int userId) {
        return getDevelop(projectId, userId) != null;
    }

    @Override
    public List<Develops> getUserIds(int projectId) {
        QueryWrapper<Develops> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("UserId").eq("ProjectId", projectId);
        return list(queryWrapper);
    }

    @Override
    public boolean saveNewDevelop(int projectId, int userId) {
        Develops develop = new Develops();
        develop.setProjectId(projectId);
        develop.setUserId(userId);
        boolean saveResult = true;
        try {
            save(develop);
        } catch (Exception e) {
            saveResult = false;
        }
        return saveResult;
    }
}
