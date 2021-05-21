package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.KnowledgeData;
import com.mesh.backend.datas.KnowledgeRequestData;
import com.mesh.backend.entity.Teammemos;
import com.mesh.backend.mapper.TeammemosMapper;
import com.mesh.backend.service.ITeammemosService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TeammemosServiceImpl extends ServiceImpl<TeammemosMapper, Teammemos> implements ITeammemosService {

    @Autowired
    private TeammemocollectionsServiceImpl teammemocollectionsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Override
    public Teammemos createNewTeamMemo(KnowledgeRequestData requestData, int userId) {
        Teammemos teammemos = new Teammemos();
        teammemos.setText(requestData.hyperlink);
        teammemos.setTitle(requestData.knowledgeName);
        teammemos.setUserId(userId);
        teammemos.setCollectionId(teammemocollectionsService.getCollectionIdByTeamId(requestData.teamId));
        return save(teammemos)? teammemos:null;
    }

    @Override
    public ArrayList<KnowledgeData> queryTeamMemo(int teamId) {
        int collectionId = teammemocollectionsService.getCollectionIdByTeamId(teamId);
        QueryWrapper<Teammemos> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Teammemos::getCollectionId, collectionId);
        List<Teammemos> teammemosList = list(queryWrapper);
        if(teammemosList == null){
            return null;
        }
        ArrayList<KnowledgeData> knowledgeData = new ArrayList<>();
        for(Teammemos t:teammemosList){
            knowledgeData.add(new KnowledgeData(t,usersService.getById(t.getUserId()).getEmail()));
        }
        return knowledgeData;
    }

    @Override
    public Teammemos updateTeamMemo(KnowledgeRequestData requestData) {
        Teammemos teammemo = getById(requestData.knowledgeId);
        teammemo.setTitle(requestData.knowledgeName);
        teammemo.setText(requestData.hyperlink);
        teammemo.setUpdatedTime(LocalDateTime.now());
        return updateById(teammemo)? teammemo:null;
    }
}
