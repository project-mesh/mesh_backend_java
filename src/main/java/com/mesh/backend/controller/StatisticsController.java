package com.mesh.backend.controller;

import com.mesh.backend.datas.BaseData;
import com.mesh.backend.datas.BaseReturnValue;
import com.mesh.backend.datas.BaseStatisticsData;
import com.mesh.backend.datas.BaseUserQueryData;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.AdminsServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/api/mesh/statistics")
public class StatisticsController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private AdminsServiceImpl adminsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(value = "search-user", method = RequestMethod.GET)
    public Object queryUserInfo(@RequestParam String username, String keyword){
        BaseUserQueryData baseUserQueryData = new BaseUserQueryData(true,
                adminsService.getUserInformationByKeyword(keyword));
        return new BaseReturnValue(0, baseUserQueryData);
    }

    @ResponseBody
    @RequestMapping(value = "totaluser", method = RequestMethod.GET)
    public Object queryTotalUser(@RequestParam int timeInterval, int itemCount){
        if(timeInterval <= 0){
            BaseData baseData = new BaseData("Invalid timeInterval.");
            return new BaseReturnValue(457, baseData);
        }

        int currentTotalUser = adminsService.getCurrentTotalUser();
        ArrayList<Integer> historyTotalUser = adminsService.getHistoryTotalUser(timeInterval, itemCount);
        BaseStatisticsData baseStatisticsData = new BaseStatisticsData(currentTotalUser, historyTotalUser);
        return new BaseReturnValue(0, baseStatisticsData);
    }

    @ResponseBody
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public Object queryUserStatistics(){
        int maleUser = adminsService.getMaleUser();
        int femaleUser = adminsService.getFemaleUser();
        int unknownUser = adminsService.getUnknownUser();
        Map<Integer, Integer> userAge = adminsService.getUserAge();
        Map<String, Integer> userLocation = adminsService.getUserLocation();
        BaseStatisticsData baseStatisticsData = new BaseStatisticsData(maleUser, femaleUser,
                unknownUser, userAge, userLocation);
        return new BaseReturnValue(0, baseStatisticsData);
    }

    @ResponseBody
    @RequestMapping(value = "general", method = RequestMethod.GET)
    public Object queryGeneralStatistics(){
        int currentOnlineUser = adminsService.getCurrentOnlineUser();
        double avgTeamUser = adminsService.getAvgTeamUser();
        double avgTeamProject = adminsService.getAvgTeamProject();
        BaseStatisticsData baseStatisticsData = new BaseStatisticsData(currentOnlineUser, avgTeamUser, avgTeamProject);
        return new BaseReturnValue(0, baseStatisticsData);
    }

}
