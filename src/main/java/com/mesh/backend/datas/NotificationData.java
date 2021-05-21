package com.mesh.backend.datas;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 通知基本类
 *
 * @author xuedixuedi
 */
public class NotificationData {
    public int teamId;
    public int projectId;
    public int notificationId;
    public String title;
    public String description;
    public LocalDate createTime;
    public boolean isFinished;

    public NotificationData(int teamId, int projectId, int notificationId, String title, String description, LocalDateTime createTime, boolean isFinished) {
        this.teamId = teamId;
        this.projectId = projectId;
        this.notificationId = notificationId;
        this.title = title;
        this.description = description;
        this.createTime = createTime.toLocalDate();
        this.isFinished = isFinished;
    }

}
