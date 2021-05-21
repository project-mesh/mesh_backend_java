package com.mesh.backend.datas;

import com.mesh.backend.entity.Tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务
 *
 * @author xuedixuedi
 */
public class TaskData {
    public int taskId;
    public String taskName;
    public boolean isFinished;
    public int priority;
    public LocalDate createTime;
    public LocalDate deadline;
    public String description;
    public String founder;
    public String principal;
    public List<SubTaskData> subTasks;

    /**
     * 基本构造函数
     */
    public TaskData(int taskId, String taskName, boolean isFinished, int priority, LocalDateTime createTime,
                    LocalDateTime deadline, String description, String founder, String principal, List<SubTaskData> subTasks) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.isFinished = isFinished;
        this.priority = priority;
        this.createTime = createTime.toLocalDate();
        this.deadline = deadline.toLocalDate();
        this.description = description;
        this.founder = founder;
        this.principal = principal;
        this.subTasks = subTasks;
    }


    /**
     * 从实体构造
     *
     * @param tasks
     * @param founder
     * @param principal
     * @param subTasks
     */
    public TaskData(Tasks tasks, String founder, String principal, List<SubTaskData> subTasks) {
        this.taskId = tasks.getId();
        this.taskName = tasks.getName();
        this.isFinished = tasks.getFinished();
        this.priority = tasks.getPriority();
        this.createTime = tasks.getCreatedTime().toLocalDate();
        this.deadline = tasks.getEndTime().toLocalDate();
        this.description = tasks.getDescription();
        this.founder = founder;
        this.principal = principal;
        this.subTasks = subTasks;
    }
}
