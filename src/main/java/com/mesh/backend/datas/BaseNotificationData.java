package com.mesh.backend.datas;

import java.time.LocalDateTime;

public class BaseNotificationData extends BaseData {
    public NotificationData notification;

    public BaseNotificationData(int teamId, int projectId, int notificationId, String title, String description, LocalDateTime createTime, boolean isFinished) {
        super(true, "");
        notification = new NotificationData(teamId, projectId, notificationId, title, description, createTime, isFinished);
    }
}
