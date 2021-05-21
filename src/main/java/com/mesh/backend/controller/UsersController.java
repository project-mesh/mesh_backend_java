package com.mesh.backend.controller;


import com.mesh.backend.datas.*;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.AdminCheckHelper;
import com.mesh.backend.helper.PasswordVerifier;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.CooperationsServiceImpl;
import com.mesh.backend.service.impl.TeamsServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh")
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private TeamsServiceImpl teamsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody UserData userData){
        Users user = usersService.getUserByUsername(userData.username);
        if(user != null){
            BaseData data = new BaseData("User already exists.");
            return new BaseReturnValue(101, data);
        }
        Users result = usersService.saveNewUser(userData);
        if(result != null){
            return new BaseReturnValue(0, new UserData(result, "user", -1));
        }else{
            BaseData data = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, data);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest request, @RequestBody UserData userData){
        if(AdminCheckHelper.checkIfAdmin(userData)){
            UserData data = new UserData();
            data.username = "root";
            data.nickname = "root";
            data.role = "admin";
            return new BaseReturnValue(0, data);
        }
        Users user = usersService.getUserByUsername(userData.username);
        if(user == null){
            BaseData data = new BaseData("Invalid username or password.");
            return new BaseReturnValue(102, data);
        }

        boolean loginResult = PasswordVerifier.verify(userData.password,
                new PasswordData(user.getPasswordDigest(), user.getPasswordSalt()));
        if(!loginResult){
            BaseData data = new BaseData("Invalid username or password.");
            return new BaseReturnValue(102, data);
        }

        sessionVerifier.createSession(user);

        ArrayList<TeamData> teamData = teamsService.getUserTeams(user.getId());
        UserData data = new UserData(user, "user",
                cooperationsService.getPreferenceTeam(user.getId()), teamData);
        return new BaseReturnValue(0, data);
    }

    @ResponseBody
    @RequestMapping(value = "/preference/color", method = RequestMethod.POST)
    public Object preferenceColor(@RequestBody UserRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        boolean result = usersService.updatePreferenceColor(users, requestData.preferenceColor);
        if(!result){
            BaseData data = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, data);
        }
        BaseData data = new BaseData(true,"");
        return new BaseReturnValue(0, data);
    }

    @ResponseBody
    @RequestMapping(value = "/preference/layout", method = RequestMethod.POST)
    public Object preferenceLayout(@RequestBody UserRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        boolean result = usersService.updatePreferenceLayout(users, requestData.preferenceLayout);
        if(!result){
            BaseData data = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, data);
        }
        BaseData data = new BaseData(true,"");
        return new BaseReturnValue(0, data);
    }

    @ResponseBody
    @RequestMapping(value = "/preference/show-mode", method = RequestMethod.POST)
    public Object preferenceShowMode(@RequestBody UserRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        boolean result = usersService.updatePreferenceShowMode(users, requestData.preferenceShowMode);
        if(!result){
            BaseData data = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, data);
        }
        BaseData data = new BaseData(true,"");
        return new BaseReturnValue(0, data);
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    public Object updateUserInformation(@RequestBody UserRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Users updatedUser = usersService.updateUserInformation(users, requestData);
        if(updatedUser == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        UserData userData = new UserData(updatedUser, "user",
                cooperationsService.getPreferenceTeam(users.getId()));
        return new BaseReturnValue(0, userData);
    }

    @ResponseBody
    @RequestMapping(value = "/user/password", method = RequestMethod.PATCH)
    public Object updatePassword(@RequestBody UserRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        boolean verifyResult =  PasswordVerifier.verify(requestData.oldPassword,
                new PasswordData(users.getPasswordDigest(), users.getPasswordSalt()));
        if(!verifyResult){
            BaseData baseData = new BaseData("Invalid oldPassword.");
            return new BaseReturnValue(110, baseData);
        }
        boolean updateResult = usersService.updateUserPassword(users, requestData);
        if(!updateResult){
            BaseData baseData =new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object queryUser(@RequestParam String username, String keyword){
        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        ArrayList<Users> usersArrayList = usersService.getUserListByKeyword(keyword);
        BaseUserQueryData baseUserQueryData = new BaseUserQueryData(usersArrayList);
        return new BaseReturnValue(0, baseUserQueryData);
    }
}

