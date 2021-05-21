package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.BulletinData;
import com.mesh.backend.datas.BulletinRequestData;
import com.mesh.backend.entity.Bulletinboards;
import com.mesh.backend.entity.Bulletins;
import com.mesh.backend.mapper.BulletinsMapper;
import com.mesh.backend.service.IBulletinsService;
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
public class BulletinsServiceImpl extends ServiceImpl<BulletinsMapper, Bulletins> implements IBulletinsService {

    @Autowired
    private BulletinboardsServiceImpl bulletinboardsService;

    @Override
    public Bulletins createBulletin(BulletinRequestData requestData) {
        int boardId = bulletinboardsService.getBoardIdByProjectId(requestData.projectId);
        if(boardId == -1){
            return null;
        }
        Bulletins bulletin = new Bulletins();
        bulletin.setBoardId(boardId);
        bulletin.setTitle(requestData.bulletinName);
        bulletin.setContent(requestData.description);
        boolean result = save(bulletin);
        return result? bulletin : null;
    }

    @Override
    public ArrayList<BulletinData> getAllProjectBulletins(int projectId) {
        int boardId = bulletinboardsService.getBoardIdByProjectId(projectId);
        if(boardId == -1){
            return null;
        }
        QueryWrapper<Bulletins> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Bulletins::getBoardId, boardId);
        List<Bulletins> bulletinsList = list(queryWrapper);
        ArrayList<BulletinData> bulletinData = new ArrayList<>();
        for(Bulletins b : bulletinsList){
            bulletinData.add(new BulletinData(b));
        }
        return bulletinData;
    }

    @Override
    public Bulletins updateProjectBulletin(BulletinRequestData requestData) {
        Bulletins bulletin = getById(requestData.bulletinId);
        if(bulletin == null){
            return null;
        }

        bulletin.setUpdatedTime(LocalDateTime.now());
        bulletin.setTitle(requestData.bulletinName);
        bulletin.setContent(requestData.description);
        boolean result = updateById(bulletin);
        return result? bulletin: null;
    }
}
