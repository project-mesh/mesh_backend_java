package com.mesh.backend.datas;

import com.mesh.backend.entity.Subtasks;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 子任务
 *
 * @author xuedixuedi
 */
public class SubTaskData {
    public int taskId;
    public String taskName;
    public boolean isFinished;
    public LocalDate createTime;
    public String description;
    public String founder;
    public String principal;

    /**
     * 基础构造函数
     */
    public SubTaskData(int taskId, String taskName, boolean isFinished, LocalDateTime createTime, String description, String founder, String principal) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.isFinished = isFinished;
        this.createTime = createTime.toLocalDate();
        this.description = description;
        this.founder = founder;
        this.principal = principal;
    }

    /**
     * 从实体创建
     *
     * @param subtasks  子任务实体
     * @param founder   创建者
     * @param principal 负责人
     */
    public SubTaskData(Subtasks subtasks, String founder, String principal) {
        this.taskId = subtasks.getTaskId();
        this.taskName = subtasks.getTitle();
        this.isFinished = subtasks.getFinished();
        this.createTime = subtasks.getCreatedTime().toLocalDate();
        this.description = subtasks.getDescription();
        this.founder = founder;
        this.principal = principal;
    }

}
