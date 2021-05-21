package com.mesh.backend.service;

import com.mesh.backend.datas.ProjectData;
import com.mesh.backend.datas.ProjectRequestData;
import com.mesh.backend.entity.Projects;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mesh.backend.entity.Users;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IProjectsService extends IService<Projects> {

    @Transactional
    Projects createNewProject(ProjectRequestData requestData, int adminId);

    boolean checkProjectAdmin(Projects projects, int userId);

    List<Users> getProjectMembers(int projectId);

    boolean updateProject(Projects project, boolean isPublic, String projectName);

    List<ProjectData> getTeamProjects(int teamId);

}
