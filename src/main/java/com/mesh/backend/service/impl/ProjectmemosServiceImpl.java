package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.KnowledgeData;
import com.mesh.backend.datas.KnowledgeRequestData;
import com.mesh.backend.entity.Projectmemos;
import com.mesh.backend.entity.Teammemos;
import com.mesh.backend.mapper.ProjectmemosMapper;
import com.mesh.backend.service.IProjectmemosService;
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
public class ProjectmemosServiceImpl extends ServiceImpl<ProjectmemosMapper, Projectmemos> implements IProjectmemosService {

    @Autowired
    private ProjectmemocollectionsServiceImpl proejctmemocollectionsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Override
    public Projectmemos createNewProjectMemo(KnowledgeRequestData requestData, int userId) {
        Projectmemos projectmemos = new Projectmemos();
        projectmemos.setText(requestData.hyperlink);
        projectmemos.setTitle(requestData.knowledgeName);
        projectmemos.setUserId(userId);
        projectmemos.setCollectionId(proejctmemocollectionsService.getCollectionIdByProjectId(requestData.projectId));
        return save(projectmemos)? projectmemos:null;
    }

    @Override
    public ArrayList<KnowledgeData> queryProjectMemo(int projectId) {
        int collectionId = proejctmemocollectionsService.getCollectionIdByProjectId(projectId);
        QueryWrapper<Projectmemos> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Projectmemos::getCollectionId, collectionId);
        List<Projectmemos> projectmemosList = list(queryWrapper);
        if(projectmemosList == null){
            return null;
        }
        ArrayList<KnowledgeData> knowledgeData = new ArrayList<>();
        for(Projectmemos t:projectmemosList){
            knowledgeData.add(new KnowledgeData(t,usersService.getById(t.getUserId()).getEmail()));
        }
        return knowledgeData;
    }

    @Override
    public Projectmemos updateProjectMemo(KnowledgeRequestData requestData) {
        Projectmemos projectmemo = getById(requestData.knowledgeId);
        projectmemo.setTitle(requestData.knowledgeName);
        projectmemo.setText(requestData.hyperlink);
        projectmemo.setUpdatedTime(LocalDateTime.now());
        return updateById(projectmemo)? projectmemo:null;
    }
}
