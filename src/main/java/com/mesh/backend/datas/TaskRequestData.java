package com.mesh.backend.datas;

import java.time.LocalDateTime;

/**
 * 任务请求数据
 *
 * @author xuedixuedi
 */
public class TaskRequestData {
    public String username;
    public int projectId;
    public int teamId;
    public int taskId;
    public String taskName;
    public String priority;
    public String deadline;
    public String description;
    public String principal;
    public boolean isFinished;
}
