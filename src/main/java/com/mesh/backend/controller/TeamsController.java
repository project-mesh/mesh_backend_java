package com.mesh.backend.controller;


import com.mesh.backend.datas.*;
import com.mesh.backend.entity.Teams;
import com.mesh.backend.entity.Users;
import com.mesh.backend.helper.SessionVerifier;
import com.mesh.backend.service.impl.CooperationsServiceImpl;
import com.mesh.backend.service.impl.ProjectsServiceImpl;
import com.mesh.backend.service.impl.TeamsServiceImpl;
import com.mesh.backend.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
@Controller
@RequestMapping("/api/mesh/team")
public class TeamsController {

    @Autowired
    private TeamsServiceImpl teamsService;

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private CooperationsServiceImpl cooperationsService;

    @Autowired
    private SessionVerifier sessionVerifier;

    @Autowired
    private ProjectsServiceImpl projectsService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object createTeam(@RequestBody TeamRequestData teamRequestData){

        Users users = usersService.getUserByUsername(teamRequestData.username);
        if(!sessionVerifier.verify(users)){
            BaseData baseData = new BaseData("User status error.");
            return new BaseReturnValue(2, baseData);
        }

        Teams result = teamsService.saveNewTeam(teamRequestData, users.getId());
        if(result == null){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        ArrayList<Users> usersArrayList = new ArrayList<>();
        usersArrayList.add(users);
        BaseTeamData baseTeamData = new BaseTeamData(result, usersArrayList, users.getEmail());
        return new BaseReturnValue(0, baseTeamData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Object queryTeam(@RequestParam String username, int teamId){

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

        Users admin = usersService.getById(team.getAdminId());

        cooperationsService.addAccessCount(teamId, users.getId());
        List<Users> usersList = teamsService.getTeamMembers(teamId);
        List<ProjectData> projectDataList = projectsService.getTeamProjects(teamId);
        BaseTeamData baseTeamData = new BaseTeamData(team, usersList, admin.getEmail(), projectDataList);
        return new BaseReturnValue(0, baseTeamData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public Object updateTeam(@RequestBody TeamRequestData requestData){

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

        if(!teamsService.checkTeamAdmin(team, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }


        boolean updateResult = teamsService.updateTeam(team, requestData.teamName);
        if(!updateResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        List<Users> usersList = teamsService.getTeamMembers(requestData.teamId);
        List<ProjectData> projectDataList = projectsService.getTeamProjects(requestData.teamId);
        BaseTeamData baseTeamData = new BaseTeamData(team, usersList, users.getEmail(), projectDataList);
        return new BaseReturnValue(0, baseTeamData);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public Object deleteTeam(@RequestParam String username, int teamId){

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

        if(!teamsService.checkTeamAdmin(team, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        boolean deleteResult = teamsService.removeById(teamId);
        if(!deleteResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true,"");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(value = "/quit", method = RequestMethod.DELETE)
    public Object quitTeam(@RequestParam String username, int teamId){

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

        if(team.getAdminId().equals(users.getId())){
            teamsService.removeById(teamId);
        }else{
            cooperationsService.quitTeam(teamId, users.getId());
        }
        BaseData baseData = new BaseData(true,"");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public Object inviteNewTeamMember(@RequestBody TeamRequestData requestData){

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

        Users inviteUser = usersService.getUserByUsername(requestData.inviteName);
        if(inviteUser == null){
            BaseData baseData = new BaseData("Invalid inviteName.");
            return new BaseReturnValue(202, baseData);
        }

        if(!teamsService.checkTeamAdmin(team, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        boolean inviteResult = cooperationsService.saveNewCooperation(team.getId(), inviteUser.getId());
        if(!inviteResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true,"");
        return new BaseReturnValue(0, baseData);
    }

    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Object removeTeamMember(@RequestParam String username, int teamId, String removeName){

        if(username.equals(removeName)){
            BaseData baseData = new BaseData("Invalid removeName.");
            return new BaseReturnValue(202, baseData);
        }

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

        if(!teamsService.checkTeamAdmin(team, users.getId())){
            BaseData baseData = new BaseData("Permission denied.");
            return new BaseReturnValue(901, baseData);
        }

        Users removeUser = usersService.getUserByUsername(removeName);
        if(removeUser == null){
            BaseData baseData = new BaseData("Invalid removeName.");
            return new BaseReturnValue(202, baseData);
        }

        if(!cooperationsService.checkTeamMember(team.getId(), removeUser.getId())){
            BaseData baseData = new BaseData("User is not in the team.");
            return new BaseReturnValue(203, baseData);
        }

        boolean removeResult = cooperationsService.quitTeam(teamId, removeUser.getId());
        if(!removeResult){
            BaseData baseData = new BaseData("Unexpected error.");
            return new BaseReturnValue(1, baseData);
        }

        BaseData baseData = new BaseData(true,"");
        return new BaseReturnValue(0, baseData);
    }
}

