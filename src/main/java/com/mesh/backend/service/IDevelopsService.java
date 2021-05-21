package com.mesh.backend.service;

import com.mesh.backend.entity.Develops;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public interface IDevelopsService extends IService<Develops> {

    boolean saveNewDevelop(int projectId, int userId);

    boolean checkProjectMember(int projectId, int userId);

    List<Develops> getUserIds(int projectId);
}
