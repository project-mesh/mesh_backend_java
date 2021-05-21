package com.mesh.backend.service;

import com.mesh.backend.datas.UserData;
import com.mesh.backend.datas.UserRequestData;
import com.mesh.backend.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IUsersService extends IService<Users> {

    Users getUserByUsername(String username);

    Users saveNewUser(UserData userData);

    boolean updatePreferenceColor(Users user, String color);

    boolean updatePreferenceShowMode(Users user, String showMode);

    boolean updatePreferenceLayout(Users user, String layout);

    Users updateUserInformation(Users user, UserRequestData requestData);

    boolean updateUserPassword(Users user, UserRequestData requestData);

    ArrayList<Users> getUserListByKeyword(String keyword);

}
