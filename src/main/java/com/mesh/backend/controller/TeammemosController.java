package com.mesh.backend.controller;


import com.mesh.backend.datas.*;
import com.mesh.backend.entity.Teammemos;
import com.mesh.backend.entity.Teams;
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
@RequestMapping("/api/mesh/knowledgebase/team")
public class TeammemosController {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private TeamsServiceImpl teamsService;

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private TeammemosServiceImpl teammemosService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createTeamKB(@RequestBody KnowledgeRequestData requestData){

        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Teams team = teamsService.getById(requestData.teamId);
        if(team == null){
            BaseData baseData = new BaseData("Team does not exist.");
            return new BaseReturnValue(201, baseData);
        }

        if(!cooperationsService.checkTeamMember(team.getId(), users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Teammemos teammemos = teammemosService.createNewTeamMemo(requestData, users.getId());

        if(teammemos == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseKnowledgeData baseKnowledgeData  = new BaseKnowledgeData(teammemos, users.getEmail());
        return new BaseReturnValue(0, baseKnowledgeData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object queryTeamKB(@RequestParam String username, int teamId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Teams team = teamsService.getById(teamId);
        if(team == null){
            BaseData baseData = new BaseData("Team does not exist.");
            return new BaseReturnValue(201, baseData);
        }

        if(!cooperationsService.checkTeamMember(team.getId(), users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        ArrayList<KnowledgeData> teammemosList = teammemosService.queryTeamMemo(teamId);
        if(teammemosList == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseKnowledgeData baseKnowledgeData = new BaseKnowledgeData(teammemosList);
        return new BaseReturnValue(0, baseKnowledgeData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteTeamKB(@RequestParam String username, int teamId, int knowledgeId){

        Users users = usersService.getUserByUsername(username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Teams team = teamsService.getById(teamId);
        if(team == null){
            BaseData baseData = new BaseData("Team does not exist.");
            return new BaseReturnValue(201, baseData);
        }

        Teammemos teammemos = teammemosService.getById(knowledgeId);
        if(teammemos == null){
            BaseData baseData = new BaseData("Knowledge does not exist.");
            return new BaseReturnValue(401, baseData);
        }

        if(!teamsService.checkTeamAdmin(team, users.getId())&&!users.getId().equals(teammemos.getUserId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        boolean result = teammemosService.removeById(teammemos.getId());
        if(!result){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseData baseData = new BaseData(true, "");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateTeamKB(@RequestBody KnowledgeRequestData requestData){
        Users users = usersService.getUserByUsername(requestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Teams team = teamsService.getById(requestData.teamId);
        if(team == null){
            BaseData baseData = new BaseData("Team does not exist.");
            return new BaseReturnValue(201, baseData);
        }

        Teammemos teammemos = teammemosService.getById(requestData.knowledgeId);
        if(teammemos == null){
            BaseData baseData = new BaseData("Knowledge does not exist.");
            return new BaseReturnValue(401, baseData);
        }

        if(!teamsService.checkTeamAdmin(team, users.getId())&&!users.getId().equals(teammemos.getUserId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Teammemos updatedTeamMemo = teammemosService.updateTeamMemo(requestData);
        if(updatedTeamMemo==null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }
        BaseKnowledgeData baseKnowledgeData = new BaseKnowledgeData(updatedTeamMemo,
                usersService.getById(updatedTeamMemo.getUserId()).getEmail());
        return new BaseReturnValue(0, baseKnowledgeData);
    }
}

