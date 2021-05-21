package com.mesh.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mesh.backend.datas.PasswordData;
import com.mesh.backend.datas.UserData;
import com.mesh.backend.datas.UserRequestData;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.PasswordVerifier;
import com.mesh.backend.mapper.UsersMapper;
import com.mesh.backend.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public Users getUserByUsername(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Users::getEmail, username);
        return getOne(queryWrapper, false);
    }

    @Override
    public Users saveNewUser(UserData userData) {
        Users user = new Users();
        user.setEmail(userData.username);
        PasswordData passwordData = PasswordVerifier.encryption(userData.password);
        user.setPasswordDigest(passwordData.passwordDigest);
        user.setPasswordSalt(passwordData.passwordSalt);
        user.setNickname(userData.username);
        boolean result = save(user);
        return result? user : null;
    }

    @Override
    public boolean updatePreferenceColor(Users user, String color) {
        user.setColorPreference(color);
        return updateById(user);
    }

    @Override
    public boolean updatePreferenceShowMode(Users user, String showMode) {
        user.setRevealedPreference(showMode);
        return updateById(user);
    }

    @Override
    public boolean updatePreferenceLayout(Users user, String layout) {
        user.setLayoutPreference(layout);
        return updateById(user);
    }


    @Override
    public Users updateUserInformation(Users user, UserRequestData requestData) {
        if(requestData.address != null){
            user.setNickname(requestData.address);
        }
        if(requestData.birthday != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(requestData.birthday,formatter);
            LocalDateTime time = date.atStartOfDay();
            user.setBirthday(time);
        }
        if(requestData.gender != null){
            user.setGender(requestData.gender);
        }
        if(requestData.status != null){
            user.setStatus(requestData.status);
        }
        if(requestData.nickname != null){
            user.setNickname(requestData.nickname);
        }
        if(requestData.description != null){
            user.setDescription(requestData.description);
        }
        return updateById(user)? user :null;
    }

    @Override
    public boolean updateUserPassword(Users user, UserRequestData requestData) {
        PasswordData passwordData = PasswordVerifier.encryption(requestData.password);
        user.setPasswordDigest(passwordData.passwordDigest);
        user.setPasswordSalt(passwordData.passwordSalt);
        return updateById(user);
    }

    @Override
    public ArrayList<Users> getUserListByKeyword(String keyword) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("Email", keyword)
                .or().like("Nickname", keyword)
                .or().like("Description", keyword);
        return new ArrayList<>(list(queryWrapper));
    }
}
