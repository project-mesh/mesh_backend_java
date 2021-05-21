package com.mesh.backend.service;

import com.mesh.backend.datas.UserData;
import com.mesh.backend.entity.Admins;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IAdminsService extends IService<Admins> {

    ArrayList<UserData> getUserInformationByKeyword(String keyword);

    int getCurrentOnlineUser();

    double getAvgTeamUser();

    double getAvgTeamProject();

    Map<Integer, Integer> getUserAge();

    Map<String, Integer> getUserLocation();

    int getMaleUser();

    int getFemaleUser();

    int getUnknownUser();

    int getCurrentTotalUser();

    ArrayList<Integer> getHistoryTotalUser(int timeInterval, int itemCount);
}
