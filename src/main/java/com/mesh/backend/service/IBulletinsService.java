package com.mesh.backend.service;

import com.mesh.backend.datas.BulletinData;
import com.mesh.backend.datas.BulletinRequestData;
import com.mesh.backend.entity.Bulletins;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IBulletinsService extends IService<Bulletins> {

    Bulletins createBulletin(BulletinRequestData requestData);

    ArrayList<BulletinData> getAllProjectBulletins(int projectId);

    Bulletins updateProjectBulletin(BulletinRequestData requestData);

}
