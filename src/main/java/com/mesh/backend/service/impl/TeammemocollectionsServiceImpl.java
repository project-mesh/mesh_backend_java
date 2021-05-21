package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.entity.Teammemocollections;
import com.mesh.backend.entity.Teammemos;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.mapper.TeammemocollectionsMapper;
import com.mesh.backend.service.ITeammemocollectionsService;
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
public class TeammemocollectionsServiceImpl extends ServiceImpl<TeammemocollectionsMapper, Teammemocollections> implements ITeammemocollectionsService {

    @Override
    public int getCollectionIdByTeamId(int teamId) {
        QueryWrapper<Teammemocollections> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Teammemocollections::getTeamId, teamId);
        Teammemocollections teammemocollections =  getOne(queryWrapper, false);
        return teammemocollections==null? -1:teammemocollections.getId();
    }
}
