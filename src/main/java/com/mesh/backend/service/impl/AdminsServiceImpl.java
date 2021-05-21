package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.TeamData;
import com.mesh.backend.datas.UserData;
import com.mesh.backend.entity.Admins;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.mapper.AdminsMapper;
import com.mesh.backend.service.IAdminsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements IAdminsService {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TeamsServiceImpl teamsService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    private int getGenderCount(int gender){
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Users::getGender, gender);
        return usersService.count(queryWrapper);
    }

    private int getAge(LocalDateTime time){
        LocalDate birthday = time.toLocalDate();
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthday.getYear();
        if((now.getMonthValue() > birthday.getMonthValue()) ||
                (now.getMonthValue() == birthday.getMonthValue() && now.getDayOfMonth() > birthday.getDayOfMonth())){
            ++age;
        }
        return age;
    }

    @Override
    public ArrayList<UserData> getUserInformationByKeyword(String keyword) {
        ArrayList<Users> users = usersService.getUserListByKeyword(keyword);
        ArrayList<UserData> userData = new ArrayList<>();
        for(Users u: users){
            ArrayList<TeamData> teamData = teamsService.getUserTeams(u.getId());
            UserData data = new UserData(u, "user",
                    cooperationsService.getPreferenceTeam(u.getId()), teamData);
            userData.add(data);
        }
        return userData;
    }

    @Override
    public int getCurrentOnlineUser() {
        return sessionVerifier.getOnlineUserCount(usersService.count());
    }

    @Override
    public double getAvgTeamUser() {
        return (double) cooperationsService.count() / (double) teamsService.count();
    }

    @Override
    public double getAvgTeamProject() {
        return (double) projectsService.count() / (double) teamsService.count();
    }

    @Override
    public Map<Integer, Integer> getUserAge() {
        List<Users> usersList = usersService.list();
        HashMap<Integer, Integer> userAge = new HashMap<>();
        for(Users user: usersList){
            int age = getAge(user.getBirthday());
            userAge.compute(age, (key, value)-> value == null ? 1: value +1);
        }
        return userAge;
    }

    @Override
    public Map<String, Integer> getUserLocation() {
        List<Users> usersList = usersService.list();
        HashMap<String, Integer> userLocation = new HashMap<>();
        for(Users user: usersList){
            String address = user.getAddress();
            userLocation.compute(address, (key, value)-> value == null ? 1: value + 1);
        }
        return userLocation;
    }

    @Override
    public int getMaleUser() {
        return getGenderCount(1);
    }

    @Override
    public int getFemaleUser() {
        return getGenderCount(2);
    }

    @Override
    public int getUnknownUser() {
        return getGenderCount(0);
    }

    @Override
    public int getCurrentTotalUser() {
        return usersService.count();
    }

    @Override
    public ArrayList<Integer> getHistoryTotalUser(int timeInterval, int itemCount) {
        ArrayList<Integer> historyTotalUser = new ArrayList<>();
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("CreatedTime");
        List<Users> userList = usersService.list(queryWrapper);
        for(int i = 0; i < itemCount; ++i){
            int count = 0;
            LocalDate startTime = LocalDate.now().minusDays((long) (i + 1) * timeInterval);
            LocalDate endTime = LocalDate.now().minusDays((long) i * timeInterval);
            for(Users user: userList){
                LocalDate date = user.getCreatedTime().toLocalDate();
                if(date.isAfter(startTime) && date.isBefore(endTime) || date.isEqual(endTime)){
                    ++count;
                }
            }
            historyTotalUser.add(count);
        }
        return historyTotalUser;
    }
}
