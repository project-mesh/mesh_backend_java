package com.mesh.backend.controller;


import com.mesh.backend.datas.BaseData;
import com.mesh.backend.datas.BaseReturnValue;
import com.mesh.backend.datas.UserData;
import com.mesh.backend.datas.UserRequestData;
import com.mesh.backend.entity.Users;
import com.mesh.backend.service.impl.CooperationsServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/admin")
public class AdminsController {

    @Autowired
    UsersServiceImpl usersService;

    @Autowired
    CooperationsServiceImpl cooperationsService;

    @ResponseBody
    @RequestMapping(value = "/password", method = RequestMethod.PATCH)
    public Object updateUserPasswordAdmin(@RequestBody UserRequestData requestData){
        Users user = usersService.getUserByUsername(requestData.username);
        if(user == null){
            BaseData baseData = new BaseData("User does not exist.");
            return new BaseReturnValue(10, baseData);
        }

        boolean updateResult = usersService.updateUserPassword(user, requestData);
        if(!updateResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(value = "/information", method = RequestMethod.PATCH)
    public Object updateUserInformationAdmin(@RequestBody UserRequestData requestData){
        Users user = usersService.getUserByUsername(requestData.username);
        if(user == null){
            BaseData baseData = new BaseData("User does not exist.");
            return new BaseReturnValue(10, baseData);
        }

        Users updatedUser = usersService.updateUserInformation(user, requestData);
        if(updatedUser == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        UserData userData = new UserData(updatedUser, "user",
                cooperationsService.getPreferenceTeam(updatedUser.getId()));
        return new BaseReturnValue(0, userData);
    }
}

