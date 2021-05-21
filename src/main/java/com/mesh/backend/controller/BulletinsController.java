package com.mesh.backend.controller;


import com.mesh.backend.datas.*;
import com.mesh.backend.entity.Bulletins;
import com.mesh.backend.entity.Projects;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/bulletin")
public class BulletinsController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @Autowired
    private BulletinsServiceImpl bulletinsService;

    @Autowired
    private DevelopsServiceImpl developsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createBulletin(@RequestBody BulletinRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Projects project = projectsService.getById(requestData.projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Bulletins bulletins = bulletinsService.createBulletin(requestData);
        if(bulletins == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseBulletinData bulletinData = new BaseBulletinData(bulletins);
        return new BaseReturnValue(0, bulletinData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object queryBulletin(@RequestParam String username, int projectId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Projects project = projectsService.getById(projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!developsService.checkProjectMember(projectId, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        ArrayList<BulletinData> bulletinDataList = bulletinsService.getAllProjectBulletins(projectId);
        if(bulletinDataList == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseBulletinData baseBulletinData = new BaseBulletinData(bulletinDataList);
        return new BaseReturnValue(0, baseBulletinData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteBulletin(@RequestParam String username, int projectId, int bulletinId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Projects project = projectsService.getById(projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        boolean result = bulletinsService.removeById(bulletinId);
        if(!result){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateBulletin(@RequestBody BulletinRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }
        Projects project = projectsService.getById(requestData.projectId);
        if(project == null){
            BaseData baseData = new BaseData("Project does not exist.");
            return new BaseReturnValue(301, baseData);
        }
        if(!projectsService.checkProjectAdmin(project, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Bulletins bulletin = bulletinsService.updateProjectBulletin(requestData);
        if(bulletin == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseBulletinData baseBulletinData = new BaseBulletinData(bulletin);
        return new BaseReturnValue(0, baseBulletinData);
    }
}

